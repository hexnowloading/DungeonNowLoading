package dev.hexnowloading.skyisland.registry;

import dev.hexnowloading.skyisland.Skyisland;
import dev.hexnowloading.skyisland.registration.BlockRegistryObject;
import dev.hexnowloading.skyisland.registration.RegistrationProvider;
import dev.hexnowloading.skyisland.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class SkyislandBlocks {
    public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, Skyisland.MOD_ID);

    // DESIGN BLOCKS
    public static final BlockRegistryObject<Block> SKYLIGHT_STONE = registerBlock("skylight_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final BlockRegistryObject<Block> SKYLIGHT_GRASS_BLOCK = registerBlock("skylight_grass_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));

    // MECHANCIAL BLOCKS
    //public static final BlockRegistryObject<Block> WIND_ALTER = registerBlock("wind_alter", () -> new WindAlterBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS).strength(4.0f).requiresCorrectToolForDrops()));

    private static BlockRegistryObject<Block> registerBlock(String name, Supplier<Block> supplier) {
        RegistryObject<Block> object = BLOCKS.register(name, supplier);
        return BlockRegistryObject.wrap(object);
    }

    public static void init() {}

}
