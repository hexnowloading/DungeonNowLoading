package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.boss.FairkeeperEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class FairkeeperStrafeGoal extends Goal {

    private final FairkeeperEntity fairkeeperEntity;
    private final int range;
    private final int maxStrafeCount;
    private final int tries;
    private final int intervals;

    private int strafeCount;
    private int tickCount;

    public FairkeeperStrafeGoal(FairkeeperEntity fairkeeperEntity, int range, int maxStrafeCount, int tries, int intervalsInTick) {
        this.fairkeeperEntity = fairkeeperEntity;
        this.range = range;
        this.maxStrafeCount = maxStrafeCount;
        this.tries = tries;
        this.intervals = intervalsInTick / 2;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.fairkeeperEntity.isState(FairkeeperEntity.FairkeeperState.STRAFE);
    }

    @Override
    public void start() {
        this.strafeCount = 0;
        this.tickCount = 0;

        List<BlockPos> blockPosList = new ArrayList<>();
        BlockPos arenaCenter = this.fairkeeperEntity.getSpawnPoint();
    }

    @Override
    public void tick() {
        if (this.tickCount > this.intervals) {
            this.tickCount--;
            return;
        }

        this.strafeCount++;


        if (this.strafeCount == this.maxStrafeCount) {
            this.fairkeeperEntity.stopAttacking(60);
        }

    }
}
