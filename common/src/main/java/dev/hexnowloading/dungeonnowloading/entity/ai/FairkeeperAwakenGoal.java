package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.boss.FairkeeperEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class FairkeeperAwakenGoal extends Goal {
    private final FairkeeperEntity fairkeeperEntity;
    private final double maxSpeed;
    private final double minSpeed;
    private double distanceToTarget;
    private Vec3 targetPos;

    public FairkeeperAwakenGoal(FairkeeperEntity fairkeeperEntity, double maxSpeed, double minSpeed) {
        this.fairkeeperEntity = fairkeeperEntity;
        this.maxSpeed = maxSpeed;
        this.minSpeed = minSpeed;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.fairkeeperEntity.isState(FairkeeperEntity.FairkeeperState.AWAKENING);
    }

    @Override
    public void start() {
        double dx = this.fairkeeperEntity.getX();
        double dy = this.fairkeeperEntity.getY() + 20.0;
        double dz = this.fairkeeperEntity.getZ();
        this.targetPos = new Vec3(dx, dy, dz);
        this.distanceToTarget = this.fairkeeperEntity.position().distanceTo(targetPos);
    }

    @Override
    public void stop() {
        this.fairkeeperEntity.setDeltaMovement(Vec3.ZERO);
    }

    @Override
    public void tick() {
        double t = 1 - this.fairkeeperEntity.position().distanceTo(this.targetPos) / this.distanceToTarget;
        if (t >= 0.99) t = 1;
        double speed = this.minSpeed + (this.maxSpeed - this.minSpeed) * (1 - t * t);
        this.fairkeeperEntity.setDeltaMovement(0, speed, 0);
        if (t == 1) this.fairkeeperEntity.setState(FairkeeperEntity.FairkeeperState.IDLE);
    }
}
