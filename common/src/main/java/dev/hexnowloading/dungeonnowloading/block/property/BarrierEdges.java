package dev.hexnowloading.dungeonnowloading.block.property;

import net.minecraft.util.StringRepresentable;

public enum BarrierEdges implements StringRepresentable {
    TOP("top"),
    BOTTOM("bottom"),
    UP("up"),
    RIGHT("right"),
    DOWN("down"),
    LEFT("left");

    private final String name;

    private BarrierEdges(String string) { this.name = string; }

    public String toString() { return this.name; }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
