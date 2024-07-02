package dev.hexnowloading.dungeonnowloading.server;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.capability.forge.FairkeeperChestPositionsCapability;
import dev.hexnowloading.dungeonnowloading.capability.forge.FairkeeperChestPositionsCapabilityProvider;
import dev.hexnowloading.dungeonnowloading.entity.DNLEntityEvents;
import dev.hexnowloading.dungeonnowloading.entity.monster.HollowEntity;
import dev.hexnowloading.dungeonnowloading.entity.monster.SpawnerCarrierEntity;
import dev.hexnowloading.dungeonnowloading.registry.DNLEntityTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonNowLoading.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DNLForgeEntityEvents {

    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        for (EntityType<? extends LivingEntity> type : DNLEntityTypes.getAllAttributes().keySet()) {
            event.put(type, DNLEntityTypes.getAllAttributes().get(type));
        }
    }

    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(DNLEntityTypes.HOLLOW.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HollowEntity::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(DNLEntityTypes.SPAWNER_CARRIER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnerCarrierEntity::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
    }

    public static void onLivingDamageEvent(LivingDamageEvent event) {
        Entity attackingEntity = event.getSource().getEntity();
        LivingEntity hurtedEntity = event.getEntity();
        float damage = event.getAmount();
        if (attackingEntity instanceof LivingEntity livingEntity) {
            event.setAmount(DNLEntityEvents.onLivingDamageEvent(livingEntity, hurtedEntity, damage));
        }
    }

    public static void onLivingHurtEvent(LivingHurtEvent event) {
        Entity attacker = event.getSource().getEntity();
        LivingEntity target = event.getEntity();
        float damage = event.getAmount();
        if (attacker instanceof LivingEntity attackerEntity) {
            event.setAmount(DNLEntityEvents.onLivingHurtEvent(attackerEntity, target, damage));
        }
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            /*if(!event.getObject().getCapability(DNLForgePlayerPointProvider.PLAYER_TEST_POINT).isPresent()) {
                event.addCapability(new ResourceLocation(DungeonNowLoading.MOD_ID, "properties"), new DNLForgePlayerPointProvider());
            }*/
            if(!event.getObject().getCapability(FairkeeperChestPositionsCapabilityProvider.FAIRKEEPER_CHEST_POSITIONS).isPresent()) {
                event.addCapability(new ResourceLocation(DungeonNowLoading.MOD_ID, "fairkeeper_chest_positions"), new FairkeeperChestPositionsCapabilityProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            /*event.getOriginal().getCapability(DNLForgePlayerPointProvider.PLAYER_TEST_POINT).ifPresent(oldStore -> {
                event.getOriginal().getCapability(DNLForgePlayerPointProvider.PLAYER_TEST_POINT).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });*/
            event.getOriginal().getCapability(FairkeeperChestPositionsCapabilityProvider.FAIRKEEPER_CHEST_POSITIONS).ifPresent(oldStore -> {
                event.getOriginal().getCapability(FairkeeperChestPositionsCapabilityProvider.FAIRKEEPER_CHEST_POSITIONS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        //event.register(DNLForgePlayerPoint.class);
        event.register(FairkeeperChestPositionsCapability.class);
    }
}
