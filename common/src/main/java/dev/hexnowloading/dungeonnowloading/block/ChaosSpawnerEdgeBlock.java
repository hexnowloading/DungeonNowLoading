package dev.hexnowloading.dungeonnowloading.block;

import dev.hexnowloading.dungeonnowloading.block.property.AllSides;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlocks;
import dev.hexnowloading.dungeonnowloading.registry.DNLProperties;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.sounds.SoundEvents.RESPAWN_ANCHOR_DEPLETE;

public class ChaosSpawnerEdgeBlock extends Block implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    //public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    public static final EnumProperty<AllSides> ALL_SIDES = DNLProperties.ALL_SIDES;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape TOP_NORTH_AABB = Shapes.or(Block.box(0.0, 14.0, 0.0, 16.0, 16.0, 16.0), Block.box(0.0, 0.0, 14.0, 16.0, 14.0, 16.0));
    protected static final VoxelShape TOP_EAST_AABB = Shapes.or(Block.box(0.0, 14.0, 0.0, 16.0, 16.0, 16.0), Block.box(0.0, 0.0, 0.0, 2.0, 14.0, 16.0));
    protected static final VoxelShape TOP_SOUTH_AABB = Shapes.or(Block.box(0.0, 14.0, 0.0, 16.0, 16.0, 16.0), Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 2.0));
    protected static final VoxelShape TOP_WEST_AABB = Shapes.or(Block.box(0.0, 14.0, 0.0, 16.0, 16.0, 16.0), Block.box(14.0, 0.0, 0.0, 16.0, 14.0, 16.0));
    protected static final VoxelShape BOTTOM_NORTH_AABB = Shapes.or(Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0), Block.box(0.0, 2.0, 14.0, 16.0, 16.0, 16.0));
    protected static final VoxelShape BOTTOM_EAST_AABB = Shapes.or(Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0), Block.box(0.0, 2.0, 0.0, 2.0, 16.0, 16.0));
    protected static final VoxelShape BOTTOM_SOUTH_AABB = Shapes.or(Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0), Block.box(0.0, 2.0, 0.0, 16.0, 16.0, 2.0));
    protected static final VoxelShape BOTTOM_WEST_AABB = Shapes.or(Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0), Block.box(14.0, 2.0, 0.0, 16.0, 16.0, 16.0));
    protected static final VoxelShape VERTICAL_NORTH_AABB = Shapes.or(Block.box(0.0, 0.0, 14.0, 16.0, 16.0, 16.0), Block.box(14.0, 0.0, 0.0, 16.0, 16.0, 14.0));
    protected static final VoxelShape VERTICAL_EAST_AABB = Shapes.or(Block.box(0.0, 0.0, 0.0, 2.0, 16.0, 16.0), Block.box(2.0, 0.0, 14.0, 16.0, 16.0, 16.0));
    protected static final VoxelShape VERTICAL_SOUTH_AABB = Shapes.or(Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 2.0), Block.box(0.0, 0.0, 2.0, 2.0, 16.0, 16.0));
    protected static final VoxelShape VERTICAL_WEST_AABB = Shapes.or(Block.box(14.0, 0.0, 0.0, 16.0, 16.0, 16.0), Block.box(0.0, 0.0, 0.0, 14.0, 16.0, 2.0));

    public ChaosSpawnerEdgeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(ALL_SIDES, AllSides.BOTTOM).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING, ALL_SIDES, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        switch(blockState.getValue(ALL_SIDES)) {
            case TOP:
            default:
                switch(blockState.getValue(FACING)) {
                    case NORTH:
                    default:
                        return TOP_NORTH_AABB;
                    case EAST:
                        return TOP_EAST_AABB;
                    case SOUTH:
                        return TOP_SOUTH_AABB;
                    case WEST:
                        return TOP_WEST_AABB;
                }
            case BOTTOM:
                switch(blockState.getValue(FACING)) {
                    case NORTH:
                    default:
                        return BOTTOM_NORTH_AABB;
                    case EAST:
                        return BOTTOM_EAST_AABB;
                    case SOUTH:
                        return BOTTOM_SOUTH_AABB;
                    case WEST:
                        return BOTTOM_WEST_AABB;
                }
            case VERTICAL:
                switch (blockState.getValue(FACING)) {
                    case NORTH:
                    default:
                        return VERTICAL_NORTH_AABB;
                    case EAST:
                        return VERTICAL_EAST_AABB;
                    case SOUTH:
                        return VERTICAL_SOUTH_AABB;
                    case WEST:
                        return VERTICAL_WEST_AABB;
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
        Direction direction = ctx.getHorizontalDirection();
        BlockPos blockPos = ctx.getClickedPos();
        FluidState fluidstate = ctx.getLevel().getFluidState(ctx.getClickedPos());
        AllSides allSides;
        if (ctx.getPlayer().isCrouching()) {
            allSides = AllSides.VERTICAL;
        } else {
            allSides = direction != Direction.DOWN && (direction == Direction.UP || !(ctx.getClickLocation().y - (double)blockPos.getY() > 0.5)) ? AllSides.BOTTOM : AllSides.TOP;
        }
        return this.defaultBlockState()
                .setValue(FACING, ctx.getHorizontalDirection())
                .setValue(ALL_SIDES, allSides)
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter blockGetter, List<Component> componentList, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, blockGetter, componentList, tooltipFlag);
        componentList.add(Component.translatable("block.dungeonnowloading.chaos_spawner_edge.tooltip").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState1, boolean b) {
        brokenFrame(level, blockPos, blockState);
        super.onRemove(blockState, level, blockPos, blockState1, b);
    }

    private void playSound(Level level, BlockPos blockPos, float pitch) {
        level.playSound((Player) null, blockPos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0F, pitch);
    }

    private void brokenFrame(Level level, BlockPos blockPos, BlockState blockState) {
        if (blockState.is(DNLBlocks.CHAOS_SPAWNER_DIAMOND_EDGE.get())) {
            playSound(level, blockPos, 1.5F);
            level.setBlock(blockPos, DNLBlocks.CHAOS_SPAWNER_BROKEN_DIAMOND_EDGE.defaultBlockState().setValue(FACING, blockState.getValue(FACING)).setValue(ALL_SIDES, blockState.getValue(ALL_SIDES)).setValue(WATERLOGGED, blockState.getValue(WATERLOGGED)), 2);
            this.signalToBarrierCenter(level, blockPos, blockState);
        } else if (blockState.is(DNLBlocks.CHAOS_SPAWNER_EDGE.get())) {
            playSound(level, blockPos, 2.0F);
            level.setBlock(blockPos, DNLBlocks.CHAOS_SPAWNER_BROKEN_EDGE.defaultBlockState().setValue(FACING, blockState.getValue(FACING)).setValue(ALL_SIDES, blockState.getValue(ALL_SIDES)).setValue(WATERLOGGED, blockState.getValue(WATERLOGGED)), 2);
        }
    }

    private void signalToBarrierCenter(Level level, BlockPos blockPos, BlockState blockState) {
        if (blockState.getValue(ALL_SIDES).equals(AllSides.TOP)) {
            if (blockState.getValue(FACING).equals(Direction.NORTH)) {
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.below(2), Direction.Axis.Z);
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.north(2), Direction.Axis.Y);
            } else if (blockState.getValue(FACING).equals(Direction.EAST)) {
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.below(2), Direction.Axis.X);
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.east(2), Direction.Axis.Y);
            } else if (blockState.getValue(FACING).equals(Direction.SOUTH)) {
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.below(2), Direction.Axis.Z);
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.south(2), Direction.Axis.Y);
            } else if (blockState.getValue(FACING).equals(Direction.WEST)) {
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.below(2), Direction.Axis.X);
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.west(2), Direction.Axis.Y);
            }
        } else if (blockState.getValue(ALL_SIDES).equals(AllSides.BOTTOM)) {
            if (blockState.getValue(FACING).equals(Direction.NORTH)) {
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.above(2), Direction.Axis.Z);
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.north(2), Direction.Axis.Y);
            } else if (blockState.getValue(FACING).equals(Direction.EAST)) {
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.above(2), Direction.Axis.X);
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.east(2), Direction.Axis.Y);
            } else if (blockState.getValue(FACING).equals(Direction.SOUTH)) {
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.above(2), Direction.Axis.Z);
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.south(2), Direction.Axis.Y);
            } else if (blockState.getValue(FACING).equals(Direction.WEST)) {
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.above(2), Direction.Axis.X);
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.west(2), Direction.Axis.Y);
            }
        } else if (blockState.getValue(ALL_SIDES).equals(AllSides.VERTICAL)) {
            if (blockState.getValue(FACING).equals(Direction.NORTH)) {
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.north(2), Direction.Axis.X);
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.west(2), Direction.Axis.Z);
            } else if (blockState.getValue(FACING).equals(Direction.EAST)) {
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.east(2), Direction.Axis.Z);
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.north(2), Direction.Axis.X);
            } else if (blockState.getValue(FACING).equals(Direction.SOUTH)) {
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.south(2), Direction.Axis.X);
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.east(2), Direction.Axis.Z);
            } else if (blockState.getValue(FACING).equals(Direction.WEST)) {
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.west(2), Direction.Axis.Z);
                ChaosSpawnerBarrierCenterBlock.checkBarrierCondition(level, blockPos.south(2), Direction.Axis.X);
            }
        }
    }
}
