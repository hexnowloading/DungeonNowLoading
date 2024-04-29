package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.block.*;
import dev.hexnowloading.dungeonnowloading.registration.BlockRegistryObject;
import dev.hexnowloading.dungeonnowloading.registration.RegistrationProvider;
import dev.hexnowloading.dungeonnowloading.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
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

    // Trophies
    public static BlockRegistryObject<Block> LABYRINTH_TROPHY;// = registerBlock("labyrinth_trophy", () -> new Block(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.CUSTOM_HEAD).strength(1.0f).pushReaction(PushReaction.DESTROY)));

    public static boolean blocksRegistered = false;

    private static BlockRegistryObject<Block> registerBlock(String name, Supplier<Block> supplier) {
        RegistryObject<Block> object = BLOCKS.register(name, supplier);
        return BlockRegistryObject.wrap(object);
    }

    public static void init() {
        // MECHANCIAL BLOCKS
        BOOK_PILE = registerBlock("book_pile", () -> new BookPileBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion().sound(SoundType.WOOL)));
        EXPLOSIVE_BARREL = registerBlock("explosive_barrel", () -> new ExplosiveBarrelBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion().sound(SoundType.GRASS)));
        COBBLESTONE_PEBBLES = registerBlock("cobblestone_pebbles", () -> new PebbleBlock(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).noOcclusion().sound(SoundType.STONE)));
        MOSSY_COBBLESTONE_PEBBLES = registerBlock("mossy_cobblestone_pebbles", () -> new PebbleBlock(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).noOcclusion().sound(SoundType.STONE)));
        IRON_INGOT_PILE = registerBlock("iron_ingot_pile", () -> new PileBlock(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).noOcclusion().sound(SoundType.METAL)));
        GOLD_INGOT_PILE = registerBlock("gold_ingot_pile", () -> new PileBlock(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).noOcclusion().sound(SoundType.METAL)));
        WOODEN_WALL_RACK = registerBlock("wooden_wall_rack", () -> new WallRackBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava()));
        WOODEN_WALL_PLATFORM = registerBlock("wooden_wall_platform", () -> new WallPlatformBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava()));
        SPIKES = registerBlock("spikes", () -> new SpikesBlock(BlockBehaviour.Properties.of().strength(3.0F, 6.0F).noOcclusion().sound(SoundType.METAL).pushReaction(PushReaction.DESTROY)));
        DUNGEON_WALL_TORCH = registerBlock("dungeon_wall_torch", () -> new DungeonWallTorch(BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel(DungeonWallTorch.LIGHT_EMISSION).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY)));

        CHAOS_SPAWNER_EDGE = registerBlock("chaos_spawner_edge", () -> new ChaosSpawnerEdgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.METAL).pushReaction(PushReaction.BLOCK).noOcclusion()));
        CHAOS_SPAWNER_DIAMOND_EDGE = registerBlock("chaos_spawner_diamond_edge", () -> new ChaosSpawnerEdgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.METAL).pushReaction(PushReaction.BLOCK).noOcclusion()));
        CHAOS_SPAWNER_DIAMOND_VERTEX = registerBlock("chaos_spawner_diamond_vertex", () -> new ChaosSpawnerVertexBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.METAL).pushReaction(PushReaction.BLOCK).noOcclusion()));
        CHAOS_SPAWNER_BROKEN_EDGE = registerBlock("chaos_spawner_broken_edge", () -> new ChaosSpawnerEdgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.METAL).noOcclusion()));
        CHAOS_SPAWNER_BROKEN_DIAMOND_VERTEX = registerBlock("chaos_spawner_broken_diamond_vertex", () -> new ChaosSpawnerVertexBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.METAL).noOcclusion()));
        CHAOS_SPAWNER_BROKEN_DIAMOND_EDGE = registerBlock("chaos_spawner_broken_diamond_edge", () -> new ChaosSpawnerEdgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.METAL).noOcclusion()));
        CHAOS_SPAWNER_BARRIER_CENTER = registerBlock("chaos_spawner_barrier_center", () -> new ChaosSpawnerBarrierCenterBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.AMETHYST).lightLevel((lightLevel) -> {return 15;}).noOcclusion()));
        CHAOS_SPAWNER_BARRIER_EDGE = registerBlock("chaos_spawner_barrier_edge", () -> new ChaosSpawnerBarrierEdgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.AMETHYST).lightLevel((lightLevel) -> {return 15;}).noOcclusion()));
        CHAOS_SPAWNER_BARRIER_VERTEX = registerBlock("chaos_spawner_barrier_vertex", () -> new ChaosSpawnerBarrierVertexBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).sound(SoundType.METAL).lightLevel((lightLevel) -> {return 15;}).noOcclusion()));
        //public static final BlockRegistryObject<Block> WIND_ALTER = registerBlock("wind_alter", () -> new WindAlterBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS).strength(4.0f).requiresCorrectToolForDrops()));

        // Trophies
        LABYRINTH_TROPHY = registerBlock("labyrinth_trophy", () -> new Block(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.CUSTOM_HEAD).strength(1.0f).pushReaction(PushReaction.DESTROY)));

        blocksRegistered = true;
    }

}
