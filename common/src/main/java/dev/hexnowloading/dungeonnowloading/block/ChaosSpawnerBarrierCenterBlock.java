package dev.hexnowloading.dungeonnowloading.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import dev.hexnowloading.dungeonnowloading.entity.boss.ChaosSpawnerEntity;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlocks;
import dev.hexnowloading.dungeonnowloading.registry.DNLProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

public class ChaosSpawnerBarrierCenterBlock extends Block implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape NORTH_AABB = Block.box(0.0, 0.0, 1.0, 16.0, 16.0, 2.0);
    protected static final VoxelShape SOUTH_AABB = Block.box(0.0, 0.0, 14.0, 16.0, 16.0, 15.0);
    protected static final VoxelShape EAST_AABB = Block.box(14.0, 0.0, 0.0, 15.0, 16.0, 16.0);
    protected static final VoxelShape WEST_AABB = Block.box(1.0, 0.0, 0.0, 2.0, 16.0, 16.0);
    protected static final VoxelShape UP_AABB = Block.box(0.0, 14.0, 0.0, 16.0, 15.0, 16.0);
    protected static final VoxelShape DOWN_AABB = Block.box(0.0, 1.0, 0.0, 16.0, 2.0, 16.0);
    //private BlockPattern chaosSpawnerFull;
    /*private static final Predicate<BlockState> CHAOS_SPAWNER_DIAMOND_PREDICATE;
    private static final Predicate<BlockState> CHAOS_SPAWNER_EDGE_PREDICATE;
    private static final Predicate<BlockState> CHAOS_SPAWNER_BARRIER_PREDICATE;
*/


    public ChaosSpawnerBarrierCenterBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING, WATERLOGGED);
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
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return (BlockState)this.defaultBlockState()
                .setValue(FACING, blockPlaceContext.getNearestLookingDirection().getOpposite());
    }

    public static void checkBarrierCondition(Level level, BlockPos blockPos, Direction.Axis direction) {
        if (level.getBlockState(blockPos).is(DNLBlocks.CHAOS_SPAWNER_BARRIER_CENTER.get())) {
            Iterator iterator = null;
            if (direction == Direction.Axis.X) {
                iterator = BLOCK_LOCATIONS_X.iterator();
            } else if (direction == Direction.Axis.Y) {
                iterator = BLOCK_LOCATIONS_Y.iterator();
            } else if (direction == Direction.Axis.Z) {
                iterator = BLOCK_LOCATIONS_Z.iterator();
            }
            int brokenFrame = 0;
            while(iterator.hasNext()) {
                BlockPos offsetAmount = (BlockPos) iterator.next();
                if (level.getBlockState(blockPos.offset(offsetAmount)).is(DNLBlocks.CHAOS_SPAWNER_DIAMOND_EDGE.get()) || level.getBlockState(blockPos.offset(offsetAmount)).is(DNLBlocks.CHAOS_SPAWNER_DIAMOND_VERTEX.get())) {
                    break;
                }
                brokenFrame++;
            }
            if (brokenFrame == 8) {
                level.destroyBlock(blockPos, false);
                Iterator barrierIterator = null;
                if (direction == Direction.Axis.X) {
                    barrierIterator = BARRIER_LOCATIONS_X.iterator();
                } else if (direction == Direction.Axis.Y) {
                    barrierIterator = BARRIER_LOCATIONS_Y.iterator();
                } else if (direction == Direction.Axis.Z) {
                    barrierIterator = BARRIER_LOCATIONS_Z.iterator();
                }
                while(barrierIterator.hasNext()) {
                    BlockPos breakPos = blockPos.offset((BlockPos) barrierIterator.next());
                    level.destroyBlock(breakPos, false);
                }
            }
        }
    }

    public static void placeBarrier(Level level, BlockPos blockPos, int direction) {
        if (direction == 0) {
            level.setBlock(blockPos, DNLBlocks.CHAOS_SPAWNER_BARRIER_CENTER.get().defaultBlockState().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false), 2);
        } else if (direction == 1) {
            level.setBlock(blockPos, DNLBlocks.CHAOS_SPAWNER_BARRIER_CENTER.get().defaultBlockState().setValue(FACING, Direction.EAST).setValue(WATERLOGGED, false), 2);
        } else if (direction == 2) {
            level.setBlock(blockPos, DNLBlocks.CHAOS_SPAWNER_BARRIER_CENTER.get().defaultBlockState().setValue(FACING, Direction.SOUTH).setValue(WATERLOGGED, false), 2);
        } else if (direction == 3) {
            level.setBlock(blockPos, DNLBlocks.CHAOS_SPAWNER_BARRIER_CENTER.get().defaultBlockState().setValue(FACING, Direction.WEST).setValue(WATERLOGGED, false), 2);
        } else if (direction == 4) {
            level.setBlock(blockPos, DNLBlocks.CHAOS_SPAWNER_BARRIER_CENTER.get().defaultBlockState().setValue(FACING, Direction.UP).setValue(WATERLOGGED, false), 2);
        } else if (direction == 5) {
            level.setBlock(blockPos, DNLBlocks.CHAOS_SPAWNER_BARRIER_CENTER.get().defaultBlockState().setValue(FACING, Direction.DOWN).setValue(WATERLOGGED, false), 2);
        }
    }

    private static final ImmutableList<BlockPos> BLOCK_LOCATIONS_X = ImmutableList.of(
            new BlockPos(0, 2, 0),
            new BlockPos(0, 2, 2),
            new BlockPos(0, 0, 2),
            new BlockPos(0, -2, 2),
            new BlockPos(0, -2, 0),
            new BlockPos(0, -2, -2),
            new BlockPos(0, 0, -2),
            new BlockPos(0, 2, -2)
    );

    private static final ImmutableList<BlockPos> BLOCK_LOCATIONS_Y = ImmutableList.of(
            new BlockPos(2, 0, 0),
            new BlockPos(2, 0, 2),
            new BlockPos(0, 0, 2),
            new BlockPos(-2, 0, 2),
            new BlockPos(-2, 0, 0),
            new BlockPos(-2, 0, -2),
            new BlockPos(0, 0, -2),
            new BlockPos(2, 0, -2)
    );

    private static final ImmutableList<BlockPos> BLOCK_LOCATIONS_Z = ImmutableList.of(
            new BlockPos(0, 2, 0),
            new BlockPos(2, 2, 0),
            new BlockPos(2, 0, 0),
            new BlockPos(2, -2, 0),
            new BlockPos(0, -2, 0),
            new BlockPos(-2, -2, 0),
            new BlockPos(-2, 0, 0),
            new BlockPos(-2, 2, 0)
    );

    private static final ImmutableList<BlockPos> BARRIER_LOCATIONS_X = ImmutableList.of(
            new BlockPos(0, 1, 0),
            new BlockPos(0, 1, -1),
            new BlockPos(0, 0, -1),
            new BlockPos(0, -1, -1),
            new BlockPos(0, -1, 0),
            new BlockPos(0, -1, 1),
            new BlockPos(0, 0, 1),
            new BlockPos(0, 1, 1)
    );

    private static final ImmutableList<BlockPos> BARRIER_LOCATIONS_Y = ImmutableList.of(
            new BlockPos(1, 0, 0),
            new BlockPos(1, 0, -1),
            new BlockPos(0, 0, -1),
            new BlockPos(-1, 0, -1),
            new BlockPos(-1, 0, 0),
            new BlockPos(-1, 0, 1),
            new BlockPos(0, 0, 1),
            new BlockPos(1, 0, 1)
    );

    private static final ImmutableList<BlockPos> BARRIER_LOCATIONS_Z = ImmutableList.of(
            new BlockPos(0, 1, 0),
            new BlockPos(-1, 1, 0),
            new BlockPos(-1, 0, 0),
            new BlockPos(-1, -1, 0),
            new BlockPos(0, -1, 0),
            new BlockPos(1, -1, 0),
            new BlockPos(1, 0, 0),
            new BlockPos(1, 1, 0)
    );

    /*public static void checkBarrierCondition(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.getBlockState(blockPos).is(DNLBlocks.CHAOS_SPAWNER_BARRIER_CENTER.get())) {
            BlockPattern.BlockPatternMatch blockPatternMatch = BlockPatternBuilder.start().aisle("^ ^ ^", "     ", "^   ^", "     ", "^ ^ ^")
                    .where('#', BlockInWorld.hasState(CHAOS_SPAWNER_EDGE_PREDICATE))
                    .where('^', BlockInWorld.hasState(CHAOS_SPAWNER_DIAMOND_PREDICATE))
                    .where('~', BlockInWorld.hasState(CHAOS_SPAWNER_BARRIER_PREDICATE))
                    .build().find(level, blockPos);
            if (blockPatternMatch != null) {
                level.destroyBlock(blockPos, false);
            }
        }
    }*/

    /*private BlockPattern getOrCreateChaosSpawnerFull() {
        if (this.chaosSpawnerFull == null) {
            this.chaosSpawnerFull = BlockPatternBuilder.start().aisle("^ ^ ^", "     ", "^   ^", "     ", "^ ^ ^")
                    .where('#', BlockInWorld.hasState(CHAOS_SPAWNER_EDGE_PREDICATE))
                    .where('^', BlockInWorld.hasState(CHAOS_SPAWNER_DIAMOND_PREDICATE))
                    .where('~', BlockInWorld.hasState(CHAOS_SPAWNER_BARRIER_PREDICATE))
                    .build();
        }
        return this.chaosSpawnerFull;
    }
*/
    /*static {
        CHAOS_SPAWNER_DIAMOND_PREDICATE = (blockState) -> {
            return blockState != null && (blockState.is(DNLBlocks.CHAOS_SPAWNER_BROKEN_DIAMOND_EDGE.get()) || blockState.is(DNLBlocks.CHAOS_SPAWNER_BROKEN_DIAMOND_VERTEX.get()));
        };
        CHAOS_SPAWNER_EDGE_PREDICATE = (blockState) -> {
            return blockState != null && (blockState.is(DNLBlocks.CHAOS_SPAWNER_BROKEN_EDGE.get()) || blockState.is(DNLBlocks.CHAOS_SPAWNER_EDGE.get()));
        };
        CHAOS_SPAWNER_BARRIER_PREDICATE = (blockState) -> {
            return blockState != null && (blockState.is(DNLBlocks.CHAOS_SPAWNER_BARRIER_CENTER.get()) || blockState.is(DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get()) || blockState.is(DNLBlocks.CHAOS_SPAWNER_BARRIER_VERTEX.get()));
        };
    }*/

}
