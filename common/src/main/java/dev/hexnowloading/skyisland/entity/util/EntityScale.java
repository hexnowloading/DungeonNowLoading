package dev.hexnowloading.skyisland.entity.util;

import dev.hexnowloading.skyisland.config.BossConfig;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.UUID;

import static net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH;

public class EntityScale {

    private static final UUID SCALED_HEALTH_MODIFIER_UUID = UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479");
    private static final double bossHealthScale = BossConfig.BOSS_HEALTH_SCALE.get();
    private static final double bossAttackDamageScale = BossConfig.BOSS_DAMAGE_SCALE.get();
    private static final double multiplayerBossHealthScale = BossConfig.TOGGLE_MULTIPLAYER_SCALING.get() ? BossConfig.MULTIPLAYER_BOSS_HEALTH_SCALE.get() : 0;

    public static void scaleHealth(LivingEntity entityType, int playerCount) {
        double healthMultiplier = bossHealthScale * (1 + (playerCount - 1) * multiplayerBossHealthScale) - 1;
        AttributeModifier SCALED_HEALTH_MODIFIER = new AttributeModifier(SCALED_HEALTH_MODIFIER_UUID, "Scaled health", healthMultiplier, AttributeModifier.Operation.MULTIPLY_BASE);
        Objects.requireNonNull(entityType.getAttribute(Attributes.MAX_HEALTH)).removeModifier(SCALED_HEALTH_MODIFIER);
        Objects.requireNonNull(entityType.getAttribute(Attributes.MAX_HEALTH)).addPermanentModifier(SCALED_HEALTH_MODIFIER);
        entityType.setHealth(entityType.getMaxHealth());
    }
}
