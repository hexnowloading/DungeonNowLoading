package dev.hexnowloading.dungeonnowloading.util;

import net.minecraft.nbt.DoubleTag;
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

    public static ListTag newDoubleList(double ... ints) {
        ListTag listTag = new ListTag();
        for (double i : ints) {
            listTag.add(DoubleTag.valueOf(i));
        }
        return listTag;
    }
}
