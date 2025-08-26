package net.zack1stplayer.randomstuffs.block.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zack1stplayer.randomstuffs.RandomStuffs;
import net.zack1stplayer.randomstuffs.block.ModBlocks;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, RandomStuffs.MOD_ID);


    public static final Supplier<BlockEntityType<PotionDispenserBlockEntity>> POTION_DISPENSER_BE =
            BLOCK_ENTITIES.register("potion_dispenser_be", () -> BlockEntityType.Builder.of(
                    PotionDispenserBlockEntity::new, ModBlocks.POTION_DISPENSER.get()).build(null)
            );


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
