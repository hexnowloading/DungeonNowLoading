package dev.hexnowloading.dungeonnowloading.platform;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.platform.services.RegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ForgeCommonRegistryHelper implements RegistryHelper {
    public static final DeferredRegister<CreativeModeTab> TAB_REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DungeonNowLoading.MOD_ID);
    private static final RegistryMap registryMap = new RegistryMap();

    public static final List<SimpleJsonResourceReloadListener> dataLoaders = new ArrayList<>();

    @Override
    public <T> Supplier<T> register(Registry<? super T> registry, String name, Supplier<T> entry) {
        return registryMap.register(registry, name, entry);
    }

    @Override
    public void register(ResourceLocation id, SimpleJsonResourceReloadListener loader) {
        dataLoaders.add(loader);
    }

    @Override
    public SoundType getSoundType(float volume, float pitch, Supplier<SoundEvent> breakSound, Supplier<SoundEvent> stepSound, Supplier<SoundEvent> placeSound, Supplier<SoundEvent> hitSound, Supplier<SoundEvent> fallSound) {
        return new ForgeSoundType(volume, pitch, breakSound, stepSound, placeSound, hitSound, fallSound);
    }

    @Override
    public Supplier<CreativeModeTab> registerCreativeTab(String name, Supplier<ItemStack> iconSupplier, CreativeModeTab.DisplayItemsGenerator itemGenerator) {
        return TAB_REGISTRY.register(name, () -> CreativeModeTab.builder()
                .title(Component.translatable("tab." + DungeonNowLoading.MOD_ID + "." + name))
                .icon(iconSupplier)
                .displayItems(itemGenerator)
                .build());
    }

    public static RegistryMap getRegistryMap() {
        return registryMap;
    }

    public static class RegistryMap {

        private final Map<ResourceLocation, DeferredRegister<?>> registries = new HashMap<>();

        private <T> RegistryObject<T> register(Registry<? super T> registry, String name, Supplier<T> entry) {
            DeferredRegister<T> reg = getDeferred(registry);
            return reg != null ? reg.register(name, entry) : null;
        }

        @SuppressWarnings({"unchecked"})
        public <T> DeferredRegister<T> getDeferred(Registry<? super T> registry) {
            return (DeferredRegister<T>)registries.computeIfAbsent(registry.key().location(), (key) -> {
                DeferredRegister<T> defReg = DeferredRegister.create(registry.key().location(), DungeonNowLoading.MOD_ID);
                defReg.register(FMLJavaModLoadingContext.get().getModEventBus());
                return defReg;
            });
        }

    }
}
