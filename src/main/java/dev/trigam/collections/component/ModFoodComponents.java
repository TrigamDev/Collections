package dev.trigam.collections.component;

import net.minecraft.component.type.FoodComponent;

public class ModFoodComponents {

    public static final FoodComponent OAT_GRAIN = new FoodComponent.Builder().nutrition(1).saturationModifier(0.2F).snack().build();
    public static final FoodComponent OATMEAL = createStew(4).build();
    public static final FoodComponent OAT_BREAD = new FoodComponent.Builder().nutrition(7).saturationModifier(0.7F).build();

    private static FoodComponent.Builder createStew(int nutrition) {
        return new FoodComponent.Builder().nutrition(nutrition).saturationModifier(0.6F);
    }

}
