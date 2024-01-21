package dev.hexnowloading.dungeonnowloading.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class DungeonWallTorch extends HorizontalDirectionalBlock {

    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final BooleanProperty LIT = BlockStateProperties.LIT;
    private static final VoxelShape NORTH_AABB = Block.box(4, 1, 9, 12, 16, 16);
    private static final VoxelShape EAST_AABB = Block.box(0, 1, 4, 7, 16, 12);
    private static final VoxelShape SOUTH_AABB = Block.box(4, 1, 0, 12, 16, 7);
    private static final VoxelShape WEST_AABB = Block.box(9, 1, 4, 16, 16, 12);
    public static final ToIntFunction<BlockState> LIGHT_EMISSION = light -> light.getValue(LIT) ? 14 : 0;

    public DungeonWallTorch(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH).setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(WATERLOGGED).add(FACING).add(LIT);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context) {
        switch (blockState.getValue(FACING)) {
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

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockState blockState = this.defaultBlockState();
        LevelReader levelReader = blockPlaceContext.getLevel();
        BlockPos blockPos = blockPlaceContext.getClickedPos();
        Direction[] directions = blockPlaceContext.getNearestLookingDirections();

        for (Direction direction : directions) {
            if (direction.getAxis().isHorizontal()) {
                Direction oppositeDirection = direction.getOpposite();
                blockState = blockState.setValue(FACING, oppositeDirection);
                if (blockState.canSurvive(levelReader, blockPos)) {
                    return blockState;
                }
            }
        }
        return null;
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        Direction direction = blockState.getValue(FACING);
        BlockPos oppositePos = blockPos.relative(direction.getOpposite());
        BlockState oppositeState = levelReader.getBlockState(oppositePos);
        return oppositeState.isFaceSturdy(levelReader, oppositePos, direction);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState1, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos1) {
        return direction.getOpposite() == blockState.getValue(FACING) && !blockState.canSurvive(levelAccessor, blockPos) ? Blocks.AIR.defaultBlockState() : blockState;
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        if (isLit(blockState)) {
            Direction direction = blockState.getValue(FACING);
            double x = (double)blockPos.getX() + 0.5;
            double y = (double)blockPos.getY() + 0.85;
            double z = (double)blockPos.getZ() + 0.5;
            Direction directionOpposite = direction.getOpposite();
            level.addParticle(ParticleTypes.SMOKE, x + 0.19 * (double)directionOpposite.getStepX(), y + 0.22, z + 0.19 * (double)directionOpposite.getStepZ(), 0.0, 0.0, 0.0);
            level.addParticle(ParticleTypes.FLAME, x + 0.19 * (double)directionOpposite.getStepX(), y + 0.22, z + 0.19 * (double)directionOpposite.getStepZ(), 0.0, 0.0, 0.0);
        }
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
        if (player.getAbilities().mayBuild) {
            if (player.getItemInHand(interactionHand).isEmpty() && blockState.getValue(LIT)) {
                setLit(level, blockState, blockPos, false);
                level.playSound(null, blockPos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
            ItemStack flintAndSteel = player.getItemInHand(interactionHand);
            if (flintAndSteel.is(Items.FLINT_AND_STEEL) && !blockState.getValue(LIT)) {
                if (flintAndSteel.getDamageValue() < flintAndSteel.getMaxDamage()) {
                    setLit(level, blockState, blockPos, true);
                    if (player instanceof ServerPlayer) { flintAndSteel.hurtAndBreak(1, player, playerx -> playerx.broadcastBreakEvent(interactionHand)); }
                    level.playSound(player, blockPos, SoundEvents.FLINTANDSTEEL_USE,SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                    level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }
        return InteractionResult.PASS;

    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (this.canBeLit(blockState) && entity instanceof Projectile projectile && projectile.isOnFire() && this.canBeLit(blockState)) {
            setLit(level, blockState, blockPos, true);
        }
    }

    private boolean canBeLit(BlockState blockState) { return !blockState.getValue(WATERLOGGED) && !blockState.getValue(LIT); }

    private static boolean isLit(BlockState blockState) {
        return blockState.hasProperty(LIT) && blockState.getValue(LIT);
    }

    private static void setLit(LevelAccessor levelAccessor, BlockState blockState, BlockPos blockPos, boolean b) {
        levelAccessor.setBlock(blockPos, blockState.setValue(LIT, Boolean.valueOf(b)), 11);
    }
}
