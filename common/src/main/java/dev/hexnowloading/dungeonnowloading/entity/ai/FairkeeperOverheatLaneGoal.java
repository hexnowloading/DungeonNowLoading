package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.ai.control.FairkeeperFlyingMoveControl;
import dev.hexnowloading.dungeonnowloading.entity.boss.FairkeeperEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class FairkeeperOverheatLaneGoal extends Goal {

    private final FairkeeperEntity fairkeeperEntity;
    private final FairkeeperEntity.FairkeeperState state;
    private final double altitude;
    private final double strafeMaxSpeed;
    private final double strafeMinSpeed;
    private final double strafeStopAccuracy;
    private final int strafeToOverheatInterval;
    private final int nodeCount;
    private final int nodeGridLength;
    private final int nodeGridSpacing;
    private final int nodePlacingTime;
    private final int nodePlacingTimeVariety;

    List<BlockPos> nodePos = new ArrayList<>();
    private int phase;
    private int tickCount;

    public FairkeeperOverheatLaneGoal(FairkeeperEntity fairkeeperEntity, FairkeeperEntity.FairkeeperState state, double altitude, double strafeMaxSpeed, double strafeMinSpeed, double strafeStopAccuracy, int strafeToOverheatInterval, int nodeCount, int nodeGridLength, int nodeGridSpacing, int nodePlacingTime, int nodePlacingTimeVariety) {
        this.fairkeeperEntity = fairkeeperEntity;
        this.state = state;
        this.altitude = altitude;
        this.strafeMaxSpeed = strafeMaxSpeed;
        this.strafeMinSpeed = strafeMinSpeed;
        this.strafeStopAccuracy = strafeStopAccuracy;
        this.strafeToOverheatInterval = strafeToOverheatInterval / 2;
        this.nodeCount = nodeCount;
        this.nodeGridLength = nodeGridLength;
        this.nodeGridSpacing = nodeGridSpacing;
        this.nodePlacingTime = nodePlacingTime / 2;
        this.nodePlacingTimeVariety = nodePlacingTimeVariety / 2;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.fairkeeperEntity.isState(this.state);
    }

    @Override
    public void start() {
        this.tickCount = 0;
        this.phase = 0;
        this.nodePos = new ArrayList<>();
        int actualSpacing = this.nodeGridSpacing + 1;
        int halfGridLength = this.nodeGridLength / 2;
        BlockPos startPos = new BlockPos(this.fairkeeperEntity.getSpawnPoint().getX() - actualSpacing * halfGridLength, this.fairkeeperEntity.getSpawnPoint().getY(), this.fairkeeperEntity.getSpawnPoint().getZ() - actualSpacing * halfGridLength);
        List<BlockPos> possibleNodePos = new ArrayList<>();
        for (int x = 0; x < this.nodeGridLength; x++) {
            for (int z = 0; z < this.nodeGridLength; z++) {
                possibleNodePos.add(new BlockPos(startPos.getX() + x * actualSpacing, startPos.getY(), startPos.getZ() + z * actualSpacing));
            }
        }
        for (int i = 0; i < this.nodeCount; i++) {
            int randomElement = this.fairkeeperEntity.getRandom().nextInt(possibleNodePos.size());
            this.nodePos.add(possibleNodePos.get(randomElement));
            possibleNodePos.remove(randomElement);
        }
    }

    @Override
    public void tick() {
        //System.out.println(this.phase);
        if (this.phase == 0) {
            ((FairkeeperFlyingMoveControl) this.fairkeeperEntity.getMoveControl()).setWantedPositionWithSpeed(this.fairkeeperEntity.getSpawnPoint().getX() + 0.5d, this.fairkeeperEntity.getSpawnPoint().getY() + this.altitude, this.fairkeeperEntity.getSpawnPoint().getZ() + 0.5d, this.strafeMaxSpeed, this.strafeMinSpeed, this.strafeStopAccuracy);
            this.phase++;
        } else if (this.phase == 1) {
            if (!this.fairkeeperEntity.getMoveControl().hasWanted()) {
                this.phase++;
                this.tickCount = this.strafeToOverheatInterval;
            }
        } else if (this.phase == 2) {
            if (this.tickCount > 0) {
                this.tickCount--;
                return;
            }
            this.phase++;
        } else if (this.phase == 3) {
            if (this.nodePos.isEmpty()) {
                this.phase++;
                return;
            }
            if (this.tickCount > 0) {
                this.tickCount--;
                return;
            }
            int randomElement = this.fairkeeperEntity.getRandom().nextInt(this.nodePos.size());
            this.fairkeeperEntity.level().setBlock(this.nodePos.get(randomElement), Blocks.MAGMA_BLOCK.defaultBlockState(), Block.UPDATE_ALL);
            this.nodePos.remove(randomElement);
            this.tickCount = (int) (this.nodePlacingTime + this.nodePlacingTimeVariety * this.fairkeeperEntity.getRandom().nextFloat());
        } else if (this.phase == 4) {
            this.fairkeeperEntity.stopAttacking(60);
        }
    }
}
