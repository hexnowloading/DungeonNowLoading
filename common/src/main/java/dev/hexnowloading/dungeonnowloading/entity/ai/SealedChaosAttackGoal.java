package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.passive.SealedChaosEntity;
import dev.hexnowloading.dungeonnowloading.entity.projectile.ChaosSpawnerProjectileEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class SealedChaosAttackGoal extends Goal {

    private final SealedChaosEntity sealedChaosEntity;
    private final int shootIntervalTick;
    private int attackTick;

    public SealedChaosAttackGoal(SealedChaosEntity sealedChaosEntity, int shootIntervalTick) {
        this.sealedChaosEntity = sealedChaosEntity;
        this.shootIntervalTick = shootIntervalTick;
        this.setFlags(EnumSet.of(Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.sealedChaosEntity.getTarget();
        return target != null && target.isAlive();
    }

    /*@Override
    public boolean canContinueToUse() {
        LivingEntity target = this.sealedChaosEntity.getTarget();
        return target != null && target.isAlive();
    }*/

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = this.sealedChaosEntity.getTarget();
        if (target != null) {
            this.sealedChaosEntity.getLookControl().setLookAt(target, 30.0F, 30.0F);
            if (this.attackTick > 0) {
                this.attackTick--;
            } else {
                if (this.sealedChaosEntity.hasLineOfSight(target)) {
                    performSingleShot(target);
                    this.attackTick = this.shootIntervalTick;
                }
            }
        }
    }

    private void performSingleShot(LivingEntity target) {
        if (target.distanceTo(this.sealedChaosEntity) < this.sealedChaosEntity.getAttributeValue(Attributes.FOLLOW_RANGE) && this.sealedChaosEntity.getLookControl().isLookingAtTarget()) {
            vecFromCenterToFrontOfFace(0.0F);
        }
    }

    private void vecFromCenterToFrontOfFace(float angle) {
        this.sealedChaosEntity.level().playSound(null, this.sealedChaosEntity.getX(), this.sealedChaosEntity.getY(), this.sealedChaosEntity.getZ(), SoundEvents.WITHER_SHOOT, this.sealedChaosEntity.getSoundSource(), 3.0F, 1.0F + (this.sealedChaosEntity.getRandom().nextFloat() - this.sealedChaosEntity.getRandom().nextFloat()) * 0.2F);
        double viewDistance = 0.1F;
        Vec3 viewVector = this.sealedChaosEntity.getViewVector(1.0F);
        if (angle != 0.0F) {
            float offset = (float) Math.toRadians(angle);
            viewVector = viewVector.yRot(offset);
        }
        double d0 = viewVector.x * viewDistance;
        double d1 = viewVector.y * viewDistance;
        double d2 = viewVector.z * viewDistance;
        ChaosSpawnerProjectileEntity chaosSpawnerProjectileEntity = new ChaosSpawnerProjectileEntity(this.sealedChaosEntity, d0, d1, d2);
        chaosSpawnerProjectileEntity.setPos(chaosSpawnerProjectileEntity.getX() + d0, chaosSpawnerProjectileEntity.getY() + d1, chaosSpawnerProjectileEntity.getZ() + d2);
        this.sealedChaosEntity.level().addFreshEntity(chaosSpawnerProjectileEntity);
    }
}
