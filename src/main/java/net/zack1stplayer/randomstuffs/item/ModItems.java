package net.zack1stplayer.randomstuffs.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zack1stplayer.randomstuffs.RandomStuffs;
import net.zack1stplayer.randomstuffs.component.ModDataComponents;
import net.zack1stplayer.randomstuffs.item.custom.ChiselItem;
import net.zack1stplayer.randomstuffs.item.custom.FlaskItem;
import net.zack1stplayer.randomstuffs.item.custom.PotionFlaskItem;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(RandomStuffs.MOD_ID);

    public static final DeferredItem<Item> TEST_ITEM = ITEMS.registerItem("test_item",
            Item::new, new Item.Properties()//.stacksTo(16)
    );
    public static final DeferredItem<Item> RAW_TEST_ITEM = ITEMS.registerItem("raw_test_item",
            Item::new, new Item.Properties()
    );

    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build())
    );


    public static final DeferredItem<Item> CHISEL = ITEMS.registerItem("chisel",
            ChiselItem::new, new Item.Properties().durability(150)
    );

    public static final DeferredItem<Item> CHARGED_COAL = ITEMS.registerItem("charged_coal",
            Item::new, new Item.Properties().fireResistant()
    );

    // POTION CONTAINER //TODO
//    public static final DeferredItem<FlaskItem> GLASS_FLASK = ITEMS.registerItem("glass_flask",
//            FlaskItem::new, new Item.Properties().stacksTo(1)
//    );
//    public static final DeferredItem<PotionFlaskItem> POTION_FLASK = ITEMS.registerItem("potion_flask",
//            PotionFlaskItem::new, new Item.Properties().stacksTo(1)
//    );


    // TOOLS
    public static final DeferredItem<SwordItem> TEST_SWORD = ITEMS.register("test_sword",
            () -> new SwordItem(ModToolTiers.TEST, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.TEST, 3, -2.4f)))
    );
    public static final DeferredItem<PickaxeItem> TEST_PICKAXE = ITEMS.register("test_pickaxe",
            () -> new PickaxeItem(ModToolTiers.TEST, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.TEST, 1.0f, -2.8f)))
    );
    public static final DeferredItem<ShovelItem> TEST_SHOVEL = ITEMS.register("test_shovel",
            () -> new ShovelItem(ModToolTiers.TEST, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.TEST, 1.5f, -3.0f)))
    );
    public static final DeferredItem<AxeItem> TEST_AXE = ITEMS.register("test_axe",
            () -> new AxeItem(ModToolTiers.TEST, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.TEST, 6.0f, -3.2f)))
    );
    public static final DeferredItem<HoeItem> TEST_HOE = ITEMS.register("test_hoe",
            () -> new HoeItem(ModToolTiers.TEST, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.TEST, 0.0f, -3.0f)))
    );


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
