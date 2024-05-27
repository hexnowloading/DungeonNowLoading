package dev.hexnowloading.dungeonnowloading.capability.forge;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.*;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class FairkeeperChestPositionsCapability {

    private List<BlockPos> fairkeeperPosList;

    public List<BlockPos> getList() {
        if (this.fairkeeperPosList == null) {
            this.fairkeeperPosList = new ArrayList<>();
        }
        return this.fairkeeperPosList;
    }

    public void addBlockPos(BlockPos blockPos) {
        if (this.fairkeeperPosList == null) {
            this.fairkeeperPosList = new ArrayList<>();
        }
        if (!this.fairkeeperPosList.contains(blockPos)) { this.fairkeeperPosList.add(blockPos);}
    }

    public void copyList(List<BlockPos> list) {
        this.fairkeeperPosList = list;
    }

    public void copyFrom(FairkeeperChestPositionsCapability source) {
        this.fairkeeperPosList = source.fairkeeperPosList;
    }

    public void saveNBTData(CompoundTag compoundTag) {
        if (this.fairkeeperPosList != null) {
            ListTag listTag = new ListTag();
            this.fairkeeperPosList.forEach(blockPos -> listTag.add(this.newIntList(blockPos.getX(), blockPos.getY(), blockPos.getZ())));
            compoundTag.put("FairkeeperChestPositions", listTag);
        }
    }

    public void loadNBTData(CompoundTag compoundTag) {
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

    private ListTag newIntList(int ... ints) {
        ListTag listTag = new ListTag();
        for (int i : ints) {
            listTag.add(IntTag.valueOf(i));
        }
        return listTag;
    }
}
