package dev.hexnowloading.skyisland.registry;

import dev.hexnowloading.skyisland.Skyisland;
import dev.hexnowloading.skyisland.registration.RegistrationProvider;
import dev.hexnowloading.skyisland.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class SkyislandCreativeModeTabs {
    public static final RegistrationProvider<CreativeModeTab> CREATIVE_MODE_TAB = RegistrationProvider.get(Registries.CREATIVE_MODE_TAB, Skyisland.MOD_ID);

    public static final RegistryObject<CreativeModeTab> SKYISLAND_TAB = CREATIVE_MODE_TAB.register("skyisland", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 1)
            .title(Component.translatable("creaetivemodetab.skyisland_tab"))
            .icon(() -> SkyislandItems.WIND_JADE.get().getDefaultInstance())
            .displayItems(((itemDisplayParameters, output) -> {
                output.accept(SkyislandItems.WIND_JADE.get());
                output.accept(SkyislandItems.EYE_OF_THE_STORM.get());
                output.accept(SkyislandItems.WIND_ALTER.get());
                output.accept(SkyislandItems.SKYLIGHT_STONE.get());
                output.accept(SkyislandItems.SKYLIGHT_GRASS_BLOCK.get());
            }))
            .build());

    public static void init() {}
}
