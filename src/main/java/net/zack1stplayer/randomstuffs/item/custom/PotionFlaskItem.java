package net.zack1stplayer.randomstuffs.item.custom;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.zack1stplayer.randomstuffs.component.ModDataComponents;
import net.zack1stplayer.randomstuffs.item.ModItems;

import java.util.List;
import java.util.Objects;

public class PotionFlaskItem extends PotionItem {
    private static final int MAX_FLASK_CHARGES = 8;

    public PotionFlaskItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack itemstack = super.getDefaultInstance();
        itemstack.set(ModDataComponents.FLASK_CHARGES.get(), 1);
        return itemstack;
    }

//    @Override
//    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
//        Player player = entityLiving instanceof Player ? (Player)entityLiving : null;
//        if (player instanceof ServerPlayer) {
//            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, stack);
//        }
//
//        if (!level.isClientSide) {
//            PotionContents potioncontents = (PotionContents)stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
//            potioncontents.forEachEffect((effectInstance) -> {
//                if (((MobEffect)effectInstance.getEffect().value()).isInstantenous()) {
//                    ((MobEffect)effectInstance.getEffect().value()).applyInstantenousEffect(player, player, entityLiving, effectInstance.getAmplifier(), (double)1.0F);
//                } else {
//                    entityLiving.addEffect(effectInstance);
//                }
//
//            });
//        }
//
//        if (player != null && !player.hasInfiniteMaterials()) {
//            player.awardStat(Stats.ITEM_USED.get(this));
//            if(decrementFlaskCharges(stack) <= 0) {
//                stack.consume(1, player);
//                return new ItemStack(ModItems.GLASS_FLASK.get());
//            }
//        }
//
//        if (player == null || !player.hasInfiniteMaterials()) {
//            if (stack.isEmpty()) {
//                return new ItemStack(ModItems.GLASS_FLASK.get());
//            }
//        }
//
//        entityLiving.gameEvent(GameEvent.DRINK);
//        return stack;
//    }
//
//    @Override
//    public InteractionResult useOn(UseOnContext context) {
//        Level level = context.getLevel();
//        BlockPos blockpos = context.getClickedPos();
//        Player player = context.getPlayer();
//        ItemStack itemstack = context.getItemInHand();
//        PotionContents potioncontents = (PotionContents)itemstack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
//        BlockState blockstate = level.getBlockState(blockpos);
//        if (context.getClickedFace() != Direction.DOWN && blockstate.is(BlockTags.CONVERTABLE_TO_MUD) && potioncontents.is(Potions.WATER)) {
//            level.playSound((Player)null, blockpos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 1.0F, 1.0F);
//
//            if (!player.hasInfiniteMaterials()) {
//                if (decrementFlaskCharges(itemstack) <= 0) {
//                    player.setItemInHand(context.getHand(), ItemUtils.createFilledResult(itemstack, player, new ItemStack(ModItems.GLASS_FLASK.get())));
//                }
//            }
//            player.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
//            if (!level.isClientSide) {
//                ServerLevel serverlevel = (ServerLevel)level;
//
//                for(int i = 0; i < 5; ++i) {
//                    serverlevel.sendParticles(ParticleTypes.SPLASH, (double)blockpos.getX() + level.random.nextDouble(), (double)(blockpos.getY() + 1), (double)blockpos.getZ() + level.random.nextDouble(), 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)1.0F);
//                }
//            }
//
//            level.playSound((Player)null, blockpos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
//            level.gameEvent((Entity)null, GameEvent.FLUID_PLACE, blockpos);
//            level.setBlockAndUpdate(blockpos, Blocks.MUD.defaultBlockState());
//            return InteractionResult.sidedSuccess(level.isClientSide);
////        } else if (context.getClickedFace() != Direction.DOWN && blockstate.is()) { //TODO
//
//        } else {
//            return InteractionResult.PASS;
//        }
//    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        PotionContents potioncontents = stack.get(DataComponents.POTION_CONTENTS);
        if (potioncontents != null) {
            Objects.requireNonNull(tooltipComponents);
            potioncontents.addPotionTooltip(tooltipComponents::add, 1.0F, context.tickRate());
            tooltipComponents.add(Component.translatable("tooltip.randomstuffs.potion_flask.charges." + getFlaskCharges(stack)));
        }
    }

    public static int getMaxFlaskCharges() {
        return MAX_FLASK_CHARGES;
    }

    public static int getFlaskCharges(ItemStack itemstack) {
        return itemstack.get(ModDataComponents.FLASK_CHARGES.get()) != null ? itemstack.get(ModDataComponents.FLASK_CHARGES.get()) : 0;
    }

    public static boolean flaskHasRoom(ItemStack itemStack) {
        return getFlaskCharges(itemStack) < MAX_FLASK_CHARGES;
    }

    public static void setFlaskCharges(ItemStack itemstack, int value) {
        if (value >= MAX_FLASK_CHARGES) {
            itemstack.set(ModDataComponents.FLASK_CHARGES.get(), MAX_FLASK_CHARGES);
        } else itemstack.set(ModDataComponents.FLASK_CHARGES.get(), Math.max(value, 0));
    }

    public static int incrementFlaskCharges(ItemStack itemstack) {
        setFlaskCharges(itemstack, getFlaskCharges(itemstack) + 1);
        return getFlaskCharges(itemstack);
    }

    public static int decrementFlaskCharges(ItemStack itemstack) {
        setFlaskCharges(itemstack, getFlaskCharges(itemstack) - 1);
        return getFlaskCharges(itemstack);
    }
}
