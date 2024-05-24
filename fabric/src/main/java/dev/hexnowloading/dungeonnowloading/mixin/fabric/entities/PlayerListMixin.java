package dev.hexnowloading.dungeonnowloading.mixin.fabric.entities;

import dev.hexnowloading.dungeonnowloading.DungeonNowLoading;
import dev.hexnowloading.dungeonnowloading.capabilities.CapabilityList;
import dev.hexnowloading.dungeonnowloading.capabilities.player.IPlayerCapability;
import dev.hexnowloading.dungeonnowloading.capabilities.player .PlayerCapabilityHandler;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.intellij.lang.annotations.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerListMixin {
    @Inject(method = "placeNewPlayer", at = @At("TAIL")) // fabric event fires too early
    private void dungeonnowloading_onLoginTail(Connection netManager, ServerPlayer player, CallbackInfo ci) {
        //player.getComponent(CapabilityList.PLAYER_CAP).setPoint(1);
        CompoundTag compoundTag = player.getCustomData();
        CompoundTag playerData = compoundTag.getCompound("test_point");
        playerData.putInt("test_point", 11);
        compoundTag.put("test_point", playerData);
        System.out.println(CapabilityList.PLAYER_CAP.maybeGet(player).map(IPlayerCapability::getPoint).orElse(0));
    }
}
