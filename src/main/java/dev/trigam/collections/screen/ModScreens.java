package dev.trigam.collections.screen;

import dev.trigam.collections.Collections;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

@Environment(EnvType.CLIENT)
public class ModScreens {

    public static void register() {
        Collections.LOGGER.debug("Registering screens for {}...", Collections.ModId);

        HandledScreens.register(ModScreenHandlers.MOD_ENCHANTMENT_SCREEN_HANDLER, ModEnchantmentScreen::new);
    }

}
