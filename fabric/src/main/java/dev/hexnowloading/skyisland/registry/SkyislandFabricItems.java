package dev.hexnowloading.skyisland.registry;

import dev.hexnowloading.skyisland.Skyisland;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.intellij.lang.annotations.Identifier;


public class SkyislandFabricItems {

    public static final Item WIND_JADE = registerItem("wind_jade", new Item(new FabricItemSettings()));
    public static final Item EYE_OF_THE_STORM = registerItem("eye_of_the_storm", new Item(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Skyisland.MOD_ID, name), item);
    }



    public static void registerItems() {
        Skyisland.LOGGER.info("Registering Items for " + Skyisland.MOD_ID);
    }

}