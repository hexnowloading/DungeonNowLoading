package dev.hexnowloading.dungeonnowloading.world.features.entities;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SkeletonHorseFeature extends Feature<NoneFeatureConfiguration> {

    public SkeletonHorseFeature() { super(NoneFeatureConfiguration.CODEC); }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        SkeletonHorse skeletonHorse = EntityType.SKELETON_HORSE.create(context.level().getLevel());
        skeletonHorse.setPersistenceRequired();
        skeletonHorse.moveTo((double)context.origin().getX() + 0.5D, context.origin().getY(), (double)context.origin().getZ() + 0.5D, 0.0F, 0.0F);
        skeletonHorse.finalizeSpawn(context.level(), context.level().getCurrentDifficultyAt(context.origin()), MobSpawnType.STRUCTURE, null, null);
        skeletonHorse.setTamed(true);
        skeletonHorse.equipSaddle(SoundSource.NEUTRAL);

        context.level().addFreshEntity(skeletonHorse);
        return true;
    }
}
