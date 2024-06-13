package dev.trigam.collections.mixin.decoration.bannerLayer;

import dev.trigam.collections.CollectionsConfig;
import net.minecraft.client.gui.screen.ingame.LoomScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LoomScreen.class)
public class LoomScreenFix {
	@ModifyConstant(method = "onInventoryChanged()V", constant = @Constant(intValue = 6))
	private int getLimit(int limit) {
		return CollectionsConfig.BANNER_LAYER_LIMIT;
	}
}
