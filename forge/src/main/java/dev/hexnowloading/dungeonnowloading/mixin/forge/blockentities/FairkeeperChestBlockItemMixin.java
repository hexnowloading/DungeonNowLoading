package dev.hexnowloading.dungeonnowloading.mixin.forge.blockentities;

import dev.hexnowloading.dungeonnowloading.block.client.renderer.FairkeeperChestItemRenderer;
import dev.hexnowloading.dungeonnowloading.item.blockitem.FairkeeperChestBlockItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Consumer;

@Mixin(FairkeeperChestBlockItem.class)
public abstract class FairkeeperChestBlockItemMixin extends Item {

    public FairkeeperChestBlockItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private FairkeeperChestItemRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) {
                    renderer = new FairkeeperChestItemRenderer();
                }
                return renderer;
            }
        });
    }
}
