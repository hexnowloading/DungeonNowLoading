package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.boss.ChaosSpawnerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChaosSpawnerMoveControl extends MoveControl {

    public ChaosSpawnerMoveControl(Mob mob) {
        super(mob);
    }


    @Override
    public void tick() {
        if (this.operation == Operation.MOVE_TO) {
            this.operation = MoveControl.Operation.WAIT;
            double d0 = this.wantedX - this.mob.getX();
            double d1 = this.wantedY - this.mob.getY();
            double d2 = this.wantedZ - this.mob.getZ();
            double d3 = Mth.sqrt((float) (d0 * d0 + d1 * d1 + d2 * d2));
            d1 /= d3;
            Vec3 vec3 = new Vec3(d0, d1, d2);
            vec3 = vec3.normalize();
            float f0 = (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
            this.mob.setSpeed(f0);
            this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(0.0D, d1 * 0.1D, 0.0D));
        }
    }

    @Override
    public void strafe(float $$0, float $$1) {
    }

    /*@Override
    public void tick() {
        if (operation == Operation.MOVE_TO) {
            this.operation = MoveControl.Operation.WAIT;
            double x = this.wantedX - this.mob.getX();
            double y = this.wantedY - this.mob.getY();
            double z = this.wantedZ - this.mob.getZ();
            Vec3 vec3 = new Vec3(x, y, z);
            double d = vec3.length();
            vec3 = vec3.normalize();
            if (this.canReach(vec3, Mth.ceil(d))) {
                this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(vec3.scale(0.1)));
            }
        }
    }*/

    private boolean canReach(Vec3 vec3, int d) {
        /*AABB aabb = this.mob.getBoundingBox();
        for (int i = 1; i < d; ++i) {
            aabb = aabb.move(vec3);
            if (!this.mob.level().noCollision(this.mob, aabb)) {
                return false;
            }
        }*/
        return true;
    }
}
