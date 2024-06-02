package dev.hexnowloading.dungeonnowloading.capabilities;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.capabilities.fabric.FairkeeperChestPositionsCapabilityHandler;
import dev.hexnowloading.dungeonnowloading.capabilities.fabric.IFairkeeperChestPositionsCapability;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.intellij.lang.annotations.Identifier;

public class CapabilityList implements EntityComponentInitializer {

    public static final ComponentKey<IFairkeeperChestPositionsCapability> FAIRKEEPER_CHEST_POSITIONS_CAP = ComponentRegistry.getOrCreate(new ResourceLocation(DungeonNowLoading.MOD_ID, "fairkeeper_chest_positions"), IFairkeeperChestPositionsCapability.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(FAIRKEEPER_CHEST_POSITIONS_CAP, player -> new FairkeeperChestPositionsCapabilityHandler());
    }
}