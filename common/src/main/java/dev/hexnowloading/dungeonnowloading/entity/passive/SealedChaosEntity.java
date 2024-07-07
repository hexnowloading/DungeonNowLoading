package dev.hexnowloading.dungeonnowloading.entity.passive;

import dev.hexnowloading.dungeonnowloading.config.PvpConfig;
import dev.hexnowloading.dungeonnowloading.entity.ai.EntityBodyRotationControl;
import dev.hexnowloading.dungeonnowloading.entity.ai.SealedChaosAttackGoal;
import dev.hexnowloading.dungeonnowloading.entity.util.PlayerSupporterEntity;
import dev.hexnowloading.dungeonnowloading.registry.DNLItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.UUID;

public class SealedChaosEntity extends PathfinderMob implements PlayerSupporterEntity {

    private static final EntityDataAccessor<Integer> DESPAWN_TICK = SynchedEntityData.defineId(SealedChaosEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(SealedChaosEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    public SealedChaosEntity(EntityType<? extends SealedChaosEntity> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 0;
    }

    /*public SealedChaosEntity(Level level, UUID uuid) {
        this(DNLEntityTypes.SEALED_CHAOS.get(), level);
        this.setOwnerUUID(uuid);
    }*/

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 10.0F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new SealedChaosAttackGoal(this, 20));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
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

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.is(DNLItems.SCEPTER_OF_SEALED_CHAOS.get())) {
            if (player.getUUID().equals(this.getOwnerUUID())) {
                if (player.getCooldowns().isOnCooldown(DNLItems.SCEPTER_OF_SEALED_CHAOS.get())) {
                    player.getCooldowns().addCooldown(DNLItems.SCEPTER_OF_SEALED_CHAOS.get(), 20);
                    this.discardWithParticle();
                }
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }
        return super.mobInteract(player, interactionHand);
    }

    private void discardWithParticle() {
        if (!this.level().isClientSide) {
            ((ServerLevel) this.level()).sendParticles(ParticleTypes.POOF, this.getX(), this.getY(), this.getZ(), 20, 0.3D, 0.3D, 0.3D, 0.0D);
        }
        this.discard();
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new EntityBodyRotationControl(this);
    }

    @Override
    public Vec3 getDeltaMovement() {
        return Vec3.ZERO;
    }

    @Override
    public void setDeltaMovement(Vec3 vec3) {
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return 0.5F;
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket clientboundAddEntityPacket) {
        super.recreateFromPacket(clientboundAddEntityPacket);
        this.yBodyRot = 0.0F;
        this.yBodyRotO = 0.0F;
    }

    @Override
    public void push(Entity entity) {
    }

    public int getDespawnTick() { return this.entityData.get(DESPAWN_TICK); }

    public void setDespawnTick(int tick) { this.entityData.set(DESPAWN_TICK, tick); }

    public UUID getOwnerUUID() { return (UUID) ((Optional) this.entityData.get(OWNER_UUID)).orElse((Object) null); }

    public void setOwnerUUID(UUID uuid) { this.entityData.set(OWNER_UUID, Optional.ofNullable(uuid));}
}
