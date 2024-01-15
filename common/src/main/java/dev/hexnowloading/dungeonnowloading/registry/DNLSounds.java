package dev.hexnowloading.dungeonnowloading.registry;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.registration.RegistrationProvider;
import dev.hexnowloading.dungeonnowloading.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class DNLSounds {
    public static final RegistrationProvider<SoundEvent> SOUND_EVENTS = RegistrationProvider.get(Registries.SOUND_EVENT, DungeonNowLoading.MOD_ID);

    public static final RegistryObject<SoundEvent> HOLLOW_AMBIENT = registerSoundEvent("hollow_ambient");
    public static final RegistryObject<SoundEvent> HOLLOW_HURT = registerSoundEvent("hollow_hurt");
    public static final RegistryObject<SoundEvent> HOLLOW_DEATH = registerSoundEvent("hollow_death");
    public static final RegistryObject<SoundEvent> WHIMPER_AMBIENT = registerSoundEvent("whimper_ambient");
    public static final RegistryObject<SoundEvent> WHIMPER_HURT = registerSoundEvent("whimper_hurt");
    public static final RegistryObject<SoundEvent> WHIMPER_DEATH = registerSoundEvent("whimper_death");

    private static RegistryObject<SoundEvent> registerSoundEvent(String string) {
        return SOUND_EVENTS.register(string, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DungeonNowLoading.MOD_ID, string)));
    }

    public static void init() {}

}
