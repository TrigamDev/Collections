package dev.trigam.collections.screen;

import dev.trigam.collections.Collections;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {

    public static ScreenHandlerType<ModEnchantmentScreenHandler> MOD_ENCHANTMENT_SCREEN_HANDLER;

    public static void register() {
        Collections.LOGGER.debug("Registering screen handler types for {}...", Collections.ModId);

        // Enchantment Screen
        MOD_ENCHANTMENT_SCREEN_HANDLER = Registry.register(
            Registries.SCREEN_HANDLER,
            Collections.id("enchanting"),
            new ScreenHandlerType<>(
                ModEnchantmentScreenHandler::new,
                FeatureFlags.VANILLA_FEATURES
            )
        );
    }
}
