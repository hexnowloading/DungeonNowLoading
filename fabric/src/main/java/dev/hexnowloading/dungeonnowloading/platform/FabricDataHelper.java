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
        return Optional.ofNullable(CapabilityList.FAIRKEEPER_CHEST_POSITIONS_CAP.get(player).getList());
    }

    @Override
    public void addFairkeeperChestPositionList(Player player, BlockPos blockPos) {
        CapabilityList.FAIRKEEPER_CHEST_POSITIONS_CAP.get(player).addBlock(blockPos);
    }

    @Override
    public void copyFairkeeperChestPositionList(Player player, List<BlockPos> list) {
        CapabilityList.FAIRKEEPER_CHEST_POSITIONS_CAP.get(player).copyList(list);
    }
}
