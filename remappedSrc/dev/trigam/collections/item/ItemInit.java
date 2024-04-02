package dev.trigam.collections.item;

import dev.trigam.collections.Collections;
import dev.trigam.collections.block.BannerTagInit;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.item.BannerPatternItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BannerPatternTags;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.util.Identifier;

public class ItemInit {

    public static final Item SULPHUR = register("sulphur", new Item(new Item.Settings()));
    public static final Item STAR_BANNER_PATTERN = register(
    "star_banner_pattern", new BannerPatternItem(BannerTagInit.STAR_BANNER_PATTERN, new Item.Settings().maxCount(1))
    );

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Collections.ModId, name), item);
    }

    public static void register() {
        Collections.LOGGER.info("Registering items for {}", Collections.ModId);
    }

}
