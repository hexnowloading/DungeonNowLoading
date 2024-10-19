package dev.hexnowloading.dungeonnowloading.potion;

import dev.hexnowloading.dungeonnowloading.entity.projectile.VertexArrowEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class VertexTransmissionEffect extends MobEffect {
    public VertexTransmissionEffect() {
        super(MobEffectCategory.HARMFUL, 0x5e35b1); // Set the effect type and color
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // Custom behavior for the effect can be added here if needed
        // In this case, ensure the entity can be connected to a Vertex Arrow
        if (entity.level() instanceof ServerLevel) {
            List<VertexArrowEntity> nearbyArrows = entity.level().getEntitiesOfClass(VertexArrowEntity.class, entity.getBoundingBox().inflate(10.0D));
            for (VertexArrowEntity arrow : nearbyArrows) {
                if (arrow.onGround() && arrow.connectedArrows.size() < VertexArrowEntity.MAX_CONNECTIONS && !arrow.connectedArrows.contains(entity.getUUID())) {
                    arrow.connectedArrows.add(entity.getUUID());
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
