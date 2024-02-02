package dev.hexnowloading.dungeonnowloading.world.features.entities;

import dev.hexnowloading.dungeonnowloading.util.EntityScale;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SkeletonSpiderJokeyFeature extends Feature<NoneFeatureConfiguration> {

    public SkeletonSpiderJokeyFeature() { super(NoneFeatureConfiguration.CODEC); }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {

        Spider spider = EntityType.SPIDER.create(context.level().getLevel());
        spider.setPersistenceRequired();
        spider.moveTo((double)context.origin().getX() + 0.5D, context.origin().getY(), (double)context.origin().getZ() + 0.5D, 0.0F, 0.0F);
        spider.finalizeSpawn(context.level(), context.level().getCurrentDifficultyAt(context.origin()), MobSpawnType.STRUCTURE, null, null);
        EntityScale.scaleMobAttributes(spider);

        Skeleton skeleton = EntityType.SKELETON.create(context.level().getLevel());
        skeleton.finalizeSpawn(context.level(), context.level().getCurrentDifficultyAt(context.origin()), MobSpawnType.STRUCTURE, null, null);
        skeleton.setPersistenceRequired();
        EntityScale.scaleMobAttributes(skeleton);
        skeleton.moveTo((double)context.origin().getX() + 0.5D, context.origin().getY() + 1, (double)context.origin().getZ() + 0.5D, 0.0F, 0.0F);

        skeleton.startRiding(spider);

        context.level().addFreshEntityWithPassengers(spider);
        return true;
    }
}
