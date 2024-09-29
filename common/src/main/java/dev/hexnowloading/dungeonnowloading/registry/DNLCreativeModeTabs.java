package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.platform.Services;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class DNLCreativeModeTabs {

    public static final Supplier<CreativeModeTab> DUNGEONNOWLOADING_TAB = register("main",
            () -> DNLItems.DNL_LOGO.get().getDefaultInstance(),
            (itemDisplayParameters, output) -> {
                // Spawn Eggs
                output.accept(DNLItems.CHAOS_SPAWNER_SPAWNEGG.get());
                output.accept(DNLItems.FAIRKEEPER_SPAWNEGG.get());
                output.accept(DNLItems.HOLLOW_SPAWNEGG.get());
                output.accept(DNLItems.SPAWNER_CARRIER_SPAWNEGG.get());
                output.accept(DNLItems.SCUTTLE_SPAWNEGG.get());
                output.accept(DNLItems.SEALED_CHAOS_SPAWNEGG.get());
                output.accept(DNLItems.WHIMPER_SPAWNEGG.get());
                // Items - Ingredients
                output.accept(DNLItems.SPAWNER_FRAGMENT.get());
                output.accept(DNLItems.SPAWNER_FRAME.get());
                output.accept(DNLItems.SPAWNER_BLADE.get());
                output.accept(DNLItems.SOUL_CLOTH.get());
                output.accept(DNLItems.SOUL_SILK.get());
                output.accept(DNLItems.CHAOTIC_HEXAHEDRON.get());
                output.accept(DNLItems.REDSTONE_SUPPRESSOR.get());
                output.accept(DNLItems.REDSTONE_CORE.get());
                output.accept(DNLItems.REDSTONE_CHIP.get());
                output.accept(DNLItems.REDSTONE_CIRCUIT.get());
                // Items - Functional
                output.accept(DNLItems.GREAT_EXPERIENCE_BOTTLE.get());
                output.accept(DNLItems.SCEPTER_OF_SEALED_CHAOS.get());
                output.accept(DNLItems.LIFE_STEALER.get());
                output.accept(DNLItems.SPAWNER_SWORD.get());
                output.accept(DNLItems.SKULL_OF_CHAOS.get());
                output.accept(DNLItems.REDSTONE_CATALYST.get());
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
                output.accept(DNLItems.POLISHED_STONE.get());
                output.accept(DNLItems.BORDERED_STONE.get());
                output.accept(DNLItems.MOSS.get());

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
                output.accept(DNLItems.WISE_FAIRKEEPER_CHEST.get());
                output.accept(DNLItems.FIERCE_FAIRKEEPER_CHEST.get());
                output.accept(DNLItems.FAIRKEEPER_SPAWNER.get());
                output.accept(DNLItems.REDSTONE_LANE_I.get());
                output.accept(DNLItems.REDSTONE_LANE_L.get());
                output.accept(DNLItems.REDSTONE_LANE_T.get());
                output.accept(DNLItems.ROTATOR_PRESSURE_PLATE.get());
                output.accept(DNLItems.STONE_NOTCH.get());
                output.accept(DNLItems.COAL_STONE_NOTCH.get());
                output.accept(DNLItems.COPPER_STONE_NOTCH.get());
                output.accept(DNLItems.IRON_STONE_NOTCH.get());
                output.accept(DNLItems.GOLD_STONE_NOTCH.get());
                output.accept(DNLItems.REDSTONE_STONE_NOTCH.get());
                output.accept(DNLItems.AMETHYST_STONE_NOTCH.get());
                output.accept(DNLItems.LAPIS_STONE_NOTCH.get());
                output.accept(DNLItems.EMERALD_STONE_NOTCH.get());
                output.accept(DNLItems.QUARTZ_STONE_NOTCH.get());
                output.accept(DNLItems.GLOWSTONE_STONE_NOTCH.get());
                output.accept(DNLItems.PRISMARINE_STONE_NOTCH.get());
                output.accept(DNLItems.CHORUS_STONE_NOTCH.get());
                output.accept(DNLItems.ECHO_STONE_NOTCH.get());
                output.accept(DNLItems.DIAMOND_STONE_NOTCH.get());
                output.accept(DNLItems.NETHERITE_STONE_NOTCH.get());
                output.accept(DNLItems.SIGNAL_GATE.get());
                output.accept(DNLItems.SCUTTLE_STATUE.get());
                output.accept(DNLItems.STONE_PILLAR.get());
                output.accept(DNLItems.SHIELDING_STONE_PILLAR.get());

                // Blocks - Trophies
                output.accept(DNLItems.DNL_LOGO.get());
                output.accept(DNLItems.LABYRINTH_TROPHY.get());
            });

    public static Supplier<CreativeModeTab> register(String name, Supplier<ItemStack> iconSupplier, CreativeModeTab.DisplayItemsGenerator itemsGenerator) {
        return Services.REGISTRY.registerCreativeTab(name, iconSupplier, itemsGenerator);
    }

    public static void init() {}
}
