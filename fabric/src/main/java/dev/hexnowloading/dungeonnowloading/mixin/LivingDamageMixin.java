package dev.hexnowloading.dungeonnowloading.mixin;

import dev.hexnowloading.dungeonnowloading.entity.DNLEntityEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingDamageMixin {

    @Inject(method = "Lnet/minecraft/world/entity/LivingEntity;actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;awardStat(Lnet/minecraft/resources/ResourceLocation;I)V", shift = At.Shift.BY, by = 2))
    private void dungeonnowloading_actuallyHurt(DamageSource damageSource, float f, CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        Entity attackingEntity = damageSource.getEntity();
        if (attackingEntity instanceof LivingEntity livingEntity1) {
            DNLEntityEvents.onLivingDamageEvent(livingEntity1, livingEntity, f);
        }
    }
}
