package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.boss.ChaosSpawnerEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

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
        chaosSpawnerEntity.triggerSmashAttackAnimation();
        chaosSpawnerEntity.setAttackTick(100);
    }

    @Override
    public void tick() {
        if (chaosSpawnerEntity.getAttackTick() == 66) { // Only even number tick works for some reason
            chaosSpawnerEntity.playSound(SoundEvents.GENERIC_EXPLODE, 1.0F, 1.0F);
            ((ServerLevel) chaosSpawnerEntity.level()).sendParticles(ParticleTypes.POOF, chaosSpawnerEntity.getX(), chaosSpawnerEntity.getY(), chaosSpawnerEntity.getZ(), 50, 3.0D, 0.0D, 3.0D, 0.0D);
            AABB aabb = (new AABB(this.chaosSpawnerEntity.blockPosition())).inflate(8);
            List<LivingEntity> targets = this.chaosSpawnerEntity.level().getEntitiesOfClass(LivingEntity.class, aabb);
            for (LivingEntity mob : targets) {
                if (mob != this.chaosSpawnerEntity) {
                    if (mob instanceof Player player) {
                        this.pushNearbyPlayers(player);
                    } else {
                        this.pushNearbyMobs(mob);
                    }
                }
            }
        }
        if (chaosSpawnerEntity.getAttackTick() == 0) {
            chaosSpawnerEntity.stopAttacking(60);
        }
    }

    private void pushNearbyMobs(LivingEntity mob) {
        double knockbackStrength = 12.0D;
        int damageAmount = (int) (chaosSpawnerEntity.getAttackDamage() * 0.9F);
        double x = mob.getX() - chaosSpawnerEntity.getX();
        double z = mob.getZ() - chaosSpawnerEntity.getZ();
        double a = Math.max(x * x + z * z, 0.001);
        mob.push(x / a * knockbackStrength, 0.2, z / a * knockbackStrength);
        mob.hurt(chaosSpawnerEntity.damageSources().noAggroMobAttack(chaosSpawnerEntity), damageAmount);
    }

    private void pushNearbyPlayers(Player player) {
        double knockbackStrength = 12.0D;
        int damageAmount;
        if (player.isBlocking()) {
            player.disableShield(true);
            damageAmount = (int) (chaosSpawnerEntity.getAttackDamage() * 0.45F);
        } else {
            damageAmount = (int) (chaosSpawnerEntity.getAttackDamage() * 0.9F);
        }
        double x = player.getX() - chaosSpawnerEntity.getX();
        double z = player.getZ() - chaosSpawnerEntity.getZ();
        double a = Math.max(x * x + z * z, 0.001);
        player.push(x / a * knockbackStrength, 0.2, z / a * knockbackStrength);
        player.hurt(chaosSpawnerEntity.damageSources().mobAttack(chaosSpawnerEntity), damageAmount);
    }
}
