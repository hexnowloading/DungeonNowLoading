package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class DNLTags {
    public static final TagKey<DamageType> HOLLOW_HURTABLE = registerDamageTypeTag("hollow_hurtable");

    private static TagKey<DamageType> registerDamageTypeTag(String string) {
        return TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(DungeonNowLoading.MOD_ID, string));
    }
}
