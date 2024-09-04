package dev.hexnowloading.dungeonnowloading.entity.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class ModelledProjectileEntity extends Entity implements TraceableEntity {

    @Nullable
    private UUID ownerUUID;
    @Nullable
    private Entity cachedOwner;
    private boolean leftOwner;
    private boolean hasBeenShot;

    public ModelledProjectileEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        if (!this.hasBeenShot) {
            this.gameEvent(GameEvent.PROJECTILE_SHOOT, this.getOwner());
            this.hasBeenShot = true;
        }
        if (!this.leftOwner) {
            this.leftOwner = this.checkLeftOwner();
        }
        Entity owner = this.getOwner();
        if (this.level().isClientSide || (owner == null || !owner.isRemoved()) && this.level().hasChunkAt(this.blockPosition())) {
            super.tick();

            HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (hitResult.getType() != HitResult.Type.MISS) {
                this.onHit(hitResult);
            }

            this.tickProjectile();

        } else {
            discard();
        }
    }

    @Nullable
    @Override
    public Entity getOwner() {
        if (this.cachedOwner != null && !this.cachedOwner.isRemoved()) {
            return this.cachedOwner;
        } else if (this.ownerUUID != null && this.level() instanceof ServerLevel) {
            this.cachedOwner = ((ServerLevel) this.level()).getEntity(this.ownerUUID);
            return this.cachedOwner;
        } else {
            return null;
        }
    }

    public void setOwner(@Nullable Entity entity) {
        if (entity != null) {
            this.ownerUUID = entity.getUUID();
            this.cachedOwner = entity;
        }
    }

    protected boolean checkLeftOwner() {
        Entity entity = this.getOwner();
        if (entity != null) {
            for (Entity entity1 : this.level().getEntities(this, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), (owner) -> {
                return !owner.isSpectator() && owner.isPickable();
            })) {
                if (entity1.getRootVehicle() == entity.getRootVehicle()) {
                    return false;
                }
            }
        }
        return true;
    }

    protected void onHit(HitResult hitResult) {
        HitResult.Type hitresult$type = hitResult.getType();
        if (hitresult$type == HitResult.Type.ENTITY) {
            this.onHitEntity((EntityHitResult)hitResult);
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, hitResult.getLocation(), GameEvent.Context.of(this, (BlockState)null));
        } else if (hitresult$type == HitResult.Type.BLOCK) {
            BlockHitResult blockhitresult = (BlockHitResult)hitResult;
            this.onHitBlock(blockhitresult);
            BlockPos blockpos = blockhitresult.getBlockPos();
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, blockpos, GameEvent.Context.of(this, this.level().getBlockState(blockpos)));
        }
    }

    protected boolean canHitEntity(Entity entity) {
        if (!entity.canBeHitByProjectile()) {
            return false;
        } else {
            Entity ownerEntity = this.getOwner();
            return (ownerEntity == null || this.leftOwner || !ownerEntity.isPassengerOfSameVehicle(entity)) && !entity.noPhysics;
        }
    }

    protected void onHitEntity(EntityHitResult entityHitResult) {
    }

    protected void onHitBlock(BlockHitResult blockHitResult) {
    }

    protected void tickProjectile() {

    }

    @Override
    public void lerpMotion(double x, double y, double z) {
        this.setDeltaMovement(x, y, z);
        /*if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            float f = Mth.sqrt()
        }*/
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double d0 = this.getBoundingBox().getSize() * 4.0D;
        if (Double.isNaN(d0)) {
            d0 = 4.0D;
        }

        d0 *= 64.0D;
        return distance < d0 * d0;
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket recreateFromPacket) {
        super.recreateFromPacket(recreateFromPacket);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        if (this.ownerUUID != null) {
            compoundTag.putUUID("Owner", this.ownerUUID);
        }

        if (this.leftOwner) {
            compoundTag.putBoolean("LeftOwner", true);
        }

        compoundTag.putBoolean("HasBeenShot", this.hasBeenShot);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.hasUUID("Owner")) {
            this.ownerUUID = compoundTag.getUUID("Owner");
            this.cachedOwner = null;
        }

        this.leftOwner = compoundTag.getBoolean("LeftOwner");
        this.hasBeenShot = compoundTag.getBoolean("HasBeenShot");
    }
}
