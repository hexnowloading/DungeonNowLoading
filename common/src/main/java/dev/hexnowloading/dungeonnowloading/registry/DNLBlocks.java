package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.block.*;
import dev.hexnowloading.dungeonnowloading.registration.BlockRegistryObject;
import dev.hexnowloading.dungeonnowloading.registration.RegistrationProvider;
import dev.hexnowloading.dungeonnowloading.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

public class DNLBlocks {
    public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, DungeonNowLoading.MOD_ID);

    // DESIGN BLOCKS
    //public static final BlockRegistryObject<Block> SKYLIGHT_STONE = registerBlock("skylight_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    //public static final BlockRegistryObject<Block> SKYLIGHT_GRASS_BLOCK = registerBlock("skylight_grass_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));
    //public static final BlockRegistryObject<Block> RUINED_STONE_BRICKS = registerBlock("ruined_stone_bricks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static BlockRegistryObject<Block> COILING_STONE_PILLAR;
    public static BlockRegistryObject<Block> COILING_STONE_PILLAR_CAPITAL;
    public static BlockRegistryObject<Block> COILING_STONE_PILLAR_STAIRS;
    public static BlockRegistryObject<Block> COILING_STONE_PILLAR_SLAB;
    public static BlockRegistryObject<Block> COILING_STONE_PILLAR_WALL;
    public static BlockRegistryObject<Block> CHISELED_COILING_STONE_PILLAR;
    public static BlockRegistryObject<Block> STONE_TILES;
    public static BlockRegistryObject<Block> CRACKED_STONE_TILES;
    public static BlockRegistryObject<Block> STONE_TILE_STAIRS;
    public static BlockRegistryObject<Block> STONE_TILE_SLAB;
    public static BlockRegistryObject<Block> STONE_TILE_WALL;
    public static BlockRegistryObject<Block> SIGNALING_STONE_EMBLEM;
    public static BlockRegistryObject<Block> DUELING_STONE_EMBLEM;
    public static BlockRegistryObject<Block> PUZZLING_STONE_EMBLEM;

    public static BlockRegistryObject<Block> MOSS;

    // MECHANCIAL BLOCKS
    public static BlockRegistryObject<Block> BOOK_PILE;// = registerBlock("book_pile", () -> new BookPileBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion().sound(SoundType.WOOL)));
    public static BlockRegistryObject<Block> EXPLOSIVE_BARREL;// = registerBlock("explosive_barrel", () -> new ExplosiveBarrelBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion().sound(SoundType.GRASS)));
    public static BlockRegistryObject<Block> COBBLESTONE_PEBBLES;// = registerBlock("cobblestone_pebbles", () -> new PebbleBlock(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).noOcclusion().sound(SoundType.STONE)));
    public static BlockRegistryObject<Block> MOSSY_COBBLESTONE_PEBBLES;// = registerBlock("mossy_cobblestone_pebbles", () -> new PebbleBlock(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).noOcclusion().sound(SoundType.STONE)));
    public static BlockRegistryObject<Block> IRON_INGOT_PILE;// = registerBlock("iron_ingot_pile", () -> new PileBlock(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).noOcclusion().sound(SoundType.METAL)));
    public static BlockRegistryObject<Block> GOLD_INGOT_PILE;// = registerBlock("gold_ingot_pile", () -> new PileBlock(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).noOcclusion().sound(SoundType.METAL)));
    public static BlockRegistryObject<Block> WOODEN_WALL_RACK;// = registerBlock("wooden_wall_rack", () -> new WallRackBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava()));
    public static BlockRegistryObject<Block> WOODEN_WALL_PLATFORM;// = registerBlock("wooden_wall_platform", () -> new WallPlatformBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava()));
    public static BlockRegistryObject<Block> SPIKES;// = registerBlock("spikes", () -> new SpikesBlock(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).noOcclusion().sound(SoundType.METAL).pushReaction(PushReaction.DESTROY)));
    public static BlockRegistryObject<Block> DUNGEON_WALL_TORCH;// = registerBlock("dungeon_wall_torch", () -> new DungeonWallTorch(BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel(DungeonWallTorch.LIGHT_EMISSION).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY)));

    public static BlockRegistryObject<Block> CHAOS_SPAWNER_EDGE;// = registerBlock("chaos_spawner_edge", () -> new ChaosSpawnerEdgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.METAL).pushReaction(PushReaction.BLOCK).noOcclusion()));
    public static BlockRegistryObject<Block> CHAOS_SPAWNER_DIAMOND_EDGE;// = registerBlock("chaos_spawner_diamond_edge", () -> new ChaosSpawnerEdgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.METAL).pushReaction(PushReaction.BLOCK).noOcclusion()));
    public static BlockRegistryObject<Block> CHAOS_SPAWNER_DIAMOND_VERTEX;// = registerBlock("chaos_spawner_diamond_vertex", () -> new ChaosSpawnerVertexBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.METAL).pushReaction(PushReaction.BLOCK).noOcclusion()));
    public static BlockRegistryObject<Block> CHAOS_SPAWNER_BROKEN_EDGE;// = registerBlock("chaos_spawner_broken_edge", () -> new ChaosSpawnerEdgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.METAL).noOcclusion()));
    public static BlockRegistryObject<Block> CHAOS_SPAWNER_BROKEN_DIAMOND_VERTEX;// = registerBlock("chaos_spawner_broken_diamond_vertex", () -> new ChaosSpawnerVertexBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.METAL).noOcclusion()));
    public static BlockRegistryObject<Block> CHAOS_SPAWNER_BROKEN_DIAMOND_EDGE;// = registerBlock("chaos_spawner_broken_diamond_edge", () -> new ChaosSpawnerEdgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.METAL).noOcclusion()));
    public static BlockRegistryObject<Block> CHAOS_SPAWNER_BARRIER_CENTER;// = registerBlock("chaos_spawner_barrier_center", () -> new ChaosSpawnerBarrierCenterBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.AMETHYST).lightLevel((lightLevel) -> {return 15;}).noOcclusion()));
    public static BlockRegistryObject<Block> CHAOS_SPAWNER_BARRIER_EDGE;// = registerBlock("chaos_spawner_barrier_edge", () -> new ChaosSpawnerBarrierEdgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.AMETHYST).lightLevel((lightLevel) -> {return 15;}).noOcclusion()));
    public static BlockRegistryObject<Block> CHAOS_SPAWNER_BARRIER_VERTEX;// = registerBlock("chaos_spawner_barrier_vertex", () -> new ChaosSpawnerBarrierVertexBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.METAL).lightLevel((lightLevel) -> {return 15;}).noOcclusion()));
    //public static final BlockRegistryObject<Block> WIND_ALTER = registerBlock("wind_alter", () -> new WindAlterBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS).strength(4.0f).requiresCorrectToolForDrops()));

    public static BlockRegistryObject<Block> FAIRKEEPER_CHEST;
    public static BlockRegistryObject<Block> FAIRKEEEPER_SPAWNER;
    public static BlockRegistryObject<Block> REDSTONE_LANE_I;

    // Trophies
    public static BlockRegistryObject<Block> LABYRINTH_TROPHY;// = registerBlock("labyrinth_trophy", () -> new Block(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.CUSTOM_HEAD).strength(1.0f).pushReaction(PushReaction.DESTROY)));

    public static boolean blocksRegistered = false;

    public static void init() {
        // DESIGN BLOCKS
        MOSS = registerBlock("moss", () -> new MossMultifaceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.1F).noOcclusion().sound(SoundType.MOSS_CARPET).pushReaction(PushReaction.DESTROY).noLootTable()));
        COILING_STONE_PILLAR = registerBlock("coiling_stone_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F,6.0F)));
        COILING_STONE_PILLAR_CAPITAL = registerBlock("coiling_stone_pillar_capital", () -> new PillarCapBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F,6.0F)));
        COILING_STONE_PILLAR_STAIRS = registerBlock("coiling_stone_pillar_stairs", () -> new StairBlock(COILING_STONE_PILLAR.defaultBlockState(), BlockBehaviour.Properties.copy(COILING_STONE_PILLAR.get())));
        COILING_STONE_PILLAR_SLAB = registerBlock("coiling_stone_pillar_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0F,6.0F)));
        COILING_STONE_PILLAR_WALL = registerBlock("coiling_stone_pillar_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(COILING_STONE_PILLAR.get()).forceSolidOn()));
        CHISELED_COILING_STONE_PILLAR = registerBlock("chiseled_coiling_stone_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F,6.0F)));
        STONE_TILES = registerBlock("stone_tiles", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
        CRACKED_STONE_TILES = registerBlock("cracked_stone_tiles", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
        STONE_TILE_STAIRS = registerBlock("stone_tile_stairs", () -> new StairBlock(STONE_TILES.defaultBlockState(), BlockBehaviour.Properties.copy(STONE_TILES.get())));
        STONE_TILE_SLAB = registerBlock("stone_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0F,6.0F)));
        STONE_TILE_WALL = registerBlock("stone_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(STONE_TILES.get()).forceSolidOn()));
        SIGNALING_STONE_EMBLEM = registerBlock("signaling_stone_emblem", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
        DUELING_STONE_EMBLEM = registerBlock("dueling_stone_emblem", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
        PUZZLING_STONE_EMBLEM = registerBlock("puzzling_stone_emblem", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));

        // MECHANCIAL BLOCKS
        BOOK_PILE = registerBlock("book_pile", () -> new BookPileBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion().sound(SoundType.WOOL)));
        EXPLOSIVE_BARREL = registerBlock("explosive_barrel", () -> new ExplosiveBarrelBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion().sound(SoundType.GRASS)));
        COBBLESTONE_PEBBLES = registerBlock("cobblestone_pebbles", () -> new PebbleBlock(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).noOcclusion().sound(SoundType.STONE)));
        MOSSY_COBBLESTONE_PEBBLES = registerBlock("mossy_cobblestone_pebbles", () -> new PebbleBlock(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).noOcclusion().sound(SoundType.STONE)));
        IRON_INGOT_PILE = registerBlock("iron_ingot_pile", () -> new PileBlock(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).noOcclusion().sound(SoundType.METAL)));
        GOLD_INGOT_PILE = registerBlock("gold_ingot_pile", () -> new PileBlock(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).noOcclusion().sound(SoundType.METAL)));
        WOODEN_WALL_RACK = registerBlock("wooden_wall_rack", () -> new WallRackBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava()));
        WOODEN_WALL_PLATFORM = registerBlock("wooden_wall_platform", () -> new WallPlatformBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava()));
        SPIKES = registerBlock("spikes", () -> new SpikesBlock(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).noOcclusion().sound(SoundType.METAL).pushReaction(PushReaction.DESTROY).noLootTable()));
        DUNGEON_WALL_TORCH = registerBlock("dungeon_wall_torch", () -> new DungeonWallTorch(BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel(DungeonWallTorch.LIGHT_EMISSION).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY)));

        CHAOS_SPAWNER_EDGE = registerBlock("chaos_spawner_edge", () -> new ChaosSpawnerEdgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.METAL).pushReaction(PushReaction.BLOCK).noOcclusion().noLootTable()));
        CHAOS_SPAWNER_DIAMOND_EDGE = registerBlock("chaos_spawner_diamond_edge", () -> new ChaosSpawnerEdgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.METAL).pushReaction(PushReaction.BLOCK).noOcclusion().noLootTable()));
        CHAOS_SPAWNER_DIAMOND_VERTEX = registerBlock("chaos_spawner_diamond_vertex", () -> new ChaosSpawnerVertexBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.METAL).pushReaction(PushReaction.BLOCK).noOcclusion().noLootTable()));
        CHAOS_SPAWNER_BROKEN_EDGE = registerBlock("chaos_spawner_broken_edge", () -> new ChaosSpawnerEdgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.METAL).noOcclusion().noLootTable()));
        CHAOS_SPAWNER_BROKEN_DIAMOND_VERTEX = registerBlock("chaos_spawner_broken_diamond_vertex", () -> new ChaosSpawnerVertexBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.METAL).noOcclusion().noLootTable()));
        CHAOS_SPAWNER_BROKEN_DIAMOND_EDGE = registerBlock("chaos_spawner_broken_diamond_edge", () -> new ChaosSpawnerEdgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.METAL).noOcclusion().noLootTable()));
        CHAOS_SPAWNER_BARRIER_CENTER = registerBlock("chaos_spawner_barrier_center", () -> new ChaosSpawnerBarrierCenterBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.AMETHYST).lightLevel((lightLevel) -> {return 15;}).noOcclusion().noLootTable()));
        CHAOS_SPAWNER_BARRIER_EDGE = registerBlock("chaos_spawner_barrier_edge", () -> new ChaosSpawnerBarrierEdgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.AMETHYST).lightLevel((lightLevel) -> {return 15;}).noOcclusion().noLootTable()));
        CHAOS_SPAWNER_BARRIER_VERTEX = registerBlock("chaos_spawner_barrier_vertex", () -> new ChaosSpawnerBarrierVertexBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.METAL).lightLevel((lightLevel) -> {return 15;}).noOcclusion().noLootTable()));
        //public static final BlockRegistryObject<Block> WIND_ALTER = registerBlock("wind_alter", () -> new WindAlterBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS).strength(4.0f).requiresCorrectToolForDrops()));

        FAIRKEEPER_CHEST = registerBlock("fairkeeper_chest", () -> new FairkeeperChestBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.5F).noOcclusion().sound(SoundType.WOOD).lightLevel((lightLevel) -> 7).noLootTable()));
        FAIRKEEEPER_SPAWNER = registerBlock("fairkeeper_spawner", () -> new FairkeeperSpawnerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.5F).noOcclusion().sound(SoundType.WOOD).lightLevel((lightLevel) -> 7).noLootTable()));
        REDSTONE_LANE_I = registerBlock("redstone_lane_i", () -> new RedstoneLaneIBlock(BlockBehaviour.Properties.of().mapColor(MapColor.FIRE).strength(-1.0F, 3600000.0F).sound(SoundType.METAL).noLootTable()));

        // Trophies
        LABYRINTH_TROPHY = registerBlock("labyrinth_trophy", () -> new Block(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.CUSTOM_HEAD).strength(1.0f).noOcclusion().pushReaction(PushReaction.DESTROY).noLootTable()));

        blocksRegistered = true;
    }

    private static BlockRegistryObject<Block> registerBlock(String name, Supplier<Block> supplier) {
        RegistryObject<Block> object = BLOCKS.register(name, supplier);
        return BlockRegistryObject.wrap(object);
    }

    /*public static <T extends Block> BlockRegistryObject<T> registerBEWLR(String name, Supplier<T> block) {
        RegistryObject<T> ret = BLOCKS.register(name, block);
        DNLItems.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties()) {
            public void initializeClient
        });

    }*/
    /*private static RegistryObject<Block> registerBlockAndItem(String name, Supplier<Block> block, ItemType itemType) {
        RegistryObject<Block> blockRegistryObject = BLOCKS.register(name, block);
        DNLItems.ITEMS.register(name, get)
    }

    private static Supplier<? extends BlockItem> getBlockSupplier(ItemType itemType, RegistryObject<Block> blockRegistryObject) {
        return switch (itemType) {
            case DEFAULT -> () -> new BlockItem(blockRegistryObject.get(), new Item.Properties());
            case BUILTIN -> () -> new ;
        };
    }

    private enum ItemType {
        DEFAULT,
        BUILTIN;


    }*/

}
