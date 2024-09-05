package dev.hexnowloading.dungeonnowloading.entity.boss;

import dev.hexnowloading.dungeonnowloading.entity.ai.*;
import dev.hexnowloading.dungeonnowloading.entity.ai.control.FairkeeperFlyingMoveControl;
import dev.hexnowloading.dungeonnowloading.entity.util.Boss;
import dev.hexnowloading.dungeonnowloading.entity.util.EntityStates;
import dev.hexnowloading.dungeonnowloading.entity.util.SlumberingEntity;
import dev.hexnowloading.dungeonnowloading.util.NbtHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class FairkeeperEntity extends Monster implements Boss, Enemy, SlumberingEntity {

    private static final EntityDataAccessor<FairkeeperState> STATE = SynchedEntityData.defineId(FairkeeperEntity.class, EntityStates.FAIRKEEPER_STATE);
    private static final EntityDataAccessor<BlockPos> SPAWN_POINT = SynchedEntityData.defineId(FairkeeperEntity.class, EntityDataSerializers.BLOCK_POS);

    private int attackTick;

    private final ServerBossEvent bossEvent;

    public FairkeeperEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FairkeeperFlyingMoveControl(this);
        this.setNoGravity(true);
        this.bossEvent = (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 300.0)
                .add(Attributes.ATTACK_DAMAGE, 20.0)
                .add(Attributes.ATTACK_KNOCKBACK, 1.5)
                .add(Attributes.MOVEMENT_SPEED, 0.0)
                .add(Attributes.FLYING_SPEED, 2.0)
                .add(Attributes.FOLLOW_RANGE, 30.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new BossResetGoal(this, this.getFollowDistance()));
        this.goalSelector.addGoal(2, new FairkeeperAwakenGoal(this, this.getFlySpeed(), 0.0F, 0.9999f));
        this.goalSelector.addGoal(3, new FairkeeperStrafeGoal(this, FairkeeperState.STRAFE, (int) this.getFollowDistance(), 6, 20, 15, 10, 5, 2, this.getFlySpeed(), 0.0, 0.9, 10, FairkeeperStrafeGoal.StrafeReference.SELF_EXCEPT_Y, true));
        this.goalSelector.addGoal(3, new FairkeeperGroundSmashGoal(this, FairkeeperState.GROUND_SMASH, 10, this.getFlySpeed(), 0.0, 0.9, 0.95f, 1.2, 12.0, 0.2, true, 0.55f, 40));
        this.goalSelector.addGoal(3, new FairkeeperOverheatLaneGoal(this, FairkeeperState.OVERHEAT_LANE, 20.0d, this.getFlySpeed(), 0.0, 0.99f, 20, 10, 5, 4, 4, 6));
        this.goalSelector.addGoal(3, new FairkeeperStonePillarGoal(this, FairkeeperState.STONE_PILLAR, 20.0d, 1.0f, 0.0, 0.9999f, 60, 5, 4, 4, 7, 5, 2, 0.1f));
        this.targetSelector.addGoal(2, new BossTargetSelectorGoal(this, this.getFollowDistance()));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STATE, FairkeeperState.SLUMBERING);
        this.entityData.define(SPAWN_POINT, BlockPos.ZERO);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.put("SpawnPoint", NbtHelper.newIntList(this.getSpawnPoint().getX(), this.getSpawnPoint().getY(), this.getSpawnPoint().getZ()));
        compoundTag.putBoolean("Slumbering", isSlumbering());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(SPAWN_POINT, new BlockPos(compoundTag.getList("SpawnPoint", CompoundTag.TAG_INT).getInt(0), compoundTag.getList("SpawnPoint", CompoundTag.TAG_INT).getInt(1), compoundTag.getList("SpawnPoint", CompoundTag.TAG_INT).getInt(2)));
        this.entityData.set(STATE, compoundTag.getBoolean("Slumbering") ? FairkeeperState.SLUMBERING : FairkeeperState.IDLE);
        if (this.hasCustomName()) this.bossEvent.setName(this.getDisplayName());
    }

    @Override
    public void setCustomName(@Nullable Component component) {
        super.setCustomName(component);
        this.bossEvent.setName(this.getDisplayName());
    }

    @Override
    public void startSeenByPlayer(ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        this.bossEvent.addPlayer(serverPlayer);
        if (this.isSlumbering()) this.disableBossBar();
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        this.bossEvent.removePlayer(serverPlayer);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation flyingPathNavigation = new FlyingPathNavigation(this, level);
        flyingPathNavigation.setCanFloat(false);
        return flyingPathNavigation;
    }

    public void startBossFight() {
        this.entityData.set(STATE, FairkeeperState.AWAKENING);
    }

    @Override
    protected void customServerAiStep() {
        //System.out.println(this.getState());
        if (this.isState(FairkeeperState.AWAKENING)) this.enableBossBar();
        if (this.isState(FairkeeperState.IDLE)) this.abilitySelectionTick();
        super.customServerAiStep();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    private void abilitySelectionTick() {
        if (this.attackTick > 0) {
            --this.attackTick;
            return;
        }

        this.targetRandomPlayer();

        if (this.getTarget() == null) return;

        this.setState(FairkeeperState.STONE_PILLAR);
    }

    public void stopAttacking(int cooldown) {
        this.setState(FairkeeperState.IDLE);
        this.setTarget(null);
        this.setAttackTick(cooldown);
    }

    @Override
    public void targetRandomPlayer() {
        this.setState(FairkeeperState.TARGET);
    }

    @Override
    public boolean playerTargetingCondition() {
        return this.isState(FairkeeperState.TARGET);
    }

    @Override
    public void postPlayerTargeting() {
        this.setState(FairkeeperState.IDLE);
    }

    @Override
    public BlockPos resetRegionCenter() {
        return this.getSpawnPoint();
    }

    @Override
    public boolean resetCondition() {
        return !this.isSlumbering();
    }

    @Override
    public void resetBoss() {
        this.setHealth(this.getMaxHealth());
        this.disableBossBar();
        this.setDeltaMovement(Vec3.ZERO);
        ((FairkeeperFlyingMoveControl) this.moveControl).setWaitOperation();
        this.setPos(this.getSpawnPoint().getX() + 0.5, this.getSpawnPoint().getY(), this.getSpawnPoint().getZ() + 0.5);
        this.setState(FairkeeperState.SLUMBERING);
        this.setTarget(null);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        this.setSpawnPoint(this.blockPosition());
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
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
    public boolean isInWall() {
        return super.isInWall();
    }

    @Override
    protected void checkFallDamage(double $$0, boolean $$1, BlockState $$2, BlockPos $$3) {
    }

    @Override
    protected int calculateFallDamage(float $$0, float $$1) {
        return 0;
    }

    @Override
    public boolean causeFallDamage(float v, float v1, DamageSource damageSource) {
        return false;
    }

    public void enableBossBar() { this.bossEvent.setVisible(true); }
    public void disableBossBar() { this.bossEvent.setVisible(false); }
    public int getAttackTick() { return this.attackTick; }
    public void setAttackTick(int i) { this.attackTick = i; }
    public double getAttackDamage() { return this.getAttributeValue(Attributes.ATTACK_DAMAGE); }
    public double getFollowDistance() { return this.getAttributeValue(Attributes.FOLLOW_RANGE); }
    public double getFlySpeed() { return this.getAttributeValue(Attributes.FLYING_SPEED); }
    public void setSpawnPoint(BlockPos blockPos) { this.entityData.set(SPAWN_POINT, blockPos); }
    public BlockPos getSpawnPoint() { return this.entityData.get(SPAWN_POINT); }
    public void setState(FairkeeperState fairkeeperState) { this.entityData.set(STATE, fairkeeperState); }
    public FairkeeperState getState() { return this.entityData.get(STATE); }
    public boolean isState(FairkeeperState fairkeeperState) { return this.getState().equals(fairkeeperState); }

    @Override
    public boolean isStationary() {
        return isSlumbering();
    }

    @Override
    public boolean isSlumbering() {
        return isState(FairkeeperState.SLUMBERING);
    }

    public enum FairkeeperState {
        SLUMBERING,
        AWAKENING,
        IDLE,
        TARGET,
        STRAFE,
        GROUND_SMASH,
        OVERHEAT_LANE,
        STONE_PILLAR,
        DYING;

        private FairkeeperState() {}
    }
}
