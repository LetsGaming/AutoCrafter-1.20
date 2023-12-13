package de.letsgaming.autocraftermod.block.custom;

import de.letsgaming.autocraftermod.block.ModBlockEntities;
import de.letsgaming.autocraftermod.block.entity.AutoCrafterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AutoCrafterBlock extends Block implements EntityBlock {
    public AutoCrafterBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return ModBlockEntities.AUTO_CRAFTER_BLOCK_ENTITY.get().create(pos, state);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof AutoCrafterBlockEntity blockEntity)) {
            return InteractionResult.PASS;
        } else if (player instanceof ServerPlayer sPlayer) {
            sPlayer.openMenu(blockEntity, pos);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
        if (!level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof AutoCrafterBlockEntity blockEntity) {
                ItemStackHandler craftingGrid = blockEntity.getCraftingGridOptional().orElse(new ItemStackHandler(9));
                ItemStackHandler inputSlot = blockEntity.getInputSlotOptional().orElse(new ItemStackHandler(1));

                // Drop items from the crafting grid
                for (int index = 0; index < craftingGrid.getSlots(); index++) {
                    ItemStack stack = craftingGrid.getStackInSlot(index);
                    var entity = new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, stack);
                    level.addFreshEntity(entity);
                }

                // Drop item from the input slot
                ItemStack inputStack = inputSlot.getStackInSlot(0);
                if (!inputStack.isEmpty()) {
                    var entity = new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, inputStack);
                    level.addFreshEntity(entity);
                }
            }
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }
}
