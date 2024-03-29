package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.boss.ChaosSpawnerEntity;
import dev.hexnowloading.dungeonnowloading.entity.projectile.ChaosSpawnerProjectileEntity;
import dev.hexnowloading.dungeonnowloading.util.WeightedRandomBag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class ChaosSpawnerShootGhostBulletGoal extends Goal {

    private final ChaosSpawnerEntity chaosSpawnerEntity;
    private final int DURATION = 126; // Must be even duration since bullet are shot at even ticks
    private String type;

    public ChaosSpawnerShootGhostBulletGoal(ChaosSpawnerEntity chaosSpawnerEntity) {
        this.chaosSpawnerEntity = chaosSpawnerEntity;
    }

    @Override
    public boolean canUse() {
        return (chaosSpawnerEntity.isAttacking(ChaosSpawnerEntity.State.SHOOT_GHOST_BULLET_SINGLE) || chaosSpawnerEntity.isAttacking(ChaosSpawnerEntity.State.SHOOT_GHOST_BULLET_BURST)) && chaosSpawnerEntity.getTarget() != null;
    }

    @Override
    public void start() {
        super.start();
        chaosSpawnerEntity.setAttackTick(DURATION);
        WeightedRandomBag<String> bulletPatterns = new WeightedRandomBag<>();
        if (chaosSpawnerEntity.getState() == ChaosSpawnerEntity.State.SHOOT_GHOST_BULLET_SINGLE) {
            chaosSpawnerEntity.triggerRangeAttackAnimation();
            if (chaosSpawnerEntity.getPhase() == 1) {
                bulletPatterns.addEntry("Single", 1);
                bulletPatterns.addEntry("Arc", 1);
            } else if (chaosSpawnerEntity.getPhase() == 2) {
                bulletPatterns.addEntry("Rapid", 1);
                bulletPatterns.addEntry("Strong Arc", 1);
            }
        } else {
            chaosSpawnerEntity.triggerRangeBurstAttackAnimation();
            if (chaosSpawnerEntity.getPhase() == 1) {
                bulletPatterns.addEntry("Burst", 1);
            } else if (chaosSpawnerEntity.getPhase() == 2) {
                bulletPatterns.addEntry("Strong Burst", 1);
            }
        }
        type = bulletPatterns.getRandom();
    }

    @Override
    public void tick() {
        switch (type) {
            case "Single" -> {
                for (int i = 0; i < 5; i++) {
                    if (chaosSpawnerEntity.getAttackTick() == 100 - i * 20) { performSingleShot(); }
                }
            }
            case "Rapid" -> {
                for (int j = 0; j < 3; j++) {
                    for (int i = 0; i < 5; i++) {
                        if (chaosSpawnerEntity.getAttackTick() == (100 - 30 * j) - i * 4) { performSingleShot(); }
                    }
                }
            }
            case "Arc" -> {
                for (int i = 0; i < 3; i++) {
                    if (chaosSpawnerEntity.getAttackTick() == 100 - i * 30) { performArcShot(); }
                }
            }
            case "Strong Arc" -> {
                for (int i = 0; i < 3; i++) {
                    if (chaosSpawnerEntity.getAttackTick() == 100 - i * 30) { performStrongArcShot(); }
                }
            }
            case "Burst" -> {
                for (int i = 0; i < 3; i++) {
                    if (chaosSpawnerEntity.getAttackTick() == 100 - i * 30) { performBurstShot(i % 2 * 22.5F); }
                }
            }
            case "Strong Burst" -> {
                for (int i = 0; i < 3; i++) {
                    if (chaosSpawnerEntity.getAttackTick() == 100 - i * 30) { performStrongBurstShot(i % 2 * 11.5F); }
                }
            }
        }
        if (chaosSpawnerEntity.getAttackTick() == 0) {
            chaosSpawnerEntity.stopAttacking(60);
        }
    }

    private void performSingleShot() {
        LivingEntity targetEntity = chaosSpawnerEntity.getTarget();
        if (targetEntity != null) {
            if (targetEntity.distanceTo(chaosSpawnerEntity) < this.chaosSpawnerEntity.getFollowDistance()) {
                chaosSpawnerEntity.level().playSound(null, chaosSpawnerEntity.getX(), chaosSpawnerEntity.getY(), chaosSpawnerEntity.getZ(), SoundEvents.WITHER_SHOOT, chaosSpawnerEntity.getSoundSource(), 3.0F, 1.0F + (chaosSpawnerEntity.getRandom().nextFloat() - chaosSpawnerEntity.getRandom().nextFloat()) * 0.2F);
                vecFromCenterToFrontOfFace(0.0F);
            }
        }
    }

    private void performArcShot() {
        LivingEntity targetEntity = chaosSpawnerEntity.getTarget();
        if (targetEntity != null) {
            if (targetEntity.distanceTo(chaosSpawnerEntity) < this.chaosSpawnerEntity.getFollowDistance()) {
                chaosSpawnerEntity.level().playSound(null, chaosSpawnerEntity.getX(), chaosSpawnerEntity.getY(), chaosSpawnerEntity.getZ(), SoundEvents.WITHER_SHOOT, chaosSpawnerEntity.getSoundSource(), 3.0F, 1.0F + (chaosSpawnerEntity.getRandom().nextFloat() - chaosSpawnerEntity.getRandom().nextFloat()) * 0.2F);
                vecFromCenterToFrontOfFace(0.0F);
                vecFromCenterToFrontOfFace(-10.0F);
                vecFromCenterToFrontOfFace(10.0F);
            }
        }
    }

    private void performStrongArcShot() {
        LivingEntity targetEntity = chaosSpawnerEntity.getTarget();
        if (targetEntity != null) {
            if (targetEntity.distanceTo(chaosSpawnerEntity) < this.chaosSpawnerEntity.getFollowDistance()) {
                chaosSpawnerEntity.level().playSound(null, chaosSpawnerEntity.getX(), chaosSpawnerEntity.getY(), chaosSpawnerEntity.getZ(), SoundEvents.WITHER_SHOOT, chaosSpawnerEntity.getSoundSource(), 3.0F, 1.0F + (chaosSpawnerEntity.getRandom().nextFloat() - chaosSpawnerEntity.getRandom().nextFloat()) * 0.2F);
                vecFromCenterToFrontOfFace(0.0F);
                vecFromCenterToFrontOfFace(-10.0F);
                vecFromCenterToFrontOfFace(10.0F);
                vecFromCenterToFrontOfFace(-20.0F);
                vecFromCenterToFrontOfFace(20.0F);
            }
        }
    }

    private void performBurstShot(float angle) {
        float offset = (float) Math.toRadians(angle);
        Vec3 vec3 = chaosSpawnerEntity.getViewVector(1.0F);
        vec3 = vec3.yRot(offset);
        chaosSpawnerEntity.level().playSound(null, chaosSpawnerEntity.getX(), chaosSpawnerEntity.getY(), chaosSpawnerEntity.getZ(), SoundEvents.WITHER_SHOOT, chaosSpawnerEntity.getSoundSource(), 3.0F, 1.0F + (chaosSpawnerEntity.getRandom().nextFloat() - chaosSpawnerEntity.getRandom().nextFloat()) * 0.2F);
        for (int i = 0; i < 8; i++) {
            vec3 = vec3.yRot((float) Math.toRadians(45)* i);
            ChaosSpawnerProjectileEntity chaosSpawnerProjectileEntity = new ChaosSpawnerProjectileEntity(chaosSpawnerEntity, vec3.x, vec3.y,vec3.z);
            chaosSpawnerProjectileEntity.setPos(chaosSpawnerProjectileEntity.getX() + vec3.x, chaosSpawnerProjectileEntity.getY(0.5) + vec3.y, chaosSpawnerProjectileEntity.getZ() + vec3.z);
            chaosSpawnerEntity.level().addFreshEntity(chaosSpawnerProjectileEntity);
        }
    }

    private void performStrongBurstShot(float angle) {
        float offset = (float) Math.toRadians(angle);
        Vec3 vec3 = chaosSpawnerEntity.getViewVector(1.0F);
        vec3 = vec3.yRot(offset);
        chaosSpawnerEntity.level().playSound(null, chaosSpawnerEntity.getX(), chaosSpawnerEntity.getY(), chaosSpawnerEntity.getZ(), SoundEvents.WITHER_SHOOT, chaosSpawnerEntity.getSoundSource(), 3.0F, 1.0F + (chaosSpawnerEntity.getRandom().nextFloat() - chaosSpawnerEntity.getRandom().nextFloat()) * 0.2F);
        for (int i = 0; i < 16; i++) {
            vec3 = vec3.yRot((float) Math.toRadians(22.5) * i);
            ChaosSpawnerProjectileEntity chaosSpawnerProjectileEntity = new ChaosSpawnerProjectileEntity(chaosSpawnerEntity, vec3.x, vec3.y,vec3.z);
            chaosSpawnerProjectileEntity.setPos(chaosSpawnerProjectileEntity.getX() + vec3.x, chaosSpawnerProjectileEntity.getY(0.5) + vec3.y, chaosSpawnerProjectileEntity.getZ() + vec3.z);
            chaosSpawnerEntity.level().addFreshEntity(chaosSpawnerProjectileEntity);
        }
    }

    private void vecFromCenterToFrontOfFace(float angle) {
        double viewDistance = 2.0F;
        Vec3 viewVector = chaosSpawnerEntity.getViewVector(1.0F);
        if (angle != 0.0F) {
            float offset = (float) Math.toRadians(angle);
            viewVector = viewVector.yRot(offset);
        }
        double d0 = viewVector.x * viewDistance;
        double d1 = viewVector.y * viewDistance;
        double d2 = viewVector.z * viewDistance;
        ChaosSpawnerProjectileEntity chaosSpawnerProjectileEntity = new ChaosSpawnerProjectileEntity(chaosSpawnerEntity, d0, d1, d2);
        chaosSpawnerProjectileEntity.setPos(chaosSpawnerProjectileEntity.getX() + d0, chaosSpawnerProjectileEntity.getY(0.5) + d1, chaosSpawnerProjectileEntity.getZ() + d2);
        chaosSpawnerEntity.level().addFreshEntity(chaosSpawnerProjectileEntity);
    }
}
