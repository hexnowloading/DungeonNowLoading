package dev.hexnowloading.dungeonnowloading.block;

import dev.hexnowloading.dungeonnowloading.block.property.AllSides;
import dev.hexnowloading.dungeonnowloading.block.property.BarrierEdges;
import dev.hexnowloading.dungeonnowloading.block.property.BarrierVertexs;
import dev.hexnowloading.dungeonnowloading.block.property.BlockFaces;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlocks;
import dev.hexnowloading.dungeonnowloading.registry.DNLProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ChaosSpawnerBarrierEdgeBlock extends Block implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<BarrierEdges> BARRIER_EDGE = DNLProperties.BARRIER_EDGES;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape NORTH_AABB = Block.box(0.0, 0.0, 1.0, 16.0, 16.0, 2.0);
    protected static final VoxelShape SOUTH_AABB = Block.box(0.0, 0.0, 14.0, 16.0, 16.0, 15.0);
    protected static final VoxelShape EAST_AABB = Block.box(14.0, 0.0, 0.0, 15.0, 16.0, 16.0);
    protected static final VoxelShape WEST_AABB = Block.box(1.0, 0.0, 0.0, 2.0, 16.0, 16.0);
    protected static final VoxelShape UP_AABB = Block.box(0.0, 14.0, 0.0, 16.0, 15.0, 16.0);
    protected static final VoxelShape DOWN_AABB = Block.box(0.0, 1.0, 0.0, 16.0, 2.0, 16.0);
    public ChaosSpawnerBarrierEdgeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BARRIER_EDGE, BarrierEdges.UP).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING, BARRIER_EDGE, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        if (blockState.getValue(BARRIER_EDGE) == BarrierEdges.TOP) {
            return UP_AABB;
        } else if (blockState.getValue(BARRIER_EDGE) == BarrierEdges.BOTTOM) {
            return DOWN_AABB;
        } else {
            switch ((Direction) blockState.getValue(FACING)) {
                case NORTH:
                default:
                    return NORTH_AABB;
                case EAST:
                    return EAST_AABB;
                case SOUTH:
                    return SOUTH_AABB;
                case WEST:
                    return WEST_AABB;
            }
        }
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidstate = ctx.getLevel().getFluidState(ctx.getClickedPos());
        Direction direction = ctx.getNearestLookingDirection().getOpposite();
        BarrierEdges barrierEdges = BarrierEdges.UP;
        if (direction == Direction.UP) {
            direction = Direction.NORTH;
            barrierEdges = BarrierEdges.TOP;
        }
        if (direction == Direction.DOWN) {
            direction = Direction.NORTH;
            barrierEdges = BarrierEdges.BOTTOM;
        }
        return this.defaultBlockState()
                .setValue(FACING, direction)
                .setValue(BARRIER_EDGE, barrierEdges)
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
        if (player.isCreative()) {
            if (blockState.getValue(BARRIER_EDGE) == BarrierEdges.TOP || blockState.getValue(BARRIER_EDGE) == BarrierEdges.BOTTOM) {
                switch (blockState.getValue(FACING)) {
                    case NORTH -> level.setBlock(blockPos, blockState.setValue(FACING, Direction.EAST), 3);
                    case EAST -> level.setBlock(blockPos, blockState.setValue(FACING, Direction.SOUTH), 3);
                    case SOUTH -> level.setBlock(blockPos, blockState.setValue(FACING, Direction.WEST), 3);
                    case WEST -> level.setBlock(blockPos, blockState.setValue(FACING, Direction.NORTH), 3);
                }
            } else {
                switch (blockState.getValue(BARRIER_EDGE)) {
                    case UP -> level.setBlock(blockPos, blockState.setValue(BARRIER_EDGE, BarrierEdges.RIGHT), 3);
                    case RIGHT -> level.setBlock(blockPos, blockState.setValue(BARRIER_EDGE, BarrierEdges.DOWN), 3);
                    case DOWN -> level.setBlock(blockPos, blockState.setValue(BARRIER_EDGE, BarrierEdges.LEFT), 3);
                    case LEFT -> level.setBlock(blockPos, blockState.setValue(BARRIER_EDGE, BarrierEdges.UP), 3);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    public static void placeBarrier(Level level, BlockPos blockPos, int direction) {
        if (direction == 0) {
            level.setBlock(blockPos.offset(0, 1, 0), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.NORTH).setValue(BARRIER_EDGE, BarrierEdges.UP).setValue(WATERLOGGED, false), 2);
            level.setBlock(blockPos.offset(-1, 0, 0), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.NORTH).setValue(BARRIER_EDGE, BarrierEdges.RIGHT).setValue(WATERLOGGED, false), 2);
            level.setBlock(blockPos.offset(0, -1, 0), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.NORTH).setValue(BARRIER_EDGE, BarrierEdges.DOWN).setValue(WATERLOGGED, false), 2);
            level.setBlock(blockPos.offset(1, 0, 0), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.NORTH).setValue(BARRIER_EDGE, BarrierEdges.LEFT).setValue(WATERLOGGED, false), 2);
        } else if (direction == 1) {
            level.setBlock(blockPos.offset(0, 1, 0), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.EAST).setValue(BARRIER_EDGE, BarrierEdges.UP).setValue(WATERLOGGED, false), 2);
            level.setBlock(blockPos.offset(0, 0, -1), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.EAST).setValue(BARRIER_EDGE, BarrierEdges.RIGHT).setValue(WATERLOGGED, false), 2);
            level.setBlock(blockPos.offset(0, -1, 0), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.EAST).setValue(BARRIER_EDGE, BarrierEdges.DOWN).setValue(WATERLOGGED, false), 2);
            level.setBlock(blockPos.offset(0, 0, 1), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.EAST).setValue(BARRIER_EDGE, BarrierEdges.LEFT).setValue(WATERLOGGED, false), 2);
        } else if (direction == 2) {
            level.setBlock(blockPos.offset(0, 1, 0), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.SOUTH).setValue(BARRIER_EDGE, BarrierEdges.UP).setValue(WATERLOGGED, false), 2);
            level.setBlock(blockPos.offset(1, 0, 0), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.SOUTH).setValue(BARRIER_EDGE, BarrierEdges.RIGHT).setValue(WATERLOGGED, false), 2);
            level.setBlock(blockPos.offset(0, -1, 0), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.SOUTH).setValue(BARRIER_EDGE, BarrierEdges.DOWN).setValue(WATERLOGGED, false), 2);
            level.setBlock(blockPos.offset(-1, 0, 0), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.SOUTH).setValue(BARRIER_EDGE, BarrierEdges.LEFT).setValue(WATERLOGGED, false), 2);
        } else if (direction == 3) {
            level.setBlock(blockPos.offset(0, 1, 0), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.WEST).setValue(BARRIER_EDGE, BarrierEdges.UP).setValue(WATERLOGGED, false), 2);
            level.setBlock(blockPos.offset(0, 0, 1), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.WEST).setValue(BARRIER_EDGE, BarrierEdges.RIGHT).setValue(WATERLOGGED, false), 2);
            level.setBlock(blockPos.offset(0, -1, 0), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.WEST).setValue(BARRIER_EDGE, BarrierEdges.DOWN).setValue(WATERLOGGED, false), 2);
            level.setBlock(blockPos.offset(0, 0, -1), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.WEST).setValue(BARRIER_EDGE, BarrierEdges.LEFT).setValue(WATERLOGGED, false), 2);
        } else if (direction == 4) {
            level.setBlock(blockPos.offset(0, 0, -1), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.NORTH).setValue(BARRIER_EDGE, BarrierEdges.TOP).setValue(WATERLOGGED, false), 2);
            level.setBlock(blockPos.offset(1, 0, 0), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.EAST).setValue(BARRIER_EDGE, BarrierEdges.TOP).setValue(WATERLOGGED, false), 2);
            level.setBlock(blockPos.offset(0, 0, 1), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.SOUTH).setValue(BARRIER_EDGE, BarrierEdges.TOP).setValue(WATERLOGGED, false), 2);
            level.setBlock(blockPos.offset(-1, 0, 0), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.WEST).setValue(BARRIER_EDGE, BarrierEdges.TOP).setValue(WATERLOGGED, false), 2);
        } else if (direction == 5) {
            level.setBlock(blockPos.offset(0, 0, -1), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.NORTH).setValue(BARRIER_EDGE, BarrierEdges.BOTTOM).setValue(WATERLOGGED, false), 2);
            level.setBlock(blockPos.offset(1, 0, 0), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.EAST).setValue(BARRIER_EDGE, BarrierEdges.BOTTOM).setValue(WATERLOGGED, false), 2);
            level.setBlock(blockPos.offset(0, 0, 1), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.SOUTH).setValue(BARRIER_EDGE, BarrierEdges.BOTTOM).setValue(WATERLOGGED, false), 2);
            level.setBlock(blockPos.offset(-1, 0, 0), DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get().defaultBlockState().setValue(FACING, Direction.WEST).setValue(BARRIER_EDGE, BarrierEdges.BOTTOM).setValue(WATERLOGGED, false), 2);
        }
    }
}
