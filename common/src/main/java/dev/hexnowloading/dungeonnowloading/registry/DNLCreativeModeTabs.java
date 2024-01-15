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
            .icon(() -> DNLItems.SPAWNER_SWORD.get().getDefaultInstance())
            .displayItems(((itemDisplayParameters, output) -> {
                // Spawn Eggs
                output.accept(DNLItems.CHAOS_SPAWNER_SPAWNEGG.get());
                output.accept(DNLItems.HOLLOW_SPAWNEGG.get());
                output.accept(DNLItems.SEALED_CHAOS_SPAWNEGG.get());
                output.accept(DNLItems.WHIMPER_SPAWNEGG.get());
                // Items - Ingredients
                output.accept(DNLItems.SPAWNER_FRAGMENT.get());
                output.accept(DNLItems.SPAWNER_FRAME.get());
                output.accept(DNLItems.SPAWNER_BLADE.get());
                output.accept(DNLItems.SOUL_CLOTH.get());
                output.accept(DNLItems.SOUL_SILK.get());
                output.accept(DNLItems.CHAOTIC_HEXAHEDRON.get());
                // Items - Functional
                output.accept(DNLItems.GREAT_EXPERIENCE_BOTTLE.get());
                output.accept(DNLItems.SCEPTER_OF_SEALED_CHAOS.get());
                output.accept(DNLItems.LIFE_STEALER.get());
                output.accept(DNLItems.SPAWNER_SWORD.get());
                // Items - Armors
                output.accept(DNLItems.SPAWNER_HELMET.get());
                output.accept(DNLItems.SPAWNER_CHESTPLATE.get());
                output.accept(DNLItems.SPAWNER_LEGGINGS.get());
                output.accept(DNLItems.SPAWNER_BOOTS.get());
                // Blocks
                output.accept(DNLItems.BOOK_PILE.get());
                output.accept(DNLItems.CHAOS_SPAWNER_EDGE.get());
                output.accept(DNLItems.CHAOS_SPAWNER_DIAMOND_EDGE.get());
                output.accept(DNLItems.CHAOS_SPAWNER_DIAMOND_VERTEX.get());
                output.accept(DNLItems.CHAOS_SPAWNER_BROKEN_EDGE.get());
                output.accept(DNLItems.CHAOS_SPAWNER_BROKEN_DIAMOND_EDGE.get());
                output.accept(DNLItems.CHAOS_SPAWNER_BROKEN_DIAMOND_VERTEX.get());
                output.accept(DNLItems.CHAOS_SPAWNER_BARRIER_CENTER.get());
                output.accept(DNLItems.CHAOS_SPAWNER_BARRIER_EDGE.get());
                output.accept(DNLItems.CHAOS_SPAWNER_BARRIER_VERTEX.get());
            }))
            .build());

    public static void init() {}
}
