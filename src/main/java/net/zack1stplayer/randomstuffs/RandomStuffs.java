package net.zack1stplayer.randomstuffs;

import net.minecraft.util.FastColor;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.zack1stplayer.randomstuffs.block.ModBlocks;
import net.zack1stplayer.randomstuffs.block.entity.ModBlockEntities;
import net.zack1stplayer.randomstuffs.block.entity.PotionCombinerBlockEntity;
import net.zack1stplayer.randomstuffs.block.entity.PotionDispenserBlockEntity;
import net.zack1stplayer.randomstuffs.component.ModDataComponents;
import net.zack1stplayer.randomstuffs.item.ModCreativeModeTabs;
import net.zack1stplayer.randomstuffs.item.ModItems;
import net.zack1stplayer.randomstuffs.item.custom.PotionFlaskItem;
import net.zack1stplayer.randomstuffs.menu.ModMenuTypes;
import net.zack1stplayer.randomstuffs.menu.custom.PotionCombinerScreen;
import net.zack1stplayer.randomstuffs.menu.custom.PotionDispenserScreen;
import net.zack1stplayer.randomstuffs.potion.ModPotions;
import net.zack1stplayer.randomstuffs.util.ModItemProperties;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(RandomStuffs.MOD_ID)
public class RandomStuffs {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "randomstuffs";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public RandomStuffs(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);


        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        ModItems.register(modEventBus);

        ModCreativeModeTabs.register(modEventBus);

        ModDataComponents.register(modEventBus);

        ModPotions.register(modEventBus);

        ModMenuTypes.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

//        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
//            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
//        }
//
//        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());
//
//        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
//            event.accept(ModBlocks.TEST_BLOCK);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

            ModItemProperties.addCustomItemProperties();
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.POTION_DISPENSER_MENU.get(), PotionDispenserScreen::new);
            event.register(ModMenuTypes.POTION_COMBINER_MENU.get(), PotionCombinerScreen::new);
        }

        @SubscribeEvent
        public static void registerColorHandlerItems(RegisterColorHandlersEvent.Item event) {
            event.register(
                    (stack, p_329704_) -> p_329704_ > 0
                            ? -1
                            : FastColor.ARGB32.opaque(PotionFlaskItem.getOrDefaultPotionContents(stack).getColor()),
                    ModItems.POTION_FLASK.get()
            );
        }
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class CommonModEvents {
        @SubscribeEvent
        public static void registerCapabilities(RegisterCapabilitiesEvent event) {
            PotionDispenserBlockEntity.registerCapabilities(event);
            PotionCombinerBlockEntity.registerCapabilities(event);
            PotionFlaskItem.registerCapabilities(event);

        }
    }
}
