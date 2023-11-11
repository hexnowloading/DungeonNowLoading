package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.block.*;
import dev.hexnowloading.dungeonnowloading.registration.BlockRegistryObject;
import dev.hexnowloading.dungeonnowloading.registration.RegistrationProvider;
import dev.hexnowloading.dungeonnowloading.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

public class DNLBlocks {
    public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, DungeonNowLoading.MOD_ID);

    // DESIGN BLOCKS
    public static final BlockRegistryObject<Block> SKYLIGHT_STONE = registerBlock("skylight_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final BlockRegistryObject<Block> SKYLIGHT_GRASS_BLOCK = registerBlock("skylight_grass_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));

    // MECHANCIAL BLOCKS
    public static final BlockRegistryObject<Block> CHAOS_SPAWNER_EDGE = registerBlock("chaos_spawner_edge", () -> new ChaosSpawnerEdgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.METAL).noOcclusion()));
    public static final BlockRegistryObject<Block> CHAOS_SPAWNER_DIAMOND_EDGE = registerBlock("chaos_spawner_diamond_edge", () -> new ChaosSpawnerEdgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.METAL).noOcclusion()));
    public static final BlockRegistryObject<Block> CHAOS_SPAWNER_DIAMOND_VERTEX = registerBlock("chaos_spawner_diamond_vertex", () -> new ChaosSpawnerVertexBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.METAL).noOcclusion()));
    public static final BlockRegistryObject<Block> CHAOS_SPAWNER_BARRIER_CENTER = registerBlock("chaos_spawner_barrier_center", () -> new ChaosSpawnerBarrierCenterBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.AMETHYST).noOcclusion()));
    public static final BlockRegistryObject<Block> CHAOS_SPAWNER_BARRIER_EDGE = registerBlock("chaos_spawner_barrier_edge", () -> new ChaosSpawnerBarrierEdgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.AMETHYST).noOcclusion()));
    public static final BlockRegistryObject<Block> CHAOS_SPAWNER_BARRIER_VERTEX = registerBlock("chaos_spawner_barrier_vertex", () -> new ChaosSpawnerBarrierVertexBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(5.0F).sound(SoundType.METAL).noOcclusion()));
    //public static final BlockRegistryObject<Block> WIND_ALTER = registerBlock("wind_alter", () -> new WindAlterBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS).strength(4.0f).requiresCorrectToolForDrops()));

    private static BlockRegistryObject<Block> registerBlock(String name, Supplier<Block> supplier) {
        RegistryObject<Block> object = BLOCKS.register(name, supplier);
        return BlockRegistryObject.wrap(object);
    }

    public static void init() {}

}
