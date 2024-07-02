package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class DNLSounds {
    public static final Supplier<SoundEvent> CHAOS_SPAWNER_LAUGHTER = registerSoundEvent("chaos_spawner_laughter");
    public static final Supplier<SoundEvent> CHAOS_SPAWNER_CHAIN_BREAK = registerSoundEvent("chaos_spawner_chain_break");
    public static final Supplier<SoundEvent> CHAOS_SPAWNER_HURT = registerSoundEvent("chaos_spawner_hurt");
    public static final Supplier<SoundEvent> CHAOS_SPAWNER_DEATH = registerSoundEvent("chaos_spawner_death");
    public static final Supplier<SoundEvent> HOLLOW_AMBIENT = registerSoundEvent("hollow_ambient");
    public static final Supplier<SoundEvent> HOLLOW_HURT = registerSoundEvent("hollow_hurt");
    public static final Supplier<SoundEvent> HOLLOW_DEATH = registerSoundEvent("hollow_death");
    public static final Supplier<SoundEvent> WHIMPER_AMBIENT = registerSoundEvent("whimper_ambient");
    public static final Supplier<SoundEvent> WHIMPER_HURT = registerSoundEvent("whimper_hurt");
    public static final Supplier<SoundEvent> WHIMPER_DEATH = registerSoundEvent("whimper_death");

    private static <T extends SoundEvent> Supplier<SoundEvent> registerSoundEvent(String string) {
        return Services.REGISTRY.register(BuiltInRegistries.SOUND_EVENT, string, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DungeonNowLoading.MOD_ID, string)));
    }

    public static void init() {}

}
