package net.zack1stplayer.randomstuffs.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.zack1stplayer.randomstuffs.RandomStuffs;
import net.zack1stplayer.randomstuffs.block.ModBlocks;
import net.zack1stplayer.randomstuffs.item.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        List<ItemLike> TEST_SMELTABLES = List.of(ModItems.TEST_ITEM, ModBlocks.TEST_BLOCK);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TEST_BLOCK.get())
                .pattern("TTT")
                .pattern("T T")
                .pattern("TTT")
                .define('T', ModItems.TEST_ITEM.get())
                .unlockedBy("has_test_item", has(ModItems.TEST_ITEM))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.LAMP_BLOCK.get())
                .pattern(" T ")
                .pattern("TiT")
                .pattern(" T ")
                .define('T', ModItems.TEST_ITEM.get())
                .define('i', Items.TORCH)
                .unlockedBy("has_test_item", has(ModItems.TEST_ITEM))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TEST_ITEM.get(), 8)
                .requires(ModBlocks.TEST_BLOCK)
                .unlockedBy("has_test_block", has(ModBlocks.TEST_BLOCK))
                .save(recipeOutput, "randomstuffs:test_item_from_test_block");

        oreSmelting(recipeOutput, TEST_SMELTABLES, RecipeCategory.MISC, ModItems.EXAMPLE_ITEM.get(), 0.25f, 200, "test");
        oreBlasting(recipeOutput, TEST_SMELTABLES, RecipeCategory.MISC, ModItems.EXAMPLE_ITEM.get(), 0.25f, 100, "test");

        stairBuilder(ModBlocks.HONEYCOMB_STAIRS.get(), Ingredient.of(Blocks.HONEYCOMB_BLOCK))
                .group("honeycomb").unlockedBy("has_honeycomb", has(Blocks.HONEYCOMB_BLOCK)).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.HONEYCOMB_SLAB.get(), Blocks.HONEYCOMB_BLOCK);
        trapdoorBuilder(ModBlocks.HONEYCOMB_TRAPDOOR.get(), Ingredient.of(Blocks.HONEYCOMB_BLOCK))
                .group("honeycomb").unlockedBy("has_honeycomb", has(Blocks.HONEYCOMB_BLOCK)).save(recipeOutput);

//        TOOLS
        toolSuiteBuilder(recipeOutput, ModItems.TEST_ITEM.get(), ModItems.TEST_SWORD.get(), ModItems.TEST_PICKAXE.get(),
                ModItems.TEST_SHOVEL.get(), ModItems.TEST_AXE.get(), ModItems.TEST_HOE.get());
    }



    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                        List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, RandomStuffs.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }

    private static void toolSuiteBuilder (RecipeOutput recipeOutput, ItemLike ingredient, ItemLike swordOut,
                                          ItemLike pickaxeOut, ItemLike shovelOut, ItemLike axeOut, ItemLike hoeOut) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, swordOut)
                .pattern("T")
                .pattern("T")
                .pattern("s")
                .define('T', ingredient)
                .define('s', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_" + getItemName(ingredient), has(ingredient))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, pickaxeOut)
                .pattern("TTT")
                .pattern(" s ")
                .pattern(" s ")
                .define('T', ingredient)
                .define('s', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_" + getItemName(ingredient), has(ingredient))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shovelOut)
                .pattern("T")
                .pattern("s")
                .pattern("s")
                .define('T', ingredient)
                .define('s', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_" + getItemName(ingredient), has(ingredient))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, axeOut)
                .pattern("TT")
                .pattern("Ts")
                .pattern(" s")
                .define('T', ingredient)
                .define('s', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_" + getItemName(ingredient), has(ingredient))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hoeOut)
                .pattern("TT")
                .pattern(" s")
                .pattern(" s")
                .define('T', ingredient)
                .define('s', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_" + getItemName(ingredient), has(ingredient))
                .save(recipeOutput);
    }

}
