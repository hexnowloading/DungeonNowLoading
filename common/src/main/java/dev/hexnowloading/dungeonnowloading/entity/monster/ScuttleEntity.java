package dev.hexnowloading.dungeonnowloading.entity.monster;

import dev.hexnowloading.dungeonnowloading.entity.ai.ScuttleFlameThrowerAttackGoal;
import dev.hexnowloading.dungeonnowloading.entity.ai.SlumberingEntityLookAtPlayerGoal;
import dev.hexnowloading.dungeonnowloading.entity.ai.SlumberingEntityPlayerTargetGoal;
import dev.hexnowloading.dungeonnowloading.entity.ai.SlumberingEntityRandomStrollGoal;
import dev.hexnowloading.dungeonnowloading.entity.projectile.FlameProjectileEntity;
import dev.hexnowloading.dungeonnowloading.entity.util.SlumberingEntity;
import dev.hexnowloading.dungeonnowloading.registry.DNLTags;
import dev.hexnowloading.dungeonnowloading.entity.util.EntityStates;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class ScuttleEntity extends Monster implements Enemy, SlumberingEntity {

    private static final EntityDataAccessor<ScuttleState> STATE = SynchedEntityData.defineId(ScuttleEntity.class, EntityStates.SCUTTLE_STATE);
    private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(ScuttleEntity.class, EntityDataSerializers.INT);
    private int aiTick = 0;

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
        this.xpReward = 20;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 115.0)
                .add(Attributes.ATTACK_DAMAGE, 15.0)
                .add(Attributes.ATTACK_KNOCKBACK, 1.25)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.FOLLOW_RANGE, 16.0);
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
        this.entityData.define(ATTACK_TICK, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("AttackTicks", this.entityData.get(ATTACK_TICK));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(ATTACK_TICK, compoundTag.getInt("AttackTicks"));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            return;
        }
        if ((this.isState(ScuttleState.OPENING) || this.isState(ScuttleState.OPENED)) && this.getAttackTick() < 100) {
            this.setAttackTick(this.getAttackTick() + 1);
        } else if (this.getAttackTick() > 0) {
            this.setAttackTick(this.getAttackTick() - 1);
        }
    }

    @Override
    protected void customServerAiStep() {
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
        this.setState(ScuttleState.SLUMBERING);
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return 1.45F;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float damage) {
        if (isState(ScuttleState.SLUMBERING)) {
            this.setState(ScuttleState.AWAKENING);
            this.triggerWakingUpAnimation();
            this.aiTick = 72;
        }
        if (damageSource.is(DNLTags.SCUTTLE_HURTABLE) || this.isAttackingState() || damageSource.isCreativePlayer()) {
            return super.hurt(damageSource, damage);
        }
        return false;
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

    public void setAttackTick(int tick) { this.entityData.set(ATTACK_TICK, tick); }
    public int getAttackTick() { return this.entityData.get(ATTACK_TICK); }
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
