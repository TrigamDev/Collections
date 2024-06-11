package dev.trigam.collections;

import dev.trigam.collections.block.ModBannerTags;
import dev.trigam.collections.block.ModBlocks;
import dev.trigam.collections.block.ModFluidTags;
import dev.trigam.collections.block.entity.ModBlockEntityTypes;
import dev.trigam.collections.entity.ModEntityTypes;
import dev.trigam.collections.entity.dispenser.ModDispenserBehaviors;
import dev.trigam.collections.event.ModKeybinds;
import dev.trigam.collections.item.ModInventory;
import dev.trigam.collections.item.ModItems;
import dev.trigam.collections.render.ModColorProvider;
import dev.trigam.collections.resource.ModPacks;
import dev.trigam.collections.screen.ModScreenHandlers;
import dev.trigam.collections.sound.ModSoundEvents;
import dev.trigam.collections.state.ModProperties;
import dev.trigam.collections.worldgen.ModOres;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Collections implements ModInitializer {
	public static final String ModId = "collections";
	public static final int DataVersion = 1;
    public static final Logger LOGGER = LoggerFactory.getLogger(ModId);
	public static MinecraftClient mcClient = null;

	@Override
	public void onInitialize() {
		ModItems.register();
		ModInventory.sort();
		ModBlocks.register();
		ModBlockEntityTypes.register();
		ModOres.register();
		ModEntityTypes.register();
		ModSoundEvents.register();

		ModFluidTags.register();
		ModBannerTags.register();
		ModProperties.register();

		ModScreenHandlers.register();
		ModKeybinds.register();

		ModDispenserBehaviors.register();
		ModPacks.register();
		ModColorProvider.register();

		mcClient = MinecraftClient.getInstance();
	}

	public static Identifier id(String path) {
		return Identifier.of(Collections.ModId, path);
	}

	public static void sendChatMessage(String message) {
		Text chatMessage = Text.of(message);
		mcClient.inGameHud.getChatHud().addMessage(chatMessage);
	}
}