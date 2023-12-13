package de.letsgaming.autocraftermod.screen;
import de.letsgaming.autocraftermod.AutoCrafterMod;
import de.letsgaming.autocraftermod.menu.AutoCrafterMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class AutoCrafterScreen extends AbstractContainerScreen<AutoCrafterMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(AutoCrafterMod.MOD_ID, "textures/gui/auto_crafter_gui.png");

    public AutoCrafterScreen(AutoCrafterMenu pMenu, Inventory pPlayerInventory, Component pTitle) {

        super(pMenu, pPlayerInventory, pTitle);

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        renderTransparentBackground(pGuiGraphics);
        pGuiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
