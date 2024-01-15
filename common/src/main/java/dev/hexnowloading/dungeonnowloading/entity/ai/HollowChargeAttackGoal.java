package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.monster.HollowEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class HollowChargeAttackGoal extends Goal {

    private final HollowEntity hollowEntity;

    public HollowChargeAttackGoal(HollowEntity hollowEntity) {
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.hollowEntity = hollowEntity;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.hollowEntity.getTarget();
        if (target != null && target.isAlive() && !this.hollowEntity.getMoveControl().hasWanted() && this.hollowEntity.getRandom().nextInt(reducedTickDelay(7)) == 0) {
            return this.hollowEntity.distanceToSqr(target) > 4.0;
        } else {
            return false;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.hollowEntity.getMoveControl().hasWanted() && /*this.hollowEntity.isCharging() &&*/ this.hollowEntity.getTarget() != null && this.hollowEntity.getTarget().isAlive();
    }

    @Override
    public void start() {
        LivingEntity target = this.hollowEntity.getTarget();
        if (target != null) {
            Vec3 eyePosition = target.getEyePosition();
            this.hollowEntity.getMoveControl().setWantedPosition(eyePosition.x, eyePosition.y, eyePosition.z, 1.0);
        }

        this.hollowEntity.setCharging(true);
    }

    @Override
    public void stop() {
        this.hollowEntity.setCharging(false);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = this.hollowEntity.getTarget();
        if (target != null) {
            if (this.hollowEntity.getBoundingBox().intersects(target.getBoundingBox())) {
                this.hollowEntity.doHurtTarget(target);
                this.hollowEntity.setCharging(false);
            } else {
                double $$1 = this.hollowEntity.distanceToSqr(target);
                if ($$1 < 9.0) {
                    Vec3 $$2 = target.getEyePosition();
                    this.hollowEntity.getMoveControl().setWantedPosition($$2.x, $$2.y, $$2.z, 1.0);
                }
            }

        }
    }
}
