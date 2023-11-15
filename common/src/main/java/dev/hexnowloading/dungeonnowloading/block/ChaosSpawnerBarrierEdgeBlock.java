package dev.hexnowloading.dungeonnowloading.block;

import dev.hexnowloading.dungeonnowloading.block.property.AllSides;
import dev.hexnowloading.dungeonnowloading.block.property.BlockFaces;
import dev.hexnowloading.dungeonnowloading.registry.DNLProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ChaosSpawnerBarrierEdgeBlock extends Block implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final EnumProperty<BlockFaces> BLOCK_FACE = DNLProperties.BLOCK_FACES;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape NORTH_AABB = Block.box(0.0, 0.0, 1.0, 16.0, 16.0, 2.0);
    protected static final VoxelShape SOUTH_AABB = Block.box(0.0, 0.0, 14.0, 16.0, 16.0, 15.0);
    protected static final VoxelShape EAST_AABB = Block.box(14.0, 0.0, 0.0, 15.0, 16.0, 16.0);
    protected static final VoxelShape WEST_AABB = Block.box(1.0, 0.0, 0.0, 2.0, 16.0, 16.0);
    protected static final VoxelShape UP_AABB = Block.box(0.0, 14.0, 0.0, 16.0, 15.0, 16.0);
    protected static final VoxelShape DOWN_AABB = Block.box(0.0, 1.0, 0.0, 16.0, 2.0, 16.0);
    public ChaosSpawnerBarrierEdgeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BLOCK_FACE, BlockFaces.DOWN).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING, BLOCK_FACE, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
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
            case UP:
                return UP_AABB;
            case DOWN:
                return DOWN_AABB;
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
        Direction direction = ctx.getClickedFace().getOpposite();
        FluidState fluidstate = ctx.getLevel().getFluidState(ctx.getClickedPos());
        BlockFaces blockFaces = BlockFaces.UP;
        if (direction == Direction.DOWN) {
            blockFaces = BlockFaces.DOWN;
        } else if (direction == Direction.NORTH) {
            blockFaces = BlockFaces.NORTH;
        } else if (direction == Direction.EAST) {
            blockFaces = BlockFaces.EAST;
        } else if (direction == Direction.SOUTH) {
            blockFaces = BlockFaces.SOUTH;
        } else if (direction == Direction.WEST) {
            blockFaces = BlockFaces.WEST;
        }
        return this.defaultBlockState()
                .setValue(FACING, ctx.getNearestLookingDirection().getOpposite())
                .setValue(BLOCK_FACE, blockFaces)
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }
}
