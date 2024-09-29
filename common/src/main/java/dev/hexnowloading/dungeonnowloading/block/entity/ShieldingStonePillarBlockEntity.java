package dev.hexnowloading.dungeonnowloading.block.entity;

import dev.hexnowloading.dungeonnowloading.registry.DNLBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ShieldingStonePillarBlockEntity extends BlockEntity {

    public ShieldingStonePillarBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(DNLBlockEntityTypes.SHIELDING_STONE_PILLAR.get(), blockPos, blockState);
    }


}
