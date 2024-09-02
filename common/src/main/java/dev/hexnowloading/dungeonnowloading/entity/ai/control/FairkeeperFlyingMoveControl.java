package dev.hexnowloading.dungeonnowloading.entity.ai.control;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class FairkeeperFlyingMoveControl extends MoveControl {

    private double maxSpeed;
    private double minSpeed;
    private double distanceToTarget;
    private Vec3 targetPos;
    private double stopAccuracy;

    public FairkeeperFlyingMoveControl(Mob mob) {
        super(mob);
    }

    @Override
    public void setWantedPosition(double wantedX, double wantedY, double wantedZ, double speedModifier) {
        this.maxSpeed = speedModifier;
        this.minSpeed = 0.0;
        this.targetPos = new Vec3(wantedX, wantedY, wantedZ);
        this.distanceToTarget = this.mob.position().distanceTo(targetPos);
        super.setWantedPosition(wantedX, wantedY, wantedZ, speedModifier);
    }

    public void setWantedPositionWithSpeed(double wantedX, double wantedY, double wantedZ, double maxSpeed, double minSpeed) {
        this.maxSpeed = maxSpeed;
        this.minSpeed = minSpeed;
        this.targetPos = new Vec3(wantedX, wantedY, wantedZ);
        this.distanceToTarget = this.mob.position().distanceTo(targetPos);
        this.setWantedPositionWithSpeed(wantedX, wantedY, wantedZ, maxSpeed, minSpeed, 0.99d);
    }

    public void setWantedPositionWithSpeed(double wantedX, double wantedY, double wantedZ, double maxSpeed, double minSpeed, double stopAccuracy) {
        this.maxSpeed = maxSpeed;
        this.minSpeed = minSpeed;
        this.targetPos = new Vec3(wantedX, wantedY, wantedZ);
        this.distanceToTarget = this.mob.position().distanceTo(targetPos);
        this.stopAccuracy = stopAccuracy;
        super.setWantedPosition(wantedX, wantedY, wantedZ, speedModifier);
    }


    public void setWaitOperation() {
        this.operation = Operation.WAIT;
    }

    @Override
    public void tick() {
        if (this.operation == Operation.MOVE_TO) {
            this.mob.setNoGravity(true);

            Vec3 direction = targetPos.subtract(this.mob.position()).normalize();
            double t = 1 - this.mob.position().distanceTo(this.targetPos) / this.distanceToTarget;
            if (t >= this.stopAccuracy || t < 0 || Double.isNaN(t)) {
                t = 1;
                this.mob.setPos(this.wantedX, this.wantedY, this.wantedZ);
                this.operation = Operation.WAIT;
            }
            double speed = this.minSpeed + (this.maxSpeed - this.minSpeed) * (1 - t * t);
            Vec3 velocity = direction.scale(speed);
            this.mob.setDeltaMovement(velocity);
        } else {
            this.mob.setYya(0.0F);
            this.mob.setZza(0.0F);
        }
    }
}
