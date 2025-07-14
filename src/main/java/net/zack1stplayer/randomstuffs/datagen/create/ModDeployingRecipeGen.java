package net.zack1stplayer.randomstuffs.datagen.create;

import com.simibubi.create.api.data.recipe.DeployingRecipeGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.neoforged.neoforge.common.Tags;
import net.zack1stplayer.randomstuffs.RandomStuffs;
import net.zack1stplayer.randomstuffs.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModDeployingRecipeGen extends DeployingRecipeGen {
    public ModDeployingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, RandomStuffs.MOD_ID);
    }

    GeneratedRecipe TEST = createWithDeferredId(idWithSuffix(() -> ModItems.CHISEL, "_from_deployer"),
    b -> b
            .require(Tags.Items.RODS)
            .require(ModItems.TEST_ITEM)
            .output(ModItems.CHISEL)
        );

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        all.forEach(c -> c.register(recipeOutput));
    }
}
