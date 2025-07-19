package net.zack1stplayer.randomstuffs.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zack1stplayer.randomstuffs.RandomStuffs;
import net.zack1stplayer.randomstuffs.item.ModItems;
import net.zack1stplayer.randomstuffs.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, RandomStuffs.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Items.TEST_TAG)
                .add(ModItems.TEST_ITEM.get())
                .add(ModItems.RAW_TEST_ITEM.get())
        ;


//        TOOL TAGS
        tag(ItemTags.SWORDS)
                .add(ModItems.TEST_SWORD.get())
        ;
        tag(ItemTags.PICKAXES)
                .add(ModItems.TEST_PICKAXE.get())
        ;
        tag(ItemTags.SHOVELS)
                .add(ModItems.TEST_SHOVEL.get())
        ;
        tag(ItemTags.AXES)
                .add(ModItems.TEST_AXE.get())
        ;
        tag(ItemTags.HOES)
                .add(ModItems.TEST_HOE.get())
        ;
    }
}
