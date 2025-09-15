package net.zack1stplayer.randomstuffs.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zack1stplayer.randomstuffs.RandomStuffs;
import net.zack1stplayer.randomstuffs.block.custom.*;
import net.zack1stplayer.randomstuffs.block.custom.burnables.BurnableBlock;
import net.zack1stplayer.randomstuffs.block.custom.burnables.BurnableSlabBlock;
import net.zack1stplayer.randomstuffs.block.custom.burnables.BurnableStairBlock;
import net.zack1stplayer.randomstuffs.item.ModItems;
import net.zack1stplayer.randomstuffs.worldgen.tree.ModTreeGrowers;

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


    // BLOCK ENTITIES
    public static final DeferredBlock<Block> POTION_DISPENSER = registerBlock("potion_dispenser",
            () -> new PotionDispenserBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.COPPER)
                    .mapColor(MapColor.COLOR_ORANGE)
            ));

    public static final DeferredBlock<Block> POTION_COMBINER = registerBlock("potion_combiner",
            () -> new PotionCombinerBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.COPPER)
                    .mapColor(MapColor.COLOR_ORANGE)
            ));


    // TREE BUILDING BLOCKS
    public static final DeferredBlock<Block> BLOODWOOD_LOG = registerBlock("bloodwood_log",
            () -> new ModLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)
            ));
    public static final DeferredBlock<Block> STRIPPED_BLOODWOOD_LOG = registerBlock("stripped_bloodwood_log",
            () -> new ModLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)
            ));
    public static final DeferredBlock<Block> BLOODWOOD_WOOD = registerBlock("bloodwood_wood",
            () -> new ModLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)
            ));
    public static final DeferredBlock<Block> STRIPPED_BLOODWOOD_WOOD = registerBlock("stripped_bloodwood_wood",
            () -> new ModLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)
            ));

    public static final DeferredBlock<Block> BLOODWOOD_LEAVES = registerBlock("bloodwood_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }
            }
            );

    public static final DeferredBlock<Block> BLOODWOOD_SAPLING = registerBlock("bloodwood_sapling",
            () -> new SaplingBlock(ModTreeGrowers.BLOODWOOD, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)
            ));

    public static final DeferredBlock<Block> BLOODWOOD_PLANKS = registerBlock("bloodwood_planks",
            () -> new BurnableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
            ));
    public static final DeferredBlock<StairBlock> BLOODWOOD_STAIRS = registerBlock("bloodwood_stairs",
            () -> new BurnableStairBlock(BLOODWOOD_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS)
            ));
    public static final DeferredBlock<SlabBlock> BLOODWOOD_SLAB = registerBlock("bloodwood_slab",
            () -> new BurnableSlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)
            ));
    public static final DeferredBlock<FenceBlock> BLOODWOOD_FENCE = registerBlock("bloodwood_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)
            ));
    public static final DeferredBlock<FenceGateBlock> BLOODWOOD_FENCE_GATE = registerBlock("bloodwood_fence_gate",
            () -> new FenceGateBlock(WoodType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)
            ));
    public static final DeferredBlock<PressurePlateBlock> BLOODWOOD_PRESSURE_PLATE = registerBlock("bloodwood_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)
            ));
    public static final DeferredBlock<ButtonBlock> BLOODWOOD_BUTTON = registerBlock("bloodwood_button",
            () -> new ButtonBlock(BlockSetType.OAK, 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON) //.noCollission()
            ));


    // VANILLA BUILDING BLOCKS
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
                    BlockBehaviour.Properties.ofFullCopy(Blocks.HONEYCOMB_BLOCK).noOcclusion()
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
