package dev.hexnowloading.dungeonnowloading.network;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ServerboundSetPlayerCapability {
    public static final ResourceLocation ID = DungeonNowLoading.prefix("test_point");

    public void handler(ServerPlayer serverPlayer) {
        Services.DATA.setPoint(serverPlayer, 12);
    }
}
