package dev.hexnowloading.skyisland.entity;

import dev.hexnowloading.skyisland.registry.SkyislandEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

public class SkyislandForgeEntityEvents {
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        for (EntityType<? extends LivingEntity> type : SkyislandEntityTypes.getAllAttributes().keySet()) {
            event.put(type, SkyislandEntityTypes.getAllAttributes().get(type));
        }
    }
}
