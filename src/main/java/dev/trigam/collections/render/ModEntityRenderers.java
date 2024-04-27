package dev.trigam.collections.render;

import dev.trigam.collections.entity.ModEntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class ModEntityRenderers {

	public static void init() {
		EntityRendererRegistry.register(ModEntityTypes.DynamiteEntityType, FlyingItemEntityRenderer::new);
	}

}
