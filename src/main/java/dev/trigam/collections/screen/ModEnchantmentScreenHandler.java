package dev.trigam.collections.screen;

import com.google.common.collect.Lists;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class ModEnchantmentScreenHandler extends ScreenHandler {
    private final ScreenHandlerContext context;
    private final World world;
    private final Inventory inventory = new SimpleInventory(2) {
        @Override
        public void markDirty() {
            super.markDirty();
            ModEnchantmentScreenHandler.this.onContentChanged(this);
        };
    };
    private List<Enchantment> availableEnchantments = Lists.newArrayList();

    public ModEnchantmentScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }
    public ModEnchantmentScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ModScreenHandlers.MOD_ENCHANTMENT_SCREEN_HANDLER, syncId);
        this.context = context;
        this.world = playerInventory.player.getWorld();

        // Gear Slot
        this.addSlot(new Slot(this.inventory, 0, 19, 48) {
            @Override
            public int getMaxItemCount() {
                return 1;
            }
        });
        // Catalyst Slot
        this.addSlot(new Slot(this.inventory, 1, 39, 48) {
//            @Override
//            public boolean canInsert(ItemStack stack) {
//                return stack.isOf(Items.LAPIS_LAZULI);
//            }

//            @Override
//            public Pair<Identifier, Identifier> getBackgroundSprite() {
//                return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, EnchantmentScreenHandler.EMPTY_LAPIS_SLOT_TEXTURE);
//            }
        });

        int slotSize = 18;
        // Inventory Slots
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot( new Slot( playerInventory,
                    ( (row * 9) + column ) + 9,
                    23 + (column * slotSize),
                    92 + (row * slotSize)
                ) );
            }
        }
        // Hotbar Slots
        for (int slot = 0; slot < 9; ++slot) {
            this.addSlot( new Slot( playerInventory,
                slot,
                23 + (slot * slotSize),
                150
            ) );
        }
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        if (inventory != this.inventory) return;
        this.availableEnchantments.clear();

        ItemStack gearItem = inventory.getStack(0);
        if (!gearItem.isEmpty() && gearItem.isEnchantable()) {
            this.availableEnchantments = getEnchantmentsForItem(this.world.getRegistryManager(), gearItem);
        }
    }


    // Enchantment List
    private List<Enchantment> getEnchantmentsForItem(DynamicRegistryManager registryManager, ItemStack targetItem) {
        Optional<RegistryEntryList.Named<Enchantment>> enchantingTableEnchants =
            registryManager.get(RegistryKeys.ENCHANTMENT).getEntryList(EnchantmentTags.IN_ENCHANTING_TABLE);

        if (enchantingTableEnchants.isEmpty()) return List.of();

        // Ok actually go find em
        List<Enchantment> itemEnchantments = Lists.newArrayList();
        enchantingTableEnchants.get().stream().forEach(entry -> {
            Enchantment enchantment = entry.value();
            if (enchantment.isPrimaryItem(targetItem)) itemEnchantments.add(enchantment);
        });

        return itemEnchantments;
    }
    public List<Enchantment> getAvailableEnchantments() { return this.availableEnchantments; }

    // Item Handling
    @Override
    public ItemStack quickMove(PlayerEntity player, int movingFromIndex) {
        ItemStack returnItem = ItemStack.EMPTY;
        Slot movingFromSlot = this.slots.get(movingFromIndex);

        if (movingFromSlot.hasStack()) {
            ItemStack insertItem = movingFromSlot.getStack();
            returnItem = insertItem.copy();

            // Don't quick move if slots are full
            // Transferring out of Gear or Catalyst Slots
            if (movingFromIndex == 0 || movingFromIndex == 1) {
                if (!this.insertItem(insertItem, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            // Transferring in a catalyst
            } else if (insertItem.isOf(Items.LAPIS_LAZULI)) {
                if (!this.insertItem(insertItem, 1, 2, true)) {
                    return ItemStack.EMPTY;
                }
            // Transferring in a gear item
            } else if (this.slots.get(0).hasStack() || !this.slots.get(0).canInsert(insertItem)) {
                return ItemStack.EMPTY;
            } else {
                ItemStack insertedItem = insertItem.copyWithCount(1);
                insertItem.decrement(1);
                this.slots.get(0).setStack(insertedItem);
            }

            // If actually moving an item, mark the inventory as modified
            if (insertItem.isEmpty()) { movingFromSlot.setStack(ItemStack.EMPTY); }
            else { movingFromSlot.markDirty(); }

            // Don't do anything if the item didn't actually move
            if (insertItem.getCount() == returnItem.getCount()) return ItemStack.EMPTY;

            // After ten million checks, finally move the item
            movingFromSlot.onTakeItem(player, insertItem);
        }

        return returnItem;
    }

    // Opening/Closing
    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.context.run((world, pos) -> this.dropInventory(player, this.inventory));
    }
    @Override
    public boolean canUse(PlayerEntity player) { return canUse(this.context, player, Blocks.ENCHANTING_TABLE); }
}
