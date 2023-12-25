package de.letsgaming.autocraftermod.menu;

import de.letsgaming.autocraftermod.block.ModBlocks;
import de.letsgaming.autocraftermod.block.entity.AutoCrafterBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class AutoCrafterMenu extends AbstractContainerMenu {
    private final AutoCrafterBlockEntity blockEntity;
    private final ContainerLevelAccess levelAccess;

    // Client Constructor
    public AutoCrafterMenu(int containerId, Inventory playerInv, FriendlyByteBuf additionalData) {
        this(containerId, playerInv, playerInv.player.level().getBlockEntity(additionalData.readBlockPos()));
    }

    // Server Constructor
    public AutoCrafterMenu(int containerId, Inventory playerInv, BlockEntity blockEntity) {
        super(ModMenus.AUTO_CRAFTER_MENU.get(), containerId);
        if (blockEntity instanceof AutoCrafterBlockEntity be) {
            this.blockEntity = be;
        } else {
            throw new IllegalStateException("Incorrect block entity class (%s) passed into ExampleMenu!"
                    .formatted(blockEntity.getClass().getCanonicalName()));
        }

        this.levelAccess = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());

        createCraftingGrid();
        createPlayerHotbar(playerInv);
        createPlayerInventory(playerInv);
    }

    private void createCraftingGrid() {
        AutoCrafterBlockEntity be = this.blockEntity;
        // Input Slot
        be.getInputSlotOptional().ifPresent(inventory -> {
            addSlot(new SlotItemHandler(inventory, be.getInputSlot(), 27, 35));
        });
        be.getCraftingGridOptional().ifPresent(inventory -> {
            for (int row = 0; row < 3; row++) {
                for (int column = 0; column < 3; column++) {
                    addSlot(new SlotItemHandler(inventory,
                            column + (row * 3),
                            62 + (column * 18),
                            17 + (row * 18)));
                }
            }
        });
    }

    private void createPlayerInventory(Inventory playerInv) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(playerInv,
                        9 + column + (row * 9),
                        8 + (column * 18),
                        84 + (row * 18)));
            }
        }
    }

    private void createPlayerHotbar(Inventory playerInv) {
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(playerInv,
                    column,
                    8 + (column * 18),
                    142));
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        Slot fromSlot = getSlot(pIndex);

        if (fromSlot != null && fromSlot.hasItem()) {
            ItemStack fromStack = fromSlot.getItem().copy();

            if (pIndex < 10) {
                // Moving from crafting grid or input slot to player inventory
                if (!moveItemStackTo(fromStack, 10, 46, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (pIndex < 46) {
                // Moving from player inventory to crafting grid or input slot
                if(!moveItemStackTo(fromStack, 0, 10, false))
                    return ItemStack.EMPTY;
            } else {
                System.err.println("Invalid slot index: " + pIndex);
                return ItemStack.EMPTY;
            }

            fromSlot.setChanged();

            if (fromStack.getCount() <= 0) {
                fromSlot.set(ItemStack.EMPTY);
            }

            return fromStack;
        }

        return ItemStack.EMPTY;
    }

    private boolean isCraftingGridSlot(int index) {
        // Check if the slot index corresponds to a slot in the crafting grid
        return index >= 1 && index < 10;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(this.levelAccess, pPlayer, ModBlocks.AUTO_CRAFTER_BLOCK.get());
    }

    public AutoCrafterBlockEntity getBlockEntity() {
        return this.blockEntity;
    }
}