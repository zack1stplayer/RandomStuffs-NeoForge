package net.zack1stplayer.randomstuffs.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.zack1stplayer.randomstuffs.block.ModBlocks;
import net.zack1stplayer.randomstuffs.component.ModDataComponents;

import java.util.List;
import java.util.Map;

public class ChiselItem extends Item {
    private static final Map<Block, Block> CHISEL_MAP =
            Map.of(
                    Blocks.STONE, Blocks.STONE_BRICKS,
                    ModBlocks.TEST_BLOCK.get(), Blocks.GRASS_BLOCK
            );

    public ChiselItem(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        if(!level.isClientSide() && player.isShiftKeyDown()) {
            itemStack.set(ModDataComponents.COORDINATES, null);
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();

        if(CHISEL_MAP.containsKey(clickedBlock) && !level.isClientSide()) {
            level.setBlockAndUpdate(context.getClickedPos(), CHISEL_MAP.get(clickedBlock).defaultBlockState());

            context.getItemInHand().hurtAndBreak(1, ((ServerLevel) level), context.getPlayer(),
                    item -> context.getPlayer().onEquippedItemBroken(item, EquipmentSlot.MAINHAND));

            level.playSound(null, context.getClickedPos(), SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS);

            context.getItemInHand().set(ModDataComponents.COORDINATES, context.getClickedPos());
        }

        return super.useOn(context);
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(stack.get(ModDataComponents.COORDINATES) != null) {
            tooltipComponents.add(Component.literal("Last block changed at: " + stack.get(ModDataComponents.COORDINATES)));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
