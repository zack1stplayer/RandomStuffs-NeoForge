package net.zack1stplayer.randomstuffs.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zack1stplayer.randomstuffs.RandomStuffs;
import net.zack1stplayer.randomstuffs.block.ModBlocks;
import net.zack1stplayer.randomstuffs.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, RandomStuffs.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.BLOODWOOD_LOG.get())
                .add(ModBlocks.STRIPPED_BLOODWOOD_LOG.get())
                .add(ModBlocks.BLOODWOOD_WOOD.get())
                .add(ModBlocks.STRIPPED_BLOODWOOD_WOOD.get())
        ;

        tag(BlockTags.FENCES)
                .add(ModBlocks.BLOODWOOD_FENCE.get())
        ;
        tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.BLOODWOOD_FENCE_GATE.get())
        ;
        tag(BlockTags.WALLS)
        ;

        // MINEABLE BY TAGS
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.TEST_ORE.get())
                .add(ModBlocks.DEEPSLATE_TEST_ORE.get())
                .add(ModBlocks.TEST_BLOCK.get())
                .add(ModBlocks.MAGIC_BLOCK.get())
                .add(ModBlocks.LAMP_BLOCK.get())
                .add(ModBlocks.POTION_DISPENSER.get())
        ;
        tag(BlockTags.MINEABLE_WITH_SHOVEL)
        ;
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.BLOODWOOD_LOG.get())
                .add(ModBlocks.STRIPPED_BLOODWOOD_LOG.get())
                .add(ModBlocks.BLOODWOOD_WOOD.get())
                .add(ModBlocks.STRIPPED_BLOODWOOD_WOOD.get())
                .add(ModBlocks.BLOODWOOD_PLANKS.get())
                .add(ModBlocks.BLOODWOOD_STAIRS.get())
                .add(ModBlocks.BLOODWOOD_SLAB.get())
                .add(ModBlocks.BLOODWOOD_FENCE.get())
                .add(ModBlocks.BLOODWOOD_FENCE_GATE.get())
                .add(ModBlocks.BLOODWOOD_PRESSURE_PLATE.get())
                .add(ModBlocks.BLOODWOOD_BUTTON.get())
        ;
        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(ModBlocks.BLOODWOOD_LEAVES.get())
        ;

        // TIER TAGS
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.TEST_ORE.get())
                .add(ModBlocks.DEEPSLATE_TEST_ORE.get())
                .add(ModBlocks.TEST_BLOCK.get())
        ;
        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.MAGIC_BLOCK.get())
        ;

        // TEST TOOL TIER TAGS
        tag(ModTags.Blocks.NEEDS_TEST_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL)
        ;
        tag(ModTags.Blocks.INCORRECT_FOR_TEST_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .remove(ModTags.Blocks.NEEDS_TEST_TOOL)
        ;
    }
}
