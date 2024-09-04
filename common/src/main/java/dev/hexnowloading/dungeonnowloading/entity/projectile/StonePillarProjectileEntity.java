package dev.hexnowloading.dungeonnowloading.entity.projectile;

import dev.hexnowloading.dungeonnowloading.entity.util.ModelledProjectileEntity;
import dev.hexnowloading.dungeonnowloading.registry.DNLEntityTypes;
import dev.hexnowloading.dungeonnowloading.util.NbtHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.UUID;

public class StonePillarProjectileEntity extends ModelledProjectileEntity {

    private Vec3 targetPos;
    private double hoverX;
    private double hoverY;
    private double hoverZ;
    private double maxSpeed;
    private double minSpeed;
    private double stopAccuracy;
    private double dropSpeed;
    private int hoverToDropInterval;
    private int dropToDespawnInterval;

    private float damage;
    private int phase;
    private int tickCount;
    private double distanceToTarget;

    public StonePillarProjectileEntity(EntityType<? extends StonePillarProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public StonePillarProjectileEntity(LivingEntity livingEntity, Level level, float damagePercentage, double x, double y, double z, Vec3 hoverPos, double hoverMaxSpeed, double hoverMinSpeed, double hoverStopAccuracy, int hoverToDropInterval, double dropSpeed, int dropToDespawnInterval) {
        this(DNLEntityTypes.STONE_PILLAR_PROJECTILE.get(), level);
        this.setOwner(livingEntity);
        this.moveTo(x, y, z, this.getYRot(), this.getXRot());
        this.setNoGravity(true);
        this.damage = 0;
        if (this.getOwner() instanceof LivingEntity entity) {
            this.damage = (float) (damagePercentage * entity.getAttributeValue(Attributes.ATTACK_DAMAGE));
        }
        /*this.hoverX = hoverPos.x;
        this.hoverY = hoverPos.y;
        this.hoverZ = hoverPos.z;*/
        this.targetPos = hoverPos;
        this.maxSpeed = hoverMaxSpeed;
        this.minSpeed = hoverMinSpeed;
        this.stopAccuracy = hoverStopAccuracy;
        this.dropSpeed = dropSpeed;
        this.distanceToTarget = this.position().distanceTo(hoverPos);
        this.phase = 0;
        this.tickCount = 0;
        this.dropToDespawnInterval = dropToDespawnInterval;
        this.hoverToDropInterval = hoverToDropInterval;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            //System.out.println("Phase" + this.phase);
            if (this.phase == 0) {
                this.phase++;
            } else if (this.phase == 1) {
                //Vec3 direction = targetPos.subtract(this.position()).normalize();
                double t = 1 - this.position().distanceTo(this.targetPos) / this.distanceToTarget;
                if (t >= this.stopAccuracy || t < 0 || Double.isNaN(t)) {
                    //t = 1;
                    //this.setPos(this.targetPos.x, this.targetPos.y, this.targetPos.z);
                    this.phase++;
                    this.tickCount = this.hoverToDropInterval;
                }
                /*double speed = this.minSpeed + (this.maxSpeed - this.minSpeed) * (1 - t * t);
                Vec3 velocity = direction.scale(speed);
                this.setDeltaMovement(velocity);
                double d0 = this.getX() + getDeltaMovement().x;
                double d1 = this.getY() + getDeltaMovement().y;
                double d2 = this.getZ() + getDeltaMovement().z;
                this.setPos(d0, d1, d2);*/
                //System.out.println(t + "|||" + this.position().distanceTo(this.targetPos) + "|||" + this.distanceToTarget + "|||" + velocity + "|||" + this.getDeltaMovement());
            } else if (this.phase == 2) {
                if (this.tickCount > 0) {
                    this.tickCount--;
                    return;
                }
                this.tickCount = this.dropToDespawnInterval;
                this.phase++;
            } else if (this.phase == 3) {
                this.setDeltaMovement(0, -this.dropSpeed, 0);
                if (this.tickCount > 0) {
                    this.tickCount--;
                    return;
                }
                this.phase++;
            } else if (this.phase == 4) {
                this.discard();
            }
        }
    }

    @Override
    protected void tickProjectile() {
        this.checkInsideBlocks();

        Vec3 delta = this.getDeltaMovement();
        double d0 = this.getX() + delta.x;
        double d1 = this.getY() + delta.y;
        double d2 = this.getZ() + delta.z;
        this.setPos(d0, d1, d2);

        if (this.phase != 1) {
            return;
        }

        Vec3 direction = targetPos.subtract(this.position()).normalize();
        double t = 1 - this.position().distanceTo(this.targetPos) / this.distanceToTarget;
        if (t >= this.stopAccuracy || t < 0 || Double.isNaN(t)) {
            t = 1;
        }
        double speed = this.minSpeed + (this.maxSpeed - this.minSpeed) * (1 - t * t);
        Vec3 velocity = direction.scale(speed);
        this.setDeltaMovement(velocity);
    }

    @Override
    public void lerpMotion(double x, double y, double z) {
        super.lerpMotion(x, y, z);
        //this.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        this.targetPos = new Vec3(compoundTag.getList("HoverPos", CompoundTag.TAG_DOUBLE).getDouble(0), compoundTag.getList("HoverPos", CompoundTag.TAG_DOUBLE).getDouble(1), compoundTag.getList("HoverPos", CompoundTag.TAG_DOUBLE).getDouble(2));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.put("HoverPos", NbtHelper.newDoubleList(this.targetPos.x, this.targetPos.y, this.targetPos.z));
    }

    /*protected void onHitEntity(EntityHitResult entityHitResult) {
        if (this.level().isClientSide)  {
            return;
        }
        Entity target = entityHitResult.getEntity();
        target.hurt(this.damageSources().mobProjectile(this, (LivingEntity) this.getOwner()), this.damage);
    }

    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        discard();
    }*/

    /*private void stonePillarMoveControl(Vec3 targetPos, double totalDistance, double maxSpeed, double minSpeed, double stopAccuracy) {
        Vec3 direction = targetPos.subtract(this.position()).normalize();
        double t = 1 - this.position().distanceTo(targetPos) / totalDistance;
        if (t >= stopAccuracy || t < 0 || Double.isNaN(t)) {
            t = 1;
            this.setPos(targetPos);
            this.phase++;
        }
        double speed = minSpeed + (maxSpeed - minSpeed) * (1 - t * t);
        Vec3 velocity = direction.scale(speed);
        this.setDeltaMovement(velocity);
    }*/
}
