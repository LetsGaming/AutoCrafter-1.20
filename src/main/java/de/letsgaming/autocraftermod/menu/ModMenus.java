package de.letsgaming.autocraftermod.menu;
import de.letsgaming.autocraftermod.AutoCrafterMod;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, AutoCrafterMod.MOD_ID);

    public static final RegistryObject<MenuType<AutoCrafterMenu>> AUTO_CRAFTER_MENU = MENU_TYPES.register("auto_crafter_menu",
            () -> IForgeMenuType.create(AutoCrafterMenu::new));

    public static void  register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
