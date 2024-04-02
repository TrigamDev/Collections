package dev.trigam.collections.item;

import dev.trigam.collections.Collections;
import dev.trigam.collections.block.BlockInit;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;

public class InventoryInit {

    public static void sort() {
        Collections.LOGGER.info("Sorting inventory items for {}", Collections.ModId);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content -> {
            content.addAfter(Items.LAPIS_BLOCK, BlockInit.SULPHUR_BLOCK);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(content -> {
            content.addAfter(Items.DEEPSLATE_LAPIS_ORE, BlockInit.SULPHUR_ORE, BlockInit.DEEPSLATE_SULPHUR_ORE, BlockInit.NETHER_SULPHUR_ORE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> {
            content.addAfter(Items.LAPIS_LAZULI, ItemInit.SULPHUR);
            content.addAfter(Items.GUSTER_BANNER_PATTERN, ItemInit.STAR_BANNER_PATTERN);
        });
    }

}
