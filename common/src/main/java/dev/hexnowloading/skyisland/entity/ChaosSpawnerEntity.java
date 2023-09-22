package dev.hexnowloading.skyisland.entity;

import dev.hexnowloading.skyisland.entity.util.EntityStates;
import dev.hexnowloading.skyisland.registry.SkyislandEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.SpawnUtil;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class ChaosSpawnerEntity extends Monster {
    private static final EntityDataAccessor<Boolean> DATA_FLAGS_ID = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<State> DATA_STATE;
    private static final EntityDataAccessor<BlockPos> SPAWN_POINT = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.BLOCK_POS);
    private static final EntityDataAccessor<Integer> AWAKENING_TICKS = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityDataSerializers.INT);

    public final AnimationState awakeningAnimationState = new AnimationState();
    public final AnimationState sleepingAnimationState = new AnimationState();
    private final ServerBossEvent bossEvent;

    public ChaosSpawnerEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setPersistenceRequired();
        this.bossEvent = (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        this.xpReward = 500;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new ChaosSpawnerResetAI(this, 10.0D));
        this.goalSelector.addGoal(2, new ChaosSpawnerSummonMobAttackAI(this, 1));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SPAWN_POINT, BlockPos.ZERO);
        this.entityData.define(DATA_STATE, State.NONE);
        this.entityData.define(PHASE, 0);
        this.entityData.define(DATA_FLAGS_ID, false);
        this.entityData.define(AWAKENING_TICKS, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("SpawnPointX", this.getSpawnPointPos().getX());
        compoundTag.putInt("SpawnPointY", this.getSpawnPointPos().getY());
        compoundTag.putInt("SpawnPointZ", this.getSpawnPointPos().getZ());
        compoundTag.putInt("Phase", this.entityData.get(PHASE));
        compoundTag.putInt("AwakeningTicks", this.entityData.get(AWAKENING_TICKS));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        int i = compoundTag.getInt("SpawnPointX");
        int j = compoundTag.getInt("SpawnPointY");
        int k = compoundTag.getInt("SpawnPointZ");
        this.entityData.set(SPAWN_POINT, new BlockPos(i, j, k));
        this.entityData.set(AWAKENING_TICKS, compoundTag.getInt("AwakeningTicks"));
        int phase = compoundTag.getInt("Phase");
        if (phase < 1) {
            this.entityData.set(DATA_STATE, State.SLEEPING);
            this.entityData.set(PHASE, 0);
        } else {
            this.entityData.set(PHASE, phase);
        }
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket clientboundAddEntityPacket) {
        super.recreateFromPacket(clientboundAddEntityPacket);
        this.yBodyRot = 0.0F;
        this.yBodyRotO = 0.0F;
    }

    protected BodyRotationControl createBodyControl() {
        return new ChaosSpawnerBodyRotationControl(this);
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.bossEvent.setName(this.getDisplayName());
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    // Called when entity starts rendering
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
        if (this.entityData.get(PHASE) == 0) {
            disableBossBar();
        }
    }

    @Override
    // Called when entity stops rendering
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    @Override
    protected void customServerAiStep() {
        if (this.entityData.get(AWAKENING_TICKS) > 0) {
            int k1 = this.entityData.get(AWAKENING_TICKS) - 1;
            if (k1 <= 0) {
                this.entityData.set(PHASE, 1);
                this.entityData.set(DATA_STATE, State.IDLE);
            }
            this.entityData.set(AWAKENING_TICKS, k1);
            if (k1 == 0) {
                enableBossBar();
            }
        }
        super.customServerAiStep();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_STATE.equals(entityDataAccessor)) {
            State state = this.entityData.get(DATA_STATE);
            resetAnimations();
            switch (state) {
                case SLEEPING:
                    this.sleepingAnimationState.start(this.tickCount);
                    break;
                case AWAKENING:
                    this.sleepingAnimationState.stop();
                    this.awakeningAnimationState.start(this.tickCount);
                    break;
            }
        }

        super.onSyncedDataUpdated(entityDataAccessor);
    }

    private void resetAnimations() {
        this.sleepingAnimationState.stop();
        this.awakeningAnimationState.stop();
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, SpawnGroupData spawnGroupData, CompoundTag compoundTag) {
        this.entityData.set(SPAWN_POINT, this.blockPosition());
        this.entityData.set(DATA_STATE, State.SLEEPING);
        this.setYRot(0.0F);
        this.setXRot(0.0F);
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        if (this.isAlive() && this.entityData.get(PHASE) < 1) {
            this.entityData.set(AWAKENING_TICKS, 100);
            this.entityData.set(DATA_STATE, State.AWAKENING);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return super.mobInteract(player, interactionHand);
        }
    }

    @Override
    public boolean hurt(DamageSource damageSource, float damage) {
        if (!damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && !damageSource.isCreativePlayer() && (this.entityData.get(DATA_STATE) == State.SLEEPING || this.entityData.get(DATA_STATE) == State.AWAKENING)) {
            return false;
        }
        return super.hurt(damageSource, damage);
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    @Override
    public void push(Entity entity) {
    }

    @Override
    public Vec3 getDeltaMovement() {
        return Vec3.ZERO;
    }

    @Override
    public void setDeltaMovement(Vec3 vec3) {
    }

    public boolean isAwakening() { return this.entityData.get(DATA_STATE) == State.AWAKENING; }

    public int getAwakeningTick() { return this.entityData.get(AWAKENING_TICKS); }

    public int getPhase() { return this.entityData.get(PHASE); }

    private void enableBossBar() { this.bossEvent.setVisible(true); }

    private void disableBossBar() { this.bossEvent.setVisible(false); }

    static {
        DATA_STATE = SynchedEntityData.defineId(ChaosSpawnerEntity.class, EntityStates.CHAOS_SPAWNER_STATE);
    }

    public enum State {
        NONE,
        SLEEPING,
        AWAKENING,
        IDLE,

        SUMMONING,
        SKULL_MISSILE;

        private State() {}
    }

    BlockPos getSpawnPointPos() {
        return (BlockPos)this.entityData.get(SPAWN_POINT);
    }

    public class ChaosSpawnerResetAI extends Goal {
        private final double range;
        ChaosSpawnerEntity livingEntity;
        Level level;

        public ChaosSpawnerResetAI(ChaosSpawnerEntity livingEntity, double range) {
            this.livingEntity = livingEntity;
            this.range = 10.0D;
            this.level = livingEntity.level();
        }

        @Override
        public boolean canUse() {
            if (this.livingEntity.entityData.get(DATA_STATE) != State.SLEEPING) {
                AABB aabb = (new AABB(this.livingEntity.blockPosition())).inflate(range);
                List<Player> list = level.getEntitiesOfClass(Player.class, aabb);
                return list.isEmpty();
            }
            return false;
        }

        public void start() {
            for (int i = 0; i < 50; ++i) {
                ((ServerLevel) this.level).sendParticles(ParticleTypes.POOF, this.livingEntity.getRandomX(1.0D), this.livingEntity.getRandomY(), this.livingEntity.getRandomZ(1.0D), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
            disableBossBar();
            this.livingEntity.entityData.set(DATA_STATE, State.SLEEPING);
            this.livingEntity.entityData.set(AWAKENING_TICKS, 0);
            this.livingEntity.entityData.set(PHASE, 0);
            this.livingEntity.setHealth(this.livingEntity.getMaxHealth());
            this.livingEntity.setPos(getSpawnPointPos().getX() + 0.5, getSpawnPointPos().getY(), getSpawnPointPos().getZ() + 0.5);
        }
    }

    public class ChaosSpawnerSummonMobAttackAI extends Goal {
        ChaosSpawnerEntity chaosSpawnerEntity;
        private int attackTime;
        private int phase;

        public ChaosSpawnerSummonMobAttackAI(ChaosSpawnerEntity chaosSpawnerEntity, int phase) {
            this.chaosSpawnerEntity = chaosSpawnerEntity;
            this.phase = phase;
        }

        @Override
        public boolean canUse() {
            return this.chaosSpawnerEntity.entityData.get(PHASE) == phase;
        }

        public void start() {
            this.attackTime = 100;

        }

        public void stop() {
        }

        public void tick() {
            --this.attackTime;
            ServerLevel serverLevel = (ServerLevel) this.chaosSpawnerEntity.level();
            if (attackTime % 20 == 0) {

                SpawnUtil.trySpawnMob(EntityType.ZOMBIE, MobSpawnType.SPAWNER, serverLevel, this.chaosSpawnerEntity.blockPosition(), 20, 3, 3, SpawnUtil.Strategy.ON_TOP_OF_COLLIDER).isPresent();
            }
        }
    }

    static class ChaosSpawnerBodyRotationControl extends BodyRotationControl {
        public ChaosSpawnerBodyRotationControl(Mob mob) {
            super(mob);
        }

        public void clientTick() {
        }
    }
}
