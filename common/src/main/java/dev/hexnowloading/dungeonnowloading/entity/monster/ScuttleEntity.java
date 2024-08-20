package dev.hexnowloading.dungeonnowloading.entity.monster;

import dev.hexnowloading.dungeonnowloading.entity.ai.*;
import dev.hexnowloading.dungeonnowloading.entity.projectile.FlameProjectileEntity;
import dev.hexnowloading.dungeonnowloading.entity.util.SlumberingEntity;
import dev.hexnowloading.dungeonnowloading.registry.DNLEntityTypes;
import dev.hexnowloading.dungeonnowloading.registry.DNLParticleTypes;
import dev.hexnowloading.dungeonnowloading.registry.DNLSounds;
import dev.hexnowloading.dungeonnowloading.registry.DNLTags;
import dev.hexnowloading.dungeonnowloading.entity.util.EntityStates;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ScuttleEntity extends Monster implements Enemy, SlumberingEntity {

    private static final EntityDataAccessor<ScuttleState> STATE = SynchedEntityData.defineId(ScuttleEntity.class, EntityStates.SCUTTLE_STATE);
    private int aiTick = 0;
    private int renderOldTick;
    private boolean renderHeating;
    private boolean renderCooling;
    private float oldAgeInTicks;
    private boolean rotateStarted;

    private int flamePosOldTick;
    private boolean mouthOpeningStarted;
    private boolean mouthClosingStarted;

    public final AnimationState wakingUpAnimationState = new AnimationState();
    public final AnimationState mouthOpenAnimationState = new AnimationState();
    public final AnimationState mouthCloseAnimationState = new AnimationState();
    public final AnimationState idleClosedAnimationState = new AnimationState();
    public final AnimationState idleOpenedAnimationState = new AnimationState();
    public final AnimationState blockFormAnimationState = new AnimationState();

    private static final byte TRIGGER_WAKING_UP_ANIMATION_BYTE = 70;
    private static final byte TRIGGER_MOUTH_OPEN_ANIMATION_BYTE = 71;
    private static final byte TRIGGER_MOUTH_CLOSE_ANIMATION_BYTE = 72;
    private static final byte TRIGGER_IDLE_CLOSED_ANIMATION_BYTE = 73;
    private static final byte TRIGGER_IDLE_OPENED_ANIMATION_BYTE = 74;
    private static final byte TRIGGER_BLOCK_FORM_ANIMATION_BYTE = 75;
    private static final byte TRIGGER_ANIMATION_STOP_BYTE = 76;

    public ScuttleEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setState(ScuttleState.SLUMBERING);
        this.xpReward = 20;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 50.0)
                .add(Attributes.ATTACK_DAMAGE, 15.0)
                .add(Attributes.ATTACK_KNOCKBACK, 1.25)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.FOLLOW_RANGE, 32.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new ScuttleFlameThrowerAttackGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(3, new MoveTowardsRestrictionGoal(this, 1.0));
        this.goalSelector.addGoal(4, new SlumberingEntityRandomStrollGoal(this, 0.5));
        this.goalSelector.addGoal(5, new SlumberingEntityLookAtPlayerGoal(this, Player.class, 6.0F));
        this.targetSelector.addGoal(2, new SlumberingEntityPlayerTargetGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STATE, ScuttleState.SLUMBERING);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Slumbering", isSlumbering());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        boolean isSlumbering = compoundTag.getBoolean("Slumbering");
        this.entityData.set(STATE, isSlumbering ? ScuttleState.SLUMBERING : ScuttleState.CLOSED);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            return;
        }
        double LOWER_FLAME_HEIGHT = 2.3D;
        double HIGHER_FLAME_HEIGHT = 3.0D;
        float timeInSeconds;
        float time;
        double height;
        switch (this.getState()) {
            case OPENING:
                if (!this.isMouthOpeningStarted()) {
                    this.setFlamePosOldTick(this.tickCount);
                    this.setMouthOpeningStarted(true);
                }
                timeInSeconds = ((float) this.tickCount - (float) this.getFlamePosOldTick()) / 20.0F - 0.38F;
                time = timeInSeconds > 0.0F ? timeInSeconds / (0.58F - 0.38F) : 0.0F;
                height = time < 1.0F ? LOWER_FLAME_HEIGHT + (HIGHER_FLAME_HEIGHT - LOWER_FLAME_HEIGHT) * time : HIGHER_FLAME_HEIGHT;
                this.animateParticles(0.03F, height, 0.1D);
                break;
            case OPENED:
                this.setMouthOpeningStarted(false);
                this.animateParticles(0.2F, HIGHER_FLAME_HEIGHT, 0.7D);
                this.level().addParticle(DNLParticleTypes.LARGE_FLAME_PARTICLE.get(), this.getX() + (this.getRandom().nextFloat() - 0.5D) * 0.3D, this.getY() + HIGHER_FLAME_HEIGHT + 0.3D + (this.getRandom().nextFloat() - 0.5D) * 0.3D, this.getZ() + (this.getRandom().nextFloat() - 0.5D) * 0.3D, 0.0, 0.0, 0.0);
                break;
            case CLOSING:
                if (!this.isMouthClosingStarted()) {
                    this.setFlamePosOldTick(this.tickCount);
                    this.setMouthClosingStarted(true);
                }
                timeInSeconds = ((float) this.tickCount - (float) this.getFlamePosOldTick()) / 20.0F - 0.38F;
                time = timeInSeconds > 0.0F ? timeInSeconds / (0.46F - 0.38F) : 0.0F;
                height = time < 1.0F ? HIGHER_FLAME_HEIGHT - (HIGHER_FLAME_HEIGHT - LOWER_FLAME_HEIGHT) * time : LOWER_FLAME_HEIGHT;
                this.animateParticles(0.03F, height, 0.1D);
                break;
            default:
                this.setMouthClosingStarted(false);
                this.animateParticles(0.03F, LOWER_FLAME_HEIGHT, 0.1D);
                break;
        }
    }

    private void animateParticles(float chance, double height, double spread) {
        this.level().addParticle(DNLParticleTypes.LARGE_FLAME_PARTICLE.get(), this.getX() + (this.getRandom().nextFloat() - 0.5D) * spread, this.getY() + height + (this.getRandom().nextFloat() - 0.5D) * 0.3D, this.getZ() + (this.getRandom().nextFloat() - 0.5D) * spread, 0.0, 0.0, 0.0);
        if (this.getRandom().nextFloat() < chance) {
            this.level().addParticle(ParticleTypes.LAVA, this.getX() + (this.getRandom().nextFloat() - 0.5D) * 0.3D, this.getY() + height + (this.getRandom().nextFloat() - 0.5D) * 0.3D, this.getZ() + (this.getRandom().nextFloat() - 0.5D) * 0.3D, 0.0, 0.0, 0.0);
            this.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (this.getRandom().nextFloat() - 0.5D) * 0.3D, this.getY() + height + (this.getRandom().nextFloat() - 0.5D) * 0.3D, this.getZ() + (this.getRandom().nextFloat() - 0.5D) * 0.3D, 0.0, 0.02, 0.0);
        }
    }

    @Override
    protected void customServerAiStep() {
        if (this.isSlumbering()) {
            this.setState(ScuttleState.AWAKENING);
            this.triggerWakingUpAnimation();
            this.playSound(DNLSounds.SCUTTLE_WAKING.get());
            this.aiTick = 72;
        }
        if (this.isState(ScuttleState.AWAKENING)) {
            if (aiTick > 0) {
                aiTick--;
            } else {
                this.setState(ScuttleState.CLOSED);
                this.triggerIdleClosedAnimation();
            }
        }
        super.customServerAiStep();
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        //this.setState(ScuttleState.SLUMBERING);
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return 1.45F;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float damage) {
        if (damageSource.getDirectEntity() instanceof FlameProjectileEntity) {
            return false;
        }
        if (damageSource.is(DNLTags.SCUTTLE_HURTABLE) || this.isAttackingState() || damageSource.isCreativePlayer()) {
            this.playDeflectSound();
            return super.hurt(damageSource, damage);
        }
        return false;
    }

    @Override
    public void push(Entity entity) {
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    protected boolean updateInWaterStateAndDoFluidPushing() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isSlumbering();
    }

    @Override
    public void handleEntityEvent(byte b) {
        switch (b) {
            case TRIGGER_WAKING_UP_ANIMATION_BYTE:
                this.wakingUpAnimationState.start(this.tickCount);
                break;
            case TRIGGER_MOUTH_OPEN_ANIMATION_BYTE:
                this.mouthOpenAnimationState.start(this.tickCount);
                break;
            case TRIGGER_MOUTH_CLOSE_ANIMATION_BYTE:
                this.idleOpenedAnimationState.stop();
                this.mouthCloseAnimationState.start(this.tickCount);
                break;
            case TRIGGER_IDLE_CLOSED_ANIMATION_BYTE:
                this.idleClosedAnimationState.start(this.tickCount);
                break;
            case TRIGGER_IDLE_OPENED_ANIMATION_BYTE:
                this.mouthOpenAnimationState.stop();
                this.idleClosedAnimationState.stop();
                this.idleOpenedAnimationState.start(this.tickCount);
                break;
            case TRIGGER_BLOCK_FORM_ANIMATION_BYTE:
                this.blockFormAnimationState.start(this.tickCount);
                break;
            case TRIGGER_ANIMATION_STOP_BYTE:
                this.resetAnimations();
                break;
            default:
                super.handleEntityEvent(b);
        }
    }

    private void resetAnimations() {
        this.wakingUpAnimationState.stop();
        this.mouthOpenAnimationState.stop();
        this.mouthCloseAnimationState.stop();
        this.idleOpenedAnimationState.stop();
        this.idleClosedAnimationState.stop();
        this.blockFormAnimationState.stop();
    }

    public void playDeflectSound() {
        this.playSound(DNLSounds.SCUTTLE_DEFLECT.get(), 1.0F, 1.0F);
    }

    public void playShootingOpenSound() {
        this.playSound(DNLSounds.SCUTTLE_SHOOTING_OPEN.get(), 1.5F, 1.0F);
    }

    public void playShootingChargeSound() {
        this.playSound(DNLSounds.SCUTTLE_SHOOTING_CHARGE.get(), 1.5F, 1.0F);
    }

    public void playShootingBurstSound() {
        this.playSound(DNLSounds.SCUTTLE_SHOOTING_BURST.get(), 1.5F, 1.0F);
    }

    public void playShootingFlameSound() {
        this.playSound(DNLSounds.SCUTTLE_SHOOTING_FLAME.get(), 1.5F, 1.0F);
    }

    public void playShootingStopSound() {
        this.playSound(DNLSounds.SCUTTLE_SHOOTING_STOP.get(), 1.5F, 1.0F);
    }

    public void playShootingCloseSound() {
        this.playSound(DNLSounds.SCUTTLE_SHOOTING_CLOSE.get(), 1.5F, 1.0F);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return DNLSounds.SCUTTLE_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return DNLSounds.SCUTTLE_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return DNLSounds.SCUTTLE_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(DNLSounds.SCUTTLE_STEP.get(), 0.5F, 1.0F);
    }

    public void setRenderOldTick(int tick) {this.renderOldTick = tick; }
    public int getRenderOldTick() { return this.renderOldTick; }
    public void setRenderHeating(boolean b) { this.renderHeating = b; }
    public boolean isRenderHeating() { return this.renderHeating; }
    public void setRenderCooling(boolean b) { this.renderCooling = b; }
    public boolean isRenderCooling() { return this.renderCooling; }
    public void setOldAgeInTicks(float tick) {this.oldAgeInTicks = tick; }
    public float getOldAgeInTicks() { return this.oldAgeInTicks; }
    public void setRotateStarted(boolean b) { this.rotateStarted = b; }
    public boolean isRotateStarted() { return this.rotateStarted; }
    public int getFlamePosOldTick() { return flamePosOldTick; }
    public void setFlamePosOldTick(int flamePosOldTick) { this.flamePosOldTick = flamePosOldTick; }
    public boolean isMouthOpeningStarted() { return mouthOpeningStarted; }
    public void setMouthOpeningStarted(boolean mouthOpeningStarted) { this.mouthOpeningStarted = mouthOpeningStarted; }
    public boolean isMouthClosingStarted() { return mouthClosingStarted; }
    public void setMouthClosingStarted(boolean mouthClosingStarted) { this.mouthClosingStarted = mouthClosingStarted; }

    public void setState(ScuttleState scuttleState) { this.entityData.set(STATE, scuttleState); }
    public ScuttleState getState() { return this.entityData.get(STATE); }
    public boolean isState(ScuttleState scuttleState) { return this.getState().equals(scuttleState); }
    public boolean isAttackingState() { return this.getState().equals(ScuttleState.OPENING) || this.getState().equals(ScuttleState.OPENED) || this.getState().equals(ScuttleState.CLOSING); }

    public void triggerWakingUpAnimation() { this.level().broadcastEntityEvent(this, TRIGGER_WAKING_UP_ANIMATION_BYTE); }
    public void triggerMouthOpenAnimation() { this.level().broadcastEntityEvent(this, TRIGGER_MOUTH_OPEN_ANIMATION_BYTE); }
    public void triggerMouthCloseAnimation() { this.level().broadcastEntityEvent(this, TRIGGER_MOUTH_CLOSE_ANIMATION_BYTE); }
    public void triggerIdleOpenedAnimation() { this.level().broadcastEntityEvent(this, TRIGGER_IDLE_OPENED_ANIMATION_BYTE); }
    public void triggerIdleClosedAnimation() { this.level().broadcastEntityEvent(this, TRIGGER_IDLE_CLOSED_ANIMATION_BYTE); }
    public void triggerBlockFormAnimation() { this.level().broadcastEntityEvent(this, TRIGGER_BLOCK_FORM_ANIMATION_BYTE); }
    public void triggerAnimationStop() { this.level().broadcastEntityEvent(this, TRIGGER_ANIMATION_STOP_BYTE); }

    @Override
    public boolean isStationary() { return this.getState().equals(ScuttleState.SLUMBERING) || this.getState().equals(ScuttleState.AWAKENING) || this.getState().equals(ScuttleState.OPENING) || this.getState().equals(ScuttleState.OPENED) || this.getState().equals(ScuttleState.CLOSING); }

    @Override
    public boolean isSlumbering() {
        return isState(ScuttleState.SLUMBERING);
    }

    public enum ScuttleState {
        SLUMBERING,
        AWAKENING,
        CLOSED,
        OPENING,
        OPENED,
        CLOSING;

        private ScuttleState() {}
    }
}
