package dev.hexnowloading.dungeonnowloading.util;

public enum ArmorTrimMaterial {
    NONE("none"),
    IRON("iron"),
    COPPER("copper"),
    GOLD("gold"),
    LAPIS("lapis"),
    EMERALD("emerald"),
    DIAMOND("diamond"),
    NETHERITE("netherite"),
    REDSTONE("redstone"),
    AMETHYST("amethyst"),
    QUARTZ("quartz");


    public final String material;
    ArmorTrimMaterial(String material) {
        this.material = material;
    }
}
