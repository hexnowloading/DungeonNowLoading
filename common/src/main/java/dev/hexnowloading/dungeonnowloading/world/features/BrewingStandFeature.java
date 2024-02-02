package dev.hexnowloading.dungeonnowloading.world.features;

import com.mojang.serialization.Codec;
import dev.hexnowloading.dungeonnowloading.world.features.configs.PotionConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.function.Predicate;

public class BrewingStandFeature extends Feature<PotionConfig> {

    public BrewingStandFeature(Codec<PotionConfig> codec) { super(codec); }

    @Override
    public boolean place(FeaturePlaceContext<PotionConfig> context) {
        Predicate<BlockState> predicate = Feature.isReplaceable(BlockTags.FEATURES_CANNOT_REPLACE);
        BlockPos blockPos = context.origin().below();
        WorldGenLevel worldGenLevel = context.level();

        this.safeSetBlock(worldGenLevel, blockPos, Blocks.BREWING_STAND.defaultBlockState(), predicate);
        BlockEntity blockEntity = worldGenLevel.getBlockEntity(blockPos);
        Item item;
        Item type = context.config().type;
        if (Items.SPLASH_POTION.equals(type)) {
            item = Items.SPLASH_POTION;
        } else if (Items.LINGERING_POTION.equals(type)) {
            item = Items.LINGERING_POTION;
        } else {
            item = Items.POTION;
        }

        if (blockEntity instanceof BrewingStandBlockEntity brewingStandBlockEntity) {
            for (int i = 0; i < 3; i++) {
                brewingStandBlockEntity.setItem(i, PotionUtils.setPotion(new ItemStack(item), context.config().potion));
            }
        }

        return true;
    }
}
