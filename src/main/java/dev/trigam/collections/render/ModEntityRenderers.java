package dev.trigam.collections.render;

import dev.trigam.collections.Collections;
import dev.trigam.collections.entity.ModEntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class ModEntityRenderers {

	public static void register() {
		Collections.LOGGER.info("Registering entity renderers for {}...", Collections.ModId);

		EntityRendererRegistry.register(ModEntityTypes.DynamiteEntityType, FlyingItemEntityRenderer::new);
	}

}
