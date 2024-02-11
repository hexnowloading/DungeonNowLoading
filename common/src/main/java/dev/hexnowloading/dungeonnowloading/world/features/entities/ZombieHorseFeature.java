package dev.hexnowloading.dungeonnowloading.world.features.entities;

import com.mojang.serialization.Codec;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ZombieHorseFeature extends Feature<NoneFeatureConfiguration> {

    public ZombieHorseFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        ZombieHorse zombieHorse = EntityType.ZOMBIE_HORSE.create(context.level().getLevel());
        zombieHorse.setPersistenceRequired();
        zombieHorse.moveTo((double)context.origin().getX() + 0.5D, context.origin().getY(), (double)context.origin().getZ() + 0.5D, 0.0F, 0.0F);
        zombieHorse.finalizeSpawn(context.level(), context.level().getCurrentDifficultyAt(context.origin()), MobSpawnType.STRUCTURE, null, null);
        zombieHorse.setTamed(true);
        zombieHorse.equipSaddle(SoundSource.NEUTRAL);

        context.level().addFreshEntity(zombieHorse);
        return true;
    }
}
