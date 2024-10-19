package dev.hexnowloading.dungeonnowloading.entity.projectile;

import dev.hexnowloading.dungeonnowloading.registry.DNLEntityTypes;
import dev.hexnowloading.dungeonnowloading.registry.DNLMobEffects;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VertexArrowEntity extends AbstractArrow {

    public static final int MAX_CONNECTIONS = 2;
    public List<UUID> connectedArrows = new ArrayList<>();

    public VertexArrowEntity(EntityType entityType, Level level) {
        super(entityType, level);
    }

    public VertexArrowEntity(Level level, LivingEntity shooter) {
        super(DNLEntityTypes.VERTEX_ARROW_PROJECTILE.get(), shooter, level);
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (entityHitResult.getEntity() instanceof LivingEntity) {
            LivingEntity hitEntity = (LivingEntity) entityHitResult.getEntity();
            hitEntity.addEffect(new MobEffectInstance(DNLMobEffects.VERTEX_TRANSMISSION.get(), 60, 0)); // Apply Vertex Transmission effect for 3 seconds
        }
    }

    private void tryConnectToNearbyArrows() {
        if (!this.inGround) {
            return;
        }

        List<VertexArrowEntity> nearbyArrows = level().getEntitiesOfClass(VertexArrowEntity.class, this.getBoundingBox().inflate(10.0D));
        for (VertexArrowEntity arrow : nearbyArrows) {
            if (arrow != this && arrow.inGround && connectedArrows.size() < MAX_CONNECTIONS && arrow.connectedArrows.size() < MAX_CONNECTIONS) {
                if (!connectedArrows.contains(arrow.getUUID())) {
                    connectWithArrow(arrow);
                }
            }
        }
    }

    public void connectWithArrow(VertexArrowEntity arrow) {
        connectedArrows.add(arrow.getUUID());
        arrow.connectedArrows.add(this.getUUID());
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            tryConnectToNearbyArrows();
            applySlownessEffectToIntersectingEntities();
        }
    }

    private void applySlownessEffectToIntersectingEntities() {
        for (UUID uuid : connectedArrows) {
            Entity entity = ((ServerLevel) level()).getEntity(uuid);
            if (entity instanceof VertexArrowEntity) {
                VertexArrowEntity otherArrow = (VertexArrowEntity) entity;
                AABB lineBox = new AABB(this.position(), otherArrow.position());
                List<LivingEntity> entities = level().getEntitiesOfClass(LivingEntity.class, lineBox);
                for (LivingEntity livingEntity : entities) {
                    if (!livingEntity.equals(this.getOwner()) && livingEntity.hasEffect(DNLMobEffects.VERTEX_TRANSMISSION.get())) {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
                    }
                }
            }
        }
    }
}
