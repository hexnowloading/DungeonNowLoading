package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.monster.HollowEntity;
import dev.hexnowloading.dungeonnowloading.entity.passive.WhimperEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class WhimperRandomMoveGoal extends Goal {
    private final WhimperEntity whimper;

    public WhimperRandomMoveGoal(WhimperEntity whimper) {
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.whimper = whimper;
    }

    @Override
    public boolean canUse() {
        return !this.whimper.getMoveControl().hasWanted() && this.whimper.getRandom().nextInt(reducedTickDelay(7)) == 0;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void tick() {
        for (int i = 0; i < 3; ++i) {
            BlockPos blockPos = this.whimper.blockPosition().offset(this.whimper.getRandom().nextInt(15) - 7, this.whimper.getRandom().nextInt(11) - 5, this.whimper.getRandom().nextInt(15) - 7);
            if (this.whimper.level().isEmptyBlock(blockPos)) {
                this.whimper.getMoveControl().setWantedPosition((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, 0.25);
                if (this.whimper.getTarget() == null) {
                    this.whimper.getLookControl().setLookAt((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, 180.0F, 20.0F);
                }
                break;
            }
        }
    }
}
