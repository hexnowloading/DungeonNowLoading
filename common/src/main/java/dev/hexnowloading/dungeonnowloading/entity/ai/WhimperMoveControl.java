package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.passive.WhimperEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class WhimperMoveControl extends MoveControl {
    
    private final WhimperEntity whimper;

    public WhimperMoveControl(WhimperEntity whimper) {
        super(whimper);
        this.whimper = whimper;
    }

    @Override
    public void tick() {
        if (this.operation == MoveControl.Operation.MOVE_TO) {
            Vec3 vec3 = new Vec3(this.wantedX - this.whimper.getX(), this.wantedY - this.whimper.getY(), this.wantedZ - this.whimper.getZ());
            double d0 = vec3.length();
            if (d0 < this.whimper.getBoundingBox().getSize()) {
                this.operation = MoveControl.Operation.WAIT;
                this.whimper.setDeltaMovement(this.whimper.getDeltaMovement().scale(0.5));
            } else {
                this.whimper.setDeltaMovement(this.whimper.getDeltaMovement().add(vec3.scale(this.speedModifier * 0.05 / d0)));
                if (this.whimper.getTarget() == null) {
                    Vec3 deltaMovement = this.whimper.getDeltaMovement();
                    this.whimper.setYRot(-((float) Mth.atan2(deltaMovement.x, deltaMovement.z)) * 57.295776F);
                    this.whimper.yBodyRot = this.whimper.getYRot();
                } else {
                    double dx = this.whimper.getTarget().getX() - this.whimper.getX();
                    double dz = this.whimper.getTarget().getZ() - this.whimper.getZ();
                    this.whimper.setYRot(-((float)Mth.atan2(dx, dz)) * 57.295776F);
                    this.whimper.yBodyRot = this.whimper.getYRot();
                }
            }
        }
    }
}
