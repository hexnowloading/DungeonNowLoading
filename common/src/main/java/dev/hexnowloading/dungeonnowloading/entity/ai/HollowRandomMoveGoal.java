package dev.hexnowloading.dungeonnowloading.entity.ai;

import dev.hexnowloading.dungeonnowloading.entity.monster.HollowEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Vex;

import java.util.EnumSet;

public class HollowRandomMoveGoal extends Goal {
    
    private final HollowEntity hollowEntity;
    
    public HollowRandomMoveGoal(HollowEntity hollowEntity) {
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.hollowEntity = hollowEntity;
    }
    
    @Override
    public boolean canUse() {
        return !this.hollowEntity.getMoveControl().hasWanted() && this.hollowEntity.getRandom().nextInt(reducedTickDelay(7)) == 0;
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void tick() {
        for (int i = 0; i < 3; ++i) {
            BlockPos blockPos = this.hollowEntity.blockPosition().offset(this.hollowEntity.getRandom().nextInt(15) - 7, this.hollowEntity.getRandom().nextInt(11) - 5, this.hollowEntity.getRandom().nextInt(15) - 7);
            if (this.hollowEntity.level().isEmptyBlock(blockPos)) {
                this.hollowEntity.getMoveControl().setWantedPosition((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, 0.25);
                if (this.hollowEntity.getTarget() == null) {
                    this.hollowEntity.getLookControl().setLookAt((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, 180.0F, 20.0F);
                }
                break;
            }
        }
    }
}
