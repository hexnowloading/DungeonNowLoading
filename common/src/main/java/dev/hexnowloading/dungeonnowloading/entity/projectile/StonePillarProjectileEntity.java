package dev.hexnowloading.dungeonnowloading.entity.projectile;

import dev.hexnowloading.dungeonnowloading.entity.boss.FairkeeperEntity;
import dev.hexnowloading.dungeonnowloading.registry.DNLEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class StonePillarProjectileEntity extends AbstractHurtingProjectile {

    private Vec3 hoverPos;
    private double hoverMaxSpeed;
    private double hoverMinSpeed;
    private double hoverStopAccuracy;
    private double dropSpeed;

    private float damage;
    private int phase;
    private int tickCount;
    private double totalDistanceToTarget;

    public StonePillarProjectileEntity(EntityType<? extends StonePillarProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public StonePillarProjectileEntity(LivingEntity livingEntity, Level level, float damagePercentage, double x, double y, double z, double hoverPosX, double hoverPosY, double hoverPosZ, double hoverMaxSpeed, double hoverMinSpeed, double hoverStopAccuracy, int hoverToDropInterval, double dropSpeed) {
        this(DNLEntityTypes.STONE_PILLAR_PROJECTILE.get(), level);
        this.setOwner(livingEntity);
        this.moveTo(x, y, z, this.getYRot(), this.getXRot());
        this.setNoGravity(true);
        this.damage = 0;
        if (this.getOwner() instanceof LivingEntity entity) {
            this.damage = (float) (damagePercentage * entity.getAttributeValue(Attributes.ATTACK_DAMAGE));
        }
        this.hoverPos = new Vec3(hoverPosX, hoverPosY, hoverPosZ);
        System.out.println(this.hoverPos);
        this.hoverMaxSpeed = hoverMaxSpeed;
        this.hoverMinSpeed = hoverMinSpeed;
        this.hoverStopAccuracy = hoverStopAccuracy;
        this.dropSpeed = dropSpeed;
        this.totalDistanceToTarget = this.position().distanceTo(hoverPos);
        this.phase = 0;
        this.tickCount = hoverToDropInterval;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.phase == 0) {
            if (this.hoverPos == null) {
                this.hoverPos = Vec3.ZERO;
            }
            Vec3 direction = this.hoverPos.subtract(this.position()).normalize();
            double t = 1 - this.position().distanceTo(this.hoverPos) / this.totalDistanceToTarget;
            if (t >= this.hoverStopAccuracy || t < 0 || Double.isNaN(t)) {
                t = 1;
                this.setPos(this.hoverPos);
                this.phase++;
            }
            double speed = this.hoverMinSpeed + (this.hoverMaxSpeed - this.hoverMinSpeed) * (1 - t * t);
            Vec3 velocity = direction.scale(speed);
            this.setDeltaMovement(velocity);
        } else if (this.phase == 1) {
            if (this.tickCount > 0) {
                this.tickCount--;
                return;
            }
            this.phase++;
        } else if (this.phase == 2) {
            this.setDeltaMovement(0, -this.dropSpeed, 0);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        if (this.level().isClientSide)  {
            return;
        }
        Entity target = entityHitResult.getEntity();
        target.hurt(this.damageSources().mobProjectile(this, (LivingEntity) this.getOwner()), this.damage);
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        discard();
    }

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
