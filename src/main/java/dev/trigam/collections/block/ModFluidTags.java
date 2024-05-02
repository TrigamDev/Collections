package dev.trigam.collections.block;

import dev.trigam.collections.Collections;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModFluidTags {

    public static final TagKey<Fluid> FLUIDS = of("fluids");

    private static TagKey<Fluid> of(String id) {
        return TagKey.of(RegistryKeys.FLUID, new Identifier(Collections.ModId, id));
    }

    public static void register() {
        Collections.LOGGER.debug("Registering fluid tags for {}...", Collections.ModId);
    }

}
