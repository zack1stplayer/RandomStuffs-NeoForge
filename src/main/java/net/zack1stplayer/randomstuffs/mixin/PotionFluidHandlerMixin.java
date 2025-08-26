package net.zack1stplayer.randomstuffs.mixin;

import com.simibubi.create.AllDataComponents;
import com.simibubi.create.content.fluids.potion.PotionFluid;
import com.simibubi.create.content.fluids.potion.PotionFluidHandler;
import net.createmod.catnip.data.Pair;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.fluids.FluidStack;
import net.zack1stplayer.randomstuffs.item.ModItems;
import net.zack1stplayer.randomstuffs.item.custom.FlaskItem;
import net.zack1stplayer.randomstuffs.item.custom.PotionFlaskItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PotionFluidHandler.class)
public class PotionFluidHandlerMixin {
//    @Shadow
//    public static FluidStack getFluidFromPotionItem(ItemStack stack) {
//        return null;
//    }
//    @Shadow
//    public static ItemLike itemFromBottleType(PotionFluid.BottleType type) {
//        return null;
//    }
//
//    /**
//     * @author randomstuffs
//     * @reason custom potion item compatibility
//     */
//    @Overwrite
//    public static Pair<FluidStack, ItemStack> emptyPotion(ItemStack stack, boolean simulate) {
//        FluidStack fluid = getFluidFromPotionItem(stack);
//        if(stack.getItem() instanceof PotionFlaskItem && !simulate) {
//            if (PotionFlaskItem.decrementFlaskCharges(stack) <= 0) {
//                stack.shrink(1);
//                return Pair.of(fluid, new ItemStack(ModItems.GLASS_FLASK.get()));
//            } else {
//                ItemStack copyStack = stack.copy();
//                stack.shrink(1);
//                return Pair.of(fluid, copyStack);
//            }
//        } else if (stack.getItem() instanceof PotionFlaskItem) {
//            return Pair.of(fluid, stack);
//        }
//        if (!simulate)
//            stack.shrink(1);
//        return Pair.of(fluid, new ItemStack(Items.GLASS_BOTTLE));
//    }
//
//    /**
//     * @author randomstuffs
//     * @reason custom potion item compatibility
//     */
//    @Overwrite
//    public static ItemStack fillBottle(ItemStack stack, FluidStack availableFluid) {
//        ItemStack potionStack;
//        if (stack.getItem() instanceof FlaskItem) {
//            potionStack = ModItems.POTION_FLASK.get().getDefaultInstance();
//
//        } else {
//            potionStack = new ItemStack(itemFromBottleType(availableFluid.getOrDefault(AllDataComponents.POTION_FLUID_BOTTLE_TYPE, PotionFluid.BottleType.REGULAR)));
//        }
//        potionStack.set(DataComponents.POTION_CONTENTS, availableFluid.get(DataComponents.POTION_CONTENTS));
//        return potionStack;
//    }
}
