package net.zack1stplayer.randomstuffs.block.entity;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.fluids.potion.PotionFluid;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.fluid.CombinedTankWrapper;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.zack1stplayer.randomstuffs.menu.custom.PotionCombinerMenu;
import net.zack1stplayer.randomstuffs.potion.ModPotions;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class PotionCombinerBlockEntity extends SmartBlockEntity implements MenuProvider, IHaveGoggleInformation {

    private static final int INPUT_TANKS_CAPACITY = 2000;
    private static final int OUTPUT_TANK_CAPACITY = 2000;

    private int progress = 0;
    private int maxProgress = 60;

    private boolean contentsChanged;

    protected SmartFluidTankBehaviour inTankLeft;
    protected SmartFluidTankBehaviour inTankRight;
    protected SmartFluidTankBehaviour outputTank;

    protected ContainerData dataAccess;
    protected IFluidHandler combinedTankWrapper;

    public PotionCombinerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.POTION_COMBINER_BE.get(), pos, state);
        this.contentsChanged = true;
        this.dataAccess = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> PotionCombinerBlockEntity.this.progress;
                    case 1 -> PotionCombinerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int val) {
                switch (i) {
                    case 0: PotionCombinerBlockEntity.this.progress = val;
                    case 1: PotionCombinerBlockEntity.this.maxProgress = val;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        inTankLeft = new SmartFluidTankBehaviour(new BehaviourType<>("InputLeft"), this, 1, INPUT_TANKS_CAPACITY, false)
                .whenFluidUpdates(() -> this.contentsChanged = true);
        inTankRight = new SmartFluidTankBehaviour(new BehaviourType<>("InputRight"), this, 1, INPUT_TANKS_CAPACITY, false)
                .whenFluidUpdates(() -> this.contentsChanged = true);
        outputTank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.OUTPUT, this, 1, OUTPUT_TANK_CAPACITY, false)
                .forbidInsertion()
                .whenFluidUpdates(() -> this.contentsChanged = true);

        behaviours.add(inTankLeft);
        behaviours.add(inTankRight);
        behaviours.add(outputTank);

        combinedTankWrapper = new CombinedTankWrapper(outputTank.getCapability(), inTankLeft.getCapability(), inTankRight.getCapability());
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK,
                ModBlockEntities.POTION_COMBINER_BE.get(),
                (be, context) -> {
            if (context == be.getBlockState().getValue(HorizontalDirectionalBlock.FACING).getClockWise()) {
                return be.inTankLeft.getCapability();
            }
            if (context == be.getBlockState().getValue(HorizontalDirectionalBlock.FACING).getCounterClockWise()) {
                return be.inTankRight.getCapability();
            }
            return be.outputTank.getCapability();
                }
        );
    }

    public Component getName() {
        return this.getDefaultName();
    }

    protected Component getDefaultName() {
        return Component.translatable("container.randomstuffs.potion_combiner");
    }

    @Override
    public Component getDisplayName() {
        return this.getName();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new PotionCombinerMenu(i, inventory, this, dataAccess);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        return containedFluidTooltip(tooltip, isPlayerSneaking, this.combinedTankWrapper);
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        this.progress = tag.getInt("potion_combiner.progress");
        this.maxProgress = tag.getInt("potion_combiner.max_progress");
        super.read(tag, registries, clientPacket);
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        tag.putInt("potion_combiner.progress", this.progress);
        tag.putInt("potion_combiner.max_progress", this.maxProgress);
        super.write(tag, registries, clientPacket);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void tick() {
        super.tick();
        serverTick();
    }

    public void serverTick() {
        if (canCombine() && outputHasRoom()) {
            increaseFillProgress();

            if (progressFull()) {
                combineFluids();
                resetProgress();
            }
        } else {
            resetProgress();
        }


        if (contentsChanged) {
            contentsChanged = false;
            notifyUpdate();
        }
    }

    private boolean canCombine() {
        if (inTankLeft.isEmpty() || inTankRight.isEmpty()) {
            return false;
        }
        FluidStack leftStack = inTankLeft.getCapability().getFluidInTank(0);
        FluidStack rightStack = inTankRight.getCapability().getFluidInTank(0);
        if (!(leftStack.getFluid() instanceof PotionFluid) || !(rightStack.getFluid() instanceof PotionFluid)) {
            return false;
        }
        return leftStack.getAmount() >= 250 && rightStack.getAmount() >= 250;
    }

    private boolean outputHasRoom() {
        if (outputTank.isEmpty()) {
            return true;
        }
        if (combinedFluidMatchesOutput()) {
            return (outputTank.getCapability().getTankCapacity(0) - outputTank.getCapability().getFluidInTank(0).getAmount() >= 250);
        }
        return false;
    }

    private boolean combinedFluidMatchesOutput() {
        List<MobEffectInstance> outputEffects = List.of();
        for (MobEffectInstance effect : outputTank.getCapability().getFluidInTank(0).get(DataComponents.POTION_CONTENTS).getAllEffects()) {
            outputEffects = Util.copyAndAdd(outputEffects, effect);
        }
        return new HashSet<>(outputEffects).containsAll(getCombinedEffects()) && new HashSet<>(getCombinedEffects()).containsAll(outputEffects);    //TODO
    }

    private void increaseFillProgress() {
        this.progress++;
    }

    private boolean progressFull() {
        return this.progress >= this.maxProgress;
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private void combineFluids() {
        if (outputTank.isEmpty()) {
            PotionContents leftContents = inTankLeft.getCapability().getFluidInTank(0).get(DataComponents.POTION_CONTENTS);
            PotionContents rightContents = inTankRight.getCapability().getFluidInTank(0).get(DataComponents.POTION_CONTENTS);

            List<MobEffectInstance> combinedEffects = getCombinedEffects();

            PotionContents newContent = new PotionContents(Optional.of(ModPotions.COMBINED_POTION),
                    Optional.of(FastColor.ARGB32.average(leftContents.getColor(), rightContents.getColor())),
                    combinedEffects
            );


            FluidStack outStack = PotionFluid.of(250, newContent, PotionFluid.BottleType.REGULAR);
//            outStack.set(DataComponents.POTION_CONTENTS, newContent);

            ((SmartFluidTankBehaviour.InternalFluidHandler)outputTank.getCapability()).forceFill(outStack, IFluidHandler.FluidAction.EXECUTE);
        } else {
            ((SmartFluidTankBehaviour.InternalFluidHandler)outputTank.getCapability()).forceFill(outputTank.getCapability().getFluidInTank(0).copyWithAmount(250), IFluidHandler.FluidAction.EXECUTE);
        }
        inTankLeft.getCapability().drain(250, IFluidHandler.FluidAction.EXECUTE);
        inTankRight.getCapability().drain(250, IFluidHandler.FluidAction.EXECUTE);
        contentsChanged = true;
    }

    private List<MobEffectInstance> getCombinedEffects() {
        List<MobEffectInstance> combinedEffects = List.of();

        for (MobEffectInstance effect : inTankLeft.getCapability().getFluidInTank(0).get(DataComponents.POTION_CONTENTS).getAllEffects()) {
            if (combinedEffects.contains(effect)) {
                continue;
            }
            combinedEffects = Util.copyAndAdd(combinedEffects, effect);
        }
        for (MobEffectInstance effect : inTankRight.getCapability().getFluidInTank(0).get(DataComponents.POTION_CONTENTS).getAllEffects()) {
            if (combinedEffects.contains(effect)) {
                continue;
            }
            combinedEffects = Util.copyAndAdd(combinedEffects, effect);
        }

        return combinedEffects;
    }


    public IFluidHandler getFluidCapability(int tank) {
        return switch (tank) {
            case 0 -> this.outputTank.getCapability();
            case 1 -> this.inTankLeft.getCapability();
            case 2 -> this.inTankRight.getCapability();
            default -> this.combinedTankWrapper;
        };
    }
}
