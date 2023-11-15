package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.registration.RegistrationProvider;
import dev.hexnowloading.dungeonnowloading.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class DNLCreativeModeTabs {
    public static final RegistrationProvider<CreativeModeTab> CREATIVE_MODE_TAB = RegistrationProvider.get(Registries.CREATIVE_MODE_TAB, DungeonNowLoading.MOD_ID);

    public static final RegistryObject<CreativeModeTab> DUNGEONNOWLOADING_TAB = CREATIVE_MODE_TAB.register("dungeonnowloading", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 1)
            .title(Component.translatable("creativemodetab.dungeonnowloading.tab"))
            .icon(() -> DNLItems.WIND_JADE.get().getDefaultInstance())
            .displayItems(((itemDisplayParameters, output) -> {
                output.accept(DNLItems.CHAOS_SPAWNER_EDGE.get());
                output.accept(DNLItems.CHAOS_SPAWNER_DIAMOND_EDGE.get());
                output.accept(DNLItems.CHAOS_SPAWNER_DIAMOND_VERTEX.get());
                output.accept(DNLItems.CHAOS_SPAWNER_BROKEN_EDGE.get());
                output.accept(DNLItems.CHAOS_SPAWNER_BROKEN_DIAMOND_EDGE.get());
                output.accept(DNLItems.CHAOS_SPAWNER_BROKEN_DIAMOND_VERTEX.get());
                output.accept(DNLItems.CHAOS_SPAWNER_BARRIER_CENTER.get());
                output.accept(DNLItems.CHAOS_SPAWNER_BARRIER_EDGE.get());
                output.accept(DNLItems.CHAOS_SPAWNER_BARRIER_VERTEX.get());
                output.accept(DNLItems.WIND_JADE.get());
                output.accept(DNLItems.EYE_OF_THE_STORM.get());
                output.accept(DNLItems.SKYLIGHT_STONE.get());
                output.accept(DNLItems.SKYLIGHT_GRASS_BLOCK.get());
            }))
            .build());

    public static void init() {}
}
