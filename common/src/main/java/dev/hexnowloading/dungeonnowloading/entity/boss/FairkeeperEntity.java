package dev.hexnowloading.dungeonnowloading.entity.boss;

import dev.hexnowloading.dungeonnowloading.entity.ai.BossResetGoal;
import dev.hexnowloading.dungeonnowloading.entity.ai.BossTargetSelectorGoal;
import dev.hexnowloading.dungeonnowloading.entity.ai.FairkeeperAwakenGoal;
import dev.hexnowloading.dungeonnowloading.entity.ai.FairkeeperFlightGoal;
import dev.hexnowloading.dungeonnowloading.entity.ai.control.FairkeeperFlyingMoveControl;
import dev.hexnowloading.dungeonnowloading.entity.ai.control.NoClipMoveControl;
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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class FairkeeperEntity extends Monster implements Boss, Enemy, SlumberingEntity {

    private static final EntityDataAccessor<FairkeeperState> STATE = SynchedEntityData.defineId(FairkeeperEntity.class, EntityStates.FAIRKEEPER_STATE);
    private static final EntityDataAccessor<BlockPos> SPAWN_POINT = SynchedEntityData.defineId(FairkeeperEntity.class, EntityDataSerializers.BLOCK_POS);

    private int aiTick = 0;
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
                .add(Attributes.MOVEMENT_SPEED, 0.7)
                .add(Attributes.FLYING_SPEED, 0.7)
                .add(Attributes.FOLLOW_RANGE, 30.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new BossResetGoal(this, this.getFollowDistance()));
        this.goalSelector.addGoal(2, new FairkeeperAwakenGoal(this, 2.0F, 0.0F));
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
        compoundTag.putInt("AttackTicks", this.getAttackTick());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(SPAWN_POINT, new BlockPos(compoundTag.getList("SpawnPoint", CompoundTag.TAG_INT).getInt(0), compoundTag.getList("SpawnPoint", CompoundTag.TAG_INT).getInt(1), compoundTag.getList("SpawnPoint", CompoundTag.TAG_INT).getInt(2)));
        this.entityData.set(STATE, compoundTag.getBoolean("Slumbering") ? FairkeeperState.SLUMBERING : FairkeeperState.IDLE);
        this.attackTick = compoundTag.getInt("AttackTicks");
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
        if (this.getState().equals(FairkeeperState.AWAKENING)) this.enableBossBar();
        if (this.getState().equals(FairkeeperState.IDLE)) this.abilitySelectionTick();
        super.customServerAiStep();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    private void abilitySelectionTick() {
        if (this.getTarget() == null) return;

    }

    public void stopAttacking(int cooldown) {
        this.setState(FairkeeperState.IDLE);
        this.setAttackTick(cooldown);

    }

    @Override
    public void targetRandomPlayer() {
        this.setState(FairkeeperState.TARGET);
    }

    @Override
    public boolean playerTargetingCondition() {
        return this.getState().equals(FairkeeperState.TARGET);
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

    public void enableBossBar() { this.bossEvent.setVisible(true); }
    public void disableBossBar() { this.bossEvent.setVisible(false); }
    public int getAttackTick() { return this.attackTick; }
    public void setAttackTick(int i) { this.attackTick = i; }
    public double getFollowDistance() { return this.getAttributeValue(Attributes.FOLLOW_RANGE); }
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
        DYING;

        private FairkeeperState() {}
    }
}
