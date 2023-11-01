package dev.hexnowloading.dungeonnowloading.entity;

import dev.hexnowloading.dungeonnowloading.registry.DNLEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

public class DNLForgeEntityEvents {
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        for (EntityType<? extends LivingEntity> type : DNLEntityTypes.getAllAttributes().keySet()) {
            event.put(type, DNLEntityTypes.getAllAttributes().get(type));
        }
    }
}
