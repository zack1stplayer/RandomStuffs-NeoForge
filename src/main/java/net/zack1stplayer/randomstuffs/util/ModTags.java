package net.zack1stplayer.randomstuffs.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.zack1stplayer.randomstuffs.RandomStuffs;

public class ModTags {
    public static class Blocks {
        public static TagKey<Block> NEEDS_TEST_TOOL = createTag("needs_test_tool");
        public static TagKey<Block> INCORRECT_FOR_TEST_TOOL = createTag("incorrect_for_test_tool");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> TEST_TAG = createTag("test_tag");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID, name));
        }
    }
}
