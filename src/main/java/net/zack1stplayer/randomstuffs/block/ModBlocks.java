package net.zack1stplayer.randomstuffs.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zack1stplayer.randomstuffs.RandomStuffs;
import net.zack1stplayer.randomstuffs.block.custom.LampBlock;
import net.zack1stplayer.randomstuffs.block.custom.MagicBlock;
import net.zack1stplayer.randomstuffs.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(RandomStuffs.MOD_ID);

    public static final DeferredBlock<Block> TEST_ORE = registerBlock("test_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 5),
                    BlockBehaviour.Properties.of()
                            .strength(3.0F, 3.0F)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.STONE)
                            .instrument(NoteBlockInstrument.BASEDRUM)
            ));
    public static final DeferredBlock<Block> DEEPSLATE_TEST_ORE = registerBlock("deepslate_test_ore",
            () -> new DropExperienceBlock(UniformInt.of(2, 5),
                    BlockBehaviour.Properties.ofFullCopy(TEST_ORE.get())
                            .strength(4.5F, 3.0F)
                            .sound(SoundType.DEEPSLATE)
                            .mapColor(MapColor.DEEPSLATE)
            ));

    public static final DeferredBlock<Block> TEST_BLOCK = registerBlock("test_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(1.5f,6.0f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
                    .mapColor(MapColor.COLOR_MAGENTA)
            ));

    public static final DeferredBlock<Block> MAGIC_BLOCK = registerBlock("magic_block",
            () -> new MagicBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST_CLUSTER)
                    .mapColor(MapColor.COLOR_MAGENTA)
            ));

    public static final DeferredBlock<Block> LAMP_BLOCK = registerBlock("lamp_block",
            () -> new LampBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.LANTERN)
                    .lightLevel(state -> state.getValue(LampBlock.CLICKED) ? 15 : 0)
            ));


    public static final DeferredBlock<StairBlock> HONEYCOMB_STAIRS = registerBlock("honeycomb_stairs",
            () -> new StairBlock(Blocks.HONEYCOMB_BLOCK.defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.HONEYCOMB_BLOCK)
            ));
    public static final DeferredBlock<SlabBlock> HONEYCOMB_SLAB = registerBlock("honeycomb_slab",
            () -> new SlabBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.HONEYCOMB_BLOCK)
            ));
    public static final DeferredBlock<TrapDoorBlock> HONEYCOMB_TRAPDOOR = registerBlock("honeycomb_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.OAK,
                    BlockBehaviour.Properties.ofFullCopy(Blocks.HONEYCOMB_BLOCK)
            ));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
