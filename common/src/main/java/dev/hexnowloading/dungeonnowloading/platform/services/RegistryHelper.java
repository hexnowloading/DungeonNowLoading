package dev.hexnowloading.dungeonnowloading.platform.services;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

import java.util.function.Supplier;

public interface RegistryHelper {
    <T> Supplier<T> register(Registry<? super T> registry, String name, Supplier<T> entry);

    void register(ResourceLocation id, SimpleJsonResourceReloadListener loader);

    SoundType getSoundType(float volume, float pitch, Supplier<SoundEvent> breakSound, Supplier<SoundEvent> stepSound,
                           Supplier<SoundEvent> placeSound, Supplier<SoundEvent> hitSound, Supplier<SoundEvent> fallSound);

    Supplier<CreativeModeTab> registerCreativeTab(String name, Supplier<ItemStack> iconSupplier, CreativeModeTab.DisplayItemsGenerator itemsGenerator);
}
