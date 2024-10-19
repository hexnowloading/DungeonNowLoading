package dev.hexnowloading.dungeonnowloading.datagen.provider;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.registry.DNLItems;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DNLForgeItemModelProvider extends ItemModelProvider {

    private static ModelFile ITEM_GENERATED;

    public DNLForgeItemModelProvider(DataGenerator gen, ExistingFileHelper existingFileHelper) {
        super(gen.getPackOutput(), DungeonNowLoading.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ITEM_GENERATED = getExistingFile(mcLoc("item/generated"));

        simpleItem(DNLItems.REDSTONE_SUPPRESSOR.get());
        simpleItem(DNLItems.REDSTONE_CORE.get());
        simpleItem(DNLItems.REDSTONE_CHIP.get());
        simpleItem(DNLItems.REDSTONE_CIRCUIT.get());
        simpleItem(DNLItems.REDSTONE_CATALYST.get());
        spawnEggItem(DNLItems.FAIRKEEPER_SPAWNEGG.get());
        spawnEggItem(DNLItems.SCUTTLE_SPAWNEGG.get());
        fourStageBowItem(DNLItems.VERTEX_BOW.get(), 0.65f, 0.9f, 1.5f);
    }

    private void simpleItem(Item item) {
        String name = ForgeRegistries.ITEMS.getKey(item).getPath();
        withExistingParent(ITEM_FOLDER + "/" + name, mcLoc(ITEM_FOLDER + "/generated")).texture("layer0", ITEM_FOLDER + "/" + name);
    }

    private void spawnEggItem(Item item) {
        String name = ForgeRegistries.ITEMS.getKey(item).getPath();
        withExistingParent(ITEM_FOLDER + "/" + name, mcLoc(ITEM_FOLDER + "/template_spawn_egg"));
    }

    private void fourStageBowItem(Item item, float pulling1, float pulling2, float pulling3) {
        String name = ForgeRegistries.ITEMS.getKey(item).getPath();

        registerBowPullingModel(name, 0);
        registerBowPullingModel(name, 1);
        registerBowPullingModel(name, 2);
        registerBowPullingModel(name, 3);

        withExistingParent(ITEM_FOLDER + "/" + name, mcLoc(ITEM_FOLDER + "/bow"))
                .texture("layer0", modLoc(ITEM_FOLDER + "/" + name))
                .override()
                .predicate(mcLoc("pulling"), 1)
                .model(getExistingFile(modLoc(ITEM_FOLDER + "/" + name + "_pulling_0")))
                .end()
                .override()
                .predicate(mcLoc("pulling"), 1)
                .predicate(mcLoc("pull"), pulling1)
                .model(getExistingFile(modLoc(ITEM_FOLDER + "/" + name + "_pulling_1")))
                .end()
                .override()
                .predicate(mcLoc("pulling"), 1)
                .predicate(mcLoc("pull"), pulling2)
                .model(getExistingFile(modLoc(ITEM_FOLDER + "/" + name + "_pulling_2")))
                .end()
                .override()
                .predicate(mcLoc("pulling"), 1)
                .predicate(mcLoc("pull"), pulling3)
                .model(getExistingFile(modLoc(ITEM_FOLDER + "/" + name + "_pulling_3")))
                .end()
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                .rotation(0, -90, 55)
                .translation(0, 4, 2)
                .scale(0.85f, 0.85f, 0.85f)
                .end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
                .rotation(-80, -280, 40)
                .translation(-1f, -2f, 2.5f)
                .scale(0.9f, 0.9f, 0.9f)
                .end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                .rotation(0, -90, 25)
                .translation(1.13f, 3.2f, 1.13f)
                .scale(0.68f, 0.68f, 0.68f)
                .end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
                .rotation(0, 90, -25 )
                .translation(1.13f, 3.2f, 1.13f)
                .scale(0.68f, 0.68f, 0.68f)
                .end();
    }

    private void simpleBowItem(Item item) {
        String name = ForgeRegistries.ITEMS.getKey(item).getPath();

        registerBowPullingModel(name, 0);
        registerBowPullingModel(name, 1);
        registerBowPullingModel(name, 2);

        withExistingParent(ITEM_FOLDER + "/" + name, mcLoc(ITEM_FOLDER + "/bow"))
                .texture("layer0", modLoc(ITEM_FOLDER + "/" + name))
                .override()
                .predicate(mcLoc("pulling"), 1)
                .model(getExistingFile(modLoc(ITEM_FOLDER + "/" + name + "_pulling_0")))
                .end()
                .override()
                .predicate(mcLoc("pulling"), 1)
                .predicate(mcLoc("pull"), 0.65f)
                .model(getExistingFile(modLoc(ITEM_FOLDER + "/" + name + "_pulling_1")))
                .end()
                .override()
                .predicate(mcLoc("pulling"), 1)
                .predicate(mcLoc("pull"), 0.9f)
                .model(getExistingFile(modLoc(ITEM_FOLDER + "/" + name + "_pulling_2")))
                .end()
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                .rotation(0, -90, 55)
                .translation(0, 4, 2)
                .scale(0.85f, 0.85f, 0.85f)
                .end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
                .rotation(-80, -280, 40)
                .translation(-1f, -2f, 2.5f)
                .scale(0.9f, 0.9f, 0.9f)
                .end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                .rotation(0, -90, 25)
                .translation(1.13f, 3.2f, 1.13f)
                .scale(0.68f, 0.68f, 0.68f)
                .end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
                .rotation(0, 90, -25 )
                .translation(1.13f, 3.2f, 1.13f)
                .scale(0.68f, 0.68f, 0.68f)
                .end();
    }

    private void registerBowPullingModel(String baseName, int pullingStage) {
        getBuilder(baseName + "_pulling_" + pullingStage)
                .parent(getExistingFile(mcLoc("item/bow")))
                .texture("layer0", modLoc("item/" + baseName + "_pulling_" + pullingStage));
    }
    /*private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(DungeonNowLoading.MOD_ID, "item/" + item.getId().getPath()));
    }*/

    public void evenSimplerBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(DungeonNowLoading.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(DungeonNowLoading.MOD_ID,"item/" + item.getId().getPath()));
    }
}
