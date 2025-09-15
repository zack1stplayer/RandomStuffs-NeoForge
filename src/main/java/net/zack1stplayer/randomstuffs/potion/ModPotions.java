package net.zack1stplayer.randomstuffs.potion;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zack1stplayer.randomstuffs.RandomStuffs;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(BuiltInRegistries.POTION, RandomStuffs.MOD_ID);

    public static final Holder<Potion> COMBINED_POTION = POTIONS.register("combined_potion",
            () -> new Potion()
    );


    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
