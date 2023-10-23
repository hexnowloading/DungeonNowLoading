package dev.hexnowloading.skyisland.entity.projectile;

import dev.hexnowloading.skyisland.registry.SkyislandEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.UUID;

public class ChaosSpawnerProjectileEntity extends Entity {
    private UUID ownerUUID;
    private Entity cachedOwner;
    private boolean leftOwner;
    private boolean hasBeenShot;
    public double xPower;
    public double yPower;
    public double zPower;
    private float INERTIA = 1F; // Keep it at 1 to maintain smooth motion at all speed.
    private ParticleOptions SPAWN_PARTICLE = ParticleTypes.POOF;
    private ParticleOptions TRAIL_PARTICLE = ParticleTypes.DRAGON_BREATH;

    public ChaosSpawnerProjectileEntity(EntityType<? extends ChaosSpawnerProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public ChaosSpawnerProjectileEntity(double x, double y, double z, double xP, double yP, double zP, Level level) {
        this(SkyislandEntityTypes.CHAOS_SPAWNER_PROJECTILE.get(), level);
        this.moveTo(x, y, z, this.getYRot(), this.getXRot());
        this.reapplyPosition();
        double d0 = Math.sqrt(xP * xP + yP * yP + zP * zP);
        if (d0 != 0.0D) {
            this.xPower = xP / d0 * 0.1D;
            this.yPower = yP / d0 * 0.1D;
            this.zPower = zP / d0 * 0.1D;
        }
    }

    public ChaosSpawnerProjectileEntity(LivingEntity owner, double xP, double yP, double zP, Level level) {
        this(owner.getX(), owner.getY(), owner.getZ(), xP, yP, zP, level);
        this.setOwner(owner);
        this.setRot(owner.getYRot(), owner.getXRot());
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
        if (this.tickCount > 400) {
            this.remove(RemovalReason.DISCARDED);
        } else {
            Entity owner = this.getOwner();
            if (this.level().isClientSide || (owner == null || !owner.isRemoved()) && this.level().hasChunkAt(this.blockPosition())) {
                super.tick();

                HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
                if (hitResult.getType() != HitResult.Type.MISS) {
                    this.onHit(hitResult);
                }

                this.checkInsideBlocks();
                Vec3 deltaMovement = this.getDeltaMovement();
                double d0 = this.getX() + deltaMovement.x;
                double d1 = this.getY() + deltaMovement.y;
                double d2 = this.getZ() + deltaMovement.z;
                float inertia = this.INERTIA;
                this.setDeltaMovement(deltaMovement.add(this.xPower, this.yPower, this.zPower).scale(inertia));
                this.level().addParticle(this.TRAIL_PARTICLE, d0, d1 + 0.5, d2, 0.0, 0.0, 0.0);
                this.setPos(d0, d1, d2);
                ProjectileUtil.rotateTowardsMovement(this, 1F);
                if (this.tickCount == 3) {
                    this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.WITHER_SHOOT, this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
                    for(int i = 0; i < 5; ++i) {
                        this.level().addParticle(this.SPAWN_PARTICLE, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
                    }
                }
            } else {
                discard();
            }
        }
        //super.tick();
    }

    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        Vec3 vector3d = (new Vec3(x, y, z)).normalize().add(this.random.nextGaussian() * (double) 0.0075F * (double) inaccuracy, this.random.nextGaussian() * (double) 0.0075F * (double) inaccuracy, this.random.nextGaussian() * (double) 0.0075F * (double) inaccuracy).scale(velocity);
        this.setDeltaMovement(vector3d);
        float f = Mth.sqrt((float) vector3d.horizontalDistanceSqr());
        this.setYRot( (float) (Mth.atan2(vector3d.x, vector3d.z) * (double) (180F / (float) Math.PI)));
        this.setXRot((float) (Mth.atan2(vector3d.y, f) * (double) (180F / (float) Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
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

    protected void onHitEntity(EntityHitResult entityHitResult) {
        if (!this.level().isClientSide) {
            Entity target = entityHitResult.getEntity();
            if (target instanceof Player) {
                boolean entityHurted = target.hurt(this.damageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 8.0F);
                if (((Player) target).isBlocking()) {
                    ((Player) target).disableShield(true);
                }
                if (entityHurted && target.isAlive()) {
                    this.doEnchantDamageEffects((LivingEntity) this.getOwner(), entityHitResult.getEntity());
                }
            }
        }
    }

    protected void onHitBlock(BlockHitResult blockHitResult) {
    }

    protected boolean canHitEntity(Entity entity) {
        if (!entity.noPhysics) {
            if (!entity.canBeHitByProjectile()) {
                return false;
            } else {
                Entity owner = this.getOwner();
                return owner == null || this.leftOwner || !owner.isPassengerOfSameVehicle(entity);
            }
        } else {
            return false;
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        Entity entity = this.getOwner();
        int i = entity == null ? 0 : entity.getId();
        return new ClientboundAddEntityPacket(this.getId(), this.getUUID(), this.getX(), this.getY(), this.getZ(), this.getXRot(), this.getYRot(), this.getType(), i, new Vec3(this.getDeltaMovement().scale(this.INERTIA).toVector3f()), 0.0D);
    }

    @Nullable
    public Entity getOwner() {
        if (this.cachedOwner != null && !this.cachedOwner.isRemoved()) {
            return this.cachedOwner;
        } else if (this.ownerUUID != null && this.level() instanceof ServerLevel) {
            this.cachedOwner = ((ServerLevel)this.level()).getEntity(this.ownerUUID);
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

    private boolean checkLeftOwner() {
        Entity entity = this.getOwner();
        if (entity != null) {
            for(Entity entity1 : this.level().getEntities(this, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), (p_37272_) -> {
                return !p_37272_.isSpectator() && p_37272_.isPickable();
            })) {
                if (entity1.getRootVehicle() == entity.getRootVehicle()) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void lerpMotion(double x, double y, double z) {
        this.setDeltaMovement(x, y, z);
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            float f = Mth.sqrt((float) (x * x + z * z));
            this.setXRot((float) (Mth.atan2(y, f) * (double) (180F / (float) Math.PI)));
            this.setYRot( (float) (Mth.atan2(x, z) * (double) (180F / (float) Math.PI)));
            this.xRotO = this.getXRot();
            this.yRotO = this.getYRot();
            this.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
        }
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
    public void recreateFromPacket(ClientboundAddEntityPacket clientboundAddEntityPacket) {
        super.recreateFromPacket(clientboundAddEntityPacket);
        double d0 = clientboundAddEntityPacket.getXa();
        double d1 = clientboundAddEntityPacket.getYa();
        double d2 = clientboundAddEntityPacket.getZa();
        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
        if (d3 != 0.0D) {
            this.xPower = d0 / d3 * 0.1D;
            this.yPower = d1 / d3 * 0.1D;
            this.zPower = d2 / d3 * 0.1D;
        }
    }

    @Override
    protected void defineSynchedData() {

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
        if (compoundTag.contains("power", 9)) {
            ListTag listtag = compoundTag.getList("power", 6);
            if (listtag.size() == 3) {
                this.xPower = listtag.getDouble(0);
                this.yPower = listtag.getDouble(1);
                this.zPower = listtag.getDouble(2);
            }
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.hasUUID("Owner")) {
            this.ownerUUID = compoundTag.getUUID("Owner");
            this.cachedOwner = null;
        }

        this.leftOwner = compoundTag.getBoolean("LeftOwner");
        this.hasBeenShot = compoundTag.getBoolean("HasBeenShot");
        compoundTag.put("power", this.newDoubleList(new double[]{this.xPower, this.yPower, this.zPower}));
    }
}
