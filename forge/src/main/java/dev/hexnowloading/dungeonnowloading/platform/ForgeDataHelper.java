package dev.hexnowloading.dungeonnowloading.platform;

import dev.hexnowloading.dungeonnowloading.capability.DNLForgePlayerPoint;
import dev.hexnowloading.dungeonnowloading.capability.DNLForgePlayerPointProvider;
import dev.hexnowloading.dungeonnowloading.capability.forge.FairkeeperChestPositionsCapability;
import dev.hexnowloading.dungeonnowloading.capability.forge.FairkeeperChestPositionsCapabilityProvider;
import dev.hexnowloading.dungeonnowloading.platform.services.DataHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Optional;

public class ForgeDataHelper implements DataHelper {
    @Override
    public void setPoint(Player player, int amount) {
        player.getCapability(DNLForgePlayerPointProvider.PLAYER_TEST_POINT).ifPresent(point -> {
            point.setPoint(10);
        });
    }

    @Override
    public int getPoint(Player player) {
        /*DNLForgePlayerPoint capPlayer = player.getCapability(DNLForgePlayerPointProvider.PLAYER_TEST_POINT).orElse(new DNLForgePlayerPoint());
        return capPlayer.getPoint();*/
        return player.getCapability(DNLForgePlayerPointProvider.PLAYER_TEST_POINT).map(DNLForgePlayerPoint::getPoint).orElse(0);
    }

    @Override
    public Optional<List<BlockPos>> getFairkeeperChestPositionList(Player player) {
        return player.getCapability(FairkeeperChestPositionsCapabilityProvider.FAIRKEEPER_CHEST_POSITIONS).map(FairkeeperChestPositionsCapability::getList);
    }

    @Override
    public void addFairkeeperChestPositionList(Player player, BlockPos blockPos) {
        player.getCapability(FairkeeperChestPositionsCapabilityProvider.FAIRKEEPER_CHEST_POSITIONS).ifPresent(cap -> {
            cap.addBlockPos(blockPos);
        });
    }

    @Override
    public void copyFairkeeperChestPositionList(Player player, List<BlockPos> list) {
        player.getCapability(FairkeeperChestPositionsCapabilityProvider.FAIRKEEPER_CHEST_POSITIONS).ifPresent(cap -> {
            cap.copyList(list);
        });
    }
}
