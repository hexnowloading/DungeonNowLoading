package dev.hexnowloading.dungeonnowloading.capabilities.player;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public interface IPlayerCapability extends ComponentV3 {

    ResourceLocation ID = DungeonNowLoading.prefix("test_point");

    void setPoint(int amount);

    int getPoint();
}
