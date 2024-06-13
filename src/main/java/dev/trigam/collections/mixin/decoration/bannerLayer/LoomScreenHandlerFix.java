package dev.trigam.collections.mixin.decoration.bannerLayer;

import dev.trigam.collections.CollectionsConfig;
import net.minecraft.screen.LoomScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LoomScreenHandler.class)
public class LoomScreenHandlerFix {
	@ModifyConstant(method = "onContentChanged", constant = @Constant(intValue = 6))
	private int getLimit(int limit) {
		return CollectionsConfig.BANNER_LAYER_LIMIT;
	}
}
