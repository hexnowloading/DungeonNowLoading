package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.block.*;
import dev.hexnowloading.dungeonnowloading.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

public class DNLBlocks {

    // DESIGN BLOCKS
    //public static final Supplier<Block> SKYLIGHT_STONE = registerBlock("skylight_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    //public static final Supplier<Block> SKYLIGHT_GRASS_BLOCK = registerBlock("skylight_grass_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));
    //public static final Supplier<Block> RUINED_STONE_BRICKS = registerBlock("ruined_stone_bricks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
    public static Supplier<Block> COILING_STONE_PILLAR;
    public static Supplier<Block> COILING_STONE_PILLAR_CAPITAL;
    public static Supplier<Block> COILING_STONE_PILLAR_STAIRS;
    public static Supplier<Block> COILING_STONE_PILLAR_SLAB;
    public static Supplier<Block> COILING_STONE_PILLAR_WALL;
    public static Supplier<Block> CHISELED_COILING_STONE_PILLAR;
    public static Supplier<Block> STONE_TILES;
    public static Supplier<Block> CRACKED_STONE_TILES;
    public static Supplier<Block> STONE_TILE_STAIRS;
    public static Supplier<Block> STONE_TILE_SLAB;
    public static Supplier<Block> STONE_TILE_WALL;
    public static Supplier<Block> SIGNALING_STONE_EMBLEM;
    public static Supplier<Block> DUELING_STONE_EMBLEM;
    public static Supplier<Block> PUZZLING_STONE_EMBLEM;
    public static Supplier<Block> POLISHED_STONE;
    public static Supplier<Block> BORDERED_STONE;

    public static Supplier<Block> MOSS;

    // MECHANCIAL BLOCKS
    public static Supplier<Block> BOOK_PILE;// = registerBlock("book_pile", () -> new BookPileBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion().sound(SoundType.WOOL)));
    public static Supplier<Block> EXPLOSIVE_BARREL;// = registerBlock("explosive_barrel", () -> new ExplosiveBarrelBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion().sound(SoundType.GRASS)));
    public static Supplier<Block> COBBLESTONE_PEBBLES;// = registerBlock("cobblestone_pebbles", () -> new PebbleBlock(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).noOcclusion().sound(SoundType.STONE)));
    public static Supplier<Block> MOSSY_COBBLESTONE_PEBBLES;// = registerBlock("mossy_cobblestone_pebbles", () -> new PebbleBlock(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).noOcclusion().sound(SoundType.STONE)));
    public static Supplier<Block> IRON_INGOT_PILE;// = registerBlock("iron_ingot_pile", () -> new PileBlock(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).noOcclusion().sound(SoundType.METAL)));
    public static Supplier<Block> GOLD_INGOT_PILE;// = registerBlock("gold_ingot_pile", () -> new PileBlock(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).noOcclusion().sound(SoundType.METAL)));
    public static Supplier<Block> WOODEN_WALL_RACK;// = registerBlock("wooden_wall_rack", () -> new WallRackBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava()));
    public static Supplier<Block> WOODEN_WALL_PLATFORM;// = registerBlock("wooden_wall_platform", () -> new WallPlatformBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava()));
    public static Supplier<Block> SPIKES;// = registerBlock("spikes", () -> new SpikesBlock(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).noOcclusion().sound(SoundType.METAL).pushReaction(PushReaction.DESTROY)));
    public static Supplier<Block> DUNGEON_WALL_TORCH;// = registerBlock("dungeon_wall_torch", () -> new DungeonWallTorch(BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel(DungeonWallTorch.LIGHT_EMISSION).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY)));

    public static Supplier<Block> CHAOS_SPAWNER_EDGE;// = registerBlock("chaos_spawner_edge", () -> new ChaosSpawnerEdgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.METAL).pushReaction(PushReaction.BLOCK).noOcclusion()));
    public static Supplier<Block> CHAOS_SPAWNER_DIAMOND_EDGE;// = registerBlock("chaos_spawner_diamond_edge", () -> new ChaosSpawnerEdgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.METAL).pushReaction(PushReaction.BLOCK).noOcclusion()));
    public static Supplier<Block> CHAOS_SPAWNER_DIAMOND_VERTEX;// = registerBlock("chaos_spawner_diamond_vertex", () -> new ChaosSpawnerVertexBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.METAL).pushReaction(PushReaction.BLOCK).noOcclusion()));
    public static Supplier<Block> CHAOS_SPAWNER_BROKEN_EDGE;// = registerBlock("chaos_spawner_broken_edge", () -> new ChaosSpawnerEdgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.METAL).noOcclusion()));
    public static Supplier<Block> CHAOS_SPAWNER_BROKEN_DIAMOND_VERTEX;// = registerBlock("chaos_spawner_broken_diamond_vertex", () -> new ChaosSpawnerVertexBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.METAL).noOcclusion()));
    public static Supplier<Block> CHAOS_SPAWNER_BROKEN_DIAMOND_EDGE;// = registerBlock("chaos_spawner_broken_diamond_edge", () -> new ChaosSpawnerEdgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.METAL).noOcclusion()));
    public static Supplier<Block> CHAOS_SPAWNER_BARRIER_CENTER;// = registerBlock("chaos_spawner_barrier_center", () -> new ChaosSpawnerBarrierCenterBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.AMETHYST).lightLevel((lightLevel) -> {return 15;}).noOcclusion()));
    public static Supplier<Block> CHAOS_SPAWNER_BARRIER_EDGE;// = registerBlock("chaos_spawner_barrier_edge", () -> new ChaosSpawnerBarrierEdgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.AMETHYST).lightLevel((lightLevel) -> {return 15;}).noOcclusion()));
    public static Supplier<Block> CHAOS_SPAWNER_BARRIER_VERTEX;// = registerBlock("chaos_spawner_barrier_vertex", () -> new ChaosSpawnerBarrierVertexBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.METAL).lightLevel((lightLevel) -> {return 15;}).noOcclusion()));
    //public static final Supplier<Block> WIND_ALTER = registerBlock("wind_alter", () -> new WindAlterBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS).strength(4.0f).requiresCorrectToolForDrops()));

    public static Supplier<Block> FAIRKEEPER_CHEST;
    public static Supplier<Block> WISE_FAIRKEEPER_CHEST;
    public static Supplier<Block> FIERCE_FAIRKEEPER_CHEST;
    public static Supplier<Block> FAIRKEEEPER_SPAWNER;
    public static Supplier<Block> REDSTONE_LANE_I;
    public static Supplier<Block> REDSTONE_LANE_L;
    public static Supplier<Block> REDSTONE_LANE_T;
    public static Supplier<Block> ROTATOR_PRESSURE_PLATE;
    public static Supplier<Block> STONE_NOTCH;
    public static Supplier<Block> COAL_STONE_NOTCH;
    public static Supplier<Block> COPPER_STONE_NOTCH;
    public static Supplier<Block> IRON_STONE_NOTCH;
    public static Supplier<Block> GOLD_STONE_NOTCH;
    public static Supplier<Block> REDSTONE_STONE_NOTCH;
    public static Supplier<Block> AMETHYST_STONE_NOTCH;
    public static Supplier<Block> LAPIS_STONE_NOTCH;
    public static Supplier<Block> EMERALD_STONE_NOTCH;
    public static Supplier<Block> QUARTZ_STONE_NOTCH;
    public static Supplier<Block> GLOWSTONE_STONE_NOTCH;
    public static Supplier<Block> PRISMARINE_STONE_NOTCH;
    public static Supplier<Block> CHORUS_STONE_NOTCH;
    public static Supplier<Block> ECHO_STONE_NOTCH;
    public static Supplier<Block> DIAMOND_STONE_NOTCH;
    public static Supplier<Block> NETHERITE_STONE_NOTCH;
    public static Supplier<Block> SIGNAL_GATE;
    public static Supplier<Block> SCUTTLE_STATUE;


    // Trophies
    public static Supplier<Block> LABYRINTH_TROPHY;// = registerBlock("labyrinth_trophy", () -> new Block(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.CUSTOM_HEAD).strength(1.0f).pushReaction(PushReaction.DESTROY)));

    public static boolean blocksRegistered = false;

    public static void init() {
        // DESIGN BLOCKS
        MOSS = registerBlock("moss", () -> new MossMultifaceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.1F).noOcclusion().sound(SoundType.MOSS_CARPET).pushReaction(PushReaction.DESTROY).noLootTable()));
        COILING_STONE_PILLAR = registerBlock("coiling_stone_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F,6.0F)));
        COILING_STONE_PILLAR_CAPITAL = registerBlock("coiling_stone_pillar_capital", () -> new PillarCapBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F,6.0F)));
        COILING_STONE_PILLAR_STAIRS = registerBlock("coiling_stone_pillar_stairs", () -> new StairBlock(COILING_STONE_PILLAR.get().defaultBlockState(), BlockBehaviour.Properties.copy(COILING_STONE_PILLAR.get())));
        COILING_STONE_PILLAR_SLAB = registerBlock("coiling_stone_pillar_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0F,6.0F)));
        COILING_STONE_PILLAR_WALL = registerBlock("coiling_stone_pillar_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(COILING_STONE_PILLAR.get()).forceSolidOn()));
        CHISELED_COILING_STONE_PILLAR = registerBlock("chiseled_coiling_stone_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F,6.0F)));
        STONE_TILES = registerBlock("stone_tiles", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
        CRACKED_STONE_TILES = registerBlock("cracked_stone_tiles", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
        STONE_TILE_STAIRS = registerBlock("stone_tile_stairs", () -> new StairBlock(STONE_TILES.get().defaultBlockState(), BlockBehaviour.Properties.copy(STONE_TILES.get())));
        STONE_TILE_SLAB = registerBlock("stone_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0F,6.0F)));
        STONE_TILE_WALL = registerBlock("stone_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(STONE_TILES.get()).forceSolidOn()));
        SIGNALING_STONE_EMBLEM = registerBlock("signaling_stone_emblem", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
        DUELING_STONE_EMBLEM = registerBlock("dueling_stone_emblem", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
        PUZZLING_STONE_EMBLEM = registerBlock("puzzling_stone_emblem", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
        POLISHED_STONE = registerBlock("polished_stone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
        BORDERED_STONE = registerBlock("bordered_stone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));

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
        //public static final Supplier<Block> WIND_ALTER = registerBlock("wind_alter", () -> new WindAlterBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS).strength(4.0f).requiresCorrectToolForDrops()));

        FAIRKEEPER_CHEST = registerBlock("fairkeeper_chest", () -> new FairkeeperChestBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.5F, 2.5F).noOcclusion().sound(SoundType.WOOD).lightLevel((lightLevel) -> 7)));
        WISE_FAIRKEEPER_CHEST = registerBlock("wise_fairkeeper_chest", () -> new DisabledFairkeeperChestBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.5F, 2.5F).noOcclusion().sound(SoundType.WOOD).lightLevel((lightLevel) -> 7)));
        FIERCE_FAIRKEEPER_CHEST = registerBlock("fierce_fairkeeper_chest", () -> new DisabledFairkeeperChestBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.5F, 2.5F).noOcclusion().sound(SoundType.WOOD).lightLevel((lightLevel) -> 7)));
        FAIRKEEEPER_SPAWNER = registerBlock("fairkeeper_spawner", () -> new FairkeeperSpawnerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.WOOD).lightLevel((lightLevel) -> 7).noLootTable()));
        REDSTONE_LANE_I = registerBlock("redstone_lane_i", () -> new RedstoneLaneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.FIRE).strength(-1.0F, 3600000.0F).sound(SoundType.METAL).lightLevel(DNLBlocks::laneLight).noLootTable()));
        REDSTONE_LANE_L = registerBlock("redstone_lane_l", () -> new RedstoneLaneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.FIRE).strength(-1.0F, 3600000.0F).sound(SoundType.METAL).lightLevel(DNLBlocks::laneLight).noLootTable()));
        REDSTONE_LANE_T = registerBlock("redstone_lane_t", () -> new RedstoneLaneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.FIRE).strength(-1.0F, 3600000.0F).sound(SoundType.METAL).lightLevel(DNLBlocks::laneLight).noLootTable()));
        ROTATOR_PRESSURE_PLATE = registerBlock("rotator_pressure_plate", () -> new RotatorPressurePlate(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY).lightLevel(blockState -> blockState.getValue(PressurePlateBlock.POWERED) ? 9 : 0).noLootTable(), BlockSetType.STONE));
        STONE_NOTCH = registerBlock("stone_notch", () -> new StoneNotchBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F), StoneNotchBlock.StoneNotchMaterialSignalStrength.NONE));
        COAL_STONE_NOTCH = registerBlock("coal_stone_notch", () -> new StoneNotchBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F), StoneNotchBlock.StoneNotchMaterialSignalStrength.COAL));
        COPPER_STONE_NOTCH = registerBlock("copper_stone_notch", () -> new StoneNotchBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F), StoneNotchBlock.StoneNotchMaterialSignalStrength.COPPER));
        IRON_STONE_NOTCH = registerBlock("iron_stone_notch", () -> new StoneNotchBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F), StoneNotchBlock.StoneNotchMaterialSignalStrength.IRON));
        GOLD_STONE_NOTCH = registerBlock("gold_stone_notch", () -> new StoneNotchBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F), StoneNotchBlock.StoneNotchMaterialSignalStrength.GOLD));
        REDSTONE_STONE_NOTCH = registerBlock("redstone_stone_notch", () -> new StoneNotchBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F), StoneNotchBlock.StoneNotchMaterialSignalStrength.REDSTONE));
        AMETHYST_STONE_NOTCH = registerBlock("amethyst_stone_notch", () -> new StoneNotchBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F), StoneNotchBlock.StoneNotchMaterialSignalStrength.AMETHYST));
        LAPIS_STONE_NOTCH = registerBlock("lapis_stone_notch", () -> new StoneNotchBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F), StoneNotchBlock.StoneNotchMaterialSignalStrength.LAPIS));
        EMERALD_STONE_NOTCH = registerBlock("emerald_stone_notch", () -> new StoneNotchBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F), StoneNotchBlock.StoneNotchMaterialSignalStrength.EMERALD));
        QUARTZ_STONE_NOTCH = registerBlock("quartz_stone_notch", () -> new StoneNotchBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F), StoneNotchBlock.StoneNotchMaterialSignalStrength.QUARTZ));
        GLOWSTONE_STONE_NOTCH = registerBlock("glowstone_stone_notch", () -> new StoneNotchBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F), StoneNotchBlock.StoneNotchMaterialSignalStrength.GLOWSTONE));
        PRISMARINE_STONE_NOTCH = registerBlock("prismarine_stone_notch", () -> new StoneNotchBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F), StoneNotchBlock.StoneNotchMaterialSignalStrength.PRISMARINE));
        CHORUS_STONE_NOTCH = registerBlock("chorus_stone_notch", () -> new StoneNotchBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F), StoneNotchBlock.StoneNotchMaterialSignalStrength.CHORUS));
        ECHO_STONE_NOTCH = registerBlock("echo_stone_notch", () -> new StoneNotchBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F), StoneNotchBlock.StoneNotchMaterialSignalStrength.ECHO));
        DIAMOND_STONE_NOTCH = registerBlock("diamond_stone_notch", () -> new StoneNotchBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F), StoneNotchBlock.StoneNotchMaterialSignalStrength.DIAMOND));
        NETHERITE_STONE_NOTCH = registerBlock("netherite_stone_notch", () -> new StoneNotchBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 6.0F), StoneNotchBlock.StoneNotchMaterialSignalStrength.NETHERITE));
        SIGNAL_GATE = registerBlock("signal_gate", () -> new SignalGateBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).isRedstoneConductor(DNLBlocks::never).requiresCorrectToolForDrops().strength(1.5F, 6.0F)));
        SCUTTLE_STATUE = registerBlock("scuttle_statue", () -> new ScuttleStatueBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).noOcclusion().noLootTable().pushReaction(PushReaction.IGNORE).sound(SoundType.METAL)));

        // Trophies
        LABYRINTH_TROPHY = registerBlock("labyrinth_trophy", () -> new Block(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.CUSTOM_HEAD).strength(1.0f).noOcclusion().pushReaction(PushReaction.DESTROY).noLootTable()));

        blocksRegistered = true;
    }

    public static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> blockSupplier) {
        return Services.REGISTRY.register(BuiltInRegistries.BLOCK, name, blockSupplier);
    }

    private static int laneLight(BlockState blockState) {
        return switch (blockState.getValue(DNLProperties.REDSTONE_LANE_MODE)) {
            default -> 0;
            case POWERED -> 3;
            case OVERPOWERED -> 15;
        };
    }

    private static boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }

    /*public static <T extends Block> Supplier<T> registerBEWLR(String name, Supplier<T> block) {
        RegistryObject<T> ret = BLOCKS.register(name, block);
        DNLItems.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties()) {
            public void initializeClient
        });

    }*/
    /*private static RegistryObject<Block> registerBlockAndItem(String name, Supplier<Block> block, ItemType itemType) {
        RegistryObject<Block> Supplier = BLOCKS.register(name, block);
        DNLItems.ITEMS.register(name, get)
    }

    private static Supplier<? extends BlockItem> getBlockSupplier(ItemType itemType, RegistryObject<Block> Supplier) {
        return switch (itemType) {
            case DEFAULT -> () -> new BlockItem(Supplier.get(), new Item.Properties());
            case BUILTIN -> () -> new ;
        };
    }

    private enum ItemType {
        DEFAULT,
        BUILTIN;


    }*/

}
