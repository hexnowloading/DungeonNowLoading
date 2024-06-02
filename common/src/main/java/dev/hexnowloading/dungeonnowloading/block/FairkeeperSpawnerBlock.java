package dev.hexnowloading.dungeonnowloading.block;

import dev.hexnowloading.dungeonnowloading.block.entity.FairkeeperChestBlockEntity;
import dev.hexnowloading.dungeonnowloading.block.entity.FairkeeperSpawnerBlockEntity;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlockEntityTypes;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlocks;
import dev.hexnowloading.dungeonnowloading.registry.DNLProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public class FairkeeperSpawnerBlock extends BaseEntityBlock implements EntityBlock {

    public static final BooleanProperty FAIRKEEPER_ALERT = DNLProperties.FAIRKEEPER_ALERT;
    public static final BooleanProperty FAIRKEEPER_DISABLED = DNLProperties.FAIRKEEPER_DISABLED;

    public FairkeeperSpawnerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FAIRKEEPER_ALERT, Boolean.FALSE).setValue(FAIRKEEPER_DISABLED, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateDefinition) {
        blockStateDefinition.add(FAIRKEEPER_ALERT, FAIRKEEPER_DISABLED);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return createTickerHelper(type, DNLBlockEntityTypes.FAIRKEEPER_SPAWNER.get(), level.isClientSide ? FairkeeperSpawnerBlockEntity::clientTick : FairkeeperSpawnerBlockEntity::serverTick);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FairkeeperSpawnerBlockEntity(blockPos, blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState renderShape) {
        return RenderShape.MODEL;
    }

    public static void setFairkeeperDisabled(Level level, BlockPos blockPos, Boolean b) {
        level.setBlock(blockPos, DNLBlocks.FAIRKEEEPER_SPAWNER.defaultBlockState().setValue(FAIRKEEPER_ALERT, Boolean.FALSE).setValue(FAIRKEEPER_DISABLED, b), 2);

    }

    public static void setFairkeeperAlert(Level level, BlockPos blockPos, Boolean b) {
        boolean fairkeeper_disabled = level.getBlockState(blockPos).getValue(FAIRKEEPER_DISABLED);
        level.setBlock(blockPos, DNLBlocks.FAIRKEEEPER_SPAWNER.defaultBlockState().setValue(FAIRKEEPER_ALERT, b).setValue(FAIRKEEPER_DISABLED, fairkeeper_disabled), 2);
    }
}
