package dev.hexnowloading.dungeonnowloading.entity.misc;

import dev.hexnowloading.dungeonnowloading.registry.DNLEntityTypes;
import dev.hexnowloading.dungeonnowloading.registry.DNLItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class GreatExperienceBottleEntity extends ThrowableItemProjectile {

    private static final EntityDataAccessor<Integer> EXPERIENCE_AMOUNT = SynchedEntityData.defineId(GreatExperienceBottleEntity.class, EntityDataSerializers.INT);

    public GreatExperienceBottleEntity(EntityType entityType, Level level) {
        super(entityType, level);
    }

    public GreatExperienceBottleEntity(Level level, LivingEntity thrower) {
        super(DNLEntityTypes.GREAT_EXPERIENCE_BOTTLE.get(), thrower, level);
    }

    public GreatExperienceBottleEntity(Level level, double x, double y, double z) {
        super(DNLEntityTypes.GREAT_EXPERIENCE_BOTTLE.get(), x, y, z, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(EXPERIENCE_AMOUNT, 100);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setExperienceAmount(compoundTag.getInt("StoredExperience"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("StoredExperience", this.getExperienceAmount());
    }

    @Override
    protected Item getDefaultItem() {
        return DNLItems.GREAT_EXPERIENCE_BOTTLE.get();
    }

    @Override
    protected float getGravity() {
        return 0.07F;
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (this.level() instanceof ServerLevel) {
            this.level().levelEvent(2002, this.blockPosition(), PotionUtils.getColor(Potions.LUCK));
            ExperienceOrb.award((ServerLevel)this.level(), this.position(), this.getExperienceAmount());
            this.discard();
        }
    }

    public int getExperienceAmount() {
        return this.entityData.get(EXPERIENCE_AMOUNT);
    }

    public void setExperienceAmount(int experienceAmount) {
        this.entityData.set(EXPERIENCE_AMOUNT, experienceAmount);
    }
}
