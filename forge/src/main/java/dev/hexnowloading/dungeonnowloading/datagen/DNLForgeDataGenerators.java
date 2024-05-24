package dev.hexnowloading.dungeonnowloading.datagen;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.datagen.provider.DNLForgeBlockStateProvider;
import dev.hexnowloading.dungeonnowloading.datagen.provider.DNLForgeLootTableProvider;
import dev.hexnowloading.dungeonnowloading.datagen.provider.DNLForgeRecipeProvider;
import dev.hexnowloading.dungeonnowloading.datagen.tag.DNLForgeBlockTagGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = DungeonNowLoading.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DNLForgeDataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        DNLForgeBlockTagGenerator blockTagGenerator = new DNLForgeBlockTagGenerator(generator, lookupProvider, existingFileHelper);
        generator.addProvider(true, blockTagGenerator);
        generator.addProvider(true, new DNLForgeBlockStateProvider(generator, existingFileHelper));
        generator.addProvider(true, new DNLForgeRecipeProvider(generator.getPackOutput()));
        generator.addProvider(true, DNLForgeLootTableProvider.create(generator.getPackOutput()));
    }
}
