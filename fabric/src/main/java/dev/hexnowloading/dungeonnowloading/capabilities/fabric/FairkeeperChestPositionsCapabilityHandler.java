package dev.hexnowloading.dungeonnowloading.capabilities.fabric;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

import static org.spongepowered.asm.util.perf.Profiler.setActive;

public class FairkeeperChestPositionsCapabilityHandler implements IFairkeeperChestPositionsCapability, PlayerComponent<FairkeeperChestPositionsCapabilityHandler> {

    private List<BlockPos> fairkeeperPosList;

    public FairkeeperChestPositionsCapabilityHandler() {
        this(new ArrayList<BlockPos>());
    }

    public FairkeeperChestPositionsCapabilityHandler(List<BlockPos> blockPosList) {
        this.fairkeeperPosList = blockPosList;
    }

    @Override
    public List<BlockPos> getList() {
        if (this.fairkeeperPosList == null) {
            this.fairkeeperPosList = new ArrayList<>();
        }
        return this.fairkeeperPosList;
    }

    @Override
    public void addBlock(BlockPos blockPos) {
        if (this.fairkeeperPosList == null) {
            this.fairkeeperPosList = new ArrayList<>();
        }
        if (!this.fairkeeperPosList.contains(blockPos)) { this.fairkeeperPosList.add(blockPos);}
    }

    @Override
    public void copyList(List<BlockPos> list) {
        this.fairkeeperPosList = list;
    }

    @Override
    public void readFromNbt(CompoundTag compoundTag) {
        if (compoundTag.contains("FairkeeperChestPositions", CompoundTag.TAG_LIST)) {
            ListTag listTag = compoundTag.getList("FairkeeperChestPositions", CompoundTag.TAG_LIST);
            if (this.fairkeeperPosList == null) {
                this.fairkeeperPosList = new ArrayList<>();
            }
            for (int a = 0; a < listTag.size(); ++a) {
                this.fairkeeperPosList.add(new BlockPos(listTag.getInt(0), listTag.getInt(1), listTag.getInt(2)));
            }
        }
    }

    @Override
    public void writeToNbt(CompoundTag compoundTag) {
        //if (this.fairkeeperPosList != null) {
            ListTag listTag = new ListTag();
            this.fairkeeperPosList.forEach(blockPos -> listTag.add(this.newIntList(blockPos.getX(), blockPos.getY(), blockPos.getZ())));
            compoundTag.put("FairkeeperChestPositions", listTag);
        //}
    }

    @Override
    public boolean shouldCopyForRespawn(boolean lossless, boolean keepInventory, boolean sameCharacter) {
        return lossless || keepInventory;
    }

    @Override
    public void copyForRespawn(FairkeeperChestPositionsCapabilityHandler original, boolean lossless, boolean keepInventory, boolean sameCharacter) {
        PlayerComponent.super.copyForRespawn(original, lossless, keepInventory, sameCharacter);
        setActive(false);
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    private ListTag newIntList(int ... ints) {
        ListTag listTag = new ListTag();
        for (int i : ints) {
            listTag.add(IntTag.valueOf(i));
        }
        return listTag;
    }
}