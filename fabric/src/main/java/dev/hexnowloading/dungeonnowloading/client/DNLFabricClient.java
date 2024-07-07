package dev.hexnowloading.dungeonnowloading.client;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.block.client.model.FairkeeperChestModel;
import dev.hexnowloading.dungeonnowloading.block.client.renderer.FairkeeperChestBlockRenderer;
import dev.hexnowloading.dungeonnowloading.block.client.renderer.FairkeeperChestItemRenderer;
import dev.hexnowloading.dungeonnowloading.entity.client.model.*;
import dev.hexnowloading.dungeonnowloading.entity.client.renderer.*;
import dev.hexnowloading.dungeonnowloading.entity.monster.HollowEntity;
import dev.hexnowloading.dungeonnowloading.particle.LargeFlameParticle;
import dev.hexnowloading.dungeonnowloading.registry.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class DNLFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerItemRenderers();
        registerBlockRenderers();
        registerRenderers();
        registerModelLayers();
        registerParticleFactories();
    }

    private void registerItemRenderers() {
        BuiltinItemRendererRegistry.INSTANCE.register(DNLItems.FAIRKEEPER_CHEST.get(), FairkeeperChestItemRenderer.getInstance()::renderByItem);
    }

    private void registerBlockRenderers() {
        BlockRenderLayerMap.INSTANCE.putBlock(DNLBlocks.CHAOS_SPAWNER_BARRIER_CENTER.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(DNLBlocks.CHAOS_SPAWNER_BARRIER_EDGE.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(DNLBlocks.CHAOS_SPAWNER_BARRIER_VERTEX.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(DNLBlocks.CHAOS_SPAWNER_DIAMOND_EDGE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DNLBlocks.CHAOS_SPAWNER_DIAMOND_VERTEX.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DNLBlocks.CHAOS_SPAWNER_BROKEN_DIAMOND_EDGE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DNLBlocks.CHAOS_SPAWNER_BROKEN_DIAMOND_VERTEX.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DNLBlocks.CHAOS_SPAWNER_EDGE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DNLBlocks.CHAOS_SPAWNER_BROKEN_EDGE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DNLBlocks.SPIKES.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DNLBlocks.MOSS.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(DNLBlocks.FAIRKEEPER_CHEST.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DNLBlocks.FAIRKEEEPER_SPAWNER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(DNLBlocks.ROTATOR_PRESSURE_PLATE.get(), RenderType.cutout());
    }

    private void registerRenderers() {
        // Bosses
        EntityRendererRegistry.register(DNLEntityTypes.CHAOS_SPAWNER.get(), ChaosSpawnerRenderer::new);

        // Monsters
        EntityRendererRegistry.register(DNLEntityTypes.HOLLOW.get(), HollowRenderer::new);
        EntityRendererRegistry.register(DNLEntityTypes.SPAWNER_CARRIER.get(), SpawnerCarrierRenderer::new);
        EntityRendererRegistry.register(DNLEntityTypes.SCUTTLE.get(), ScuttleRenderer::new);

        // Passive
        EntityRendererRegistry.register(DNLEntityTypes.SEALED_CHAOS.get(), SealedChaosRenderer::new);
        EntityRendererRegistry.register(DNLEntityTypes.WHIMPER.get(), WhimperRenderer::new);


        // Projectiles
        EntityRendererRegistry.register(DNLEntityTypes.CHAOS_SPAWNER_PROJECTILE.get(), ChaosSpawnerProjectileRenderer::new);
        EntityRendererRegistry.register(DNLEntityTypes.FLAME_PROJECTILE.get(), ThrownItemRenderer::new);
        EntityRendererRegistry.register(DNLEntityTypes.GREAT_EXPERIENCE_BOTTLE.get(), (context) -> {
            return new ThrownItemRenderer<>(context, 1.25F, false);
        });
        // Misc
        EntityRendererRegistry.register(DNLEntityTypes.SPECIAL_ITEM_ENTITY.get(), SpecialItemEntityRenderer::new);

        // Block Entities
        BlockEntityRenderers.register(DNLBlockEntityTypes.FAIRKEEPER_CHEST.get(), FairkeeperChestBlockRenderer::new);

    }

    private void registerModelLayers() {
        // Bosses
        EntityModelLayerRegistry.registerModelLayer(ChaosSpawnerModel.LAYER_LOCATION, ChaosSpawnerModel::createBodyLayer);

        // Monsters
        EntityModelLayerRegistry.registerModelLayer(HollowModel.LAYER_LOCATION, HollowModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SpawnerCarrierModel.LAYER_LOCATION, SpawnerCarrierModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ScuttleModel.LAYER_LOCATION, ScuttleModel::createBodyLayer);

        // Passive
        EntityModelLayerRegistry.registerModelLayer(SealedChaosModel.LAYER_LOCATION, SealedChaosModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(WhimperModel.LAYER_LOCATION, WhimperModel::createBodyLayer);


        //Projectiles
        EntityModelLayerRegistry.registerModelLayer(ChaosSpawnerProjectileModel.LAYER_LOCATION, ChaosSpawnerProjectileModel::createBodyLayer);

        // Block Entities
        EntityModelLayerRegistry.registerModelLayer(FairkeeperChestModel.LAYER_LOCATION, FairkeeperChestModel::createBodyLayer);
    }

    private static void registerParticleFactories() {
        ParticleFactoryRegistry registry = ParticleFactoryRegistry.getInstance();
        registry.register(DNLParticleTypes.LARGE_FLAME_PARTICLE.get(), LargeFlameParticle.Factory::new);
    }
}
