package dev.hexnowloading.dungeonnowloading.block;

import dev.hexnowloading.dungeonnowloading.registry.DNLProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PileBlock extends Block implements SimpleWaterloggedBlock {
    public static final int MAX_PILE = 4;
    protected static final VoxelShape ONE_AABB = Block.box(0, 0, 0, 16, 2,16);
    protected static final VoxelShape TWO_AABB = Block.box(0, 0, 0, 16, 3,16);
    protected static final VoxelShape THREE_AABB = Block.box(0, 0, 0, 16, 4,16);
    protected static final VoxelShape FOUR_AABB = Block.box(0, 0, 0, 16, 5,16);
    public static final IntegerProperty PILE = DNLProperties.PILE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public PileBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE).setValue(PILE, Integer.valueOf(1)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(WATERLOGGED, PILE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext ctx) {
        switch (state.getValue(PILE)) {
            case 1:
            default:
                return ONE_AABB;
            case 2:
                return TWO_AABB;
            case 3:
                return THREE_AABB;
            case 4:
                return FOUR_AABB;
        }
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState = ctx.getLevel().getBlockState(ctx.getClickedPos());
        if (blockState.is(this)) {
            return blockState.setValue(PILE, Integer.valueOf(Math.min(4, blockState.getValue(PILE) + 1)));
        } else {
            FluidState fluidstate = ctx.getLevel().getFluidState(ctx.getClickedPos());
            boolean flag = fluidstate.getType() == Fluids.WATER;
            return super.getStateForPlacement(ctx).setValue(WATERLOGGED, Boolean.valueOf(flag));
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
        return !ctx.isSecondaryUseActive() && ctx.getItemInHand().is(this.asItem()) && state.getValue(PILE) < 4 ? true : super.canBeReplaced(state, ctx);
    }

    @Override
    public boolean isPathfindable(BlockState p_154341_, BlockGetter p_154342_, BlockPos p_154343_, PathComputationType p_154344_) {
        return false;
    }
}
