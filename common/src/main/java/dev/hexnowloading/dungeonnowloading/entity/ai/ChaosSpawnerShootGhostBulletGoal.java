package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.boss.ChaosSpawnerEntity;
import dev.hexnowloading.dungeonnowloading.entity.projectile.ChaosSpawnerProjectileEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class ChaosSpawnerShootGhostBulletGoal extends Goal {

    private final ChaosSpawnerEntity chaosSpawnerEntity;
    private final int type;

    public ChaosSpawnerShootGhostBulletGoal(ChaosSpawnerEntity chaosSpawnerEntity, int type) {
        this.chaosSpawnerEntity = chaosSpawnerEntity;
        this.type = type;
    }

    @Override
    public boolean canUse() {
        return chaosSpawnerEntity.isAttacking(ChaosSpawnerEntity.State.ABILITY_E) && chaosSpawnerEntity.getTarget() != null;
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void start() {
        super.start();
        chaosSpawnerEntity.setAttackTick(100);
    }

    @Override
    public void stop() {
        super.stop();
        chaosSpawnerEntity.setAttackTick(0);
        chaosSpawnerEntity.setDataState(ChaosSpawnerEntity.State.IDLE);
    }

    @Override
    public void tick() {
        if (chaosSpawnerEntity.getAttackTick() == 100) { performRangedAttack(); }
        if (chaosSpawnerEntity.getAttackTick() == 80) { performRangedAttack(); }
        if (chaosSpawnerEntity.getAttackTick() == 60) { performRangedAttack(); }
        if (chaosSpawnerEntity.getAttackTick() == 40) { performRangedAttack(); }
        if (chaosSpawnerEntity.getAttackTick() == 20) { performRangedAttack(); }
    }

    private void performRangedAttack() {
        LivingEntity targetEntity = chaosSpawnerEntity.getTarget();
        if (targetEntity != null) {
            if (targetEntity.distanceTo(chaosSpawnerEntity) < 30.0D) {
                vecFromCenterToFrontOfFace();
            }
        }
    }

    private void vecFromCenterToFrontOfFace() {
        double viewDistance = 2.0F;
        Vec3 viewVector = chaosSpawnerEntity.getViewVector(1.0F);
        double d0 = viewVector.x * viewDistance;
        double d1 = viewVector.y * viewDistance;
        double d2 = viewVector.z * viewDistance;
        ChaosSpawnerProjectileEntity chaosSpawnerProjectileEntity = new ChaosSpawnerProjectileEntity(chaosSpawnerEntity, d0, d1, d2, chaosSpawnerEntity.level());
        chaosSpawnerProjectileEntity.setPos(chaosSpawnerProjectileEntity.getX() + d0, chaosSpawnerProjectileEntity.getY(0.5) + d1, chaosSpawnerProjectileEntity.getZ() + d2);
        chaosSpawnerEntity.level().addFreshEntity(chaosSpawnerProjectileEntity);
    }
}
