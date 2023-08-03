package dev.hexnowloading.skyisland.world.entity;

import dev.hexnowloading.skyisland.registry.SkyislandEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class WindstoneEntity extends Entity {
    public WindstoneEntity(EntityType<Entity> entityType, Level level) {
        super(entityType, level);
        this.blocksBuilding = true;
    }

    public WindstoneEntity(Level level, double x, double y, double z) {
        this(SkyislandEntityTypes.WINDSTONE.get(), level);
        this.setPos(x, y, z);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }
}
