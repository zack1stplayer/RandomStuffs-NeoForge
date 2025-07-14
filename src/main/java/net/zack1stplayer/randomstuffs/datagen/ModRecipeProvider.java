package net.zack1stplayer.randomstuffs.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
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

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TEST_ITEM.get(), 8)
                .requires(ModBlocks.TEST_BLOCK)
                .unlockedBy("has_test_block", has(ModBlocks.TEST_BLOCK))
                .save(recipeOutput, "randomstuffs:test_item_from_test_block");

        oreSmelting(recipeOutput, TEST_SMELTABLES, RecipeCategory.MISC, ModItems.EXAMPLE_ITEM.get(), 0.25f, 200, "test");
        oreBlasting(recipeOutput, TEST_SMELTABLES, RecipeCategory.MISC, ModItems.EXAMPLE_ITEM.get(), 0.25f, 100, "test");
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

    protected  static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                        List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, RandomStuffs.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
