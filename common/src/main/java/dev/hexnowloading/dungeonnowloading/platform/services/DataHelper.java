package dev.hexnowloading.dungeonnowloading.platform.services;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Optional;

public interface DataHelper {

    void setPoint(Player player, int amount);

    int getPoint(Player player);

    Optional<List<BlockPos>> getFairkeeperChestPositionList(Player player);

    void addFairkeeperChestPositionList(Player player, BlockPos blockPos);

    void copyFairkeeperChestPositionList(Player player, List<BlockPos> list);

}
