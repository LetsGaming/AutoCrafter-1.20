package de.letsgaming.autocraftermod.block.entity;

import de.letsgaming.autocraftermod.AutoCrafterMod;
import de.letsgaming.autocraftermod.block.ModBlockEntities;
import de.letsgaming.autocraftermod.menu.AutoCrafterMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AutoCrafterBlockEntity extends BlockEntity implements MenuProvider {
    private static final Component TITLE =
            Component.translatable("container." + AutoCrafterMod.MOD_ID + ".auto_crafter_block");

    private static final int INPUT_SLOT = 0;
    private final ItemStackHandler craftingGrid = new ItemStackHandler(9) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            AutoCrafterBlockEntity.this.setChanged();
        }
    };

    private final ItemStackHandler inputSlot = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            AutoCrafterBlockEntity.this.setChanged();
        }
    };

    private final LazyOptional<ItemStackHandler> craftingGridOptional = LazyOptional.of(() -> this.craftingGrid);
    private final LazyOptional<ItemStackHandler> inputSlotOptional = LazyOptional.of(() -> this.inputSlot);

    public AutoCrafterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.AUTO_CRAFTER_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        CompoundTag tutorialmodData = nbt.getCompound(AutoCrafterMod.MOD_ID);
        this.craftingGrid.deserializeNBT(tutorialmodData.getCompound("CraftingGrid"));
        this.inputSlot.deserializeNBT(tutorialmodData.getCompound("InputSlot"));
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        var tutorialmodData = new CompoundTag();
        tutorialmodData.put("CraftingGrid", this.craftingGrid.serializeNBT());
        tutorialmodData.put("InputSlot", this.inputSlot.serializeNBT());
        nbt.put(AutoCrafterMod.MOD_ID, tutorialmodData);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return this.craftingGridOptional.cast();
        } else if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return this.inputSlotOptional.cast();
        }
        return super.getCapability(cap);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.craftingGridOptional.invalidate();
        this.inputSlotOptional.invalidate();
    }

    public LazyOptional<ItemStackHandler> getCraftingGridOptional() {
        return this.craftingGridOptional;
    }

    public LazyOptional<ItemStackHandler> getInputSlotOptional() {
        return this.inputSlotOptional;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return TITLE;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, Player pPlayer) {
        return new AutoCrafterMenu(pContainerId, pPlayerInventory, this);
    }

    public int getInputSlot() {
        return INPUT_SLOT;
    }
}
