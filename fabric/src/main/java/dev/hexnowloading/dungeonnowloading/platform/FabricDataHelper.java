package dev.hexnowloading.dungeonnowloading.platform;

import dev.hexnowloading.dungeonnowloading.capabilities.CapabilityList;
import dev.hexnowloading.dungeonnowloading.platform.services.DataHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Optional;

public class FabricDataHelper implements DataHelper {
    @Override
    public void setPoint(Player player, int amount) {
    }

    @Override
    public int getPoint(Player player) {
        return 0;
    }

    @Override
    public Optional<List<BlockPos>> getFairkeeperChestPositionList(Player player) {
        if (CapabilityList.FAIRKEEPER_CHEST_POSITIONS_CAP.isProvidedBy(player)) {
            List<BlockPos> blockPosList = CapabilityList.FAIRKEEPER_CHEST_POSITIONS_CAP.get(player).getList();
            return Optional.ofNullable(blockPosList);
        }
        return Optional.empty();
        //return Optional.of(FairkeeperChestPositionsData.getList((IPlayerDataSaver) player));
    }

    @Override
    public void addFairkeeperChestPositionList(Player player, BlockPos blockPos) {
        if (CapabilityList.FAIRKEEPER_CHEST_POSITIONS_CAP.isProvidedBy(player)) {
            CapabilityList.FAIRKEEPER_CHEST_POSITIONS_CAP.get(player).addBlock(blockPos);
            CapabilityList.FAIRKEEPER_CHEST_POSITIONS_CAP.sync(player);
        }
        //FairkeeperChestPositionsData.addBlockPos((IPlayerDataSaver) player, blockPos);
    }

    @Override
    public void copyFairkeeperChestPositionList(Player player, List<BlockPos> list) {
        if (CapabilityList.FAIRKEEPER_CHEST_POSITIONS_CAP.isProvidedBy(player)) {
            CapabilityList.FAIRKEEPER_CHEST_POSITIONS_CAP.get(player).copyList(list);
        }
        //FairkeeperChestPositionsData.copyList((IPlayerDataSaver) player, list);
    }
}
