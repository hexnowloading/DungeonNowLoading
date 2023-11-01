package dev.hexnowloading.dungeonnowloading.platform;

import dev.hexnowloading.dungeonnowloading.platform.services.ItemHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.common.ForgeSpawnEggItem;

import java.util.function.Supplier;

public class ForgeItemHelper implements ItemHelper {
    @Override
    public Supplier<SpawnEggItem> makeSpawnEgg(Supplier<EntityType<? extends Mob>> entityType, int bg, int fg, Item.Properties properties) {
        return () -> new ForgeSpawnEggItem(entityType, bg, fg, properties);
    }
}
