package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.item.*;
import dev.hexnowloading.dungeonnowloading.item.blockitem.FairkeeperChestBlockItem;
import dev.hexnowloading.dungeonnowloading.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

import java.util.*;
import java.util.function.Supplier;

public class DNLItems {
    private static final HashMap<ResourceKey<CreativeModeTab>, ArrayList<ResourceLocation>> ITEM_TABS = new HashMap<>();

    // ITEMS - INGREDIENTS
    public static final Supplier<Item> SPAWNER_FRAGMENT = register("spawner_fragment", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> SPAWNER_FRAME = register("spawner_frame", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final Supplier<Item> SPAWNER_BLADE = register("spawner_blade", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final Supplier<Item> SOUL_CLOTH = register("soul_cloth", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> SOUL_SILK = register("soul_silk", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final Supplier<Item> CHAOTIC_HEXAHEDRON = register("chaotic_hexahedron", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final Supplier<Item> WIND_JADE = register("wind_jade", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> EYE_OF_THE_STORM = register("eye_of_the_storm", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> REDSTONE_SUPPRESSOR = register("redstone_suppressor", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> REDSTONE_CORE = register("redstone_core", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> REDSTONE_CHIP = register("redstone_chip", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> REDSTONE_CIRCUIT = register("redstone_circuit", () -> new Item(new Item.Properties()));


    // ITEMS - FUNCTIONAL
    public static final Supplier<Item> GREAT_EXPERIENCE_BOTTLE = register("great_experience_bottle", () -> new GreatExperienceBottleItem(new Item.Properties().rarity(Rarity.RARE), 100));
    public static final Supplier<Item> SCEPTER_OF_SEALED_CHAOS = register("scepter_of_sealed_chaos", () -> new ScepterOfSealedChaosItem(new Item.Properties().rarity(Rarity.RARE).durability(100)));
    public static final Supplier<Item> LIFE_STEALER = register("life_stealer", () -> new LifeStealerItem(Tiers.DIAMOND, 3, -2.8F, new Item.Properties().rarity(Rarity.COMMON).durability(1562)));
    public static final Supplier<Item> SPAWNER_SWORD = register("spawner_sword", () -> new SpawnerSword(Tiers.DIAMOND, 3, -2.4F, new Item.Properties().rarity(Rarity.COMMON).durability(1562)));
    public static final Supplier<Item> SPAWNER_HELMET = register("spawner_helmet", () -> new SpawnerArmorItem(DNLArmorMaterial.SPAWNER, ArmorItem.Type.HELMET));
    public static final Supplier<Item> SPAWNER_CHESTPLATE = register("spawner_chestplate", () -> new SpawnerArmorItem(DNLArmorMaterial.SPAWNER, ArmorItem.Type.CHESTPLATE));
    public static final Supplier<Item> SPAWNER_LEGGINGS = register("spawner_leggings", () -> new SpawnerArmorItem(DNLArmorMaterial.SPAWNER, ArmorItem.Type.LEGGINGS));
    public static final Supplier<Item> SPAWNER_BOOTS = register("spawner_boots", () -> new SpawnerArmorItem(DNLArmorMaterial.SPAWNER, ArmorItem.Type.BOOTS));
    public static final Supplier<Item> SKULL_OF_CHAOS = register("skull_of_chaos", () -> new SkullOfChaosItem(new Item.Properties().rarity(Rarity.EPIC).durability(10)));

    // BLOCKS
    //public static final Supplier<Item> SKYLIGHT_STONE = register("skylight_stone", () -> new BlockItem(DNLBlocks.SKYLIGHT_STONE.get(), new Item.Properties()));
    //public static final Supplier<Item> SKYLIGHT_GRASS_BLOCK = register("skylight_grass_block", () -> new BlockItem(DNLBlocks.SKYLIGHT_GRASS_BLOCK.get(), new Item.Properties()));
    //public static final Supplier<Item> RUINED_STONE_BRICKS = register("ruined_stone_bricks", () -> new BlockItem(DNLBlocks.RUINED_STONE_BRICKS.get(), new Item.Properties()));

    // DESIGN BLOCKS
    public static final Supplier<Item> COILING_STONE_PILLAR = register("coiling_stone_pillar", () -> new BlockItem(DNLBlocks.COILING_STONE_PILLAR.get(), new Item.Properties()));
    public static final Supplier<Item> COILING_STONE_PILLAR_CAPITAL = register("coiling_stone_pillar_capital", () -> new BlockItem(DNLBlocks.COILING_STONE_PILLAR_CAPITAL.get(), new Item.Properties()));
    public static final Supplier<Item> COILING_STONE_PILLAR_STAIRS = register("coiling_stone_pillar_stairs", () -> new BlockItem(DNLBlocks.COILING_STONE_PILLAR_STAIRS.get(), new Item.Properties()));
    public static final Supplier<Item> COILING_STONE_PILLAR_SLAB = register("coiling_stone_pillar_slab", () -> new BlockItem(DNLBlocks.COILING_STONE_PILLAR_SLAB.get(), new Item.Properties()));
    public static final Supplier<Item> COILING_STONE_PILLAR_WALL = register("coiling_stone_pillar_wall", () -> new BlockItem(DNLBlocks.COILING_STONE_PILLAR_WALL.get(), new Item.Properties()));
    public static final Supplier<Item> CHISELED_COILING_STONE_PILLAR = register("chiseled_coiling_stone_pillar", () -> new BlockItem(DNLBlocks.CHISELED_COILING_STONE_PILLAR.get(), new Item.Properties()));
    public static final Supplier<Item> MOSS = register("moss", () -> new BlockItem(DNLBlocks.MOSS.get(), new Item.Properties()));
    public static final Supplier<Item> STONE_TILES = register("stone_tiles", () -> new BlockItem(DNLBlocks.STONE_TILES.get(), new Item.Properties()));
    public static final Supplier<Item> CRACKED_STONE_TILES = register("cracked_stone_tiles", () -> new BlockItem(DNLBlocks.CRACKED_STONE_TILES.get(), new Item.Properties()));
    public static final Supplier<Item> STONE_TILE_STAIRS = register("stone_tile_stairs", () -> new BlockItem(DNLBlocks.STONE_TILE_STAIRS.get(), new Item.Properties()));
    public static final Supplier<Item> STONE_TILE_SLAB = register("stone_tile_slab", () -> new BlockItem(DNLBlocks.STONE_TILE_SLAB.get(), new Item.Properties()));
    public static final Supplier<Item> STONE_TILE_WALL = register("stone_tile_wall", () -> new BlockItem(DNLBlocks.STONE_TILE_WALL.get(), new Item.Properties()));
    public static final Supplier<Item> SIGNALING_STONE_EMBLEM = register("signaling_stone_emblem", () -> new BlockItem(DNLBlocks.SIGNALING_STONE_EMBLEM.get(), new Item.Properties()));
    public static final Supplier<Item> DUELING_STONE_EMBLEM = register("dueling_stone_emblem", () -> new BlockItem(DNLBlocks.DUELING_STONE_EMBLEM.get(), new Item.Properties()));
    public static final Supplier<Item> PUZZLING_STONE_EMBLEM = register("puzzling_stone_emblem", () -> new BlockItem(DNLBlocks.PUZZLING_STONE_EMBLEM.get(), new Item.Properties()));
    public static final Supplier<Item> POLISHED_STONE = register("polished_stone", () -> new BlockItem(DNLBlocks.POLISHED_STONE.get(), new Item.Properties()));
    public static final Supplier<Item> BORDERED_STONE = register("bordered_stone", () -> new BlockItem(DNLBlocks.BORDERED_STONE.get(), new Item.Properties()));

    // MECHANICAL BLOCKS
    public static final Supplier<Item> DUNGEON_WALL_TORCH = register("dungeon_wall_torch", () -> new BlockItem(DNLBlocks.DUNGEON_WALL_TORCH.get(), new Item.Properties()));
    public static final Supplier<Item> BOOK_PILE = register("book_pile", () -> new BlockItem(DNLBlocks.BOOK_PILE.get(), new Item.Properties()));
    public static final Supplier<Item> EXPLOSIVE_BARREL = register("explosive_barrel", () -> new BlockItem(DNLBlocks.EXPLOSIVE_BARREL.get(), new Item.Properties()));
    public static final Supplier<Item> COBBLESTONE_PEBBLE = register("cobblestone_pebble", () -> new BlockItem(DNLBlocks.COBBLESTONE_PEBBLES.get(), new Item.Properties()));
    public static final Supplier<Item> MOSSY_COBBLESTONE_PEBBLE = register("mossy_cobblestone_pebble", () -> new BlockItem(DNLBlocks.MOSSY_COBBLESTONE_PEBBLES.get(), new Item.Properties()));
    public static final Supplier<Item> IRON_INGOT_PILE = register("iron_ingot_pile", () -> new BlockItem(DNLBlocks.IRON_INGOT_PILE.get(), new Item.Properties()));
    public static final Supplier<Item> GOLD_INGOT_PILE = register("gold_ingot_pile", () -> new BlockItem(DNLBlocks.GOLD_INGOT_PILE.get(), new Item.Properties()));
    public static final Supplier<Item> WOODEN_WALL_RACK = register("wooden_wall_rack", () -> new BlockItem(DNLBlocks.WOODEN_WALL_RACK.get(), new Item.Properties()));
    public static final Supplier<Item> WOODEN_WALL_PLATFORM = register("wooden_wall_platform", () -> new BlockItem(DNLBlocks.WOODEN_WALL_PLATFORM.get(), new Item.Properties()));

    public static final Supplier<Item> SPIKES = register("spikes", () -> new BlockItem(DNLBlocks.SPIKES.get(), new Item.Properties()));

    public static final Supplier<Item> CHAOS_SPAWNER_EDGE = register("chaos_spawner_edge", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_EDGE.get(), new Item.Properties()));
    public static final Supplier<Item> CHAOS_SPAWNER_DIAMOND_EDGE = register("chaos_spawner_diamond_edge", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_DIAMOND_EDGE.get(), new Item.Properties()));
    public static final Supplier<Item> CHAOS_SPAWNER_DIAMOND_VERTEX = register("chaos_spawner_diamond_vertex", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_DIAMOND_VERTEX.get(), new Item.Properties()));
    public static final Supplier<Item> CHAOS_SPAWNER_BROKEN_EDGE = register("chaos_spawner_broken_edge", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_BROKEN_EDGE.get(), new Item.Properties()));
    public static final Supplier<Item> CHAOS_SPAWNER_BROKEN_DIAMOND_EDGE = register("chaos_spawner_broken_diamond_edge", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_BROKEN_DIAMOND_EDGE.get(), new Item.Properties()));
    public static final Supplier<Item> CHAOS_SPAWNER_BROKEN_DIAMOND_VERTEX = register("chaos_spawner_broken_diamond_vertex", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_BROKEN_DIAMOND_VERTEX.get(), new Item.Properties()));
    public static final Supplier<Item> CHAOS_SPAWNER_BARRIER_CENTER = register("chaos_spawner_barrier_center", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_BARRIER_CENTER.get(), new Item.Properties()));
    public static final Supplier<Item> CHAOS_SPAWNER_BARRIER_EDGE = register("chaos_spawner_barrier_edge", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get(), new Item.Properties()));
    public static final Supplier<Item> CHAOS_SPAWNER_BARRIER_VERTEX = register("chaos_spawner_barrier_vertex", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_BARRIER_VERTEX.get(), new Item.Properties()));

    public static final Supplier<Item> FAIRKEEPER_CHEST = register("fairkeeper_chest", FairkeeperChestBlockItem::new) ;
    public static final Supplier<Item> FAIRKEEPER_SPAWNER = register("fairkeeper_spawner", () -> new BlockItem(DNLBlocks.FAIRKEEEPER_SPAWNER.get(), new Item.Properties()));
    public static final Supplier<Item> REDSTONE_LANE_I = register("redstone_lane_i", () -> new BlockItem(DNLBlocks.REDSTONE_LANE_I.get(), new Item.Properties()));
    public static final Supplier<Item> REDSTONE_LANE_L = register("redstone_lane_l", () -> new BlockItem(DNLBlocks.REDSTONE_LANE_L.get(), new Item.Properties()));
    public static final Supplier<Item> REDSTONE_LANE_T = register("redstone_lane_t", () -> new BlockItem(DNLBlocks.REDSTONE_LANE_T.get(), new Item.Properties()));
    public static final Supplier<Item> ROTATOR_PRESSURE_PLATE = register("rotator_pressure_plate", () -> new BlockItem(DNLBlocks.ROTATOR_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final Supplier<Item> STONE_NOTCH = register("stone_notch", () -> new BlockItem(DNLBlocks.STONE_NOTCH.get(), new Item.Properties()));
    public static final Supplier<Item> COAL_STONE_NOTCH = register("coal_stone_notch", () -> new BlockItem(DNLBlocks.COAL_STONE_NOTCH.get(), new Item.Properties()));
    public static final Supplier<Item> COPPER_STONE_NOTCH = register("copper_stone_notch", () -> new BlockItem(DNLBlocks.COPPER_STONE_NOTCH.get(), new Item.Properties()));
    public static final Supplier<Item> IRON_STONE_NOTCH = register("iron_stone_notch", () -> new BlockItem(DNLBlocks.IRON_STONE_NOTCH.get(), new Item.Properties()));
    public static final Supplier<Item> GOLD_STONE_NOTCH = register("gold_stone_notch", () -> new BlockItem(DNLBlocks.GOLD_STONE_NOTCH.get(), new Item.Properties()));
    public static final Supplier<Item> REDSTONE_STONE_NOTCH = register("redstone_stone_notch", () -> new BlockItem(DNLBlocks.REDSTONE_STONE_NOTCH.get(), new Item.Properties()));
    public static final Supplier<Item> AMETHYST_STONE_NOTCH = register("amethyst_stone_notch", () -> new BlockItem(DNLBlocks.AMETHYST_STONE_NOTCH.get(), new Item.Properties()));
    public static final Supplier<Item> LAPIS_STONE_NOTCH = register("lapis_stone_notch", () -> new BlockItem(DNLBlocks.LAPIS_STONE_NOTCH.get(), new Item.Properties()));
    public static final Supplier<Item> EMERALD_STONE_NOTCH = register("emerald_stone_notch", () -> new BlockItem(DNLBlocks.EMERALD_STONE_NOTCH.get(), new Item.Properties()));
    public static final Supplier<Item> QUARTZ_STONE_NOTCH = register("quartz_stone_notch", () -> new BlockItem(DNLBlocks.QUARTZ_STONE_NOTCH.get(), new Item.Properties()));
    public static final Supplier<Item> GLOWSTONE_STONE_NOTCH = register("glowstone_stone_notch", () -> new BlockItem(DNLBlocks.GLOWSTONE_STONE_NOTCH.get(), new Item.Properties()));
    public static final Supplier<Item> PRISMARINE_STONE_NOTCH = register("prismarine_stone_notch", () -> new BlockItem(DNLBlocks.PRISMARINE_STONE_NOTCH.get(), new Item.Properties()));
    public static final Supplier<Item> CHORUS_STONE_NOTCH = register("chorus_stone_notch", () -> new BlockItem(DNLBlocks.CHORUS_STONE_NOTCH.get(), new Item.Properties()));
    public static final Supplier<Item> ECHO_STONE_NOTCH = register("echo_stone_notch", () -> new BlockItem(DNLBlocks.ECHO_STONE_NOTCH.get(), new Item.Properties()));
    public static final Supplier<Item> DIAMOND_STONE_NOTCH = register("diamond_stone_notch", () -> new BlockItem(DNLBlocks.DIAMOND_STONE_NOTCH.get(), new Item.Properties()));
    public static final Supplier<Item> NETHERITE_STONE_NOTCH = register("netherite_stone_notch", () -> new BlockItem(DNLBlocks.NETHERITE_STONE_NOTCH.get(), new Item.Properties()));
    public static final Supplier<Item> SIGNAL_GATE = register("signal_gate", () -> new BlockItem(DNLBlocks.SIGNAL_GATE.get(), new Item.Properties()));
    public static final Supplier<Item> SCUTTLE_STATUE = register("scuttle_statue", () -> new BlockItem(DNLBlocks.SCUTTLE_STATUE.get(), new Item.Properties()));

    public static final Supplier<Item> DNL_LOGO = register("dnl_logo", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final Supplier<Item> LABYRINTH_TROPHY = register("labyrinth_trophy", () -> new BlockItem(DNLBlocks.LABYRINTH_TROPHY.get(), new Item.Properties().rarity(Rarity.RARE)));

    //public static final Supplier<Item> WIND_ALTER = register("wind_alter", () -> new BlockItem(SkyislandBlocks.WIND_ALTER.get(), new Item.Properties()));

    // SPAWN EGG
    //public static final Supplier<Item> WINDSTONE_SPAWNEGG = ITEMS.register("windstone_spawn_egg", () -> new SpawnEggItem(SkyislandEntityTypes.WINDSTONE.get(), 0xF6B201, 0xA80E0E, new Item.Properties()));
    public static final Supplier<Item> CHAOS_SPAWNER_SPAWNEGG = register("spawn_egg_chaos_spawner", Services.ITEM.makeSpawnEgg(DNLEntityTypes.CHAOS_SPAWNER::get, 0x182c39, 0x9abdd6, new Item.Properties().rarity(Rarity.EPIC)));
    public static final Supplier<Item> HOLLOW_SPAWNEGG = register("spawn_egg_hollow", Services.ITEM.makeSpawnEgg(DNLEntityTypes.HOLLOW::get, 0x53455a, 0xf5d5e0, new Item.Properties().rarity(Rarity.COMMON)));
    public static final Supplier<Item> SPAWNER_CARRIER_SPAWNEGG = register("spawn_egg_spawner_carrier", Services.ITEM.makeSpawnEgg(DNLEntityTypes.SPAWNER_CARRIER::get, 0x6a6d68, 0x60c9f3, new Item.Properties().rarity(Rarity.COMMON)));
    public static final Supplier<Item> SCUTTLE_SPAWNEGG = register("spawn_egg_scuttle", Services.ITEM.makeSpawnEgg(DNLEntityTypes.SCUTTLE::get, 0x6a6d68, 0xff8800, new Item.Properties().rarity(Rarity.COMMON)));
    public static final Supplier<Item> SEALED_CHAOS_SPAWNEGG = register("spawn_egg_sealed_chaos", Services.ITEM.makeSpawnEgg(DNLEntityTypes.SEALED_CHAOS::get, 0x5f0d80, 0xd5bcd7, new Item.Properties().rarity(Rarity.COMMON)));
    public static final Supplier<Item> WHIMPER_SPAWNEGG = register("spawn_egg_whimper", Services.ITEM.makeSpawnEgg(DNLEntityTypes.WHIMPER::get, 0x60f5fa, 0xbef5fa, new Item.Properties().rarity(Rarity.COMMON)));

    private static <T extends Item> Supplier<T> register(String name, Supplier<T> itemSupplier) {
        return Services.REGISTRY.register(BuiltInRegistries.ITEM, name, itemSupplier);
    }

    public static Map<ResourceKey<CreativeModeTab>, ArrayList<ResourceLocation>> getItemTabs() {
        return ITEM_TABS;
    }

    public static List<ItemStack> getItemsForTab(ResourceKey<CreativeModeTab> tab) {
        List<ItemStack> items = new ArrayList<>();
        getItemTabs().forEach((itemTab, itemLikes) -> {
            if (tab == itemTab) {
                itemLikes.forEach((itemLike) -> items.add(Objects.requireNonNull(BuiltInRegistries.ITEM.get(itemLike)).getDefaultInstance()));
            }
        });
        return items;
    }

    public static void init() {
    }
}
