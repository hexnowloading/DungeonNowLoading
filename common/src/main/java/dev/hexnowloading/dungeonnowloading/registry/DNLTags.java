package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class DNLTags {
    public static final TagKey<DamageType> HOLLOW_HURTABLE = registerDamageTypeTag("hollow_hurtable");
    public static final TagKey<DamageType> SCUTTLE_HURTABLE = registerDamageTypeTag("scuttle_hurtable");
    public static final TagKey<Item> STONE_NOTCH_MATERIAL = registerItemTag("stone_notch_material");

    private static TagKey<Item> registerItemTag(String string) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(DungeonNowLoading.MOD_ID, string));
    }

    private static TagKey<DamageType> registerDamageTypeTag(String string) {
        return TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(DungeonNowLoading.MOD_ID, string));
    }
}
