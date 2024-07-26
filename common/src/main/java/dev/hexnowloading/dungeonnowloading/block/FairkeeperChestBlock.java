package dev.hexnowloading.dungeonnowloading.block;

import dev.hexnowloading.dungeonnowloading.block.entity.FairkeeperChestBlockEntity;
import dev.hexnowloading.dungeonnowloading.block.entity.FairkeeperSpawnerBlockEntity;
import dev.hexnowloading.dungeonnowloading.block.property.ChestStates;
import dev.hexnowloading.dungeonnowloading.particle.FairkeeperBoundaryParticle;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlockEntityTypes;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlocks;
import dev.hexnowloading.dungeonnowloading.registry.DNLParticleTypes;
import dev.hexnowloading.dungeonnowloading.registry.DNLProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

public class FairkeeperChestBlock extends BaseEntityBlock implements SimpleWaterloggedBlock, EntityBlock {
    public static final EnumProperty<ChestStates> CHEST_STATES = DNLProperties.CHEST_STATES;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty FAIRKEEPER_ALERT = DNLProperties.FAIRKEEPER_ALERT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SHAPE_X = Block.box(1.5D, 0.0D, 1.5D, 14.5D, 15.0D, 14.5D);
    private static final VoxelShape SHAPE_Z = Block.box(1.5D, 0.0D, 1.5D, 14.5D, 15.0D, 14.5D);
    private static final int XP_AXIS = 0;
    private static final int YP_AXIS = 1;



    public FairkeeperChestBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CHEST_STATES, ChestStates.CLOSED).setValue(FACING, Direction.NORTH).setValue(FAIRKEEPER_ALERT, Boolean.FALSE).setValue(WATERLOGGED, Boolean.FALSE));
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
        stateBuilder.add(CHEST_STATES, FACING, FAIRKEEPER_ALERT, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction direction = ctx.getHorizontalDirection().getOpposite();
        FluidState fluidstate = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return this.defaultBlockState().setValue(CHEST_STATES, ChestStates.CLOSED).setValue(FACING, direction).setValue(FAIRKEEPER_ALERT, Boolean.FALSE).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
    }

    // Makes the block waterlogged when placed in water.
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
            if (blockentity instanceof FairkeeperChestBlockEntity fairkeeperChestBlock && fairkeeperChestBlock.isDisabled(fairkeeperChestBlock)) {
                Containers.dropContents(level, pos, (Container)blockentity);
                level.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(oldState, level, pos, state, moved);
        }
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {

        super.playerWillDestroy(level, blockPos, blockState, player);
    }

    protected Stat<ResourceLocation> getOpenChestStat() { return Stats.CUSTOM.get(Stats.OPEN_CHEST); }

    public static boolean isChestBlockedByBlock(BlockGetter blockGetter, BlockPos pos) {
        BlockPos blockPos = pos.above();
        return blockGetter.getBlockState(blockPos).isRedstoneConductor(blockGetter, blockPos);
    }

    public static Container getContainer(FairkeeperChestBlock dungeonChestBlock) {
        return (Container) dungeonChestBlock;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        if (isChestBlockedByBlock(level, pos)) {
            return InteractionResult.SUCCESS;
        }
        FairkeeperChestBlockEntity blockEntity = (FairkeeperChestBlockEntity) level.getBlockEntity(pos);
        if (blockEntity.hasSpawnerLocations()) {
            return InteractWithLockedFairkeeperChest(level, player, pos);
        }
        if (state.getValue(FAIRKEEPER_ALERT)) {
            AABB aabb = blockEntity.getBoundaryAABB();
            List<Mob> list = level.getEntitiesOfClass(Mob.class, aabb);
            if (list.size() > 0) {
                return InteractWithLockedFairkeeperChest(level, player, pos);
            }
            // Required for preventing player from opening the combat fairkeeper chest when the last alerted fairkeeper spawner hasn't spawned the mob yet.
            if (blockEntity.hasLastSpawner(level, blockEntity)) {
                return InteractWithLockedFairkeeperChest(level, player, pos);
            }
        }
        setFairkeeperDisabled(level, pos, blockEntity, true);
        MenuProvider menuProvider = this.getMenuProvider(state, level, pos);
        if (menuProvider != null) {
            player.openMenu(menuProvider);
            player.awardStat(this.getOpenChestStat());
            PiglinAi.angerNearbyPiglins(player, true);
        }
        return InteractionResult.CONSUME;
    }

    private static InteractionResult InteractWithLockedFairkeeperChest(Level level, Player player, BlockPos blockPos) {
        player.displayClientMessage(Component.translatable("warning.dungeonnowloading.cannot_open_fairkeeper_chest"), true);
        playSound(level, blockPos, SoundEvents.CHEST_LOCKED);
        return InteractionResult.SUCCESS;
    }

    public static void setFairkeeperDisabled(Level level, BlockPos blockPos, FairkeeperChestBlockEntity blockEntity, boolean b) {
        Direction direction = level.getBlockState(blockPos).getValue(FACING);
        boolean fairkeeper_alert = level.getBlockState(blockPos).getValue(FAIRKEEPER_ALERT);
        blockEntity.setDisabled(b);
        level.setBlock(blockPos, DNLBlocks.FAIRKEEPER_CHEST.get().defaultBlockState().setValue(FAIRKEEPER_ALERT, fairkeeper_alert).setValue(FACING, direction).setValue(CHEST_STATES, ChestStates.CLOSED), 2);
    }

    public static void setFairkeeperChest(Level level, BlockPos pos, ChestStates chestStates) {
        Direction direction = level.getBlockState(pos).getValue(FACING);
        boolean fairkeeper_alert = level.getBlockState(pos).getValue(FAIRKEEPER_ALERT);
        level.setBlock(pos, DNLBlocks.FAIRKEEPER_CHEST.get().defaultBlockState().setValue(FAIRKEEPER_ALERT, fairkeeper_alert).setValue(FACING, direction).setValue(CHEST_STATES, chestStates), 2);
    }

    public static void setFairkeeperAlert(Level level, BlockPos blockPos, Boolean b) {
        Direction direction = level.getBlockState(blockPos).getValue(FACING);
        level.setBlock(blockPos, DNLBlocks.FAIRKEEPER_CHEST.get().defaultBlockState().setValue(FAIRKEEPER_ALERT, b).setValue(FACING, direction).setValue(CHEST_STATES, ChestStates.CLOSED), 2);
    }

    public static int getFacingInt(Level level, BlockPos blockPos) {
        Direction direction = level.getBlockState(blockPos).getValue(FACING);
        return switch (direction) {
            default -> 0;
            case EAST -> 1;
            case SOUTH -> 2;
            case WEST -> 3;
        };
    }

    private static void playSound(Level level, BlockPos pos, SoundEvent soundEvent) {
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + 0.5D;
        double d2 = (double)pos.getZ() + 0.5D;

        level.playSound((Player)null, d0, d1, d2, soundEvent, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        /*if (level.isClientSide) {
            int actualRegion1X = fairkeeperChest.getActualRegion1X(fairkeeperChest);
            int actualRegion2X = fairkeeperChest.getActualRegion2X(fairkeeperChest);
            int actualRegion1Y = fairkeeperChest.getActualRegion1Y(fairkeeperChest);
            int actualRegion2Y = fairkeeperChest.getActualRegion2Y(fairkeeperChest);
            int actualRegion1Z = fairkeeperChest.getActualRegion1Z(fairkeeperChest)
            double x = actualRegion2X + (actualRegion1X - actualRegion2X) * level.random.nextFloat();
            double y = actualRegion2Y + (actualRegion1Y - actualRegion2Y) * level.random.nextFloat();
            double z = actualRegion2Z + (actualRegion1Z - actualRegion2Z) * level.random.nextFloat();
            level.addParticle(DNLParticleTypes.FAIRKEEPER_BOUNDARY_PARTICLE.get(), blockEntity.actualRegion1X, y, z, 1, 90.0F, 0.0F);
            level.addParticle(DNLParticleTypes.FAIRKEEPER_BOUNDARY_PARTICLE.get(), blockEntity.actualRegion2X, y, z, 1, 90.0F, 0.0F);
            level.addParticle(DNLParticleTypes.FAIRKEEPER_BOUNDARY_PARTICLE.get(), x, blockEntity.actualRegion1Y, z, 0, 90.0F, 0.0F);
            level.addParticle(DNLParticleTypes.FAIRKEEPER_BOUNDARY_PARTICLE.get(), x, blockEntity.actualRegion2Y, z, 0, 90.0F, 0.0F);
            level.addParticle(DNLParticleTypes.FAIRKEEPER_BOUNDARY_PARTICLE.get(), x, y, blockEntity.actualRegion1Z, 1, 90.0F, 0.0F);
            level.addParticle(DNLParticleTypes.FAIRKEEPER_BOUNDARY_PARTICLE.get(), x, y, blockEntity.actualRegion2Z, 1, 90.0F, 0.0F);
        }*/
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, DNLBlockEntityTypes.FAIRKEEPER_CHEST.get(), level.isClientSide ? FairkeeperChestBlockEntity::clientTick : FairkeeperChestBlockEntity::serverTick);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FairkeeperChestBlockEntity(pos, state);
    }
}
