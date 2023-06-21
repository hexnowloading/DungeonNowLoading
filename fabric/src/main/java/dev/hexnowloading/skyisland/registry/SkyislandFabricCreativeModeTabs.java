package dev.hexnowloading.skyisland.registry;

import dev.hexnowloading.skyisland.Skyisland;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;

public class SkyislandFabricCreativeModeTabs {

    private static final CreativeModeTab SKYISLAND_TAB = registerCreativeModeTab("skyisland_tab", FabricItemGroup.builder()
            .icon(() -> new ItemStack(SkyislandFabricItems.WIND_JADE))
            .title(Component.translatable("creativemodetab.skyisland_tab"))
            .displayItems((parameters, output) -> {
                output.accept(SkyislandFabricItems.WIND_JADE);
                output.accept(SkyislandFabricItems.EYE_OF_THE_STORM);
                output.accept(SkyislandFabricBlocks.WIND_ALTER);

            }).build());

    private static CreativeModeTab registerCreativeModeTab(String name, CreativeModeTab tab) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, new ResourceLocation(Skyisland.MOD_ID, name), tab);
    }

    public static void registerCreativeModeTabs() {
        Skyisland.LOGGER.info("Registering Creative Mode Tabs for " + Skyisland.MOD_ID);
    }
}
