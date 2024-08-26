package dev.hexnowloading.dungeonnowloading.entity.boss;

import dev.hexnowloading.dungeonnowloading.entity.ai.FairkeeperAwakenGoal;
import dev.hexnowloading.dungeonnowloading.entity.ai.FairkeeperFlightGoal;
import dev.hexnowloading.dungeonnowloading.entity.ai.control.FairkeeperFlyingMoveControl;
import dev.hexnowloading.dungeonnowloading.entity.ai.control.NoClipMoveControl;
import dev.hexnowloading.dungeonnowloading.entity.util.EntityStates;
import dev.hexnowloading.dungeonnowloading.entity.util.SlumberingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
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
import org.jetbrains.annotations.Nullable;

public class FairkeeperEntity extends Monster implements Enemy, SlumberingEntity {

    private static final EntityDataAccessor<FairkeeperState> STATE = SynchedEntityData.defineId(FairkeeperEntity.class, EntityStates.FAIRKEEPER_STATE);

    private int aiTick = 0;

    public FairkeeperEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FairkeeperFlyingMoveControl(this);
        this.setNoGravity(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 300.0)
                .add(Attributes.ATTACK_DAMAGE, 20.0)
                .add(Attributes.ATTACK_KNOCKBACK, 1.5)
                .add(Attributes.MOVEMENT_SPEED, 10.0)
                .add(Attributes.FLYING_SPEED, 10.0)
                .add(Attributes.FOLLOW_RANGE, 30.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FairkeeperFlightGoal(this, 100.0F));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STATE, FairkeeperState.SLUMBERING);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Slumbering", isSlumbering());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(STATE, isSlumbering() ? FairkeeperState.SLUMBERING : FairkeeperState.IDLE);
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
        /*if (this.isSlumbering()) {
            this.aiTick = 30;
        }
        if (this.isState(FairkeeperState.AWAKENING)) {
            if (this.aiTick > 0) {
                this.aiTick--;
            } else {
                System.out.println("Hi");
                this.setState(FairkeeperState.IDLE);
                double dx = this.getX();
                double dy = this.getY() + 8.0;
                double dz = this.getZ();
                this.getMoveControl().setWantedPosition(dx, dy, dz, 1.0);
            }
        }
        super.customServerAiStep();*/
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
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
        DYING;

        private FairkeeperState() {}
    }
}
