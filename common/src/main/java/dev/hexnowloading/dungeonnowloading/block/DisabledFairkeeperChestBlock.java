package dev.hexnowloading.dungeonnowloading.block;

import dev.hexnowloading.dungeonnowloading.block.entity.DisabledFairkeeperChestBlockEntity;
import dev.hexnowloading.dungeonnowloading.block.entity.FairkeeperChestBlockEntity;
import dev.hexnowloading.dungeonnowloading.block.property.ChestStates;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlockEntityTypes;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlocks;
import dev.hexnowloading.dungeonnowloading.registry.DNLProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.*;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DisabledFairkeeperChestBlock extends BaseEntityBlock implements SimpleWaterloggedBlock, EntityBlock {

    public static final EnumProperty<ChestStates> CHEST_STATES = DNLProperties.CHEST_STATES;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SHAPE_X = Block.box(1.5D, 0.0D, 1.5D, 14.5D, 15.0D, 14.5D);
    private static final VoxelShape SHAPE_Z = Block.box(1.5D, 0.0D, 1.5D, 14.5D, 15.0D, 14.5D);

    public DisabledFairkeeperChestBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CHEST_STATES, ChestStates.CLOSED).setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING).getAxis()) {
            case X:
            default:
                return SHAPE_X;
            case Z:
                return SHAPE_Z;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(CHEST_STATES, FACING, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction direction = ctx.getHorizontalDirection().getOpposite();
        FluidState fluidstate = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return this.defaultBlockState().setValue(CHEST_STATES, ChestStates.CLOSED).setValue(FACING, direction).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public void onRemove(BlockState oldState, Level level, BlockPos pos, BlockState state, boolean moved) {
        if (!oldState.is(state.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof DisabledFairkeeperChestBlockEntity) {
                Containers.dropContents(level, pos, (Container)blockentity);
                level.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(oldState, level, pos, state, moved);
        }
    }

    protected Stat<ResourceLocation> getOpenChestStat() { return Stats.CUSTOM.get(Stats.OPEN_CHEST); }

    public static boolean isChestBlockedByBlock(BlockGetter blockGetter, BlockPos pos) {
        BlockPos blockPos = pos.above();
        return blockGetter.getBlockState(blockPos).isRedstoneConductor(blockGetter, blockPos);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        if (isChestBlockedByBlock(level, pos)) {
            return InteractionResult.SUCCESS;
        }
        DisabledFairkeeperChestBlockEntity blockEntity = (DisabledFairkeeperChestBlockEntity) level.getBlockEntity(pos);
        MenuProvider menuProvider = this.getMenuProvider(state, level, pos);
        if (menuProvider != null) {
            player.openMenu(menuProvider);
            player.awardStat(this.getOpenChestStat());
            PiglinAi.angerNearbyPiglins(player, true);
        }
        return InteractionResult.CONSUME;
    }

    public static void setFairkeeperChest(Level level, BlockPos pos, ChestStates chestStates) {
        Direction direction = level.getBlockState(pos).getValue(FACING);
        if (level.getBlockState(pos).is(DNLBlocks.WISE_FAIRKEEPER_CHEST.get())) {
            level.setBlock(pos, DNLBlocks.WISE_FAIRKEEPER_CHEST.get().defaultBlockState().setValue(FACING, direction).setValue(CHEST_STATES, chestStates), 2);
        } else {
            level.setBlock(pos, DNLBlocks.FIERCE_FAIRKEEPER_CHEST.get().defaultBlockState().setValue(FACING, direction).setValue(CHEST_STATES, chestStates), 2);

        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, DNLBlockEntityTypes.DISABLED_FAIRKEEPER_CHEST.get(), DisabledFairkeeperChestBlockEntity::clientTick);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new DisabledFairkeeperChestBlockEntity(blockPos, blockState);
    }
}
