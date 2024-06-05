package dev.hexnowloading.dungeonnowloading.entity.util;

public enum ArmorTrimPattern {
    NONE("none"),
    COAST("coast"),
    DUNE("dune"),
    EYE("eye"),
    HOST("host"),
    RAISER("raiser"),
    RIB("rib"),
    SENTRY("sentry"),
    SHAPER("shaper"),
    SILENCE("silence"),
    SNOUT("snout"),
    SPIRE("spire"),
    TIDE("tide"),
    VEX("vex"),
    WARD("ward"),
    WAYFINDER("wayfinder"),
    WILD("wild");

    final String pattern;
    ArmorTrimPattern(String pattern) {
        this.pattern = pattern;
    }

}