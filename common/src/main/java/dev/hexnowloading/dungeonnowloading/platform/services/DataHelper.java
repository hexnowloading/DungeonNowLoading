package dev.hexnowloading.dungeonnowloading.platform.services;

import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public interface DataHelper {

    void setPoint(Player player, int amount);

    int getPoint(Player player);

}
