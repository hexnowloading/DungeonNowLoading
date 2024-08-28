package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.util.Boss;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class BossTargetSelectorGoal extends Goal {

    private final LivingEntity livingEntity;
    private final double range;
    private final TargetingConditions attackTargeting;

    public BossTargetSelectorGoal(LivingEntity livingEntity, double range) {
        this.livingEntity = livingEntity;
        this.range = range;
        this.attackTargeting = TargetingConditions.forCombat().range(range).ignoreLineOfSight().ignoreInvisibilityTesting();
        setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        return livingEntity instanceof Boss boss && boss.playerTargetingCondition();
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        List<Player> list = livingEntity.level().getNearbyPlayers(attackTargeting, livingEntity, livingEntity.getBoundingBox().inflate(range));
        list = list.stream().filter(player -> !player.getAbilities().instabuild).toList();
        if (list.isEmpty()) return;
        if (livingEntity instanceof Mob mob) {
            mob.setTarget(list.get(mob.getRandom().nextInt(list.size())));
        }
    }

    @Override
    public void stop() {
        if (livingEntity instanceof Boss boss) {
            boss.postPlayerTargeting();
        }
    }
}
