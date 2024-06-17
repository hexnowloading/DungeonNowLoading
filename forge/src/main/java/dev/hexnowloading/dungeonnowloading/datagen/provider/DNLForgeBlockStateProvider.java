package dev.hexnowloading.dungeonnowloading.datagen.provider;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.block.FairkeeperSpawnerBlock;
import dev.hexnowloading.dungeonnowloading.block.PillarCapBlock;
import dev.hexnowloading.dungeonnowloading.block.RedstoneLaneBlock;
import dev.hexnowloading.dungeonnowloading.block.SignalGateBlock;
import dev.hexnowloading.dungeonnowloading.block.property.RedstoneLaneMode;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlocks;
import dev.hexnowloading.dungeonnowloading.registry.DNLProperties;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DNLForgeBlockStateProvider extends BlockStateProvider {
    public DNLForgeBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), DungeonNowLoading.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(DNLBlocks.STONE_TILES.get());
        simpleBlockWithItem(DNLBlocks.CRACKED_STONE_TILES.get());
        simpleBlockWithItem(DNLBlocks.SIGNALING_STONE_EMBLEM.get());
        simpleBlockWithItem(DNLBlocks.DUELING_STONE_EMBLEM.get());
        simpleBlockWithItem(DNLBlocks.PUZZLING_STONE_EMBLEM.get());
        simpleBlockWithItem(DNLBlocks.POLISHED_STONE.get());
        simpleBlockWithItem(DNLBlocks.BORDERED_STONE.get());
        simpleBlockWithItem(DNLBlocks.STONE_NOTCH.get());
        simpleBlockWithItem(DNLBlocks.COAL_STONE_NOTCH.get());
        simpleBlockWithItem(DNLBlocks.COPPER_STONE_NOTCH.get());
        simpleBlockWithItem(DNLBlocks.IRON_STONE_NOTCH.get());
        simpleBlockWithItem(DNLBlocks.GOLD_STONE_NOTCH.get());
        simpleBlockWithItem(DNLBlocks.REDSTONE_STONE_NOTCH.get());
        simpleBlockWithItem(DNLBlocks.AMETHYST_STONE_NOTCH.get());
        simpleBlockWithItem(DNLBlocks.LAPIS_STONE_NOTCH.get());
        simpleBlockWithItem(DNLBlocks.EMERALD_STONE_NOTCH.get());
        simpleBlockWithItem(DNLBlocks.QUARTZ_STONE_NOTCH.get());
        simpleBlockWithItem(DNLBlocks.GLOWSTONE_STONE_NOTCH.get());
        simpleBlockWithItem(DNLBlocks.PRISMARINE_STONE_NOTCH.get());
        simpleBlockWithItem(DNLBlocks.CHORUS_STONE_NOTCH.get());
        simpleBlockWithItem(DNLBlocks.ECHO_STONE_NOTCH.get());
        simpleBlockWithItem(DNLBlocks.DIAMOND_STONE_NOTCH.get());
        simpleBlockWithItem(DNLBlocks.NETHERITE_STONE_NOTCH.get());

        stairsBlockWithItem((StairBlock) DNLBlocks.STONE_TILE_STAIRS.get(), DNLBlocks.STONE_TILES.get());
        slabBlockWithItems((SlabBlock) DNLBlocks.STONE_TILE_SLAB.get(), DNLBlocks.STONE_TILES.get());
        wallBlockWithItem((WallBlock) DNLBlocks.STONE_TILE_WALL.get(), DNLBlocks.STONE_TILES.get());
        rotatedPillarBlockWithItem((RotatedPillarBlock) DNLBlocks.COILING_STONE_PILLAR.get());
        rotatedPillarBlockWithItem((RotatedPillarBlock) DNLBlocks.CHISELED_COILING_STONE_PILLAR.get());
        rotatedPillarCapBlockWithItem((PillarCapBlock) DNLBlocks.COILING_STONE_PILLAR_CAPITAL.get());
        slabBlockWithItems((SlabBlock) DNLBlocks.COILING_STONE_PILLAR_SLAB.get());
        stairsBlockWithItem((StairBlock) DNLBlocks.COILING_STONE_PILLAR_STAIRS.get());
        wallBlockWithItem((WallBlock) DNLBlocks.COILING_STONE_PILLAR_WALL.get());
        redstoneLaneWithItem((RedstoneLaneBlock) DNLBlocks.REDSTONE_LANE_I.get());
        redstoneLaneWithItem((RedstoneLaneBlock) DNLBlocks.REDSTONE_LANE_L.get());
        redstoneLaneWithItem((RedstoneLaneBlock) DNLBlocks.REDSTONE_LANE_T.get());
        signalGateWithItem((SignalGateBlock) DNLBlocks.SIGNAL_GATE.get());

        //fairkeeperSpawnerWithItem((FairkeeperSpawnerBlock) DNLBlocks.FAIRKEEEPER_SPAWNER.get());
        //simpleRandomBlockWithItem(DNLBlocks.MOSS.get(), 5);
    }

    private void simpleBlockWithItem(Block block) {
        simpleBlock(block);
        simpleBlockItem(block, models().getExistingFile(modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block).getPath())));
    }

    private void simpleItem(Block block) {
        String name = ForgeRegistries.BLOCKS.getKey(block).getPath();
        itemModels()
                .withExistingParent(ModelProvider.ITEM_FOLDER + "/" + name, mcLoc(ModelProvider.ITEM_FOLDER + "/generated"))
                .texture("layer0", ModelProvider.BLOCK_FOLDER + "/" + name);
    }

    private void simpleRandomBlockWithItem(Block block, int numVariants) {
        String name = ForgeRegistries.BLOCKS.getKey(block).getPath();
        ConfiguredModel[] models = new ConfiguredModel[numVariants];
        for(int i = 0; i < numVariants; i++) {
            models[i] = new ConfiguredModel(models().getExistingFile(modLoc("block/" + name + "_" + i)));
        }
        getVariantBuilder(block).partialState().setModels(models);
        itemModels().getBuilder(name)
                .parent(models().getExistingFile(modLoc("block/" + name + "_0")));
    }

    private void stairsBlockWithItem(StairBlock block, Block parent) {
        stairsBlock(block, blockTexture(parent));
        simpleBlockItem(block, models().getExistingFile(blockTexture(block)));
    }

    private void stairsBlockWithItem(StairBlock block) {
        ResourceLocation side = blockTexture(block);
        ResourceLocation ends = extend(blockTexture(block), "_top");

        ModelFile stairs = models().stairs(name(block), side, ends, ends);
        ModelFile stairsInner = models().stairsInner(name(block) + "_inner", side, ends, ends);
        ModelFile stairsOuter = models().stairsOuter(name(block) + "_outer", side, ends, ends);
        stairsBlock(block, stairs, stairsInner, stairsOuter);
        simpleBlockItem(block, models().getExistingFile(blockTexture(block)));
    }

    private void slabBlockWithItems(SlabBlock block, Block parent) {
        ResourceLocation path = blockTexture(parent);
        slabBlock(block, path, blockTexture(parent));
        simpleBlockItem(block, models().getExistingFile(blockTexture(block)));
    }

    private void slabBlockWithItems(SlabBlock block) {
        ResourceLocation side = blockTexture(block);
        ResourceLocation bottom = extend(blockTexture(block), "_top");
        ResourceLocation top = extend(blockTexture(block), "_top");

        ModelFile slabBottom = models().slab(name(block), side, bottom, top);
        ModelFile slabTop = models().slabTop(name(block) + "_top", side, bottom, top);
        ModelFile slabDouble = models().cubeColumn(name(block) + "_double", side, top);

        slabBlock(block, slabBottom, slabTop, slabDouble);
        simpleBlockItem(block, models().getExistingFile(blockTexture(block)));
    }

    private void pressurePlateblockWithItems(PressurePlateBlock block) {
        ResourceLocation deactive = blockTexture(block);
        ResourceLocation active = extend(blockTexture(block), "_on");

        ModelFile off = models().pressurePlate(name(block), deactive).renderType("cutout");
        ModelFile on = models().pressurePlateDown(name(block) + "_on", active).renderType("cutout");

        pressurePlateBlock(block, off, on);
        simpleBlockItem(block, models().getExistingFile(blockTexture(block)));
    }

    private void rotatedPillarBlockWithItem(RotatedPillarBlock block) {
        logBlock(block);
        simpleBlockItem(block, models().getExistingFile(blockTexture(block)));
    }

    private void rotatedPillarCapBlockWithItem(PillarCapBlock block) {
        ResourceLocation side = blockTexture(block);
        ResourceLocation sideReversed = extend(blockTexture(block), "_reversed");
        ResourceLocation top = extend(blockTexture(block), "_top");
        ResourceLocation bottom = extend(blockTexture(block), "_bottom");

        ModelFile normal = models().cubeBottomTop(name(block), side, bottom, top);
        ModelFile reversed = models().cubeBottomTop(name(block) + "_reversed", sideReversed, bottom, top);

        getVariantBuilder(block)
                .partialState().with(PillarCapBlock.FACING, Direction.UP).modelForState().modelFile(normal).addModel()
                .partialState().with(PillarCapBlock.FACING, Direction.DOWN).modelForState().modelFile(reversed).rotationX(180).rotationY(180).addModel()
                .partialState().with(PillarCapBlock.FACING, Direction.NORTH).modelForState().modelFile(normal).rotationX(90).addModel()
                .partialState().with(PillarCapBlock.FACING, Direction.EAST).modelForState().modelFile(normal).rotationX(90).rotationY(90).addModel()
                .partialState().with(PillarCapBlock.FACING, Direction.SOUTH).modelForState().modelFile(reversed).rotationX(270).addModel()
                .partialState().with(PillarCapBlock.FACING, Direction.WEST).modelForState().modelFile(reversed).rotationX(90).rotationY(270).addModel();

        simpleBlockItem(block, models().getExistingFile(blockTexture(block)));
    }

    private void orientableWithBottomBlockWithItem(HorizontalDirectionalBlock block) {
        ResourceLocation side = extend(blockTexture(block), "_side");
        ResourceLocation front = extend(blockTexture(block), "_front");
        ResourceLocation bottom = extend(blockTexture(block), "_bottom");
        ResourceLocation top = extend(blockTexture(block), "_top");

        ModelFile normal = models().orientableWithBottom(name(block), side, front, bottom, top);

        getVariantBuilder(block)
                .partialState().with(HorizontalDirectionalBlock.FACING, Direction.NORTH).modelForState().modelFile(normal).addModel()
                .partialState().with(HorizontalDirectionalBlock.FACING, Direction.EAST).modelForState().modelFile(normal).rotationY(90).addModel()
                .partialState().with(HorizontalDirectionalBlock.FACING, Direction.SOUTH).modelForState().modelFile(normal).addModel()
                .partialState().with(HorizontalDirectionalBlock.FACING, Direction.WEST).modelForState().modelFile(normal).rotationX(90).addModel();

        simpleBlockItem(block, models().getExistingFile(blockTexture(block)));
    }

    private void signalGateWithItem(SignalGateBlock block) {
        ResourceLocation front = extend(blockTexture(block), "_front");
        ResourceLocation back_on = extend(blockTexture(block), "_back_on");
        ResourceLocation back_off = extend(blockTexture(block), "_back_off");
        ResourceLocation bottom = extend(blockTexture(block), "_bottom");
        ResourceLocation side_on = extend(blockTexture(block), "_side_on");
        ResourceLocation side_on_reverse = extend(blockTexture(block), "_side_on_reverse");
        ResourceLocation side_off = extend(blockTexture(block), "_side_off");
        ResourceLocation side_off_reverse = extend(blockTexture(block), "_side_off_reverse");
        ResourceLocation top_00 = extend(blockTexture(block), "_top_00");
        ResourceLocation top_01 = extend(blockTexture(block), "_top_01");
        ResourceLocation top_02 = extend(blockTexture(block), "_top_02");
        ResourceLocation top_03 = extend(blockTexture(block), "_top_03");
        ResourceLocation top_04 = extend(blockTexture(block), "_top_04");
        ResourceLocation top_05 = extend(blockTexture(block), "_top_05");
        ResourceLocation top_06 = extend(blockTexture(block), "_top_06");
        ResourceLocation top_07 = extend(blockTexture(block), "_top_07");
        ResourceLocation top_08 = extend(blockTexture(block), "_top_08");
        ResourceLocation top_09 = extend(blockTexture(block), "_top_09");
        ResourceLocation top_10 = extend(blockTexture(block), "_top_10");
        ResourceLocation top_11 = extend(blockTexture(block), "_top_11");
        ResourceLocation top_12 = extend(blockTexture(block), "_top_12");
        ResourceLocation top_13 = extend(blockTexture(block), "_top_13");
        ResourceLocation top_14 = extend(blockTexture(block), "_top_14");
        ResourceLocation top_15 = extend(blockTexture(block), "_top_15");

        ModelFile off_00 = models().cube(name(block), bottom, top_00, front, back_off, side_off_reverse, side_off).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile off_01 = models().cube(name(block) + "_off_01", bottom, top_01, front, back_off, side_off_reverse, side_off).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile off_02 = models().cube(name(block) + "_off_02", bottom, top_02, front, back_off, side_off_reverse, side_off).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile off_03 = models().cube(name(block) + "_off_03", bottom, top_03, front, back_off, side_off_reverse, side_off).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile off_04 = models().cube(name(block) + "_off_04", bottom, top_04, front, back_off, side_off_reverse, side_off).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile off_05 = models().cube(name(block) + "_off_05", bottom, top_05, front, back_off, side_off_reverse, side_off).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile off_06 = models().cube(name(block) + "_off_06", bottom, top_06, front, back_off, side_off_reverse, side_off).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile off_07 = models().cube(name(block) + "_off_07", bottom, top_07, front, back_off, side_off_reverse, side_off).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile off_08 = models().cube(name(block) + "_off_08", bottom, top_08, front, back_off, side_off_reverse, side_off).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile off_09 = models().cube(name(block) + "_off_09", bottom, top_09, front, back_off, side_off_reverse, side_off).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile off_10 = models().cube(name(block) + "_off_10", bottom, top_10, front, back_off, side_off_reverse, side_off).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile off_11 = models().cube(name(block) + "_off_11", bottom, top_11, front, back_off, side_off_reverse, side_off).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile off_12 = models().cube(name(block) + "_off_12", bottom, top_12, front, back_off, side_off_reverse, side_off).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile off_13 = models().cube(name(block) + "_off_13", bottom, top_13, front, back_off, side_off_reverse, side_off).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile off_14 = models().cube(name(block) + "_off_14", bottom, top_14, front, back_off, side_off_reverse, side_off).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile off_15 = models().cube(name(block) + "_off_15", bottom, top_15, front, back_off, side_off_reverse, side_off).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile on_00 = models().cube(name(block) + "_on_00", bottom, top_00, front, back_on, side_on_reverse, side_on).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile on_01 = models().cube(name(block) + "_on_01", bottom, top_01, front, back_on, side_on_reverse, side_on).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile on_02 = models().cube(name(block) + "_on_02", bottom, top_02, front, back_on, side_on_reverse, side_on).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile on_03 = models().cube(name(block) + "_on_03", bottom, top_03, front, back_on, side_on_reverse, side_on).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile on_04 = models().cube(name(block) + "_on_04", bottom, top_04, front, back_on, side_on_reverse, side_on).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile on_05 = models().cube(name(block) + "_on_05", bottom, top_05, front, back_on, side_on_reverse, side_on).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile on_06 = models().cube(name(block) + "_on_06", bottom, top_06, front, back_on, side_on_reverse, side_on).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile on_07 = models().cube(name(block) + "_on_07", bottom, top_07, front, back_on, side_on_reverse, side_on).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile on_08 = models().cube(name(block) + "_on_08", bottom, top_08, front, back_on, side_on_reverse, side_on).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile on_09 = models().cube(name(block) + "_on_09", bottom, top_09, front, back_on, side_on_reverse, side_on).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile on_10 = models().cube(name(block) + "_on_10", bottom, top_10, front, back_on, side_on_reverse, side_on).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile on_11 = models().cube(name(block) + "_on_11", bottom, top_11, front, back_on, side_on_reverse, side_on).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile on_12 = models().cube(name(block) + "_on_12", bottom, top_12, front, back_on, side_on_reverse, side_on).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile on_13 = models().cube(name(block) + "_on_13", bottom, top_13, front, back_on, side_on_reverse, side_on).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile on_14 = models().cube(name(block) + "_on_14", bottom, top_14, front, back_on, side_on_reverse, side_on).texture("particle", extend(blockTexture(block), "_front"));
        ModelFile on_15 = models().cube(name(block) + "_on_15", bottom, top_15, front, back_on, side_on_reverse, side_on).texture("particle", extend(blockTexture(block), "_front"));

        List<ModelFile> modelFileListOff = Arrays.asList(off_00, off_01, off_02, off_03, off_04, off_05, off_06, off_07, off_08, off_09, off_10, off_11, off_12, off_13, off_14, off_15);
        int j = 0;
        for (ModelFile model : modelFileListOff) {
            getVariantBuilder(block)
                    .partialState().with(SignalGateBlock.FACING, Direction.UP).with(SignalGateBlock.POWER, j).with(SignalGateBlock.POWERED, false).modelForState().modelFile(model).rotationX(270).addModel()
                    .partialState().with(SignalGateBlock.FACING, Direction.DOWN).with(SignalGateBlock.POWER, j).with(SignalGateBlock.POWERED, false).modelForState().modelFile(model).rotationX(90).addModel()
                    .partialState().with(SignalGateBlock.FACING, Direction.NORTH).with(SignalGateBlock.POWER, j).with(SignalGateBlock.POWERED, false).modelForState().modelFile(model).addModel()
                    .partialState().with(SignalGateBlock.FACING, Direction.EAST).with(SignalGateBlock.POWER, j).with(SignalGateBlock.POWERED, false).modelForState().modelFile(model).rotationY(90).addModel()
                    .partialState().with(SignalGateBlock.FACING, Direction.SOUTH).with(SignalGateBlock.POWER, j).with(SignalGateBlock.POWERED, false).modelForState().modelFile(model).rotationY(180).addModel()
                    .partialState().with(SignalGateBlock.FACING, Direction.WEST).with(SignalGateBlock.POWER, j).with(SignalGateBlock.POWERED, false).modelForState().modelFile(model).rotationY(270).addModel();
            j++;
        }

        List<ModelFile> modelFileList = Arrays.asList(on_00, on_01, on_02, on_03, on_04, on_05, on_06, on_07, on_08, on_09, on_10, on_11, on_12, on_13, on_14, on_15);
        int i = 0;
        for (ModelFile model : modelFileList) {
            getVariantBuilder(block)
                    .partialState().with(SignalGateBlock.FACING, Direction.UP).with(SignalGateBlock.POWER, i).with(SignalGateBlock.POWERED, true).modelForState().modelFile(model).rotationX(270).addModel()
                    .partialState().with(SignalGateBlock.FACING, Direction.DOWN).with(SignalGateBlock.POWER, i).with(SignalGateBlock.POWERED, true).modelForState().modelFile(model).rotationX(90).addModel()
                    .partialState().with(SignalGateBlock.FACING, Direction.NORTH).with(SignalGateBlock.POWER, i).with(SignalGateBlock.POWERED, true).modelForState().modelFile(model).addModel()
                    .partialState().with(SignalGateBlock.FACING, Direction.EAST).with(SignalGateBlock.POWER, i).with(SignalGateBlock.POWERED, true).modelForState().modelFile(model).rotationY(90).addModel()
                    .partialState().with(SignalGateBlock.FACING, Direction.SOUTH).with(SignalGateBlock.POWER, i).with(SignalGateBlock.POWERED, true).modelForState().modelFile(model).rotationY(180).addModel()
                    .partialState().with(SignalGateBlock.FACING, Direction.WEST).with(SignalGateBlock.POWER, i).with(SignalGateBlock.POWERED, true).modelForState().modelFile(model).rotationY(270).addModel();
            i++;
        }

        simpleBlockItem(block, models().getExistingFile(blockTexture(block)));
    }

    private void redstoneLaneWithItem(RedstoneLaneBlock block) {
        ResourceLocation base_lane = new ResourceLocation(DungeonNowLoading.MOD_ID + ":block/redstone_lane");
        ResourceLocation side = extend(base_lane, "_side");
        ResourceLocation front = extend(base_lane, "_front");
        ResourceLocation front_powered = extend(base_lane, "_front_powered");
        ResourceLocation front_overpowered = extend(base_lane, "_front_overpowered");
        ResourceLocation bottom = extend(base_lane, "_bottom");
        ResourceLocation top = extend(blockTexture(block), "_top_unpowered");
        ResourceLocation top_powered = extend(blockTexture(block), "_top_powered");
        ResourceLocation top_overpowered = extend(blockTexture(block), "_top_overpowered");

        ModelFile unpowered = models().cube(name(block) + "_unpowered", bottom, top, front, front, side, side).texture("particle", top);
        ModelFile powered = models().cube(name(block) + "_powered", bottom, top_powered, front_powered, front_powered, side, side).texture("particle", top_powered);
        ModelFile overpowered= models().cube(name(block) + "_overpowered", bottom, top_overpowered, front_overpowered, front_overpowered, side, side).texture("particle", top_overpowered);
        if (name(block).equals("redstone_lane_l")) {
            unpowered = models().cube(name(block) + "_unpowered", bottom, top, side, front, front, side).texture("particle", top);
            powered = models().cube(name(block) + "_powered", bottom, top_powered, side, front_powered, front_powered, side).texture("particle", top_powered);
            overpowered = models().cube(name(block) + "_overpowered", bottom, top_overpowered, side, front_overpowered, front_overpowered, side).texture("particle", top_overpowered);
        } else if (name(block).equals("redstone_lane_t")) {
            unpowered = models().cube(name(block) + "_unpowered", bottom, top, side, front, front, front).texture("particle", top);
            powered = models().cube(name(block) + "_powered", bottom, top_powered, side, front_powered, front_powered, front_powered).texture("particle", top_powered);
            overpowered = models().cube(name(block) + "_overpowered", bottom, top_overpowered, side, front_overpowered, front_overpowered, front_overpowered).texture("particle", top_overpowered);
        }

        if (name(block).equals("redstone_lane_i")) {
            getVariantBuilder(block)
                    .partialState().with(RedstoneLaneBlock.FACING, Direction.NORTH).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.UNPOWERED).modelForState().modelFile(unpowered).addModel()
                    .partialState().with(RedstoneLaneBlock.FACING, Direction.EAST).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.UNPOWERED).modelForState().modelFile(unpowered).rotationY(90).addModel()
                    .partialState().with(RedstoneLaneBlock.FACING, Direction.SOUTH).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.UNPOWERED).modelForState().modelFile(unpowered).addModel()
                    .partialState().with(RedstoneLaneBlock.FACING, Direction.WEST).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.UNPOWERED).modelForState().modelFile(unpowered).rotationY(90).addModel()

                    .partialState().with(RedstoneLaneBlock.FACING, Direction.NORTH).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.POWERED).modelForState().modelFile(powered).addModel()
                    .partialState().with(RedstoneLaneBlock.FACING, Direction.EAST).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.POWERED).modelForState().modelFile(powered).rotationY(90).addModel()
                    .partialState().with(RedstoneLaneBlock.FACING, Direction.SOUTH).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.POWERED).modelForState().modelFile(powered).addModel()
                    .partialState().with(RedstoneLaneBlock.FACING, Direction.WEST).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.POWERED).modelForState().modelFile(powered).rotationY(90).addModel()

                    .partialState().with(RedstoneLaneBlock.FACING, Direction.NORTH).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.OVERPOWERED).modelForState().modelFile(overpowered).addModel()
                    .partialState().with(RedstoneLaneBlock.FACING, Direction.EAST).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.OVERPOWERED).modelForState().modelFile(overpowered).rotationY(90).addModel()
                    .partialState().with(RedstoneLaneBlock.FACING, Direction.SOUTH).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.OVERPOWERED).modelForState().modelFile(overpowered).addModel()
                    .partialState().with(RedstoneLaneBlock.FACING, Direction.WEST).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.OVERPOWERED).modelForState().modelFile(overpowered).rotationY(90).addModel();
        } else {
            getVariantBuilder(block)
                    .partialState().with(RedstoneLaneBlock.FACING, Direction.NORTH).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.UNPOWERED).modelForState().modelFile(unpowered).rotationY(180).addModel()
                    .partialState().with(RedstoneLaneBlock.FACING, Direction.EAST).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.UNPOWERED).modelForState().modelFile(unpowered).rotationY(270).addModel()
                    .partialState().with(RedstoneLaneBlock.FACING, Direction.SOUTH).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.UNPOWERED).modelForState().modelFile(unpowered).addModel()
                    .partialState().with(RedstoneLaneBlock.FACING, Direction.WEST).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.UNPOWERED).modelForState().modelFile(unpowered).rotationY(90).addModel()

                    .partialState().with(RedstoneLaneBlock.FACING, Direction.NORTH).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.POWERED).modelForState().modelFile(powered).rotationY(180).addModel()
                    .partialState().with(RedstoneLaneBlock.FACING, Direction.EAST).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.POWERED).modelForState().modelFile(powered).rotationY(270).addModel()
                    .partialState().with(RedstoneLaneBlock.FACING, Direction.SOUTH).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.POWERED).modelForState().modelFile(powered).addModel()
                    .partialState().with(RedstoneLaneBlock.FACING, Direction.WEST).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.POWERED).modelForState().modelFile(powered).rotationY(90).addModel()

                    .partialState().with(RedstoneLaneBlock.FACING, Direction.NORTH).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.OVERPOWERED).modelForState().modelFile(overpowered).rotationY(180).addModel()
                    .partialState().with(RedstoneLaneBlock.FACING, Direction.EAST).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.OVERPOWERED).modelForState().modelFile(overpowered).rotationY(270).addModel()
                    .partialState().with(RedstoneLaneBlock.FACING, Direction.SOUTH).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.OVERPOWERED).modelForState().modelFile(overpowered).addModel()
                    .partialState().with(RedstoneLaneBlock.FACING, Direction.WEST).with(RedstoneLaneBlock.REDSTONE_LANE_MODE, RedstoneLaneMode.OVERPOWERED).modelForState().modelFile(overpowered).rotationY(90).addModel();

        }
        simpleBlockItem(block, models().getExistingFile(extend(blockTexture(block), "_unpowered")));
    }

    private void wallBlockWithItem(WallBlock block , Block parent) {
        wallBlock(block, blockTexture(parent));
        itemModels().withExistingParent(ForgeRegistries.BLOCKS.getKey(block).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  new ResourceLocation(DungeonNowLoading.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(parent).getPath()));
    }

    private void wallBlockWithItem(WallBlock block) {
        wallBlock(block, blockTexture(block));
        //itemModels().getBuilder(name(block)).texture(name(block), blockTexture(block));
        //simpleBlockItem(block, models().getExistingFile(blockTexture(block)));
        itemModels().withExistingParent(ForgeRegistries.BLOCKS.getKey(block).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  new ResourceLocation(DungeonNowLoading.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(block).getPath()));
    }

    private void fairkeeperSpawnerWithItem(FairkeeperSpawnerBlock block) {
        ResourceLocation side_on = extend(blockTexture(block), "_on");
        ResourceLocation side_off = extend(blockTexture(block), "_off");
        ResourceLocation top = extend(blockTexture(block), "_top");

        ModelFile on = models().cubeBottomTop(name(block) + "_on", side_on, top, top).renderType("cutout");
        ModelFile off = models().cubeBottomTop(name(block) + "_off", side_off, top, top).renderType("cutout");

        getVariantBuilder(block)
                .partialState().with(DNLProperties.FAIRKEEPER_ALERT, Boolean.TRUE).modelForState().modelFile(on).addModel()
                .partialState().with(DNLProperties.FAIRKEEPER_ALERT, Boolean.FALSE).modelForState().modelFile(off).addModel();

        simpleBlockItem(block, models().getExistingFile(extend(blockTexture(block), "_off")));
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
    }
}
