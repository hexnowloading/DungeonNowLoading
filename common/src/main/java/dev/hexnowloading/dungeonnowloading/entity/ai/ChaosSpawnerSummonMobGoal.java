package dev.hexnowloading.dungeonnowloading.entity.ai;

import com.google.common.collect.ImmutableList;
import dev.hexnowloading.dungeonnowloading.entity.boss.ChaosSpawnerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.Iterator;
import java.util.Random;

public class ChaosSpawnerSummonMobGoal extends Goal {

    private final ChaosSpawnerEntity chaosSpawnerEntity;
    private static final ImmutableList<BlockPos> MOB_SUMMON_POS = ImmutableList.of(
            new BlockPos(5, 1, 0),
            new BlockPos(-5, 1, 0),
            new BlockPos(0, 1, 5),
            new BlockPos(0, 1, -5)
    );

    private static final ImmutableList<EntityType<? extends Entity>> MOB_TYPE = ImmutableList.of(
            EntityType.ZOMBIE,
            EntityType.SKELETON,
            EntityType.SPIDER
    );

    public ChaosSpawnerSummonMobGoal(ChaosSpawnerEntity chaosSpawnerEntity) {
        this.chaosSpawnerEntity = chaosSpawnerEntity;
    }

    @Override
    public boolean canUse() {
        return chaosSpawnerEntity.isAttacking(ChaosSpawnerEntity.State.ABILITY_A) && chaosSpawnerEntity.getTarget() != null;
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void start() {
        super.start();
        chaosSpawnerEntity.setAttackTick(100);
    }

    @Override
    public void stop() {
        super.stop();
        chaosSpawnerEntity.setAttackTick(0);
        chaosSpawnerEntity.setDataState(ChaosSpawnerEntity.State.IDLE);
    }

    @Override
    public void tick() {
        if (chaosSpawnerEntity.getAttackTick() == 100) {
            summonRandomMob();
            for (int i = 0; i < 50; ++i) {
                ((ServerLevel) chaosSpawnerEntity.level()).sendParticles(ParticleTypes.CLOUD, chaosSpawnerEntity.getRandomX(1.0D), chaosSpawnerEntity.getY(), chaosSpawnerEntity.getRandomZ(1.0D), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    private void summonRandomMob() {
        for (BlockPos offsetAmount : MOB_SUMMON_POS) {
            BlockPos summonPos = chaosSpawnerEntity.blockPosition().offset(offsetAmount);
            summonMob(MOB_TYPE.get(chaosSpawnerEntity.getRandom().nextInt(MOB_TYPE.size())), summonPos);
        }
    }

    private void summonMob(EntityType<? extends Entity> entityType, BlockPos summonPos) {
        Mob mob = (Mob) entityType.create(chaosSpawnerEntity.level());
        if (mob != null) {
            mob.moveTo(summonPos, 0.0F, 0.0F);
            mob.finalizeSpawn((ServerLevel) chaosSpawnerEntity.level(), chaosSpawnerEntity.level().getCurrentDifficultyAt(summonPos), MobSpawnType.MOB_SUMMONED, null, null);
            chaosSpawnerEntity.level().addFreshEntity(mob);
        }
    }
}
