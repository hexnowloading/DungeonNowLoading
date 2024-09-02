package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.ai.control.FairkeeperFlyingMoveControl;
import dev.hexnowloading.dungeonnowloading.entity.boss.FairkeeperEntity;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class FairkeeperAwakenGoal extends Goal {
    private final FairkeeperEntity fairkeeperEntity;
    private final double maxSpeed;
    private final double minSpeed;
    private final double stopAccuracy;
    private double distanceToTarget;
    private Vec3 targetPos;

    public FairkeeperAwakenGoal(FairkeeperEntity fairkeeperEntity, double maxSpeed, double minSpeed, double stopAccuracy) {
        this.fairkeeperEntity = fairkeeperEntity;
        this.maxSpeed = maxSpeed;
        this.minSpeed = minSpeed;
        this.stopAccuracy = stopAccuracy;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.fairkeeperEntity.isState(FairkeeperEntity.FairkeeperState.AWAKENING);
    }

    @Override
    public void start() {
        System.out.println(this.fairkeeperEntity.getY() + " / " + this.fairkeeperEntity.getSpawnPoint().getY());
        double dx = this.fairkeeperEntity.getSpawnPoint().getX() + 0.5d;
        double dy = this.fairkeeperEntity.getSpawnPoint().getY() + 20.0d;
        double dz = this.fairkeeperEntity.getSpawnPoint().getZ() + 0.5d;
        ((FairkeeperFlyingMoveControl) this.fairkeeperEntity.getMoveControl()).setWantedPositionWithSpeed(dx, dy, dz, this.maxSpeed, this.minSpeed, this.stopAccuracy);
    }

    @Override
    public void tick() {
        if (!this.fairkeeperEntity.getMoveControl().hasWanted()) {
            this.fairkeeperEntity.stopAttacking(20);
        }
    }
}
