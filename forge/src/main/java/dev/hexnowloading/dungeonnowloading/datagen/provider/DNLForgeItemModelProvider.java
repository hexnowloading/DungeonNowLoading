package dev.hexnowloading.dungeonnowloading.datagen.provider;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.registry.DNLItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
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
    }

    private void simpleItem(Item item) {
        String name = ForgeRegistries.ITEMS.getKey(item).getPath();
        withExistingParent(ITEM_FOLDER + "/" + name, mcLoc(ITEM_FOLDER + "/generated")).texture("layer0", ITEM_FOLDER + "/" + name);
    }

    private void spawnEggItem(Item item) {
        String name = ForgeRegistries.ITEMS.getKey(item).getPath();
        withExistingParent(ITEM_FOLDER + "/" + name, mcLoc(ITEM_FOLDER + "/template_spawn_egg"));
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
