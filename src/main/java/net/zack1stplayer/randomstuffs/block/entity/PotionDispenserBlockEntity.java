package net.zack1stplayer.randomstuffs.block.entity;

import com.simibubi.create.content.fluids.potion.PotionFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.zack1stplayer.randomstuffs.item.custom.PotionFlaskItem;
import net.zack1stplayer.randomstuffs.menu.custom.PotionDispenserMenu;
import org.jetbrains.annotations.Nullable;

public class PotionDispenserBlockEntity extends BaseContainerBlockEntity {
    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private static final int FLUID_TANK_CAPACITY = 2000;

    private int progress = 0;
    private int maxProgress = 60;

    protected NonNullList<ItemStack> items;
    protected ContainerData dataAccess;

    protected FluidTank tankInventory;

    public PotionDispenserBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.POTION_DISPENSER_BE.get(), pos, blockState);
        this.items = NonNullList.withSize(2, ItemStack.EMPTY);
        this.dataAccess = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> PotionDispenserBlockEntity.this.progress;
                    case 1 -> PotionDispenserBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int val) {
                switch (i) {
                    case 0: PotionDispenserBlockEntity.this.progress = val;
                    case 1: PotionDispenserBlockEntity.this.maxProgress = val;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
        this.tankInventory = new FluidTank(FLUID_TANK_CAPACITY, (fluidStack) -> (fluidStack.getFluid() instanceof PotionFluid) || (fluidStack.is(Fluids.WATER))) {
            @Override
            protected void onContentsChanged() {
                setChanged();
                sendData();
            }
        };
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK,
                ModBlockEntities.POTION_DISPENSER_BE.get(),
                (be, context) -> be.tankInventory
        );
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.randomstuffs.potion_dispenser");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.items = nonNullList;
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new PotionDispenserMenu(i, inventory, this, dataAccess);
    }



    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items, registries);
        this.progress = tag.getInt("potion_dispenser.progress");
        this.maxProgress = tag.getInt("potion_dispenser.max_progress");
        //tankInventory.readFromNBT(registries, compound.getCompound("TankContent"));
        this.tankInventory.readFromNBT(registries, tag.getCompound("potion_dispenser.tank_content"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, this.items, registries);
        tag.putInt("potion_dispenser.progress", this.progress);
        tag.putInt("potion_dispenser.max_progress", this.maxProgress);
        //compound.put("TankContent", tankInventory.writeToNBT(registries, new CompoundTag()));
        tag.put("potion_dispenser.tank_content", tankInventory.writeToNBT(registries, new CompoundTag()));
    }


    @Override
    public int getContainerSize() {
        return this.items.size();
    }


    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void sendData() {
        if (level instanceof ServerLevel serverLevel)
            serverLevel.getChunkSource().blockChanged(getBlockPos());
    }


    public void serverTick(Level level, BlockPos blockPos, BlockState blockState) {
        if (isOutputEmpty() && canFill()) {
            increaseFillProgress();
            setChanged(level, blockPos, blockState);

            if (progressFull()) {
                fillItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }


    private boolean canFill() {
        if (this.items.get(INPUT_SLOT).isEmpty() || this.tankInventory.isEmpty()) {
            return false;
        }
        ItemStack itemStack = this.items.get(INPUT_SLOT);
        FluidStack fluidStack = this.tankInventory.getFluid();
        if (itemStack.is(Items.BUCKET) && fluidStack.is(Fluids.WATER)) {
            return fluidStack.getAmount() >= 1000;
        }
        if (itemStack.getItem() instanceof PotionFlaskItem && fluidStack.getFluid() instanceof PotionFluid) {
            return PotionFlaskItem.getFluidHandler(itemStack).fill(fluidStack, IFluidHandler.FluidAction.SIMULATE) >= 250;
        }
        return false;
    }

    private boolean isOutputEmpty() {
        return this.items.get(OUTPUT_SLOT).isEmpty();
    }

    private void increaseFillProgress() {
        this.progress++;
    }

    private boolean progressFull() {
        return this.progress >= this.maxProgress;
    }

    private void fillItem() {
        ItemStack outItem = null;
        ItemStack internalInput = this.items.get(INPUT_SLOT).copy();

        if (internalInput.is(Items.BUCKET)) {
            outItem = new ItemStack(Items.WATER_BUCKET);
            this.tankInventory.drain(1000, IFluidHandler.FluidAction.EXECUTE);

        } else if (internalInput.getItem() instanceof PotionFlaskItem) {
            IFluidHandlerItem capability = PotionFlaskItem.getFluidHandler(internalInput);

            capability.fill(this.tankInventory.drain(
                    (capability.getTankCapacity(0) - capability.getFluidInTank(0).getAmount()),
                    IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);

            outItem = internalInput.copy();
        }
        if (outItem == null) {
            outItem = ItemStack.EMPTY;
        }
        this.items.set(OUTPUT_SLOT, outItem);
        this.items.get(INPUT_SLOT).shrink(1);
    }

    private void resetProgress() {
        this.progress = 0;
    }


    public FluidTank getTankInventory() {
        return this.tankInventory;
    }


    //    public int getPotionFluidColor() {
//        if (this.tankInventory.isEmpty()) {
////            return FastColor.ARGB32.color(198,198, 198);
//            return PotionContents.EMPTY.getColor();
//        } else {
//            return this.tankInventory.getFluid().getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor();
//        }
//    }
}
