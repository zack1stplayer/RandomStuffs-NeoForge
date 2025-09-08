package net.zack1stplayer.randomstuffs.item.custom;

import com.simibubi.create.content.fluids.potion.PotionFluid;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;
import net.zack1stplayer.randomstuffs.component.ModDataComponents;
import net.zack1stplayer.randomstuffs.item.ModItems;

import java.util.List;
import java.util.Objects;

public class PotionFlaskItem extends Item {
    protected static final int FLUID_CAPACITY = 2000;

    public PotionFlaskItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack itemstack = super.getDefaultInstance();
        itemstack.set(ModDataComponents.FLUID_CONTENT.get(), SimpleFluidContent.EMPTY);
        return itemstack;
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(Capabilities.FluidHandler.ITEM,
                (stack, vxd) -> new FluidHandlerItemStack(ModDataComponents.FLUID_CONTENT, stack, FLUID_CAPACITY) {
                    @Override
                    public boolean isFluidValid(int tank, FluidStack stack) {
                        return (stack.getFluid() instanceof PotionFluid);
                    }

                    @Override
                    protected void setContainerToEmpty() {
                        this.container.set(this.componentType, SimpleFluidContent.EMPTY);
                    }

                },
                ModItems.POTION_FLASK.get()
        );
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = entityLiving instanceof Player ? (Player)entityLiving : null;
        IFluidHandlerItem capability = getFluidHandler(stack);
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, stack);
        }

        if (!level.isClientSide) {
            PotionContents potioncontents = capability.getFluidInTank(0).getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            potioncontents.forEachEffect((effectInstance) -> {
                if (((MobEffect)effectInstance.getEffect().value()).isInstantenous()) {
                    ((MobEffect)effectInstance.getEffect().value()).applyInstantenousEffect(player, player, entityLiving, effectInstance.getAmplifier(), (double)1.0F);
                } else {
                    entityLiving.addEffect(effectInstance);
                }

            });
        }

        if (player != null && !player.hasInfiniteMaterials()) {
            player.awardStat(Stats.ITEM_USED.get(this));
            capability.drain(250, IFluidHandler.FluidAction.EXECUTE);
        }

        if (player == null || !player.hasInfiniteMaterials()) {
            if (stack.isEmpty()) {
                return new ItemStack(ModItems.POTION_FLASK.get());
            }
        }

        entityLiving.gameEvent(GameEvent.DRINK);
        return stack;
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) {
    return 32;
}

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (getFluidHandler(player.getItemInHand(hand)).getFluidInTank(0).getAmount() >= 250) {
            return ItemUtils.startUsingInstantly(level, player, hand);
        }
        else {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
    }

//    @Override
//    public String getDescriptionId(ItemStack stack) {
//        return Potion.getName(((PotionContents)getOrDefaultPotionContents(stack)).potion(), this.getDescriptionId() + ".effect.");
//    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        PotionContents potioncontents = getPotionContents(stack);
        if (potioncontents != null) {
            Objects.requireNonNull(tooltipComponents);
            tooltipComponents.add(Component.translatable("tooltip.randomstuffs.potion_flask.fluid_content",
                    getFluidHandler(stack).getFluidInTank(0).getAmount() / 250, FLUID_CAPACITY / 250));
            potioncontents.addPotionTooltip(tooltipComponents::add, 1.0F, context.tickRate());
        }
    }

    public static IFluidHandlerItem getFluidHandler(ItemStack stack) {
        return stack.getCapability(Capabilities.FluidHandler.ITEM);
    }

    public static PotionContents getPotionContents(ItemStack stack) {
        return getFluidHandler(stack).getFluidInTank(0).get(DataComponents.POTION_CONTENTS);
    }

    public static PotionContents getOrDefaultPotionContents(ItemStack stack) {
        return getFluidHandler(stack).getFluidInTank(0).getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
    }
}
