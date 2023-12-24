package dev.hexnowloading.dungeonnowloading.item;

import dev.hexnowloading.dungeonnowloading.config.GeneralConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SwordOfSparkingSoul extends SwordItem {

    public SwordOfSparkingSoul(Tier $$0, int $$1, float $$2, Properties $$3) {
        super($$0, $$1, $$2, $$3);
    }



    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
        boolean result  = super.hurtEnemy(itemStack, target, attacker);
        if (result) {
            if (!target.level().isClientSide) {
                if (attacker.getHealth() > 1) {
                    attacker.hurt(attacker.damageSources().generic(), 1.0F);
                }
            }
        }
        return result;
    }

    public static float soulDispersionEffect(LivingEntity attacker, LivingEntity target, float damage) {
        return attacker.getHealth() > 1 ? damage + 3.0F : damage;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, components, tooltipFlag);
        if (GeneralConfig.TOGGLE_HELPFUL_ITEM_TOOLTIP.get()) {
            components.add(Component.translatable("item.dungeonnowloading.sword_of_sparking_soul.tooltip.ability_name").withStyle(ChatFormatting.GRAY));
            components.add(Component.translatable("item.dungeonnowloading.sword_of_sparking_soul.tooltip.ability_description").withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
