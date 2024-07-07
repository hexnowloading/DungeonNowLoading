package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.util.SlumberingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

import java.util.EnumSet;

public class SlumberingEntityRandomStrollGoal extends WaterAvoidingRandomStrollGoal {

    private Mob mob;

    public SlumberingEntityRandomStrollGoal(PathfinderMob pathfinderMob, double speedModifier) {
        super(pathfinderMob, speedModifier);
        this.mob = pathfinderMob;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (mob instanceof SlumberingEntity slumberingEntity && slumberingEntity.isStationary()) {
            return false;
        }
        return super.canUse();
    }
}
