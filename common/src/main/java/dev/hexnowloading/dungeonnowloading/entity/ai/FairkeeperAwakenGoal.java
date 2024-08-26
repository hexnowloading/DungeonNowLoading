package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.boss.FairkeeperEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class FairkeeperAwakenGoal extends Goal {
    private final FairkeeperEntity fairkeeperEntity;
    private double speedModifier;
    private double maxSpeed;
    private double distanceToTarget;
    private Vec3 targetPos;

    public FairkeeperAwakenGoal(FairkeeperEntity fairkeeperEntity, double speedModifier) {
        this.fairkeeperEntity = fairkeeperEntity;
        this.speedModifier = speedModifier;
        this.maxSpeed = speedModifier;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.fairkeeperEntity.isState(FairkeeperEntity.FairkeeperState.AWAKENING);
    }

    @Override
    public boolean canContinueToUse() {
        return this.fairkeeperEntity.position().distanceToSqr(targetPos) < 1.0E-4f;
    }

    @Override
    public void start() {
        this.fairkeeperEntity.setState(FairkeeperEntity.FairkeeperState.IDLE);
        double dx = this.fairkeeperEntity.getX();
        double dy = this.fairkeeperEntity.getY() + 20.0;
        double dz = this.fairkeeperEntity.getZ();
        this.targetPos = new Vec3(dx, dy, dz);
        this.distanceToTarget = this.fairkeeperEntity.position().distanceToSqr(targetPos);
        //this.fairkeeperEntity.getMoveControl().setWantedPosition(dx, dy, dz, 1.0);
        //this.distanceToTarget = this.targetPos.distanceToSqr(this.targetPos);
    }

    @Override
    public void stop() {
        this.fairkeeperEntity.setDeltaMovement(Vec3.ZERO);
        this.fairkeeperEntity.getNavigation().stop();
    }

    @Override
    public void tick() {
        double progressToTarget = this.fairkeeperEntity.position().distanceToSqr(this.targetPos) / this.distanceToTarget;
        //this.speedModifier = this.speedModifier * progressToTarget;
        this.speedModifier = 0.1 + (this.maxSpeed - 0.1) * (1 - progressToTarget * progressToTarget);
        this.fairkeeperEntity.getMoveControl().setWantedPosition(this.targetPos.x, this.targetPos.y, this.targetPos.z, this.speedModifier);
        System.out.println(this.fairkeeperEntity.position().distanceToSqr(this.targetPos) + " / " + this.distanceToTarget + " / " + this.speedModifier);
    }
}
