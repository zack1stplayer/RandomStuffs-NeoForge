package net.zack1stplayer.randomstuffs.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.zack1stplayer.randomstuffs.block.ModBlocks;
import net.zack1stplayer.randomstuffs.item.ModItems;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        easyMultiOreDrop(ModBlocks.TEST_ORE.get(), ModItems.RAW_TEST_ITEM.get(), 1, 3);
        easyMultiOreDrop(ModBlocks.DEEPSLATE_TEST_ORE.get(), ModItems.RAW_TEST_ITEM.get(), 1, 3);
        dropSelf(ModBlocks.TEST_BLOCK.get());
        dropSelf(ModBlocks.MAGIC_BLOCK.get());
        dropSelf(ModBlocks.LAMP_BLOCK.get());

        // TREE BUILDING BLOCKS
        dropSelf(ModBlocks.BLOODWOOD_LOG.get());
        dropSelf(ModBlocks.STRIPPED_BLOODWOOD_LOG.get());
        dropSelf(ModBlocks.BLOODWOOD_WOOD.get());
        dropSelf(ModBlocks.STRIPPED_BLOODWOOD_WOOD.get());

        add(ModBlocks.BLOODWOOD_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.BLOODWOOD_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES)
        );
        dropSelf(ModBlocks.BLOODWOOD_SAPLING.get());

        dropSelf(ModBlocks.BLOODWOOD_PLANKS.get());
        dropSelf(ModBlocks.BLOODWOOD_STAIRS.get());
        easySlabDrop(ModBlocks.BLOODWOOD_SLAB.get());
        dropSelf(ModBlocks.BLOODWOOD_FENCE.get());
        dropSelf(ModBlocks.BLOODWOOD_FENCE_GATE.get());
        dropSelf(ModBlocks.BLOODWOOD_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.BLOODWOOD_BUTTON.get());

        // VANILLA BUILDING BLOCKS
        dropSelf(ModBlocks.HONEYCOMB_STAIRS.get());
        dropSelf(ModBlocks.HONEYCOMB_TRAPDOOR.get());
        easySlabDrop(ModBlocks.HONEYCOMB_SLAB.get());
    }



    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    protected void easyMultiOreDrop(Block pBlock, Item item, float minDrops, float maxDrops) {
        add(pBlock, createMultipleOreDrops(pBlock, item, minDrops, maxDrops));
    }

    protected void easySlabDrop(Block pBlock) {
        add(pBlock, block -> createSlabItemTable(pBlock));
    }

    protected void easyDoorDrop(Block block) {
        add(block, this::createDoorTable);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
