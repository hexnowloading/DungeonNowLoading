package dev.hexnowloading.dungeonnowloading.block;

import dev.hexnowloading.dungeonnowloading.block.entity.ScuttleStatueBlockEntity;
import dev.hexnowloading.dungeonnowloading.block.property.TripleBlock;
import dev.hexnowloading.dungeonnowloading.registry.DNLProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class ScuttleStatueBlock extends BaseEntityBlock implements EntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public ScuttleStatueBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    /*@Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState1, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos1) {
        return super.updateShape(blockState, direction, blockState1, levelAccessor, blockPos, blockPos1);
    }*/

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(HALF);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos blockPos = blockPlaceContext.getClickedPos();
        Level level = blockPlaceContext.getLevel();
        Direction direction = blockPlaceContext.getHorizontalDirection().getOpposite();
        return blockPos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockPos.above()).canBeReplaced(blockPlaceContext) ? this.defaultBlockState().setValue(FACING, direction).setValue(HALF, DoubleBlockHalf.LOWER) : null;
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState oldBlockState, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos oldBlockPos) {
        DoubleBlockHalf doubleBlockHalf = blockState.getValue(HALF);
        if (direction.getAxis() != Direction.Axis.Y || doubleBlockHalf == DoubleBlockHalf.LOWER != (direction == Direction.UP) || oldBlockState.is(this) && oldBlockState.getValue(HALF) != doubleBlockHalf) {
            return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, direction, oldBlockState, levelAccessor, blockPos, oldBlockPos);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        BlockPos upperBlockPos = blockPos.above();
        Direction direction = blockState.getValue(FACING);
        level.setBlock(upperBlockPos, this.defaultBlockState().setValue(FACING, direction).setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ScuttleStatueBlockEntity(blockPos, blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState $blockState0) {
        return RenderShape.MODEL;
    }

    /* @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockState blockStateAbove = levelReader.getBlockState(blockPos.above());
        BlockState blockStateBelow = levelReader.getBlockState(blockPos.below());
        return switch (blockState.getValue(TRIPLE_BLOCK)) {
            case UPPER -> blockStateBelow.is(this) && blockStateBelow.getValue(TRIPLE_BLOCK) == TripleBlock.MIDDLE;
            case MIDDLE -> blockStateBelow.is(this) && blockStateBelow.getValue(TRIPLE_BLOCK) == TripleBlock.LOWER && blockStateAbove.is(this) && blockStateAbove.getValue(TRIPLE_BLOCK) == TripleBlock.UPPER;
            case LOWER -> blockStateAbove.is(this) && blockStateAbove.getValue(TRIPLE_BLOCK) == TripleBlock.UPPER;
        };
    }*/
}
