package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.monster.ScuttleEntity;
import dev.hexnowloading.dungeonnowloading.entity.util.SlumberingEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class SlumberingEntityPlayerTargetGoal extends NearestAttackableTargetGoal<Player> {

    private Mob mob;

    public SlumberingEntityPlayerTargetGoal(Mob mob) {
        super(mob, Player.class, 10, true, true, null);
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (mob instanceof SlumberingEntity slumberingEntity && slumberingEntity.isStationary()) {
            return false;
        }
        return super.canUse();
    }
}
