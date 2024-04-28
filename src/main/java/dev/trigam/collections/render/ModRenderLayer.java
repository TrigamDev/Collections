package dev.trigam.collections.render;

import dev.trigam.collections.Collections;
import dev.trigam.collections.block.ModBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class ModRenderLayer {

    public static void register() {
        Collections.LOGGER.info("Registering render layers for {}...", Collections.ModId);

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), ModBlocks.OAT_GRASS);
    }

}
