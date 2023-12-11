package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.monster.HollowEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.phys.Vec3;

public class HollowMoveControl extends MoveControl {

    private final HollowEntity hollowEntity;

    public HollowMoveControl(HollowEntity hollowEntity) {
        super(hollowEntity);
        this.hollowEntity = hollowEntity;
    }

    @Override
    public void tick() {
        if (this.operation == Operation.MOVE_TO) {
            Vec3 vec3 = new Vec3(this.wantedX - this.hollowEntity.getX(), this.wantedY - this.hollowEntity.getY(), this.wantedZ - this.hollowEntity.getZ());
            double d0 = vec3.length();
            if (d0 < this.hollowEntity.getBoundingBox().getSize()) {
                this.operation = Operation.WAIT;
                this.hollowEntity.setDeltaMovement(this.hollowEntity.getDeltaMovement().scale(0.5));
            } else {
                this.hollowEntity.setDeltaMovement(this.hollowEntity.getDeltaMovement().add(vec3.scale(this.speedModifier * 0.05 / d0)));
                if (this.hollowEntity.getTarget() == null) {
                    Vec3 deltaMovement = this.hollowEntity.getDeltaMovement();
                    this.hollowEntity.setYRot(-((float) Mth.atan2(deltaMovement.x, deltaMovement.z)) * 57.295776F);
                    this.hollowEntity.yBodyRot = this.hollowEntity.getYRot();
                } else {
                    double dx = this.hollowEntity.getTarget().getX() - this.hollowEntity.getX();
                    double dz = this.hollowEntity.getTarget().getZ() - this.hollowEntity.getZ();
                    this.hollowEntity.setYRot(-((float)Mth.atan2(dx, dz)) * 57.295776F);
                    this.hollowEntity.yBodyRot = this.hollowEntity.getYRot();
                }
            }
        }
    }
}
