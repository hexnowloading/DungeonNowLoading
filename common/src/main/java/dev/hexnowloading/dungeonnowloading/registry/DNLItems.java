package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.platform.Services;
import dev.hexnowloading.dungeonnowloading.registration.RegistrationProvider;
import dev.hexnowloading.dungeonnowloading.registration.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.function.Supplier;

public class DNLItems {
    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, DungeonNowLoading.MOD_ID);
    private static final HashMap<ResourceKey<CreativeModeTab>, ArrayList<ResourceLocation>> ITEM_TABS = new HashMap<>();

    // ITEMS
    public static final RegistryObject<Item> WIND_JADE = register("wind_jade", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EYE_OF_THE_STORM = register("eye_of_the_storm", () -> new Item(new Item.Properties()));

    // BLOCKS
    public static final RegistryObject<Item> SKYLIGHT_STONE = register("skylight_stone", () -> new BlockItem(DNLBlocks.SKYLIGHT_STONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> SKYLIGHT_GRASS_BLOCK = register("skylight_grass_block", () -> new BlockItem(DNLBlocks.SKYLIGHT_GRASS_BLOCK.get(), new Item.Properties()));

    // MECHANICAL BLOCKS
    public static final RegistryObject<Item> CHAOS_SPAWNER_EDGE = register("chaos_spawner_edge", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_EDGE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHAOS_SPAWNER_DIAMOND_EDGE = register("chaos_spawner_diamond_edge", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_DIAMOND_EDGE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHAOS_SPAWNER_DIAMOND_VERTEX = register("chaos_spawner_diamond_vertex", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_DIAMOND_VERTEX.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHAOS_SPAWNER_BROKEN_EDGE = register("chaos_spawner_broken_edge", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_BROKEN_EDGE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHAOS_SPAWNER_BROKEN_DIAMOND_EDGE = register("chaos_spawner_broken_diamond_edge", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_BROKEN_DIAMOND_EDGE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHAOS_SPAWNER_BROKEN_DIAMOND_VERTEX = register("chaos_spawner_broken_diamond_vertex", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_BROKEN_DIAMOND_VERTEX.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHAOS_SPAWNER_BARRIER_CENTER = register("chaos_spawner_barrier_center", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_BARRIER_CENTER.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHAOS_SPAWNER_BARRIER_EDGE = register("chaos_spawner_barrier_edge", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHAOS_SPAWNER_BARRIER_VERTEX = register("chaos_spawner_barrier_vertex", () -> new BlockItem(DNLBlocks.CHAOS_SPAWNER_BARRIER_VERTEX.get(), new Item.Properties()));

    //public static final RegistryObject<Item> WIND_ALTER = register("wind_alter", () -> new BlockItem(SkyislandBlocks.WIND_ALTER.get(), new Item.Properties()));

    // SPAWN EGG
    //public static final RegistryObject<Item> WINDSTONE_SPAWNEGG = ITEMS.register("windstone_spawn_egg", () -> new SpawnEggItem(SkyislandEntityTypes.WINDSTONE.get(), 0xF6B201, 0xA80E0E, new Item.Properties()));
    public static final RegistryObject<Item> CHAOS_SPAWNER_SPAWNEGG = register("spawn_egg_chaos_spawner", Services.ITEM.makeSpawnEgg(DNLEntityTypes.CHAOS_SPAWNER::get, 0xF6B201, 0xA80E0E, new Item.Properties()));
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

    public static void init() {}
}
