package dev.trigam.collections.item;

import dev.trigam.collections.Collections;
import dev.trigam.collections.block.ModBannerTags;
import dev.trigam.collections.component.ModFoodComponents;
import dev.trigam.collections.item.explosive.DynamiteItem;
import net.minecraft.item.BannerPatternItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item SULPHUR = register("sulphur", new Item(new Item.Settings()));
    public static final Item STAR_BANNER_PATTERN = register("star_banner_pattern",
        new BannerPatternItem(ModBannerTags.STAR_BANNER_PATTERN, new Item.Settings().maxCount(1))
    );
    public static final Item OAT_GRAIN = register("oat_grain", new Item(new Item.Settings().food(ModFoodComponents.OAT_GRAIN)));
    public static final Item OATMEAL = register("oatmeal", new ConsumableItem(new Item.Settings().maxCount(16).food(ModFoodComponents.OATMEAL), Items.BOWL));
    public static final Item OAT_BREAD = register("oat_bread", new Item(new Item.Settings().food(ModFoodComponents.OAT_BREAD)));

    public static final Item DYNAMITE = register("dynamite", new DynamiteItem(new Item.Settings().maxCount(16), 0));
    public static final Item BOUNCY_DYNAMITE = register("bouncy_dynamite", new DynamiteItem(new Item.Settings().maxCount(16), 3));

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Collections.ModId, name), item);
    }

    public static void register() {
        Collections.LOGGER.info("Registering items for {}", Collections.ModId);
    }

}
