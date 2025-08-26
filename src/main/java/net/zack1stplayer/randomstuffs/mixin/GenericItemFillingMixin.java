package net.zack1stplayer.randomstuffs.mixin;

import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.fluids.potion.PotionFluidHandler;
import com.simibubi.create.content.fluids.transfer.GenericItemFilling;
import com.simibubi.create.foundation.fluid.FluidHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.zack1stplayer.randomstuffs.item.custom.FlaskItem;
import net.zack1stplayer.randomstuffs.item.custom.PotionFlaskItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GenericItemFilling.class)
public class GenericItemFillingMixin {

//    @Inject(at = @At("HEAD"), method = "canItemBeFilled", cancellable = true)
//    private static void randomstuffs$canItemBeFilled(Level world, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
//        if (stack.getItem() instanceof FlaskItem) {
//            cir.setReturnValue(true);
//            return; // Just in case
//        }
//        if (stack.getItem() instanceof PotionFlaskItem && PotionFlaskItem.flaskHasRoom(stack)) {
//            cir.setReturnValue(true);
//            return; // Just in case
//        }
//    }
//
//    @Inject(at = @At("HEAD"), method = "fillItem", cancellable = true)
//    private static void randomstuffs$fillItem(Level world, int requiredAmount, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<ItemStack> cir) {
//        FluidStack toFill = availableFluid.copy();
//        toFill.setAmount(requiredAmount);
//
//        if ((stack.getItem() instanceof PotionFlaskItem) && canFillPotionFlaskInternally(toFill, stack)) {
//            ItemStack fillBottle;
//            PotionFlaskItem.incrementFlaskCharges(stack);
//            fillBottle = stack.copy();
//            stack.shrink(1);
//            availableFluid.shrink(requiredAmount);
//            cir.setReturnValue(fillBottle);
//            return; // Just in case
//        }
//
//        if ((stack.getItem() instanceof FlaskItem) && canFillFlaskInternally(toFill)) {
//            ItemStack fillBottle;
//            fillBottle = PotionFluidHandler.fillBottle(stack, availableFluid);
//            stack.shrink(1);
//            availableFluid.shrink(requiredAmount);
//            cir.setReturnValue(fillBottle);
//            return; // Just in case
//        }
//    }
//
//    @Inject(at = @At("HEAD"), method = "getRequiredAmountForItem", cancellable = true)
//    private static void randomstuffs$getRequiredAmountForItem(Level world, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<Integer> cir) {
//        if ((stack.getItem() instanceof FlaskItem) && canFillFlaskInternally(availableFluid)) {
//            cir.setReturnValue(PotionFluidHandler.getRequiredAmountForFilledBottle(stack, availableFluid));
//            return; // Just in case
//        }
//        if ((stack.getItem() instanceof PotionFlaskItem) && canFillPotionFlaskInternally(availableFluid, stack)) {
//            cir.setReturnValue(PotionFluidHandler.getRequiredAmountForFilledBottle(stack, availableFluid));
//            return; // Just in case
//        }
//    }
//
//    private static boolean canFillPotionFlaskInternally(FluidStack availableFluid, ItemStack stack) {
//        Fluid fluid = availableFluid.getFluid();
//        Fluid flaskFluid = PotionFluidHandler.getFluidFromPotionItem(stack).getFluid();
//        if (fluid.isSame(flaskFluid)) {
//            if (fluid.isSame(Fluids.WATER)) {
//                return true;
//            }
//            if (fluid.isSame(AllFluids.POTION.get())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private static boolean canFillFlaskInternally(FluidStack availableFluid) {
//        Fluid fluid = availableFluid.getFluid();
//        if (fluid.isSame(Fluids.WATER)) {
//            return true;
//        }
//        if (fluid.isSame(AllFluids.POTION.get())) {
//            return true;
//        }
//        return false;
//    }
}
