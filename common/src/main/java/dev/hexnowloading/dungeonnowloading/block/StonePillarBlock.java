package dev.hexnowloading.dungeonnowloading.block;

import dev.hexnowloading.dungeonnowloading.block.entity.ScuttleStatueBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class StonePillarBlock extends Block implements SimpleWaterloggedBlock {

    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 16, 14);

    public StonePillarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, Boolean.FALSE).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(WATERLOGGED);
        blockBlockStateBuilder.add(HALF);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos blockPos = blockPlaceContext.getClickedPos();
        Level level = blockPlaceContext.getLevel();
        return blockPos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockPos.above()).canBeReplaced(blockPlaceContext) ? this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER) : null;
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPos1 = blockPos.below();
        return this.mayPlaceOn(levelReader.getBlockState(blockPos1), levelReader, blockPos1);
    }

    private boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return !blockState.getCollisionShape(blockGetter, blockPos).getFaceShape(Direction.UP).isEmpty() || blockState.isFaceSturdy(blockGetter, blockPos, Direction.UP);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState oldBlockState, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos oldBlockPos) {
        DoubleBlockHalf doubleBlockHalf = blockState.getValue(HALF);
        BlockState topBlockState = levelAccessor.getBlockState(blockPos.above());
        if (!blockState.canSurvive(levelAccessor, blockPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        if (doubleBlockHalf == DoubleBlockHalf.LOWER && !topBlockState.hasProperty(HALF)) {
            return Blocks.AIR.defaultBlockState();
        }
        if (doubleBlockHalf == DoubleBlockHalf.LOWER && topBlockState.hasProperty(HALF) && topBlockState.getValue(HALF) != DoubleBlockHalf.UPPER) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(blockState, direction, oldBlockState, levelAccessor, blockPos, oldBlockPos);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        BlockPos upperBlockPos = blockPos.above();
        level.setBlock(upperBlockPos, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER), Block.UPDATE_ALL);
    }
}

