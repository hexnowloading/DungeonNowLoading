package dev.hexnowloading.dungeonnowloading.block.property;

import net.minecraft.util.StringRepresentable;

public enum TripleBlock implements StringRepresentable {

    UPPER("upper"),
    MIDDLE("middle"),
    LOWER("lower");

    private final String name;

    private TripleBlock(String string) { this.name = string; }

    public String toString() { return this.name; }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
