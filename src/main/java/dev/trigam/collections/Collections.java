package dev.trigam.collections;

import dev.trigam.collections.block.ModBannerTags;
import dev.trigam.collections.block.ModBlocks;
import dev.trigam.collections.item.ModInventory;
import dev.trigam.collections.item.ModItems;
import dev.trigam.collections.render.ModColorProvider;
import dev.trigam.collections.render.ModRenderLayer;
import dev.trigam.collections.resource.ModPacks;
import dev.trigam.collections.state.ModProperties;
import dev.trigam.collections.worldgen.ModOres;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Collections implements ModInitializer {
	public static final String ModId = "collections";
    public static final Logger LOGGER = LoggerFactory.getLogger(ModId);

	@Override
	public void onInitialize() {
		ModItems.register();
		ModInventory.sort();
		ModBlocks.register();
		ModOres.register();

		ModBannerTags.register();
		ModProperties.register();

		ModPacks.register();

		ModRenderLayer.init();
		ModColorProvider.init();
	}
}