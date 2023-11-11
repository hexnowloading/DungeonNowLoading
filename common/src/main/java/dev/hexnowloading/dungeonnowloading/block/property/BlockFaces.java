package dev.hexnowloading.dungeonnowloading.block.property;

import net.minecraft.util.StringRepresentable;

public enum BlockFaces implements StringRepresentable {
    UP("up"),
    DOWN("down"),
    NORTH("north"),
    EAST("east"),
    SOUTH("south"),
    WEST("west");

    private final String name;

    private BlockFaces(String string) { this.name = string; }

    public String toString() { return this.name; }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
