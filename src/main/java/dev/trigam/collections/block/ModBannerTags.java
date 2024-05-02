package dev.trigam.collections.block;

import dev.trigam.collections.Collections;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModBannerTags {

    public static final TagKey<BannerPattern> STAR_BANNER_PATTERN = of("pattern_item/star");

    private static TagKey<BannerPattern> of(String id) {
        return TagKey.of(RegistryKeys.BANNER_PATTERN, new Identifier(Collections.ModId, id));
    }

    public static void register() {
        Collections.LOGGER.debug("Registering banner pattern tags for {}...", Collections.ModId);
    }

}
