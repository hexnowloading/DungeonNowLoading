package dev.hexnowloading.dungeonnowloading.item;

import dev.hexnowloading.dungeonnowloading.entity.misc.GreatExperienceBottleEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GreatExperienceBottleItem extends Item {

    private final int experienceLevelAmount;

    public GreatExperienceBottleItem(Properties properties, int experienceLevelAmount) {
        super(properties);
        this.experienceLevelAmount = experienceLevelAmount;
    }

    @Override
    public boolean isFoil(ItemStack item) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_BOTTLE_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!level.isClientSide) {
            /*int experience = experienceLevelAmount;
            CompoundTag compoundTag = itemStack.getOrCreateTag().getCompound("StoredExperience");
            compoundTag.putInt("StoredExperience", 2000);
            if (compoundTag.contains("StoredExperience", CompoundTag.TAG_INT)) {
                experience = compoundTag.getInt("StoredExperience");
            }*/
            GreatExperienceBottleEntity thrownExperienceBottle = new GreatExperienceBottleEntity(level, player);
            thrownExperienceBottle.setItem(itemStack);
            thrownExperienceBottle.setExperienceAmount(experienceLevelAmount);
            thrownExperienceBottle.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.7F, 1.0F);
            level.addFreshEntity(thrownExperienceBottle);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, components, tooltipFlag);
        components.add(Component.translatable("item.dungeonnowloading.great_experience_bottle.tooltip", this.experienceLevelAmount).withStyle(ChatFormatting.GRAY));
    }
}
