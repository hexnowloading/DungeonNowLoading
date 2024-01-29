package dev.hexnowloading.dungeonnowloading.block.property;

import net.minecraft.util.StringRepresentable;

public enum BarrierVertexs implements StringRepresentable {
    TOP("top"),
    BOTTOM("bottom"),
    TOP_RIGHT("top_right"),
    TOP_LEFT("top_left"),
    BOTTOM_RIGHT("bottom_right"),
    BOTTOM_LEFT("bottom_left");

    private final String name;

    private BarrierVertexs(String string) { this.name = string; }

    public String toString() { return this.name; }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
