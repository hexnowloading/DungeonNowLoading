package dev.hexnowloading.dungeonnowloading.block;

import dev.hexnowloading.dungeonnowloading.block.property.RedstoneLaneMode;
import dev.hexnowloading.dungeonnowloading.registry.DNLProperties;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class RedstoneLaneIBlock extends DirectionalBlock {

    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final EnumProperty<RedstoneLaneMode> REDSTONE_LANE_MODE = DNLProperties.REDSTONE_LANE_MODE;
    private static final IntegerProperty REDSTONE_LANE_POWER = DNLProperties.REDSTONE_LANE_POWER;

    public RedstoneLaneIBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(REDSTONE_LANE_MODE, RedstoneLaneMode.UNPOWERED).setValue(REDSTONE_LANE_POWER, 0));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite()).setValue(REDSTONE_LANE_MODE, RedstoneLaneMode.UNPOWERED).setValue(REDSTONE_LANE_POWER, 0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING);
        stateBuilder.add(REDSTONE_LANE_MODE);
        stateBuilder.add(REDSTONE_LANE_POWER);
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }
}
