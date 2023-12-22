package dev.hexnowloading.dungeonnowloading.entity;

import dev.hexnowloading.dungeonnowloading.item.LifeStealerItem;
import dev.hexnowloading.dungeonnowloading.registry.DNLItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DNLEntityEvents {
    public static void onLivingDamageEvent(LivingEntity hurtingEntity, LivingEntity hurtedEntity, float damage) {
        ItemStack itemStack = hurtingEntity.getMainHandItem();
        if (itemStack.is(DNLItems.LIFE_STEALER.get())) {
            LifeStealerItem.healthDrain(itemStack, hurtingEntity, hurtedEntity, damage);
        }
    }
}
