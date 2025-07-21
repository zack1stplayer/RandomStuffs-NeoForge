package net.zack1stplayer.randomstuffs.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.zack1stplayer.randomstuffs.RandomStuffs;
import net.zack1stplayer.randomstuffs.block.ModBlocks;
import net.zack1stplayer.randomstuffs.item.ModItems;
import net.zack1stplayer.randomstuffs.util.ModTags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        List<ItemLike> TEST_SMOKEABLES = List.of(ModItems.TEST_ITEM, ModBlocks.TEST_BLOCK);
        List<ItemLike> TEST_SMELTABLES = List.of(ModItems.RAW_TEST_ITEM);

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


        easyOreSmoking(recipeOutput, TEST_SMOKEABLES, ModItems.EXAMPLE_ITEM.get(), "test");
        easyOreBlasting(recipeOutput, TEST_SMELTABLES, ModItems.TEST_ITEM.get(), "test");


        // TREE BUILDING BLOCKS
        woodFromLogs(recipeOutput, ModBlocks.BLOODWOOD_WOOD.asItem(), ModBlocks.BLOODWOOD_LOG.asItem());
        woodFromLogs(recipeOutput, ModBlocks.STRIPPED_BLOODWOOD_WOOD.asItem(), ModBlocks.STRIPPED_BLOODWOOD_LOG.asItem());

        planksFromLog(recipeOutput, ModBlocks.BLOODWOOD_PLANKS.asItem(), ModTags.Items.BLOODWOOD_LOGS, 4);

        easyStairs(recipeOutput, ModBlocks.BLOODWOOD_STAIRS.asItem(), ModBlocks.BLOODWOOD_PLANKS.asItem());
        easySlab(recipeOutput, ModBlocks.BLOODWOOD_SLAB.asItem(), ModBlocks.BLOODWOOD_PLANKS.asItem());
        easyFence(recipeOutput, ModBlocks.BLOODWOOD_FENCE.asItem(), ModBlocks.BLOODWOOD_PLANKS.asItem());
        easyFenceGate(recipeOutput, ModBlocks.BLOODWOOD_FENCE_GATE.asItem(), ModBlocks.BLOODWOOD_PLANKS.asItem());
        pressurePlate(recipeOutput, ModBlocks.BLOODWOOD_PRESSURE_PLATE.asItem(), ModBlocks.BLOODWOOD_PLANKS.asItem());
        easyButton(recipeOutput, ModBlocks.BLOODWOOD_BUTTON.asItem(), ModBlocks.BLOODWOOD_PLANKS.asItem());


        // VANILLA BUILDING BLOCKS
        easyStairs(recipeOutput, ModBlocks.HONEYCOMB_STAIRS.get(), Blocks.HONEYCOMB_BLOCK);
        easySlab(recipeOutput, ModBlocks.HONEYCOMB_SLAB.get(), Blocks.HONEYCOMB_BLOCK);
        easyTrapdoor(recipeOutput, ModBlocks.HONEYCOMB_TRAPDOOR.get(), Blocks.HONEYCOMB_BLOCK);

        // TOOLS
        toolSuiteBuilder(recipeOutput, ModItems.TEST_ITEM.get(), ModItems.TEST_SWORD.get(), ModItems.TEST_PICKAXE.get(),
                ModItems.TEST_SHOVEL.get(), ModItems.TEST_AXE.get(), ModItems.TEST_HOE.get());
    }




    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_smelting");
    }

    protected static void oreSmoking(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                     float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_smoking");
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


    protected static void easyOreSmoking(RecipeOutput recipeOutput, List<ItemLike> pIngredients, ItemLike pResult, String pGroup) {
        oreSmelting(recipeOutput, pIngredients, RecipeCategory.MISC, pResult, 0.25f, 200, pGroup);
        oreSmoking(recipeOutput, pIngredients, RecipeCategory.MISC, pResult, 0.25f, 100, pGroup);
    }
    protected static void easyOreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, ItemLike pResult, String pGroup) {
        oreSmelting(recipeOutput, pIngredients, RecipeCategory.MISC, pResult, 0.25f, 200, pGroup);
        oreBlasting(recipeOutput, pIngredients, RecipeCategory.MISC, pResult, 0.25f, 100, pGroup);
    }

    private static void easyStairs(RecipeOutput recipeOutput, ItemLike pResult, ItemLike pIngredient) {
        stairBuilder(pResult, Ingredient.of(pIngredient))
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(recipeOutput);
    }

    private static void easySlab(RecipeOutput recipeOutput, ItemLike pResult, ItemLike pIngredient) {
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, pResult, pIngredient);
    }

    private static void easyTrapdoor(RecipeOutput recipeOutput, ItemLike pResult, ItemLike pIngredient) {
        trapdoorBuilder(pResult, Ingredient.of(pIngredient))
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(recipeOutput);
    }

    private static void easyDoor(RecipeOutput recipeOutput, ItemLike pResult, ItemLike pIngredient) {
        doorBuilder(pResult, Ingredient.of(pIngredient))
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(recipeOutput);
    }

    private static void easyFence(RecipeOutput recipeOutput, ItemLike pResult, ItemLike pIngredient) {
        fenceBuilder(pResult, Ingredient.of(pIngredient))
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(recipeOutput);
    }

    private static void easyFenceGate(RecipeOutput recipeOutput, ItemLike pResult, ItemLike pIngredient) {
        fenceGateBuilder(pResult, Ingredient.of(pIngredient))
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(recipeOutput);
    }

    private static void easyButton(RecipeOutput recipeOutput, ItemLike pResult, ItemLike pIngredient) {
        buttonBuilder(pResult, Ingredient.of(pIngredient))
                .unlockedBy(getHasName(pIngredient), has(pIngredient))
                .save(recipeOutput);
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
