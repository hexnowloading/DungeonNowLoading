package dev.hexnowloading.dungeonnowloading.entity.ai.control;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class NoClipMoveControl extends MoveControl {

    private final LivingEntity parentEntity;

    public NoClipMoveControl(Mob mob) {
        super(mob);
        this.parentEntity = mob;
    }

    @Override
    public void tick() {
        if (this.operation == Operation.MOVE_TO) {
            double dx = this.getWantedX() - this.parentEntity.getX();
            double dy = this.getWantedY() - this.parentEntity.getY();
            double dz = this.getWantedZ() - this.parentEntity.getZ();
            Vec3 vec3 = new Vec3(dx, dy, dz);
            double dist = vec3.length();
            if (dist < (double)1.0f) {
                this.operation = Operation.WAIT;
                this.parentEntity.setDeltaMovement(Vec3.ZERO);
                return;
            }
            vec3 = vec3.normalize();
            if (this.canReach(vec3, Mth.ceil(dist))) {
                this.parentEntity.setDeltaMovement(this.parentEntity.getDeltaMovement().add(vec3.scale(0.1)));
            }
        }
    }

    private boolean canReach(Vec3 vec3, int i) {
        AABB aabb = this.parentEntity.getBoundingBox();

        for (int j = 1; j < i; j++) {
            aabb = aabb.move(vec3);
            if (!this.parentEntity.level().noCollision(parentEntity, aabb)) {
                return false;
            }
        }

        return true;
    }
}
