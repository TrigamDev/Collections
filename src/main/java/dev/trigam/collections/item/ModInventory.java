package dev.trigam.collections.item;

import dev.trigam.collections.Collections;
import dev.trigam.collections.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;

public class ModInventory {

    public static void sort() {
        Collections.LOGGER.debug("Sorting inventory items for {}...", Collections.ModId);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content -> {
            content.addAfter(Blocks.LAPIS_BLOCK, ModBlocks.SULPHUR_BLOCK);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(content -> {
            content.addAfter(Blocks.SHORT_GRASS, ModBlocks.OAT_GRASS);
            content.addAfter(Blocks.DEEPSLATE_LAPIS_ORE, ModBlocks.SULPHUR_ORE, ModBlocks.DEEPSLATE_SULPHUR_ORE, ModBlocks.NETHER_SULPHUR_ORE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(content -> {
            content.addAfter(Blocks.OBSERVER, ModBlocks.RELAY);
            content.addAfter(Blocks.TNT, ModItems.DYNAMITE, ModItems.BOUNCY_DYNAMITE);
            content.addAfter(Blocks.REDSTONE_LAMP, ModBlocks.COLORED_LAMP);
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
