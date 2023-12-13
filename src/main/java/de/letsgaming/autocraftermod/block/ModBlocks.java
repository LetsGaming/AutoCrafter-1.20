package de.letsgaming.autocraftermod.block;

import de.letsgaming.autocraftermod.AutoCrafterMod;
import de.letsgaming.autocraftermod.block.custom.AutoCrafterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, AutoCrafterMod.MOD_ID);

    public static final RegistryObject<AutoCrafterBlock> AUTO_CRAFTER_BLOCK = BLOCKS.register("auto_crafter_block",
            () -> new AutoCrafterBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));

    public static void  register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
