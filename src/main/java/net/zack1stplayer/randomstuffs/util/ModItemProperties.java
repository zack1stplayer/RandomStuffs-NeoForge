package net.zack1stplayer.randomstuffs.util;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.zack1stplayer.randomstuffs.RandomStuffs;
import net.zack1stplayer.randomstuffs.component.ModDataComponents;
import net.zack1stplayer.randomstuffs.item.ModItems;
import net.zack1stplayer.randomstuffs.item.custom.PotionFlaskItem;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        ItemProperties.register(ModItems.CHISEL.get(), ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID, "used"),
                (itemStack, clientLevel, livingEntity, i) -> itemStack.get(ModDataComponents.COORDINATES) != null ? 1f : 0f);

        ItemProperties.register(ModItems.POTION_FLASK.get(), ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID, "empty"),
                (itemStack, clientLevel, livingEntity, i) -> PotionFlaskItem.getFluidHandler(itemStack).getFluidInTank(0).isEmpty() ? 1f : 0f);

//        ItemProperties.register(ModItems.POTION_FLASK.get(), ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID, "charges"),
//                (itemStack, clientLevel, livingEntity, i) ->
//                        itemStack.get(ModDataComponents.FLASK_CHARGES) != null ? itemStack.get(ModDataComponents.FLASK_CHARGES).intValue() : 0);
    }
}
