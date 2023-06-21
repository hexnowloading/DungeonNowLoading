package dev.hexnowloading.skyisland.registry;

import dev.hexnowloading.skyisland.Skyisland;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Skyisland.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SkyislandForgeCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Skyisland.MOD_ID);

    public static final RegistryObject<CreativeModeTab> SKYISLAND_TAB = CREATIVE_MODE_TABS.register("skyisland", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> SkyislandForgeItems.WIND_JADE.get().getDefaultInstance())
            .title(Component.translatable("creativemodetab.skyisland_tab"))
            .displayItems((parameters, output) -> {
                output.accept(SkyislandForgeItems.WIND_JADE.get());
                output.accept(SkyislandForgeItems.EYE_OF_THE_STORM.get());
                output.accept(SkyislandForgeBlocks.WIND_ALTER.get());
            }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
