package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.boss.ChaosSpawnerEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.Arrays;
import java.util.EnumSet;

public class ChaosSpawnerRandomLookAroundGoal extends Goal {
    private final ChaosSpawnerEntity chaosSpawner;
    private double relX;
    private double relZ;
    private int lookTime;

    private final ChaosSpawnerEntity.State[] lookAroundOn = {
            ChaosSpawnerEntity.State.IDLE,
            ChaosSpawnerEntity.State.SHOOT_GHOST_BULLET_SINGLE
    };

    public ChaosSpawnerRandomLookAroundGoal(ChaosSpawnerEntity chaosSpawner) {
        this.chaosSpawner = chaosSpawner;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.chaosSpawner.getRandom().nextFloat() < 0.02F && Arrays.stream(lookAroundOn).anyMatch(state -> state == chaosSpawner.getState());
    }

    @Override
    public boolean canContinueToUse() {
        return this.lookTime >= 0 && Arrays.stream(lookAroundOn).anyMatch(state -> state == chaosSpawner.getState());
    }

    @Override
    public void start() {
        double $$0 = (Math.PI * 2) * this.chaosSpawner.getRandom().nextDouble();
        this.relX = Math.cos($$0);
        this.relZ = Math.sin($$0);
        this.lookTime = 20 + this.chaosSpawner.getRandom().nextInt(20);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        --this.lookTime;
        this.chaosSpawner.getLookControl().setLookAt(this.chaosSpawner.getX() + this.relX, this.chaosSpawner.getEyeY(), this.chaosSpawner.getZ() + this.relZ);
    }

}
