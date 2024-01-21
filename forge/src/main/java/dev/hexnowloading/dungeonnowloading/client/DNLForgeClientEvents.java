package dev.hexnowloading.dungeonnowloading.client;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.entity.client.model.*;
import dev.hexnowloading.dungeonnowloading.entity.client.renderer.*;
import dev.hexnowloading.dungeonnowloading.registry.DNLEntityTypes;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class DNLForgeClientEvents {
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        // Bosses
        event.registerLayerDefinition(ChaosSpawnerModel.LAYER_LOCATION, ChaosSpawnerModel::createBodyLayer);

        // Monsters
        event.registerLayerDefinition(HollowModel.LAYER_LOCATION, HollowModel::createBodyLayer);
        event.registerLayerDefinition(SpawnerCarrierModel.LAYER_LOCATION, SpawnerCarrierModel::createBodyLayer);

        // Passive
        event.registerLayerDefinition(SealedChaosModel.LAYER_LOCATION, SealedChaosModel::createBodyLayer);
        event.registerLayerDefinition(WhimperModel.LAYER_LOCATION, WhimperModel::createBodyLayer);

        // Projectiles
        event.registerLayerDefinition(ChaosSpawnerProjectileModel.LAYER_LOCATION, ChaosSpawnerProjectileModel::createBodyLayer);
    }
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        // Bosses
        event.registerEntityRenderer(DNLEntityTypes.CHAOS_SPAWNER.get(), ChaosSpawnerRenderer::new);

        // Monsters
        event.registerEntityRenderer(DNLEntityTypes.HOLLOW.get(), HollowRenderer::new);
        event.registerEntityRenderer(DNLEntityTypes.SPAWNER_CARRIER.get(), SpawnerCarrierRenderer::new);

        // Passive
        event.registerEntityRenderer(DNLEntityTypes.SEALED_CHAOS.get(), SealedChaosRenderer::new);
        event.registerEntityRenderer(DNLEntityTypes.WHIMPER.get(), WhimperRenderer::new);

        // Projectiles
        event.registerEntityRenderer(DNLEntityTypes.CHAOS_SPAWNER_PROJECTILE.get(), ChaosSpawnerProjectileRenderer::new);
        event.registerEntityRenderer(DNLEntityTypes.GREAT_EXPERIENCE_BOTTLE.get(), (context) -> {
            return new ThrownItemRenderer<>(context, 1.25F, false);
        });

        // Misc
        event.registerEntityRenderer(DNLEntityTypes.SPECIAL_ITEM_ENTITY.get(), SpecialItemEntityRenderer::new);
    }
}
