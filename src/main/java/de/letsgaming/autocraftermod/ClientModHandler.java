package de.letsgaming.autocraftermod;

import de.letsgaming.autocraftermod.menu.ModMenus;
import de.letsgaming.autocraftermod.screen.AutoCrafterScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = AutoCrafterMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModHandler {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenus.AUTO_CRAFTER_MENU.get(), AutoCrafterScreen::new);
        });
    }
/*
    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(Keybindings.INSTANCE.exampleKey);
        event.register(Keybindings.INSTANCE.examplePacketKey);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Entities
        event.registerEntityRenderer(EntityInit.EXAMPLE_ENTITY.get(), ExampleEntityRenderer::new);
        event.registerEntityRenderer(EntityInit.EXAMPLE_ANIMATED_ENTITY.get(), ExampleAnimatedEntityRenderer::new);

        // Block Entities
        event.registerBlockEntityRenderer(BlockEntityInit.EXAMPLE_BER_BLOCK_ENTITY.get(), ExampleBER::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ExampleEntityModel.LAYER_LOCATION, ExampleEntityModel::createBodyLayer);
        event.registerLayerDefinition(ExampleAnimatedEntityModel.LAYER_LOCATION, ExampleAnimatedEntityModel::createBodyLayer);
    }*/
}