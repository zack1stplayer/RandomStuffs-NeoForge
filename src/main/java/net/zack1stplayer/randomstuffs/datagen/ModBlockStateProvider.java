package net.zack1stplayer.randomstuffs.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
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
        
        // TREE BUILDING BLOCKS
        easyLog(ModBlocks.BLOODWOOD_LOG);
        easyLog(ModBlocks.STRIPPED_BLOODWOOD_LOG);
        easyWood(ModBlocks.BLOODWOOD_WOOD, ModBlocks.BLOODWOOD_LOG);
        easyWood(ModBlocks.STRIPPED_BLOODWOOD_WOOD, ModBlocks.STRIPPED_BLOODWOOD_LOG);

        leavesBlock(ModBlocks.BLOODWOOD_LEAVES);
        saplingBlock(ModBlocks.BLOODWOOD_SAPLING);

        blockAllWithItem(ModBlocks.BLOODWOOD_PLANKS);
        easyStairs(ModBlocks.BLOODWOOD_STAIRS, ModBlocks.BLOODWOOD_PLANKS);
        easySlab(ModBlocks.BLOODWOOD_SLAB, ModBlocks.BLOODWOOD_PLANKS);
        easyFence(ModBlocks.BLOODWOOD_FENCE, ModBlocks.BLOODWOOD_PLANKS);
        easyFenceGate(ModBlocks.BLOODWOOD_FENCE_GATE, ModBlocks.BLOODWOOD_PLANKS);
        easyPressurePlate(ModBlocks.BLOODWOOD_PRESSURE_PLATE, ModBlocks.BLOODWOOD_PLANKS);
        easyButton(ModBlocks.BLOODWOOD_BUTTON, ModBlocks.BLOODWOOD_PLANKS);



        // VANILLA BUILDING BLOCKS
        easyStairs(ModBlocks.HONEYCOMB_STAIRS, Blocks.HONEYCOMB_BLOCK);
        easySlab(ModBlocks.HONEYCOMB_SLAB, Blocks.HONEYCOMB_BLOCK);
        easyTrapdoor(ModBlocks.HONEYCOMB_TRAPDOOR, Blocks.HONEYCOMB_BLOCK);
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


    private void easyLog(DeferredBlock<?> block) {
        logBlock(((RotatedPillarBlock) block.get()));
        blockItem(block);
    }

    private void easyWood(DeferredBlock<?> block, DeferredBlock<?> texture) {
        axisBlock(((RotatedPillarBlock) block.get()), blockTexture(texture.get()), blockTexture(texture.get()));
        blockItem(block);
    }

    private void leavesBlock(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(),
                models().singleTexture(BuiltInRegistries.BLOCK.getKey(deferredBlock.get()).getPath(), ResourceLocation.parse("minecraft:block/leaves"),
                        "all", blockTexture(deferredBlock.get())).renderType("cutout")
        );
    }

    private void saplingBlock(DeferredBlock<?> deferredBlock) {
        simpleBlock(deferredBlock.get(),
                models().cross(BuiltInRegistries.BLOCK.getKey(deferredBlock.get()).getPath(), blockTexture(deferredBlock.get())).renderType("cutout")
        );
    }

    private void easyStairs(DeferredBlock<StairBlock> stairBlock, DeferredBlock<?> textureBlock) {
        easyStairs(stairBlock, textureBlock.get());
    }
    private void easyStairs(DeferredBlock<StairBlock> stairBlock, Block textureBlock) {
        stairsBlock(stairBlock.get(), blockTexture(textureBlock));
        blockItem(stairBlock);
    }

    private void easySlab(DeferredBlock<SlabBlock> slabBlock, DeferredBlock<?> textureBlock) {
        easySlab(slabBlock, textureBlock.get());
    }
    private void easySlab(DeferredBlock<SlabBlock> slabBlock, Block textureBlock) {
        slabBlock(slabBlock.get(), blockTexture(textureBlock), blockTexture(textureBlock));
        blockItem(slabBlock);
    }

    private void easyFence(DeferredBlock<FenceBlock> fenceBlock, DeferredBlock<?> textureBlock) {
        easyFence(fenceBlock, textureBlock.get());
    }
    private void easyFence(DeferredBlock<FenceBlock> fenceBlock, Block textureBlock) {
        fenceBlock(fenceBlock.get(), blockTexture(textureBlock));
        //blockItem(fenceBlock);
        // Done in ModItemModelProvider
    }

    private void easyFenceGate(DeferredBlock<FenceGateBlock> fenceGateBlock, DeferredBlock<?> textureBlock) {
        easyFenceGate(fenceGateBlock, textureBlock.get());
    }
    private void easyFenceGate(DeferredBlock<FenceGateBlock> fenceGateBlock, Block textureBlock) {
        fenceGateBlock(fenceGateBlock.get(), blockTexture(textureBlock));
        blockItem(fenceGateBlock);
    }

    private void easyWall(DeferredBlock<WallBlock> wallBlock, DeferredBlock<?> textureBlock) {
        easyWall(wallBlock, textureBlock.get());
    }
    private void easyWall(DeferredBlock<WallBlock> wallBlock, Block textureBlock) {
        wallBlock(wallBlock.get(), blockTexture(textureBlock));
        //blockItem(wallBlock);
        // Done in ModItemModelProvider
    }

    private void easyPressurePlate(DeferredBlock<PressurePlateBlock> pressurePlateBlock, DeferredBlock<?> textureBlock) {
        easyPressurePlate(pressurePlateBlock, textureBlock.get());
    }
    private void easyPressurePlate(DeferredBlock<PressurePlateBlock> pressurePlateBlock, Block textureBlock) {
        pressurePlateBlock(pressurePlateBlock.get(), blockTexture(textureBlock));
        blockItem(pressurePlateBlock);
    }

    private void easyButton(DeferredBlock<ButtonBlock> buttonBlock, DeferredBlock<?> textureBlock) {
        easyButton(buttonBlock, textureBlock.get());
    }
    private void easyButton(DeferredBlock<ButtonBlock> buttonBlock, Block textureBlock) {
        buttonBlock(buttonBlock.get(), blockTexture(textureBlock));
        //blockItem(buttonBlock);
        // Done in ModItemModelProvider
    }

    private void easyTrapdoor(DeferredBlock<TrapDoorBlock> trapdoorBlock, DeferredBlock<?> textureBlock) {
        easyTrapdoor(trapdoorBlock, textureBlock.get());
    }
    private void easyTrapdoor(DeferredBlock<TrapDoorBlock> trapdoorBlock, Block textureBlock) {
        trapdoorBlock(trapdoorBlock.get(), blockTexture(textureBlock), true);
        blockItem(trapdoorBlock, "_bottom");
    }
}
