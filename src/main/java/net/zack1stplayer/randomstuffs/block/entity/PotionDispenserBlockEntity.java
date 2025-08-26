package net.zack1stplayer.randomstuffs.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.zack1stplayer.randomstuffs.menu.custom.PotionDispenserMenu;
import org.jetbrains.annotations.Nullable;

public class PotionDispenserBlockEntity extends BaseContainerBlockEntity {
    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private int progress = 0;
    private int maxProgress = 60;

    protected NonNullList<ItemStack> items;
    protected ContainerData dataAccess;

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
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, this.items, registries);
        tag.putInt("potion_dispenser.progress", this.progress);
        tag.putInt("potion_dispenser.max_progress", this.maxProgress);
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (slot == OUTPUT_SLOT) {
            return false;
        }
        else if (stack.getItem() == Items.BUCKET) {
            return true;
        }
        else {
            return false;
        }
    }


    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
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
        if (this.items.get(INPUT_SLOT).isEmpty()) {
            return false;
        }
        return this.items.get(INPUT_SLOT).getItem() == Items.BUCKET;
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
        ItemStack outItem = new ItemStack(Items.WATER_BUCKET);
        this.items.set(OUTPUT_SLOT, outItem);
        this.items.get(INPUT_SLOT).shrink(1);
    }

    private void resetProgress() {
        this.progress = 0;
    }
}
