package dev.hexnowloading.dungeonnowloading.util;

import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;

public class NbtHelper {
    public static ListTag newIntList(int ... ints) {
        ListTag listTag = new ListTag();
        for (int i : ints) {
            listTag.add(IntTag.valueOf(i));
        }
        return listTag;
    }
}
