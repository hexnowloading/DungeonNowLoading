package dev.hexnowloading.dungeonnowloading.capabilities;

import dev.hexnowloading.dungeonnowloading.capabilities.player.IPlayerCapability;
import dev.hexnowloading.dungeonnowloading.capabilities.player.PlayerCapabilityHandler;
import dev.onyxstudios.cca.api.v3.component.Component;
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

    public static final ComponentKey<IPlayerCapability> PLAYER_CAP = ComponentRegistryV3.INSTANCE.getOrCreate(new ResourceLocation("dungeonnowloading", "test_point"), IPlayerCapability.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(PLAYER_CAP, player -> new PlayerCapabilityHandler(), RespawnCopyStrategy.LOSSLESS_ONLY);
    }
}
