package dev.trigam.collections.resource;

import dev.trigam.collections.Collections;
import net.fabricmc.loader.api.FabricLoader;

public class ModPacks {

    public static void register() {
        Collections.LOGGER.info("Registering built in resource packs for {}", Collections.ModId);
        FabricLoader.getInstance().getModContainer(Collections.ModId).ifPresent(container -> {
            // Collections: Tweaks
//            ResourceManagerHelper.registerBuiltinResourcePack(
//                new Identifier(Collections.ModId, "tweaks"),
//                container,
//                ResourcePackActivationType.DEFAULT_ENABLED
//            );
        });
    }

}