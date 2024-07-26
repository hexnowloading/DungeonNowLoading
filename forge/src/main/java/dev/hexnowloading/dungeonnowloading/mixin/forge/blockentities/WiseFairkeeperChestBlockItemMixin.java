package dev.hexnowloading.dungeonnowloading.mixin.forge.blockentities;

import dev.hexnowloading.dungeonnowloading.block.client.renderer.WiseFairkeeperChestItemRenderer;
import dev.hexnowloading.dungeonnowloading.item.blockitem.WiseFairkeeperChestBlockItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Consumer;

@Mixin(WiseFairkeeperChestBlockItem.class)
public abstract class WiseFairkeeperChestBlockItemMixin extends Item {

    public WiseFairkeeperChestBlockItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private WiseFairkeeperChestItemRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) {
                    renderer = new WiseFairkeeperChestItemRenderer();
                }
                return renderer;
            }
        });
    }
}
