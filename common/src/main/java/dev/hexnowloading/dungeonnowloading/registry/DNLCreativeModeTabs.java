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
            .icon(() -> DNLItems.DNL_LOGO.get().getDefaultInstance())
            .displayItems(((itemDisplayParameters, output) -> {
                // Spawn Eggs
                output.accept(DNLItems.CHAOS_SPAWNER_SPAWNEGG.get());
                output.accept(DNLItems.HOLLOW_SPAWNEGG.get());
                output.accept(DNLItems.SPAWNER_CARRIER_SPAWNEGG.get());
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
                output.accept(DNLItems.SKULL_OF_CHAOS.get());
                // Items - Armors
                output.accept(DNLItems.SPAWNER_HELMET.get());
                output.accept(DNLItems.SPAWNER_CHESTPLATE.get());
                output.accept(DNLItems.SPAWNER_LEGGINGS.get());
                output.accept(DNLItems.SPAWNER_BOOTS.get());
                // Blocks

                // Blocks - Design
                output.accept(DNLItems.COILING_STONE_PILLAR.get());
                output.accept(DNLItems.COILING_STONE_PILLAR_STAIRS.get());
                output.accept(DNLItems.COILING_STONE_PILLAR_SLAB.get());
                output.accept(DNLItems.COILING_STONE_PILLAR_WALL.get());
                output.accept(DNLItems.CHISELED_COILING_STONE_PILLAR.get());
                output.accept(DNLItems.COILING_STONE_PILLAR_CAPITAL.get());
                output.accept(DNLItems.STONE_TILES.get());
                output.accept(DNLItems.CRACKED_STONE_TILES.get());
                output.accept(DNLItems.STONE_TILE_STAIRS.get());
                output.accept(DNLItems.STONE_TILE_SLAB.get());
                output.accept(DNLItems.STONE_TILE_WALL.get());
                output.accept(DNLItems.SIGNALING_STONE_EMBLEM.get());
                output.accept(DNLItems.DUELING_STONE_EMBLEM.get());
                output.accept(DNLItems.PUZZLING_STONE_EMBLEM.get());
                //output.accept(DNLItems.MOSS.get());

                // Blocks - Mechanical
                output.accept(DNLItems.DUNGEON_WALL_TORCH.get());
                output.accept(DNLItems.BOOK_PILE.get());
                output.accept(DNLItems.EXPLOSIVE_BARREL.get());
                output.accept(DNLItems.COBBLESTONE_PEBBLE.get());
                output.accept(DNLItems.MOSSY_COBBLESTONE_PEBBLE.get());
                output.accept(DNLItems.IRON_INGOT_PILE.get());
                output.accept(DNLItems.GOLD_INGOT_PILE.get());
                output.accept(DNLItems.WOODEN_WALL_RACK.get());
                output.accept(DNLItems.WOODEN_WALL_PLATFORM.get());
                output.accept(DNLItems.SPIKES.get());
                output.accept(DNLItems.CHAOS_SPAWNER_EDGE.get());
                output.accept(DNLItems.CHAOS_SPAWNER_DIAMOND_EDGE.get());
                output.accept(DNLItems.CHAOS_SPAWNER_DIAMOND_VERTEX.get());
                output.accept(DNLItems.CHAOS_SPAWNER_BROKEN_EDGE.get());
                output.accept(DNLItems.CHAOS_SPAWNER_BROKEN_DIAMOND_EDGE.get());
                output.accept(DNLItems.CHAOS_SPAWNER_BROKEN_DIAMOND_VERTEX.get());
                output.accept(DNLItems.CHAOS_SPAWNER_BARRIER_CENTER.get());
                output.accept(DNLItems.CHAOS_SPAWNER_BARRIER_EDGE.get());
                output.accept(DNLItems.CHAOS_SPAWNER_BARRIER_VERTEX.get());
                output.accept(DNLItems.FAIRKEEPER_CHEST.get());
                output.accept(DNLItems.FAIRKEEPER_SPAWNER.get());
                output.accept(DNLItems.REDSTONE_LANE_I.get());
                output.accept(DNLItems.REDSTONE_LANE_L.get());
                output.accept(DNLItems.REDSTONE_LANE_T.get());

                // Blocks - Trophies
                output.accept(DNLItems.DNL_LOGO.get());
                output.accept(DNLItems.LABYRINTH_TROPHY.get());
            }))
            .build());

    public static void init() {}
}
