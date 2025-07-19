package net.zack1stplayer.randomstuffs.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.zack1stplayer.randomstuffs.RandomStuffs;
import net.zack1stplayer.randomstuffs.item.ModItems;

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

//        TOOLS
        handheldItem(ModItems.TEST_SWORD.get());
        handheldItem(ModItems.TEST_PICKAXE.get());
        handheldItem(ModItems.TEST_SHOVEL.get());
        handheldItem(ModItems.TEST_AXE.get());
        handheldItem(ModItems.TEST_HOE.get());
    }




//    public void buttonItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
//        // Use buttonInventory(name, resourceLocation)
//        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
//                .texture("texture",  ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID,
//                        "block/" + baseBlock.getId().getPath()));
//    }

//    public void fenceItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
//        // Use fenceInventory(name, resourceLocation)
//        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
//                .texture("texture",  ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID,
//                        "block/" + baseBlock.getId().getPath()));
//    }

//    public void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
//        // Use wallInventory(name, resourceLocation)
//        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
//                .texture("wall",  ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID,
//                        "block/" + baseBlock.getId().getPath()));
//    }

//    private ItemModelBuilder handheldItem(DeferredItem<?> item) {
//        // Use handheldItem(item)
//        return withExistingParent(item.getId().getPath(),
//                ResourceLocation.parse("item/handheld")).texture("layer0",
//                ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID, "item/" + item.getId().getPath())
//        );
//    }

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

}
