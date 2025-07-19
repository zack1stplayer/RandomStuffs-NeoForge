package net.zack1stplayer.randomstuffs.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.zack1stplayer.randomstuffs.RandomStuffs;
import net.zack1stplayer.randomstuffs.block.ModBlocks;
import net.zack1stplayer.randomstuffs.block.custom.LampBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, RandomStuffs.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockAllWithItem(ModBlocks.TEST_ORE);
        blockAllWithItem(ModBlocks.DEEPSLATE_TEST_ORE);
        blockAllWithItem(ModBlocks.TEST_BLOCK);
        blockAllWithItem(ModBlocks.MAGIC_BLOCK);

        customLamp();

        stairsBlock(ModBlocks.HONEYCOMB_STAIRS.get(), blockTexture(Blocks.HONEYCOMB_BLOCK));
        blockItem(ModBlocks.HONEYCOMB_STAIRS);
        slabBlock(ModBlocks.HONEYCOMB_SLAB.get(), blockTexture(Blocks.HONEYCOMB_BLOCK), blockTexture(Blocks.HONEYCOMB_BLOCK));
        blockItem(ModBlocks.HONEYCOMB_SLAB);
        trapdoorBlock(ModBlocks.HONEYCOMB_TRAPDOOR.get(), blockTexture(Blocks.HONEYCOMB_BLOCK), true);
        blockItem(ModBlocks.HONEYCOMB_TRAPDOOR, "_bottom");
    }


    private void blockAllWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        blockItem(deferredBlock, "");
    }

    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile(RandomStuffs.MOD_ID + ":block/" + deferredBlock.getId().getPath() + appendix));
    }


    private void customLamp() {
        getVariantBuilder(ModBlocks.LAMP_BLOCK.get()).forAllStates(state -> {
            if(state.getValue(LampBlock.CLICKED)) {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll("lamp_on",
                        ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID, "block/" + "lamp_on")))};
            } else {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll("lamp_off",
                        ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID, "block/" + "lamp_off")))};
            }
        });

        simpleBlockItem(ModBlocks.LAMP_BLOCK.get(), models().cubeAll("lamp_off",
                ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID, "block/" + "lamp_off")));
    }
}
