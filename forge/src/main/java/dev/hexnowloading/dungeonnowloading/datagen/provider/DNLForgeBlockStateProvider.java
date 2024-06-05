package dev.hexnowloading.dungeonnowloading.datagen.provider;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.block.FairkeeperSpawnerBlock;
import dev.hexnowloading.dungeonnowloading.block.PillarCapBlock;
import dev.hexnowloading.dungeonnowloading.block.RedstoneLaneBlock;
import dev.hexnowloading.dungeonnowloading.block.property.RedstoneLaneMode;
import dev.hexnowloading.dungeonnowloading.registry.DNLBlocks;
import dev.hexnowloading.dungeonnowloading.registry.DNLProperties;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

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
