package dev.hexnowloading.dungeonnowloading.entity.misc;

import dev.hexnowloading.dungeonnowloading.registry.DNLEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class SpecialItemEntity extends ItemEntity implements TraceableEntity {
    private UUID picker;

    public SpecialItemEntity(EntityType<? extends SpecialItemEntity> entityType, Level level) {
        super(entityType, level);
    }

    public SpecialItemEntity(Level level, double x, double y, double z, ItemStack itemStack) {
        this(level, x, y, z, itemStack, level.random.nextDouble() * 0.2 - 0.1, 0.2, level.random.nextDouble() * 0.2 - 0.1);
    }

    public SpecialItemEntity(Level level, double x, double y, double z, ItemStack itemStack, double i, double j, double k) {
        this(DNLEntityTypes.SPECIAL_ITEM_ENTITY.get(), level);
        this.setPos(x, y, z);
        this.setDeltaMovement(i, j, k);
        this.setItem(itemStack);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource $$0, float $$1) {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        if (this.picker != null) {
            compoundTag.putUUID("Picker", this.picker);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.hasUUID("Picker")) {
            this.picker = compoundTag.getUUID("Picker");
        }
    }

    @Override
    public void playerTouch(Player player) {
        if (player.getUUID().equals(this.picker)) {
            this.getItem().removeTagKey("PickerUUID");
            super.playerTouch(player);
        }
    }


    @Override
    public boolean broadcastToPlayer(ServerPlayer serverPlayer) {
        return serverPlayer.getUUID().equals(this.picker) && super.broadcastToPlayer(serverPlayer);
    }

    public void setPickerUUID(UUID uuid) {
        this.picker = uuid;
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putUUID("PickerUUID", uuid);
        this.getItem().setTag(compoundTag); // Prevents special item entity from merging with an another with a different player uuid.
    }

    public UUID getPickerUUID() {
        return this.picker;
    }
}
