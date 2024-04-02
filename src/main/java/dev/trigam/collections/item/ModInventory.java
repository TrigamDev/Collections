package dev.trigam.collections.item;

import dev.trigam.collections.Collections;
import dev.trigam.collections.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;

public class ModInventory {

    public static void sort() {
        Collections.LOGGER.info("Sorting inventory items for {}", Collections.ModId);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content -> {
            content.addAfter(Items.LAPIS_BLOCK, ModBlocks.SULPHUR_BLOCK);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(content -> {
            content.addAfter(Items.SHORT_GRASS, ModBlocks.OAT_GRASS);
            content.addAfter(Items.DEEPSLATE_LAPIS_ORE, ModBlocks.SULPHUR_ORE, ModBlocks.DEEPSLATE_SULPHUR_ORE, ModBlocks.NETHER_SULPHUR_ORE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(content -> {
            content.addAfter(Items.BREAD, ModItems.OAT_BREAD);
            content.addAfter(Items.RABBIT_STEW, ModItems.OATMEAL);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> {
            content.addAfter(Items.WHEAT, ModItems.OAT_GRAIN);
            content.addAfter(Items.LAPIS_LAZULI, ModItems.SULPHUR);
            content.addAfter(Items.GUSTER_BANNER_PATTERN, ModItems.STAR_BANNER_PATTERN);
        });
    }

}
