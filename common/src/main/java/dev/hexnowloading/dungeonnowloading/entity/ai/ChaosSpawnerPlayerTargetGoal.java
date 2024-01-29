package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.boss.ChaosSpawnerEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.Team;

import java.util.List;
import java.util.stream.Collectors;

public class ChaosSpawnerPlayerTargetGoal extends Goal {

    private final ChaosSpawnerEntity chaosSpawnerEntity;
    private int nextScanTick = reducedTickDelay(20);
    private double range;
    private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(range).ignoreLineOfSight().ignoreInvisibilityTesting();

    public ChaosSpawnerPlayerTargetGoal(ChaosSpawnerEntity chaosSpawnerEntity, double range) {
        this.chaosSpawnerEntity = chaosSpawnerEntity;
        this.range = range;
    }

    @Override
    public boolean canUse() {
        if (nextScanTick > 0) {
            --nextScanTick;
        } else {
            if (chaosSpawnerEntity.getPhase() != 0) {
                nextScanTick = reducedTickDelay(60);
                List<Player> list = chaosSpawnerEntity.level().getNearbyPlayers(attackTargeting, chaosSpawnerEntity, chaosSpawnerEntity.getBoundingBox().inflate(range));
                list = list.stream().filter(player -> !player.getAbilities().instabuild).collect(Collectors.toList());
                if (!list.isEmpty()) {
                    chaosSpawnerEntity.setTarget(list.get(chaosSpawnerEntity.getRandom().nextInt(list.size())));
                    return true;
                }
            }
            chaosSpawnerEntity.setTarget(null);
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity livingEntity = chaosSpawnerEntity.getTarget();
        if (livingEntity == null) {
            return false;
        }
        if (!chaosSpawnerEntity.canAttack(livingEntity, TargetingConditions.DEFAULT)) {
            return false;
        }
        if (livingEntity instanceof Player) {
            return !((Player) livingEntity).getAbilities().instabuild;
        }
        if (livingEntity.getTeam() != null) {
            Team team = chaosSpawnerEntity.getTeam();
            Team team2 = livingEntity.getTeam();
            if (team == team2) {
                return false;
            }
        }
        double d = chaosSpawnerEntity.getFollowDistance();
        if (chaosSpawnerEntity.distanceToSqr(livingEntity) > d * d) {
            return false;
        }
        chaosSpawnerEntity.setTarget(livingEntity);
        return true;
    }
}
