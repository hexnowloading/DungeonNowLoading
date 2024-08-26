package dev.hexnowloading.dungeonnowloading.entity.ai.control;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class FairkeeperFlyingMoveControl extends MoveControl {

    public FairkeeperFlyingMoveControl(Mob mob) {
        super(mob);
    }

    @Override
    public void tick() {
        if (this.operation == Operation.JUMPING) {

        } else if (this.operation == Operation.MOVE_TO) {
            this.operation = Operation.WAIT;
            this.mob.setNoGravity(true);
            double dx = this.wantedX - this.mob.getX();
            double dy = this.wantedY - this.mob.getY();
            double dz = this.wantedZ - this.mob.getZ();
            Vec3 vec3 = new Vec3(dx, dy, dz);
            double dist = vec3.lengthSqr();
            if (dist < 2.5000003E-7F) {
                this.mob.setYya(0.0F);
                this.mob.setZza(0.0F);
                return;
            }

            float sp = this.mob.onGround() ? (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)) : (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.FLYING_SPEED));

            double distHorizontal = Math.sqrt(dx * dx + dz * dz);
            if (Math.abs(dy) > 1.0E-5F || Math.abs(distHorizontal) > 1.0E-5F) {
                this.mob.setYya(dy > 0.0 ? sp : -sp);
            }
        } else {
            this.mob.setYya(0.0F);
            this.mob.setZza(0.0F);
        }
    }
}
