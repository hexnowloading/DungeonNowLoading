package dev.hexnowloading.skyisland.registry;

import dev.hexnowloading.skyisland.Skyisland;
import dev.hexnowloading.skyisland.registration.RegistrationProvider;
import dev.hexnowloading.skyisland.registration.RegistryObject;
import dev.hexnowloading.skyisland.world.entity.WindstoneEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class SkyislandEntityTypes {
    public static final RegistrationProvider<EntityType<?>> ENTITY_TYPE = RegistrationProvider.get(Registries.ENTITY_TYPE, Skyisland.MOD_ID);

    //public static final RegistryObject<EntityType<Entity>> WINDSTONE = ENTITY_TYPE.register("windstone", () -> EntityType.Builder.of(WindstoneEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).build(new ResourceLocation(Skyisland.MOD_ID, "windstone").toString()));

    public static void init() {}
}
