package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.monster.HollowEntity;
import dev.hexnowloading.dungeonnowloading.entity.passive.WhimperEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class WhimperChargeAttackGoal extends Goal {
    private final WhimperEntity whimper;

    public WhimperChargeAttackGoal(WhimperEntity whimper) {
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.whimper = whimper;
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.whimper.getTarget();
        if (target != null && target.isAlive() && !this.whimper.getMoveControl().hasWanted() && this.whimper.getRandom().nextInt(reducedTickDelay(7)) == 0) {
            return this.whimper.distanceToSqr(target) > 4.0;
        } else {
            return false;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.whimper.getMoveControl().hasWanted() && /*this.whimper.isCharging() &&*/ this.whimper.getTarget() != null && this.whimper.getTarget().isAlive();
    }

    @Override
    public void start() {
        LivingEntity target = this.whimper.getTarget();
        if (target != null) {
            Vec3 eyePosition = target.getEyePosition();
            this.whimper.getMoveControl().setWantedPosition(eyePosition.x, eyePosition.y, eyePosition.z, 1.0);
        }

        this.whimper.setCharging(true);
    }

    @Override
    public void stop() {
        this.whimper.setCharging(false);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = this.whimper.getTarget();
        if (target != null) {
            if (this.whimper.getBoundingBox().intersects(target.getBoundingBox())) {
                this.whimper.doHurtTarget(target);
                this.whimper.setCharging(false);
            } else {
                double $$1 = this.whimper.distanceToSqr(target);
                if ($$1 < 9.0) {
                    Vec3 $$2 = target.getEyePosition();
                    this.whimper.getMoveControl().setWantedPosition($$2.x, $$2.y, $$2.z, 1.0);
                }
            }

        }
    }
}
