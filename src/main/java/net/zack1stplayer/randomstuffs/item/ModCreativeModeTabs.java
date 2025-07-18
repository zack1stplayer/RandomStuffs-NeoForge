package net.zack1stplayer.randomstuffs.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zack1stplayer.randomstuffs.RandomStuffs;
import net.zack1stplayer.randomstuffs.block.ModBlocks;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RandomStuffs.MOD_ID);

    public static final Supplier<CreativeModeTab> RANDOMSTUFFS_TAB = CREATIVE_MODE_TAB.register("randomstuffs_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.TEST_ITEM.get()))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .title(Component.translatable("creativetab.randomstuffs.items"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(ModItems.TEST_ITEM);
                        output.accept(ModItems.EXAMPLE_ITEM);
                        output.accept(ModItems.CHISEL);
                        output.accept(ModItems.CHARGED_COAL);
                        output.accept(ModBlocks.TEST_BLOCK);
                        output.accept(ModBlocks.MAGIC_BLOCK);
                        output.accept(ModBlocks.LAMP_BLOCK);
                        output.accept(ModBlocks.HONEYCOMB_STAIRS);
                        output.accept(ModBlocks.HONEYCOMB_SLAB);
                        output.accept(ModBlocks.HONEYCOMB_TRAPDOOR);

//                        TOOLS
                        output.accept(ModItems.TEST_SWORD);
                        output.accept(ModItems.TEST_PICKAXE);
                        output.accept(ModItems.TEST_SHOVEL);
                        output.accept(ModItems.TEST_AXE);
                        output.accept(ModItems.TEST_HOE);
                    }))
                    .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
