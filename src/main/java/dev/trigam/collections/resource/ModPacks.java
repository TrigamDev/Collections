package dev.trigam.collections.resource;

import dev.trigam.collections.Collections;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;

public class ModPacks {

    public static void register() {
        Collections.LOGGER.debug("Registering built in resource packs for {}...", Collections.ModId);
        FabricLoader.getInstance().getModContainer(Collections.ModId).ifPresent(container -> {
            // Collections: Tweaks
            ResourceManagerHelper.registerBuiltinResourcePack(
                Collections.id("tweaks"),
                container,
                ResourcePackActivationType.DEFAULT_ENABLED
            );
        });
    }

}
