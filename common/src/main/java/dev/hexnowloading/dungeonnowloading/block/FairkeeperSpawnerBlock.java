package dev.hexnowloading.dungeonnowloading.block;

import dev.hexnowloading.dungeonnowloading.block.entity.FairkeeperChestBlockEntity;
import dev.hexnowloading.dungeonnowloading.block.entity.FairkeeperSpawnerBlockEntity;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlockEntityTypes;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlocks;
import dev.hexnowloading.dungeonnowloading.registry.DNLProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FairkeeperSpawnerBlock extends BaseEntityBlock implements EntityBlock {

    public static final BooleanProperty FAIRKEEPER_ALERT = DNLProperties.FAIRKEEPER_ALERT;

    public FairkeeperSpawnerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FAIRKEEPER_ALERT, Boolean.FALSE));
    }


    @Override
    public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, ItemStack itemStack) {
        super.playerDestroy(level, player, blockPos, blockState, blockEntity, itemStack);
        if (blockEntity instanceof FairkeeperSpawnerBlockEntity fairkeeperSpawnerBlockEntity) {
            fairkeeperSpawnerBlockEntity.destroyed();
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateDefinition) {
        blockStateDefinition.add(FAIRKEEPER_ALERT);
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



    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos1, boolean b) {
        if (level.isClientSide) {
            return;
        }
        if (!level.hasNeighborSignal(blockPos)) {
            return;
        }
        FairkeeperSpawnerBlockEntity blockEntity = (FairkeeperSpawnerBlockEntity) level.getBlockEntity(blockPos);
        if (blockEntity != null) {
            AABB aabb = new AABB(blockPos).inflate(32);
            List<Player> nearbyPlayers = level.getEntitiesOfClass(Player.class, aabb);
            blockEntity.alert(nearbyPlayers.size(), blockPos, blockEntity);
        }
    }

    public static void setFairkeeperAlert(Level level, BlockPos blockPos, Boolean b) {
        level.setBlock(blockPos, DNLBlocks.FAIRKEEEPER_SPAWNER.get().defaultBlockState().setValue(FAIRKEEPER_ALERT, b), 2);
    }
}
