package de.letsgaming.autocraftermod.block;

import de.letsgaming.autocraftermod.AutoCrafterMod;
import de.letsgaming.autocraftermod.block.entity.AutoCrafterBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AutoCrafterMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<AutoCrafterBlockEntity>> AUTO_CRAFTER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("auto_crafter_block_enity",
                    () -> BlockEntityType.Builder.of(AutoCrafterBlockEntity::new, ModBlocks.AUTO_CRAFTER_BLOCK.get())
                            .build(null));

    public static void  register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
