package dev.hexnowloading.dungeonnowloading.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class SignalGateBlock extends DirectionalBlock {
    public static final IntegerProperty POWER = BlockStateProperties.POWER;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public SignalGateBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.SOUTH).setValue(POWERED, false).setValue(POWER, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(POWERED).add(POWER).add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getNearestLookingDirection().getOpposite().getOpposite());
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
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        int power = blockState.getValue(POWER) + 1;
        if (power > 15) {
            power = 0;
        }
        if (!level.isClientSide) {
            Direction direction = blockState.getValue(FACING);
            boolean b = blockState.getValue(POWERED);
            level.setBlock(blockPos, blockState.setValue(POWER, power).setValue(FACING, direction).setValue(POWERED, b), 2);
            this.startSignal(level, blockPos);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    /*@Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (blockState.getValue(POWERED).booleanValue()) {
            serverLevel.setBlock(blockPos, (BlockState)blockState.setValue(POWERED, false), 2);
        } else {
            serverLevel.setBlock(blockPos, (BlockState)blockState.setValue(POWERED, true), 2);
            serverLevel.scheduleTick(blockPos, this, 2);
        }
        this.updateNeighborsInFront(serverLevel, blockPos, blockState);
    }*/

    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (this.getSignalInFront(serverLevel, blockPos, blockState) == blockState.getValue(POWER)) {
            serverLevel.setBlock(blockPos, (BlockState)blockState.setValue(POWERED, true), 2);
        } else {
            serverLevel.setBlock(blockPos, (BlockState)blockState.setValue(POWERED, false), 2);
            //serverLevel.scheduleTick(blockPos, this, 2);
        }
        this.updateNeighborsInFront(serverLevel, blockPos, blockState);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.getValue(FACING) == direction) {
            this.startSignal(levelAccessor, blockPos);
        }
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos1, boolean b) {
        startSignal(level, blockPos);
        super.neighborChanged(blockState, level, blockPos, block, blockPos1, b);
    }

    /*@Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.getValue(FACING) == direction && !blockState.getValue(POWERED).booleanValue() && this.getSignalInFront(levelAccessor, blockPos, blockState) == blockState.getValue(POWER)) {
            this.startSignal(levelAccessor, blockPos);
        }
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }*/

    private void startSignal(LevelAccessor levelAccessor, BlockPos blockPos) {
        if (!levelAccessor.isClientSide() && !levelAccessor.getBlockTicks().hasScheduledTick(blockPos, this)) {
            levelAccessor.scheduleTick(blockPos, this, 2);
        }
    }

    private void updateNeighborsInFront(Level level, BlockPos blockPos, BlockState blockState) {
        Direction direction = blockState.getValue(FACING);
        BlockPos blockPos1 = blockPos.relative(direction.getOpposite());
        level.neighborChanged(blockPos1, this, blockPos);
        level.updateNeighborsAtExceptFromFacing(blockPos1, this, direction);
    }

    private int getSignalInFront(LevelAccessor level, BlockPos blockPos, BlockState blockState) {
        Direction direction = blockState.getValue(FACING);
        BlockPos blockPos1 = blockPos.relative(direction);
        return level.getSignal(blockPos1, direction);
    }

    @Override
    public boolean isSignalSource(BlockState blockState) {
        return true;
    }

    @Override
    public int getDirectSignal(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
        return blockState.getSignal(blockGetter, blockPos, direction);
    }

    @Override
    public int getSignal(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction) {
        if (blockState.getValue(POWERED).booleanValue() && blockState.getValue(FACING) == direction) {
            return 15;
        }
        return 0;
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (blockState.is(blockState2.getBlock())) {
            return;
        }
        if (!level.isClientSide() && !blockState.getValue(POWERED) && !level.getBlockTicks().hasScheduledTick(blockPos, this)) {
            this.startSignal(level, blockPos);
            BlockState blockState3 = (BlockState)blockState.setValue(POWERED, false);
            level.setBlock(blockPos, blockState3, 18);
            this.updateNeighborsInFront(level, blockPos, blockState3);
        }
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (blockState.is(blockState2.getBlock())) {
            return;
        }
        if (!level.isClientSide && blockState.getValue(POWERED).booleanValue() && level.getBlockTicks().hasScheduledTick(blockPos, this)) {
            this.updateNeighborsInFront(level, blockPos, (BlockState)blockState.setValue(POWERED, false));
        }
    }
}
