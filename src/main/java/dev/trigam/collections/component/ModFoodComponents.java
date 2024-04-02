package dev.trigam.collections.component;

import net.minecraft.component.type.FoodComponent;

public class ModFoodComponents {

    public static final FoodComponent OAT_GRAIN = new FoodComponent.Builder().hunger(1).saturationModifier(0.2F).snack().build();
    public static final FoodComponent OATMEAL = createStew(4).build();
    public static final FoodComponent OAT_BREAD = new FoodComponent.Builder().hunger(7).saturationModifier(0.7F).build();

    private static FoodComponent.Builder createStew(int hunger) {
        return new FoodComponent.Builder().hunger(hunger).saturationModifier(0.6F);
    }

}
