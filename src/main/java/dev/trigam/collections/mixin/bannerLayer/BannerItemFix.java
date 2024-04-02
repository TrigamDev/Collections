package dev.trigam.collections.mixin.bannerLayer;

import dev.trigam.collections.CollectionsConfig;
import net.minecraft.item.BannerItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BannerItem.class)
public class BannerItemFix {
	@ModifyConstant(method = "appendBannerTooltip", constant = @Constant(intValue = 6))
	private static int getLimit(int limit) {
		return CollectionsConfig.BANNER_LAYER_LIMIT;
	}
}
