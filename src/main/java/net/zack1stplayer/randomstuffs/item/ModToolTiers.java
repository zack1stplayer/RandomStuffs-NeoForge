package net.zack1stplayer.randomstuffs.item;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.zack1stplayer.randomstuffs.util.ModTags;

public class ModToolTiers {
    public static final Tier TEST = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_TEST_TOOL,
            1400, 6f, 3f, 24, () -> Ingredient.of(ModItems.TEST_ITEM));
}
