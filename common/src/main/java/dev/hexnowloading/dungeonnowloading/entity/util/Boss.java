package dev.hexnowloading.dungeonnowloading.entity.util;

import net.minecraft.core.BlockPos;

public interface Boss {
    void resetBoss();

    boolean resetCondition();

    BlockPos resetRegionCenter();

    void targetRandomPlayer();

    boolean playerTargetingCondition();

    void postPlayerTargeting();
}
