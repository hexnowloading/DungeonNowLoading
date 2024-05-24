package dev.hexnowloading.dungeonnowloading.capability;

import net.minecraft.nbt.CompoundTag;

public class DNLForgePlayerPoint {
    private int testPoint;

    private final int MIN_TEST_POINT = 0;
    private final int MAX_TEST_POINT = 10;

    public int getPoint() {
        return this.testPoint;
    }

    public void addTest(int add) {
        this.testPoint = Math.min(testPoint + add, MAX_TEST_POINT);
    }

    public void setPoint(int amount) {
        this.testPoint = amount;
    }

    public void subTest(int sub) {
        this.testPoint = Math.max(testPoint - sub, MIN_TEST_POINT);
    }

    public void copyFrom(DNLForgePlayerPoint source) {
        this.testPoint = source.testPoint;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("testPoint", testPoint);
    }

    public void loadNBTData(CompoundTag nbt) {
        testPoint = nbt.getInt("testPoint");
    }
}
