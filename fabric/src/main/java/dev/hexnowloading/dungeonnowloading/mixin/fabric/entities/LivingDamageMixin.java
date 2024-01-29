package dev.hexnowloading.dungeonnowloading.mixin.fabric.entities;

import dev.hexnowloading.dungeonnowloading.entity.DNLEntityEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(LivingEntity.class)
public class LivingDamageMixin {
    private float f;

    //float newDamage;

    /*@Inject(method = "Lnet/minecraft/world/entity/LivingEntity;actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;awardStat(Lnet/minecraft/resources/ResourceLocation;I)V", shift = At.Shift.BY, by = 2))
    private void dungeonnowloading_actuallyHurt(DamageSource damageSource, float f, CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        Entity attackingEntity = damageSource.getEntity();
        if (attackingEntity instanceof LivingEntity livingEntity1) {
            this.newDamage = DNLEntityEvents.onLivingDamageEvent(livingEntity1, livingEntity, f);
        }
    }*/

    /*@ModifyVariable(method = "Lnet/minecraft/world/entity/LivingEntity;actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;awardStat(Lnet/minecraft/resources/ResourceLocation;I)V", shift = At.Shift.BY, by = 2), ordinal = 1)
    private float dungeonnowloading_actuallyHurt(float f, DamageSource damageSource) {
        LivingEntity target = (LivingEntity) (Object) this;
        Entity attackerEntity = damageSource.getEntity();
        float totalDamage = f;
        if (attackerEntity instanceof LivingEntity attacker) {
            totalDamage = DNLEntityEvents.onLivingDamageEvent(attacker, target, f);
        }
        return 100;
    }*/

    @ModifyVariable(method = "Lnet/minecraft/world/entity/LivingEntity;actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/CombatTracker;recordDamage(Lnet/minecraft/world/damagesource/DamageSource;F)V", shift = At.Shift.BEFORE), ordinal = 0)
    private float dungeonnowloading_onLivingDamage(float f, DamageSource damageSource) {
        LivingEntity target = (LivingEntity) (Object) this;
        Entity attackerEntity = damageSource.getEntity();
        float totalDamage = f;
        if (attackerEntity instanceof LivingEntity attacker) {
            totalDamage = DNLEntityEvents.onLivingDamageEvent(attacker, target, f);
        }
        return totalDamage;
    }

    @ModifyVariable(method = "Lnet/minecraft/world/entity/LivingEntity;actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInvulnerableTo(Lnet/minecraft/world/damagesource/DamageSource;)Z", shift = At.Shift.AFTER), ordinal = 0)
    private float dungeonnowloading_onLivingHurt(float f, DamageSource damageSource) {
        LivingEntity target = (LivingEntity) (Object) this;
        Entity attackerEntity = damageSource.getEntity();
        if (attackerEntity instanceof LivingEntity attacker) {
            f = DNLEntityEvents.onLivingHurtEvent(attacker, target, f);
        }
        return f;
    }
}


