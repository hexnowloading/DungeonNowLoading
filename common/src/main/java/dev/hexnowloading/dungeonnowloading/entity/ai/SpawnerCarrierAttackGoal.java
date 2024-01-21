package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.monster.SpawnerCarrierEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class SpawnerCarrierAttackGoal extends MeleeAttackGoal {
    private final SpawnerCarrierEntity spawnerCarrierEntity;

    public SpawnerCarrierAttackGoal(SpawnerCarrierEntity spawnerCarrierEntity, float speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(spawnerCarrierEntity, speedModifier, followingTargetEvenIfNotSeen);
        this.spawnerCarrierEntity = spawnerCarrierEntity;
    }

    @Override
    protected double getAttackReachSqr(LivingEntity livingEntity) {
        float RANGE = 1.6F;
        float Bb = spawnerCarrierEntity.getBbWidth() - 0.1F;
        return (double)(Bb * RANGE *Bb * RANGE + livingEntity.getBbWidth());
    }
}
