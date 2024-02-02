package dev.hexnowloading.dungeonnowloading.world.features;

import com.mojang.serialization.Codec;
import dev.hexnowloading.dungeonnowloading.world.features.configs.EntityTypeConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.datafix.fixes.JigsawRotationFix;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;

import java.util.function.Predicate;

public class SpawnerFeature extends Feature<EntityTypeConfig> {

    public SpawnerFeature(Codec<EntityTypeConfig> codec) { super(codec); }

    @Override
    public boolean place(FeaturePlaceContext<EntityTypeConfig> context) {
        Predicate<BlockState> predicate = Feature.isReplaceable(BlockTags.FEATURES_CANNOT_REPLACE);
        BlockPos blockPos = context.origin().below();
        RandomSource randomSource = context.level().getRandom();
        WorldGenLevel worldGenLevel = context.level();


        //worldGenLevel.setBlock(blockPos, Blocks.SPAWNER.defaultBlockState(), 2);
        this.safeSetBlock(worldGenLevel, blockPos, Blocks.SPAWNER.defaultBlockState(), predicate);
        BlockEntity blockEntity = worldGenLevel.getBlockEntity(blockPos);
        if (blockEntity instanceof SpawnerBlockEntity spawnerBlockEntity) {
            spawnerBlockEntity.setEntityId(context.config().entityType, randomSource);
        }

        return true;
    }
}
