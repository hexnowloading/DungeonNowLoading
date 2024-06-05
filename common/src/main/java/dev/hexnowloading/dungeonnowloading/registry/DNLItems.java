package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.item.*;
import dev.hexnowloading.dungeonnowloading.item.blockitem.FairkeeperChestBlockItem;
import dev.hexnowloading.dungeonnowloading.platform.Services;
import dev.hexnowloading.dungeonnowloading.registration.RegistrationProvider;
import dev.hexnowloading.dungeonnowloading.registration.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

import java.util.*;
import java.util.function.Supplier;

public class DNLItems {
    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, DungeonNowLoading.MOD_ID);
    private static final HashMap<ResourceKey<CreativeModeTab>, ArrayList<ResourceLocation>> ITEM_TABS = new HashMap<>();

    // ITEMS - INGREDIENTS
    public static final RegistryObject<Item> SPAWNER_FRAGMENT = register("spawner_fragment", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPAWNER_FRAME = register("spawner_frame", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SPAWNER_BLADE = register("spawner_blade", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SOUL_CLOTH = register("soul_cloth", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOUL_SILK = register("soul_silk", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> CHAOTIC_HEXAHEDRON = register("chaotic_hexahedron", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> WIND_JADE = register("wind_jade", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EYE_OF_THE_STORM = register("eye_of_the_storm", () -> new Item(new Item.Properties()));

    // ITEMS - FUNCTIONAL
    public static final RegistryObject<Item> GREAT_EXPERIENCE_BOTTLE = register("great_experience_bottle", () -> new GreatExperienceBottleItem(new Item.Properties().rarity(Rarity.RARE), 100));
    public static final RegistryObject<Item> SCEPTER_OF_SEALED_CHAOS = register("scepter_of_sealed_chaos", () -> new ScepterOfSealedChaosItem(new Item.Properties().rarity(Rarity.RARE).durability(100)));
    public static final RegistryObject<Item> LIFE_STEALER = register("life_stealer", () -> new LifeStealerItem(Tiers.DIAMOND, 3, -2.8F, new Item.Properties().rarity(Rarity.COMMON).durability(1562)));
    public static final RegistryObject<Item> SPAWNER_SWORD = register("spawner_sword", () -> new SpawnerSword(Tiers.DIAMOND, 3, -2.4F, new Item.Properties().rarity(Rarity.COMMON).durability(1562)));
    public static final RegistryObject<Item> SPAWNER_HELMET = register("spawner_helmet", () -> new SpawnerArmorItem(DNLArmorMaterial.SPAWNER, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> SPAWNER_CHESTPLATE = register("spawner_chestplate", () -> new SpawnerArmorItem(DNLArmorMaterial.SPAWNER, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> SPAWNER_LEGGINGS = register("spawner_leggings", () -> new SpawnerArmorItem(DNLArmorMaterial.SPAWNER, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> SPAWNER_BOOTS = register("spawner_boots", () -> new SpawnerArmorItem(DNLArmorMaterial.SPAWNER, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> SKULL_OF_CHAOS = register("skull_of_chaos", () -> new SkullOfChaosItem(new Item.Properties().rarity(Rarity.EPIC).durability(10)));

    // BLOCKS
    //public static final RegistryObject<Item> SKYLIGHT_STONE = register("skylight_stone", () -> new BlockItem(DNLBlocks.SKYLIGHT_STONE.get(), new Item.Properties()));
    //public static final RegistryObject<Item> SKYLIGHT_GRASS_BLOCK = register("skylight_grass_block", () -> new BlockItem(DNLBlocks.SKYLIGHT_GRASS_BLOCK.get(), new Item.Properties()));
    //public static final RegistryObject<Item> RUINED_STONE_BRICKS = register("ruined_stone_bricks", () -> new BlockItem(DNLBlocks.RUINED_STONE_BRICKS.get(), new Item.Properties()));

    // DESIGN BLOCKS
    public static final RegistryObject<Item> COILING_STONE_PILLAR = register("coiling_stone_pillar", () -> new BlockItem(DNLBlocks.COILING_STONE_PILLAR.get(), new Item.Properties()));
    public static final RegistryObject<Item> COILING_STONE_PILLAR_CAPITAL = register("coiling_stone_pillar_capital", () -> new BlockItem(DNLBlocks.COILING_STONE_PILLAR_CAPITAL.get(), new Item.Properties()));
    public static final RegistryObject<Item> COILING_STONE_PILLAR_STAIRS = register("coiling_stone_pillar_stairs", () -> new BlockItem(DNLBlocks.COILING_STONE_PILLAR_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> COILING_STONE_PILLAR_SLAB = register("coiling_stone_pillar_slab", () -> new BlockItem(DNLBlocks.COILING_STONE_PILLAR_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> COILING_STONE_PILLAR_WALL = register("coiling_stone_pillar_wall", () -> new BlockItem(DNLBlocks.COILING_STONE_PILLAR_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHISELED_COILING_STONE_PILLAR = register("chiseled_coiling_stone_pillar", () -> new BlockItem(DNLBlocks.CHISELED_COILING_STONE_PILLAR.get(), new Item.Properties()));
    public static final RegistryObject<Item> MOSS = register("moss", () -> new BlockItem(DNLBlocks.MOSS.get(), new Item.Properties()));
    public static final RegistryObject<Item> STONE_TILES = register("stone_tiles", () -> new BlockItem(DNLBlocks.STONE_TILES.get(), new Item.Properties()));
    public static final RegistryObject<Item> CRACKED_STONE_TILES = register("cracked_stone_tiles", () -> new BlockItem(DNLBlocks.CRACKED_STONE_TILES.get(), new Item.Properties()));
    public static final RegistryObject<Item> STONE_TILE_STAIRS = register("stone_tile_stairs", () -> new BlockItem(DNLBlocks.STONE_TILE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> STONE_TILE_SLAB = register("stone_tile_slab", () -> new BlockItem(DNLBlocks.STONE_TILE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> STONE_TILE_WALL = register("stone_tile_wall", () -> new BlockItem(DNLBlocks.STONE_TILE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> SIGNALING_STONE_EMBLEM = register("signaling_stone_emblem", () -> new BlockItem(DNLBlocks.SIGNALING_STONE_EMBLEM.get(), new Item.Properties()));
    public static final RegistryObject<Item> DUELING_STONE_EMBLEM = register("dueling_stone_emblem", () -> new BlockItem(DNLBlocks.DUELING_STONE_EMBLEM.get(), new Item.Properties()));
    public static final RegistryObject<Item> PUZZLING_STONE_EMBLEM = register("puzzling_stone_emblem", () -> new BlockItem(DNLBlocks.PUZZLING_STONE_EMBLEM.get(), new Item.Properties()));

    // MECHANICAL BLOCKS
    public static final RegistryObject<Item> DUNGEON_WALL_TORCH = register("dungeon_wall_torch", () -> new BlockItem(DNLBlocks.DUNGEON_WALL_TORCH.get(), new Item.Properties()));
    public static final RegistryObject<Item> BOOK_PILE = register("book_pile", () -> new BlockItem(DNLBlocks.BOOK_PILE.get(), new Item.Properties()));
    public static final RegistryObject<Item> EXPLOSIVE_BARREL = register("explosive_barrel", () -> new BlockItem(DNLBlocks.EXPLOSIVE_BARREL.get(), new Item.Properties()));
    public static final RegistryObject<Item> COBBLESTONE_PEBBLE = register("cobblestone_pebble", () -> new BlockItem(DNLBlocks.COBBLESTONE_PEBBLES.get(), new Item.Properties()));
    public static final RegistryObject<Item> MOSSY_COBBLESTONE_PEBBLE = register("mossy_cobblestone_pebble", () -> new BlockItem(DNLBlocks.MOSSY_COBBLESTONE_PEBBLES.get(), new Item.Properties()));
    public static final RegistryObject<Item> IRON_INGOT_PILE = register("iron_ingot_pile", () -> new BlockItem(DNLBlocks.IRON_INGOT_PILE.get(), new Item.Properties()));
    public static final RegistryObject<Item> GOLD_INGOT_PILE = register("gold_ingot_pile", () -> new BlockItem(DNLBlocks.GOLD_INGOT_PILE.get(), new Item.Properties()));
    public static final RegistryObject<Item> WOODEN_WALL_RACK = register("wooden_wall_rack", () -> new BlockItem(DNLBlocks.WOODEN_WALL_RACK.get(), new Item.Properties()));
    public static final RegistryObject<Item> WOODEN_WALL_PLATFORM = register("wooden_wall_platform", () -> new BlockItem(DNLBlocks.WOODEN_WALL_PLATFORM.get(), new Item.Properties()));

    public static final RegistryObject<Item> SPIKES = register("spikes", () -> new BlockItem(DNLBlocks.SPIKES.get(), new Item.Properties()));

    public static final RegistryObject<Item> CHAOS_SPAWNER_EDGE = register("chaos_spawner_edge", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_EDGE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHAOS_SPAWNER_DIAMOND_EDGE = register("chaos_spawner_diamond_edge", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_DIAMOND_EDGE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHAOS_SPAWNER_DIAMOND_VERTEX = register("chaos_spawner_diamond_vertex", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_DIAMOND_VERTEX.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHAOS_SPAWNER_BROKEN_EDGE = register("chaos_spawner_broken_edge", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_BROKEN_EDGE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHAOS_SPAWNER_BROKEN_DIAMOND_EDGE = register("chaos_spawner_broken_diamond_edge", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_BROKEN_DIAMOND_EDGE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHAOS_SPAWNER_BROKEN_DIAMOND_VERTEX = register("chaos_spawner_broken_diamond_vertex", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_BROKEN_DIAMOND_VERTEX.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHAOS_SPAWNER_BARRIER_CENTER = register("chaos_spawner_barrier_center", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_BARRIER_CENTER.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHAOS_SPAWNER_BARRIER_EDGE = register("chaos_spawner_barrier_edge", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHAOS_SPAWNER_BARRIER_VERTEX = register("chaos_spawner_barrier_vertex", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_BARRIER_VERTEX.get(), new Item.Properties()));

    public static final RegistryObject<Item> FAIRKEEPER_CHEST = register("fairkeeper_chest", FairkeeperChestBlockItem::new) ;
    public static final RegistryObject<Item> FAIRKEEPER_SPAWNER = register("fairkeeper_spawner", () -> new BlockItem(DNLBlocks.FAIRKEEEPER_SPAWNER.get(), new Item.Properties()));
    public static final RegistryObject<Item> REDSTONE_LANE_I = register("redstone_lane_i", () -> new BlockItem(DNLBlocks.REDSTONE_LANE_I.get(), new Item.Properties()));
    public static final RegistryObject<Item> REDSTONE_LANE_L = register("redstone_lane_l", () -> new BlockItem(DNLBlocks.REDSTONE_LANE_L.get(), new Item.Properties()));
    public static final RegistryObject<Item> REDSTONE_LANE_T = register("redstone_lane_t", () -> new BlockItem(DNLBlocks.REDSTONE_LANE_T.get(), new Item.Properties()));


    public static final RegistryObject<Item> DNL_LOGO = register("dnl_logo", () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> LABYRINTH_TROPHY = register("labyrinth_trophy", () -> new BlockItem(DNLBlocks.LABYRINTH_TROPHY.get(), new Item.Properties().rarity(Rarity.RARE)));

    //public static final RegistryObject<Item> WIND_ALTER = register("wind_alter", () -> new BlockItem(SkyislandBlocks.WIND_ALTER.get(), new Item.Properties()));

    // SPAWN EGG
    //public static final RegistryObject<Item> WINDSTONE_SPAWNEGG = ITEMS.register("windstone_spawn_egg", () -> new SpawnEggItem(SkyislandEntityTypes.WINDSTONE.get(), 0xF6B201, 0xA80E0E, new Item.Properties()));
    public static final RegistryObject<Item> CHAOS_SPAWNER_SPAWNEGG = register("spawn_egg_chaos_spawner", Services.ITEM.makeSpawnEgg(DNLEntityTypes.CHAOS_SPAWNER::get, 0x182c39, 0x9abdd6, new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> HOLLOW_SPAWNEGG = register("spawn_egg_hollow", Services.ITEM.makeSpawnEgg(DNLEntityTypes.HOLLOW::get, 0x53455a, 0xf5d5e0, new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> SPAWNER_CARRIER_SPAWNEGG = register("spawn_egg_spawner_carrier", Services.ITEM.makeSpawnEgg(DNLEntityTypes.SPAWNER_CARRIER::get, 0x6a6d68, 0x60c9f3, new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> SEALED_CHAOS_SPAWNEGG = register("spawn_egg_sealed_chaos", Services.ITEM.makeSpawnEgg(DNLEntityTypes.SEALED_CHAOS::get, 0x5f0d80, 0xd5bcd7, new Item.Properties().rarity(Rarity.COMMON)));
    public static final RegistryObject<Item> WHIMPER_SPAWNEGG = register("spawn_egg_whimper", Services.ITEM.makeSpawnEgg(DNLEntityTypes.WHIMPER::get, 0x60f5fa, 0xbef5fa, new Item.Properties().rarity(Rarity.COMMON)));

    @SafeVarargs
    private static RegistryObject<Item> register(String name, Supplier<? extends Item> supplier, ResourceKey<CreativeModeTab>... tabs)
    {
        for (ResourceKey<CreativeModeTab> tab: tabs) {
            ArrayList<ResourceLocation> list = ITEM_TABS.computeIfAbsent(tab, empty -> new ArrayList<>());
            list.add(new ResourceLocation(DungeonNowLoading.MOD_ID, name));
        }
        return ITEMS.register(name, supplier);
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
