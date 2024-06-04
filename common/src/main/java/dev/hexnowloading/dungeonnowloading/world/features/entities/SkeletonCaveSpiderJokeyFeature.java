package dev.hexnowloading.dungeonnowloading.world.features.entities;

import dev.hexnowloading.dungeonnowloading.util.EntityScale;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SkeletonCaveSpiderJokeyFeature extends Feature<NoneFeatureConfiguration> {

    public SkeletonCaveSpiderJokeyFeature() { super(NoneFeatureConfiguration.CODEC); }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        CaveSpider caveSpider = EntityType.CAVE_SPIDER.create(context.level().getLevel());
        caveSpider.setPersistenceRequired();
        caveSpider.moveTo((double)context.origin().getX() + 0.5D, context.origin().getY(), (double)context.origin().getZ() + 0.5D, 0.0F, 0.0F);
        caveSpider.finalizeSpawn(context.level(), context.level().getCurrentDifficultyAt(context.origin()), MobSpawnType.STRUCTURE, null, null);
        EntityScale.scaleMobAttributes(caveSpider);

        Skeleton skeleton = EntityType.SKELETON.create(context.level().getLevel());
        skeleton.setPersistenceRequired();
        skeleton.moveTo((double)context.origin().getX() + 0.5D, context.origin().getY() + 1, (double)context.origin().getZ() + 0.5D, 0.0F, 0.0F);
        skeleton.finalizeSpawn(context.level(), context.level().getCurrentDifficultyAt(context.origin()), MobSpawnType.STRUCTURE, null, null);
        EntityScale.scaleMobAttributes(skeleton);

        skeleton.startRiding(caveSpider);

        context.level().addFreshEntityWithPassengers(caveSpider);
        return true;
    }
}
