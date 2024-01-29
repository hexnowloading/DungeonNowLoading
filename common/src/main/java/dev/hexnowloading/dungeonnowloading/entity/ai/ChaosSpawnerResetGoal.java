package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.config.BossConfig;
import dev.hexnowloading.dungeonnowloading.entity.boss.ChaosSpawnerEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ChaosSpawnerResetGoal extends Goal {

    private final ChaosSpawnerEntity chaosSpawnerEntity;
    private final double range;

    public ChaosSpawnerResetGoal(ChaosSpawnerEntity chaosSpawnerEntity, double range) {
        this.chaosSpawnerEntity = chaosSpawnerEntity;
        this.range = range;
    }

    @Override
    public boolean canUse() {
        if (!chaosSpawnerEntity.isSleeping() && BossConfig.TOGGLE_BOSS_RESET.get()) {
            AABB aabb = new AABB(chaosSpawnerEntity.blockPosition()).inflate(range);
            List<Player> list = chaosSpawnerEntity.level().getEntitiesOfClass(Player.class, aabb);
            list.removeIf(player -> !player.isAlive());
            return list.isEmpty();
        }
        return false;
    }

    @Override
    public void start() {
        for (int i = 0; i < 50; ++i) {
            ((ServerLevel) chaosSpawnerEntity.level()).sendParticles(ParticleTypes.POOF, chaosSpawnerEntity.getRandomX(1.0D), chaosSpawnerEntity.getRandomY(), chaosSpawnerEntity.getRandomZ(1.0D), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
        chaosSpawnerEntity.disableBossBar();
        chaosSpawnerEntity.setDataState(ChaosSpawnerEntity.State.SLEEPING);
        chaosSpawnerEntity.setAwakeningTick(0);
        chaosSpawnerEntity.setAttackTick(0);
        chaosSpawnerEntity.setPhase(0);
        chaosSpawnerEntity.setBarrierNorthTick(-1);
        chaosSpawnerEntity.setBarrierEastTick(-1);
        chaosSpawnerEntity.setBarrierSouthTick(-1);
        chaosSpawnerEntity.setBarrierWestTick(-1);
        chaosSpawnerEntity.setBarrierUpTick(-1);
        chaosSpawnerEntity.setBarrierDownTick(-1);
        chaosSpawnerEntity.setTarget(null);
        chaosSpawnerEntity.setHealth(chaosSpawnerEntity.getMaxHealth());
        chaosSpawnerEntity.setPos(chaosSpawnerEntity.getSpawnPointPos().getX() + 0.5, chaosSpawnerEntity.getSpawnPointPos().getY(), chaosSpawnerEntity.getSpawnPointPos().getZ() + 0.5);
        chaosSpawnerEntity.clearParticipatingPlayers();
        chaosSpawnerEntity.triggerSleepAnimation();
    }
}
