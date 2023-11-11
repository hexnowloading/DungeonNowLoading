package dev.hexnowloading.dungeonnowloading.block.property;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum AllSides implements StringRepresentable {
    TOP("top"),
    BOTTOM("bottom"),
    VERTICAL("vertical");

    private final String name;

    private AllSides(String string) { this.name = string; }

    public String toString() { return this.name; }

    public @NotNull String getSerializedName() { return this.name; }
}
