package net.zack1stplayer.randomstuffs.mixin;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.fluid.FluidHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.zack1stplayer.randomstuffs.item.custom.PotionFlaskItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidHelper.class)
public class FluidHelperMixin {

//    @Inject(at = @At("HEAD"), method = "tryEmptyItemIntoBE", cancellable = true)
//    private static void randomstuffs$tryEmptyItemIntoBE(Level worldIn, Player player, InteractionHand handIn, ItemStack heldItem, SmartBlockEntity be, CallbackInfoReturnable<Boolean> cir) {
//        if (!player.isShiftKeyDown() && heldItem.getItem() instanceof PotionFlaskItem) {
//            cir.setReturnValue(false);
//            return; // Just in case
//        }
//    }
}
