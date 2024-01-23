package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.boss.ChaosSpawnerEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class ChaosSpawnerPushGoal extends Goal {
    private final ChaosSpawnerEntity chaosSpawnerEntity;

    public ChaosSpawnerPushGoal(ChaosSpawnerEntity chaosSpawnerEntity) {
        this.chaosSpawnerEntity = chaosSpawnerEntity;
    }

    @Override
    public boolean canUse() {
        return chaosSpawnerEntity.isAttacking(ChaosSpawnerEntity.State.PUSH) && chaosSpawnerEntity.getTarget() != null;
    }

    @Override
    public void start() {
        super.start();
        chaosSpawnerEntity.setAttackTick(100);
    }

    @Override
    public void tick() {
        if (chaosSpawnerEntity.getAttackTick() == 65) {
            for (Player player : chaosSpawnerEntity.getPushTargets()) {
                pushNearbyPlayers(player);
            }
        }
        if (chaosSpawnerEntity.getAttackTick() == 0) {
            chaosSpawnerEntity.stopAttacking(60);
        }
    }

    private void pushNearbyPlayers(Player player) {
        double knockbackStrength = 12.0D;
        int damageAmount = (int) chaosSpawnerEntity.getAttackDamage();
        chaosSpawnerEntity.level().playSound(null, chaosSpawnerEntity.getX(), chaosSpawnerEntity.getY(), chaosSpawnerEntity.getZ(), SoundEvents.GENERIC_EXPLODE, chaosSpawnerEntity.getSoundSource(), 10.0F, 1.0F);
        ((ServerLevel) chaosSpawnerEntity.level()).sendParticles(ParticleTypes.POOF, chaosSpawnerEntity.getX(), chaosSpawnerEntity.getY(), chaosSpawnerEntity.getZ(), 50, 3.0D, 0.0D, 3.0D, 0.0D);
        double x = player.getX() - chaosSpawnerEntity.getX();
        double z = player.getZ() - chaosSpawnerEntity.getZ();
        double a = Math.max(x * x + z * z, 0.001);
        player.push(x / a * knockbackStrength, 0.2, z / a * knockbackStrength);
        player.hurt(chaosSpawnerEntity.damageSources().mobAttack(chaosSpawnerEntity), damageAmount);
    }
}
