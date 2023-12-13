package de.letsgaming.autocraftermod.item;

import de.letsgaming.autocraftermod.AutoCrafterMod;
import de.letsgaming.autocraftermod.block.ModBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static de.letsgaming.autocraftermod.item.CreativeTab.addToTab;

public class ModBlockItems
{ public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AutoCrafterMod.MOD_ID);

    public static final RegistryObject<BlockItem> AUTO_CRAFTER_BLOCK_ITEM = addToTab(ITEMS.register("auto_crafter_block",
            () -> new BlockItem(ModBlocks.AUTO_CRAFTER_BLOCK.get(), new Item.Properties())));

    public static void  register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
