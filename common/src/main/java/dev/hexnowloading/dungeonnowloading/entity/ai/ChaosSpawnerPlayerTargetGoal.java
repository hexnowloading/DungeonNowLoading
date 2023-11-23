package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.boss.ChaosSpawnerEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class ChaosSpawnerPlayerTargetGoal extends Goal {

    private final ChaosSpawnerEntity chaosSpawnerEntity;
    private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(64.0D).ignoreLineOfSight().ignoreInvisibilityTesting();
    private int nextScanTick = reducedTickDelay(20);

    public ChaosSpawnerPlayerTargetGoal(ChaosSpawnerEntity chaosSpawnerEntity) {
        this.chaosSpawnerEntity = chaosSpawnerEntity;
    }

    @Override
    public boolean canUse() {
        if (nextScanTick > 0) {
            --nextScanTick;
        } else {
            nextScanTick = reducedTickDelay(60);
            List<Player> list = chaosSpawnerEntity.level().getNearbyPlayers(attackTargeting, chaosSpawnerEntity, chaosSpawnerEntity.getBoundingBox().inflate(16.0D, 64.0D, 16.0D));
            if (!list.isEmpty()) {
                chaosSpawnerEntity.setTarget(list.get(chaosSpawnerEntity.getRandom().nextInt(list.size())));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity livingEntity = chaosSpawnerEntity.getTarget();
        return livingEntity != null && chaosSpawnerEntity.canAttack(livingEntity, TargetingConditions.DEFAULT);
    }
}
