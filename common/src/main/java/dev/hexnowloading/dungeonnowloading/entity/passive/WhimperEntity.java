package dev.hexnowloading.dungeonnowloading.entity.passive;

import dev.hexnowloading.dungeonnowloading.config.PvpConfig;
import dev.hexnowloading.dungeonnowloading.entity.ai.WhimperChargeAttackGoal;
import dev.hexnowloading.dungeonnowloading.entity.ai.WhimperMoveControl;
import dev.hexnowloading.dungeonnowloading.entity.ai.WhimperRandomMoveGoal;
import dev.hexnowloading.dungeonnowloading.util.PlayerSupporterEntity;
import dev.hexnowloading.dungeonnowloading.registry.DNLSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class WhimperEntity extends PathfinderMob implements PlayerSupporterEntity {

    private static final EntityDataAccessor<Integer> DESPAWN_TICK = SynchedEntityData.defineId(WhimperEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(WhimperEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(WhimperEntity.class, EntityDataSerializers.BOOLEAN);

    public WhimperEntity(EntityType<? extends WhimperEntity> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 0;
        this.moveControl = new WhimperMoveControl(this);
        this.lookControl = new LookControl(this);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.FLYING_SPEED, 0.6F)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.FOLLOW_RANGE, 48.0F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(5, new WhimperChargeAttackGoal(this));
        this.goalSelector.addGoal(6, new WhimperRandomMoveGoal(this));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false, (c) -> {
            return !c.getUUID().equals(this.getOwnerUUID()) && PvpConfig.TOGGLE_PVP_MODE.get() && c instanceof PlayerSupporterEntity;
        }));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 5, true, false, (c) -> {
            return !c.getUUID().equals(this.getOwnerUUID()) && PvpConfig.TOGGLE_PVP_MODE.get();
        }));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false, (c) -> {
            return c instanceof Enemy;
        }));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DESPAWN_TICK, 600);
        this.entityData.define(OWNER_UUID, Optional.empty());
        this.entityData.define(CHARGING, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("DespawnTicks", this.entityData.get(DESPAWN_TICK));
        if (this.getOwnerUUID() != null) {
            compoundTag.putUUID("Owner", this.getOwnerUUID());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(DESPAWN_TICK, compoundTag.getInt("DespawnTicks"));
        UUID uuid;
        if (compoundTag.hasUUID("Owner")) {
            uuid = compoundTag.getUUID("Owner");
        } else {
            String string = compoundTag.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), string);
        }
        if (uuid != null) {
            this.setOwnerUUID(uuid);
        }
    }

    @Override
    protected void customServerAiStep() {
        if (this.getDespawnTick() > 0) {
            int despawnTick = this.getDespawnTick() - 1;
            if (despawnTick <= 0) {
                this.discardWithParticle();
            }
            setDespawnTick(despawnTick);
        }
        super.customServerAiStep();
    }

    private void discardWithParticle() {
        if (!this.level().isClientSide) {
            ((ServerLevel) this.level()).sendParticles(ParticleTypes.POOF, this.getX(), this.getY(), this.getZ(), 20, 0.3D, 0.3D, 0.3D, 0.0D);
        }
        this.discard();
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected void checkFallDamage(double $$0, boolean $$1, BlockState $$2, BlockPos $$3) {
    }

    @Override
    public boolean onClimbable() {
        return false;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return 0.6F;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return DNLSounds.WHIMPER_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource $$0) {
        return DNLSounds.WHIMPER_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return DNLSounds.WHIMPER_DEATH.get();
    }

    public int getDespawnTick() { return this.entityData.get(DESPAWN_TICK); }

    public void setDespawnTick(int tick) { this.entityData.set(DESPAWN_TICK, tick); }

    public UUID getOwnerUUID() { return (UUID) ((Optional) this.entityData.get(OWNER_UUID)).orElse((Object) null); }

    public void setOwnerUUID(UUID uuid) { this.entityData.set(OWNER_UUID, Optional.ofNullable(uuid)); }

    public boolean IsCharging() { return this.entityData.get(CHARGING); }

    public void setCharging(boolean b) { this.entityData.set(CHARGING, b); }
}
