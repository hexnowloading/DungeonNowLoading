package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.boss.ChaosSpawnerEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class ChaosSpawnerLookAtPlayerGoal extends Goal {

    private final ChaosSpawnerEntity chaosSpawnerEntity;
    private Entity lookAt;
    private final float lookDistance;
    private int lookTime;
    private final float probability;
    private final boolean onlyHorizontal;
    private final Class<? extends LivingEntity> lookAtType;
    private final TargetingConditions lookAtContext;

    public ChaosSpawnerLookAtPlayerGoal(ChaosSpawnerEntity chaosSpawnerEntity, Class<? extends LivingEntity> lookAtType, float lookDistance, float probability, boolean onlyHorizontal) {
        this.chaosSpawnerEntity = chaosSpawnerEntity;
        this.lookAtType = lookAtType;
        this.lookDistance = lookDistance;
        this.probability = probability;
        this.onlyHorizontal = onlyHorizontal;
        this.setFlags(EnumSet.of(Flag.LOOK));
        if (lookAtType == Player.class) {
            this.lookAtContext = TargetingConditions.forNonCombat().range(lookDistance).ignoreLineOfSight().ignoreInvisibilityTesting().selector((lookTarget) -> EntitySelector.notRiding(chaosSpawnerEntity).test(lookTarget));
        } else {
            this.lookAtContext = TargetingConditions.forNonCombat().range(lookDistance).ignoreLineOfSight().ignoreInvisibilityTesting();
        }
    }

    @Override
    public boolean canUse() {
        if (chaosSpawnerEntity.getRandom().nextFloat() >= probability) {
            return false;
        } else {
            if (chaosSpawnerEntity.getTarget() != null) {
                lookAt = chaosSpawnerEntity.getTarget();
            }
            if (lookAtType == Player.class) {
                lookAt = chaosSpawnerEntity.level().getNearestPlayer(lookAtContext, chaosSpawnerEntity, chaosSpawnerEntity.getX(), chaosSpawnerEntity.getEyeY(), chaosSpawnerEntity.getZ());
            } else {
                lookAt = chaosSpawnerEntity.level().getNearestEntity(chaosSpawnerEntity.level().getEntitiesOfClass(lookAtType, chaosSpawnerEntity.getBoundingBox().inflate(lookDistance, 3.0, lookDistance), (lookTarget) -> {
                    return true;
                }), lookAtContext, chaosSpawnerEntity, chaosSpawnerEntity.getX(), chaosSpawnerEntity.getEyeY(), chaosSpawnerEntity.getZ());
            }
            return lookAt != null;
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (!lookAt.isAlive()) {
            return false;
        } else if (chaosSpawnerEntity.distanceToSqr(lookAt) > (double) (lookDistance * lookDistance)) {
            return false;
        } else {
            return lookTime > 0;
        }
    }

    @Override
    public void start() {
        lookTime = adjustedTickDelay(40 + chaosSpawnerEntity.getRandom().nextInt(40));
    }

    @Override
    public void stop() {
        lookAt = null;
    }

    @Override
    public void tick() {
        if (lookAt.isAlive()) {
            if (chaosSpawnerEntity.isAttacking(ChaosSpawnerEntity.State.PUSH)) {
                double viewDistance = 2.0F;
                Vec3 viewVector = chaosSpawnerEntity.getViewVector(1.0F);
                double d0 = viewVector.x * viewDistance;
                double d1 = viewVector.y * viewDistance;
                double d2 = viewVector.z * viewDistance;
                chaosSpawnerEntity.getLookControl().setLookAt(chaosSpawnerEntity.getX() + d0, chaosSpawnerEntity.getY(0.5) + d1, chaosSpawnerEntity.getZ() + d2);
                --lookTime;
            } else {
                double b = onlyHorizontal ? chaosSpawnerEntity.getEyeY() : lookAt.getEyeY();
                chaosSpawnerEntity.getLookControl().setLookAt(lookAt.getX(), b, lookAt.getZ());
                --lookTime;
            }
        }
    }
}
