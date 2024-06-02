package dev.hexnowloading.dungeonnowloading.capabilities.fabric;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public interface IFairkeeperChestPositionsCapability extends ComponentV3, AutoSyncedComponent {

    //ResourceLocation ID = new ResourceLocation("dungeonnowloading:fairkeeper_chest_positions");

    List<BlockPos> getList();

    void addBlock(BlockPos blockPos);

    void copyList(List<BlockPos> blockPos);
}