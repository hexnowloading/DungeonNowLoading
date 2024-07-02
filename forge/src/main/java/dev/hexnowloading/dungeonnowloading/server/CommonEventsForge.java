package dev.hexnowloading.dungeonnowloading.server;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.platform.ForgeCommonRegistryHelper;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonNowLoading.MOD_ID, bus= Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEventsForge {
    @SubscribeEvent
    public static void addReloadListenerEvent(AddReloadListenerEvent event) {
        for (SimpleJsonResourceReloadListener loader : ForgeCommonRegistryHelper.dataLoaders)
            event.addListener(loader);
    }
}
