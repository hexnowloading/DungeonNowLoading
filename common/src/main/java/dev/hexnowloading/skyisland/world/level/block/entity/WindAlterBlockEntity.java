package dev.hexnowloading.skyisland.world.level.block.entity;

import dev.hexnowloading.skyisland.registry.SkyislandBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;

import net.minecraft.world.level.block.state.BlockState;

public class WindAlterBlockEntity extends BlockEntity {
    private static final int EFFECT_RANGE = 10;
    public WindAlterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(SkyislandBlockEntityTypes.WIND_ALTER.get(), pPos, pBlockState);
    }
}
