package dev.trigam.collections;

import dev.trigam.collections.block.BannerTagInit;
import dev.trigam.collections.block.BlockInit;
import dev.trigam.collections.item.InventoryInit;
import dev.trigam.collections.item.ItemInit;
import dev.trigam.collections.worldgen.OreInit;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Collections implements ModInitializer {
	public static final String ModId = "collections";
    public static final Logger LOGGER = LoggerFactory.getLogger(ModId);

	@Override
	public void onInitialize() {
		ItemInit.register();
		InventoryInit.sort();
		BlockInit.register();
		BannerTagInit.register();
		OreInit.register();
	}
}