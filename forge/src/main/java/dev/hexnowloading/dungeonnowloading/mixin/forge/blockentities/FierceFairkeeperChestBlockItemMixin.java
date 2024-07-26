package dev.hexnowloading.dungeonnowloading.mixin.forge.blockentities;

import dev.hexnowloading.dungeonnowloading.block.client.renderer.FierceFairkeeperChestItemRenderer;
import dev.hexnowloading.dungeonnowloading.item.blockitem.FierceFairkeeperChestBlockItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Consumer;

@Mixin(FierceFairkeeperChestBlockItem.class)
public abstract class FierceFairkeeperChestBlockItemMixin extends Item {

    public FierceFairkeeperChestBlockItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private FierceFairkeeperChestItemRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) {
                    renderer = new FierceFairkeeperChestItemRenderer();
                }
                return renderer;
            }
        });
    }
}
