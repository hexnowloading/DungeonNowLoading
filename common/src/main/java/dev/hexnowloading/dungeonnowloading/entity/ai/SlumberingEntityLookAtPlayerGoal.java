package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.util.SlumberingEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;

import java.util.EnumSet;

public class SlumberingEntityLookAtPlayerGoal extends LookAtPlayerGoal {

    private Mob mob;

    public SlumberingEntityLookAtPlayerGoal(Mob mob, Class<? extends LivingEntity> lookAtType, float lookDistance) {
        super(mob, lookAtType, lookDistance);
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (mob instanceof SlumberingEntity slumberingEntity && slumberingEntity.isStationary()) {
            return false;
        }
        return super.canUse();
    }
}
