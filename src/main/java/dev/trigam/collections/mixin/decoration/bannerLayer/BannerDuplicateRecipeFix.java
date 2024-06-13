package dev.trigam.collections.mixin.decoration.bannerLayer;

import dev.trigam.collections.CollectionsConfig;
import net.minecraft.recipe.BannerDuplicateRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BannerDuplicateRecipe.class)
public class BannerDuplicateRecipeFix {

	@ModifyConstant(method = "matches(Lnet/minecraft/recipe/input/CraftingRecipeInput;Lnet/minecraft/world/World;)Z", constant = @Constant(intValue = 6))
	public int getLimitMatches(int limit) {
		return CollectionsConfig.BANNER_LAYER_LIMIT;
	}

	@ModifyConstant(method = "craft(Lnet/minecraft/recipe/input/CraftingRecipeInput;Lnet/minecraft/registry/RegistryWrapper$WrapperLookup;)Lnet/minecraft/item/ItemStack;", constant = @Constant(intValue = 6))
	public int getLimitCraft(int limit) {
		return CollectionsConfig.BANNER_LAYER_LIMIT;
	}
}
