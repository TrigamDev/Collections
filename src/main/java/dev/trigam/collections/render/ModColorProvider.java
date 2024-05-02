package dev.trigam.collections.render;

import dev.trigam.collections.Collections;
import dev.trigam.collections.block.ModBlocks;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.item.Item;

public class ModColorProvider {

    public static void register() {
        Collections.LOGGER.debug("Registering color providers for {}...", Collections.ModId);

        grassTint(ModBlocks.OAT_GRASS);
    }

    private static void grassTint(Block block) {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
            world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getDefaultColor(),
        block);
    }
    private static void grassTint(Item item) {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5, 1.0), item);
    }
    private static void foliageTint(Block block) {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
            world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(),
        block);
    }
    private static void foliageTint(Item item) {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getDefaultColor(), item);
    }
    private static void spruceFoliageTint(Block block) {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
            world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getSpruceColor(),
        block);
    }
    private static void spruceFoliageTint(Item item) {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getSpruceColor(), item);
    }
    private static void birchFoliageTint(Block block) {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
            world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getBirchColor(),
        block);
    }
    private static void birchFoliageTint(Item item) {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getBirchColor(), item);
    }
    private static void mangroveFoliageTint(Block block) {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
            world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getMangroveColor(),
        block);
    }
    private static void mangroveFoliageTint(Item item) {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getMangroveColor(), item);
    }
    private static void waterTint(Block block) {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
            world != null && pos != null ? BiomeColors.getWaterColor(world, pos) : -1,
        block);
    }
}
