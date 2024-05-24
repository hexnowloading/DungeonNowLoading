package dev.hexnowloading.dungeonnowloading.datagen.provider;

import dev.hexnowloading.dungeonnowloading.registry.DNLItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

public class DNLForgeRecipeProvider extends RecipeProvider {

    public DNLForgeRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        buildShapedRecipes(consumer);
        buildStoneCutterRecipes(consumer);
        buildSmeltingRecipes(consumer);
    }

    private void buildShapedRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DNLItems.COILING_STONE_PILLAR.get(), 2)
                .pattern("s")
                .pattern("s")
                .define('s', Items.STONE_BRICKS)
                .unlockedBy("has_stone_bricks", has(Items.STONE_BRICKS))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DNLItems.COILING_STONE_PILLAR_SLAB.get(), 6)
                .pattern("ppp")
                .define('p', DNLItems.COILING_STONE_PILLAR.get())
                .unlockedBy("has_coiling_stone_pillar", has(DNLItems.COILING_STONE_PILLAR.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DNLItems.COILING_STONE_PILLAR_STAIRS.get(), 4)
                .pattern("p  ")
                .pattern("pp ")
                .pattern("ppp")
                .define('p', DNLItems.COILING_STONE_PILLAR.get())
                .unlockedBy("has_coiling_stone_pillar", has(DNLItems.COILING_STONE_PILLAR.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DNLItems.CHISELED_COILING_STONE_PILLAR.get(), 1)
                .pattern("s")
                .pattern("s")
                .define('s', DNLItems.COILING_STONE_PILLAR_SLAB.get())
                .unlockedBy("has_coiling_stone_pillar", has(DNLItems.COILING_STONE_PILLAR.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DNLItems.COILING_STONE_PILLAR_CAPITAL.get(), 5)
                .pattern("ppp")
                .pattern("p p")
                .define('p', DNLItems.COILING_STONE_PILLAR.get())
                .unlockedBy("has_coiling_stone_pillar", has(DNLItems.COILING_STONE_PILLAR.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DNLItems.COILING_STONE_PILLAR_WALL.get(), 6)
                .pattern("ppp")
                .pattern("ppp")
                .define('p', DNLItems.COILING_STONE_PILLAR.get())
                .unlockedBy("has_coiling_stone_pillar", has(DNLItems.COILING_STONE_PILLAR.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DNLItems.STONE_TILES.get(), 4)
                .pattern("ss")
                .pattern("ss")
                .define('s', Items.STONE_BRICKS)
                .unlockedBy("has_stone_bricks", has(Items.STONE_BRICKS))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DNLItems.STONE_TILE_SLAB.get(), 6)
                .pattern("ttt")
                .define('t', DNLItems.STONE_TILES.get())
                .unlockedBy("has_stone_tiles", has(DNLItems.STONE_TILES.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DNLItems.STONE_TILE_STAIRS.get(), 4)
                .pattern("t  ")
                .pattern("tt ")
                .pattern("ttt")
                .define('t', DNLItems.STONE_TILES.get())
                .unlockedBy("has_stone_tiles", has(DNLItems.STONE_TILES.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DNLItems.STONE_TILE_WALL.get(), 6)
                .pattern("ttt")
                .pattern("ttt")
                .define('t', DNLItems.STONE_TILES.get())
                .unlockedBy("has_stone_tiles", has(DNLItems.STONE_TILES.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DNLItems.SIGNALING_STONE_EMBLEM.get(), 8)
                .pattern("ccc")
                .pattern("crc")
                .pattern("ccc")
                .define('c', Items.CHISELED_STONE_BRICKS)
                .define('r', Items.REDSTONE_BLOCK)
                .unlockedBy("has_stone_bricks", has(Items.STONE_BRICKS))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DNLItems.DUELING_STONE_EMBLEM.get(), 8)
                .pattern("ccc")
                .pattern("crc")
                .pattern("ccc")
                .define('c', Items.CHISELED_STONE_BRICKS)
                .define('r', Items.KELP)
                .unlockedBy("has_stone_bricks", has(Items.STONE_BRICKS))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DNLItems.PUZZLING_STONE_EMBLEM.get(), 8)
                .pattern("ccc")
                .pattern("crc")
                .pattern("ccc")
                .define('c', Items.CHISELED_STONE_BRICKS)
                .define('r', Items.DRIED_KELP)
                .unlockedBy("has_stone_bricks", has(Items.STONE_BRICKS))
                .save(consumer);
    }

    private void buildStoneCutterRecipes(Consumer<FinishedRecipe> consumer) {
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DNLItems.COILING_STONE_PILLAR.get(), Items.STONE_BRICKS, 1);
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DNLItems.CHISELED_COILING_STONE_PILLAR.get(), DNLItems.COILING_STONE_PILLAR.get(), 1);
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DNLItems.COILING_STONE_PILLAR_CAPITAL.get(), DNLItems.COILING_STONE_PILLAR.get(), 1);
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DNLItems.COILING_STONE_PILLAR_SLAB.get(), DNLItems.COILING_STONE_PILLAR.get(), 2);
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DNLItems.COILING_STONE_PILLAR_STAIRS.get(), DNLItems.COILING_STONE_PILLAR.get(), 1);
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DNLItems.COILING_STONE_PILLAR_WALL.get(), DNLItems.COILING_STONE_PILLAR.get(), 1);
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DNLItems.STONE_TILES.get(), Items.STONE_BRICKS, 1);
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DNLItems.STONE_TILE_STAIRS.get(), DNLItems.STONE_TILES.get(), 1);
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DNLItems.STONE_TILE_SLAB.get(), DNLItems.STONE_TILES.get(), 2);
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DNLItems.STONE_TILE_WALL.get(), DNLItems.STONE_TILES.get(), 1);
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DNLItems.SIGNALING_STONE_EMBLEM.get(), Items.STONE_BRICKS, 1);
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DNLItems.DUELING_STONE_EMBLEM.get(), Items.STONE_BRICKS, 1);
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, DNLItems.PUZZLING_STONE_EMBLEM.get(), Items.STONE_BRICKS, 1);
    }

    private void buildSmeltingRecipes(Consumer<FinishedRecipe> consumer) {
        smeltingResultFromBase(consumer, DNLItems.CRACKED_STONE_TILES.get(), DNLItems.STONE_TILES.get());
    }

}
