package net.zack1stplayer.randomstuffs.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.zack1stplayer.randomstuffs.RandomStuffs;
import net.zack1stplayer.randomstuffs.block.ModBlocks;
import net.zack1stplayer.randomstuffs.item.ModItems;

import java.util.Objects;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, RandomStuffs.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.TEST_ITEM.get());
        basicItem(ModItems.RAW_TEST_ITEM.get());
        basicItem(ModItems.EXAMPLE_ITEM.get());

        binaryHandheldItem(ModItems.CHISEL, "used");
        basicItem(ModItems.CHARGED_COAL.get());

        // POTION FLASKS
//        basicItem(ModItems.GLASS_FLASK.get());
//        generateItemWithOverlay(ModItems.POTION_FLASK);

        // BLOCK ITEMS
        saplingItem(ModBlocks.BLOODWOOD_SAPLING);

        fenceItem(ModBlocks.BLOODWOOD_FENCE, ModBlocks.BLOODWOOD_PLANKS);
        buttonItem(ModBlocks.BLOODWOOD_BUTTON, ModBlocks.BLOODWOOD_PLANKS);

        // TOOLS
        handheldItem(ModItems.TEST_SWORD.get());
        handheldItem(ModItems.TEST_PICKAXE.get());
        handheldItem(ModItems.TEST_SHOVEL.get());
        handheldItem(ModItems.TEST_AXE.get());
        handheldItem(ModItems.TEST_HOE.get());
    }


    private ItemModelBuilder binaryHandheldItem(DeferredItem<?> item, String propertyName) {
        ModelFile overrideModel = withExistingParent(item.getId().getPath() + "_" + propertyName, ResourceLocation.parse("item/handheld"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID, "item/" + item.getId().getPath() + "_" + propertyName))
        ;
        return withExistingParent(item.getId().getPath(), ResourceLocation.parse("item/handheld"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID, "item/" + item.getId().getPath()))
                .override().predicate(ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID, propertyName), 1)
                .model(overrideModel).end()
        ;
    }

    private void generateItemWithOverlay(Item item) {
        String path = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)).getPath();
        this.withExistingParent(path, ResourceLocation.parse("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID, "item/" + path + "_overlay"))
                .texture("layer1", ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID, "item/" + path))
        ;
    }
    private void generateItemWithOverlay(DeferredItem<?> item) {
        generateItemWithOverlay(item.get());
    }


    private ItemModelBuilder saplingItem(DeferredBlock<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID, "block/" + item.getId().getPath()))
        ;
    }

    private void buttonItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    } // buttonInventory(name, resourceLocation)

    private void fenceItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    } // fenceInventory(name, resourceLocation)

    private void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    } // wallInventory(name, resourceLocation)

//    private ItemModelBuilder handheldItem(DeferredItem<?> item) {
//        // Use handheldItem(item)
//        return withExistingParent(item.getId().getPath(),
//                ResourceLocation.parse("item/handheld")).texture("layer0",
//                ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID, "item/" + item.getId().getPath())
//        );
//    }
}
