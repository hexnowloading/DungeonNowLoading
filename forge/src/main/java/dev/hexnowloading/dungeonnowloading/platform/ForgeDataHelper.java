package dev.hexnowloading.dungeonnowloading.platform;

import dev.hexnowloading.dungeonnowloading.capability.DNLForgePlayerPoint;
import dev.hexnowloading.dungeonnowloading.capability.DNLForgePlayerPointProvider;
import dev.hexnowloading.dungeonnowloading.platform.services.DataHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ForgeDataHelper implements DataHelper {
    @Override
    public void setPoint(Player player, int amount) {
        player.getCapability(DNLForgePlayerPointProvider.PLAYER_TEST_POINT).ifPresent(point -> {
            point.setPoint(10);
        });
    }

    @Override
    public int getPoint(Player player) {
        /*DNLForgePlayerPoint capPlayer = player.getCapability(DNLForgePlayerPointProvider.PLAYER_TEST_POINT).orElse(new DNLForgePlayerPoint());
        return capPlayer.getPoint();*/
        return player.getCapability(DNLForgePlayerPointProvider.PLAYER_TEST_POINT).map(DNLForgePlayerPoint::getPoint).orElse(0);
    }
}
