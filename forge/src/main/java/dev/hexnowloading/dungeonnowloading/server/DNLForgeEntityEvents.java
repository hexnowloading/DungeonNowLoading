package dev.hexnowloading.dungeonnowloading.server;

import dev.hexnowloading.dungeonnowloading.entity.DNLEntityEvents;
import dev.hexnowloading.dungeonnowloading.registry.DNLEntityTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class DNLForgeEntityEvents {

    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        for (EntityType<? extends LivingEntity> type : DNLEntityTypes.getAllAttributes().keySet()) {
            event.put(type, DNLEntityTypes.getAllAttributes().get(type));
        }
    }

    public static void onLivingDamageEvent(LivingDamageEvent event) {
        Entity attackingEntity = event.getSource().getEntity();
        LivingEntity hurtedEntity = event.getEntity();
        float damage = event.getAmount();
        if (attackingEntity instanceof LivingEntity livingEntity) {
            event.setAmount(DNLEntityEvents.onLivingDamageEvent(livingEntity, hurtedEntity, damage));
        }
    }

    public static void onLivingHurtEvent(LivingHurtEvent event) {
        Entity attacker = event.getSource().getEntity();
        LivingEntity target = event.getEntity();
        float damage = event.getAmount();
        if (attacker instanceof LivingEntity attackerEntity) {
            event.setAmount(DNLEntityEvents.onLivingHurtEvent(attackerEntity, target, damage));
        }
    }
}
