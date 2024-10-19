package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.platform.Services;
import dev.hexnowloading.dungeonnowloading.potion.VertexTransmissionEffect;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class DNLMobEffects {

    public static final Supplier<MobEffect> VERTEX_TRANSMISSION = registerEffects("vertex_transmission", VertexTransmissionEffect::new);

    public static <T extends MobEffect> Supplier<T> registerEffects(String name, Supplier<T> effectSupplier) {
        return Services.REGISTRY.register(BuiltInRegistries.MOB_EFFECT, name, effectSupplier);
    }

    public static void init() {
    }
}
