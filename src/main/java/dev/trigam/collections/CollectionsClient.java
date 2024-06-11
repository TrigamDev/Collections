package dev.trigam.collections;

import dev.trigam.collections.render.ModEntityRenderers;
import dev.trigam.collections.render.ModRenderLayer;
import dev.trigam.collections.screen.ModScreens;
import net.fabricmc.api.ClientModInitializer;

public class CollectionsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModScreens.register();

        ModRenderLayer.register();
        ModEntityRenderers.register();
    }
}
