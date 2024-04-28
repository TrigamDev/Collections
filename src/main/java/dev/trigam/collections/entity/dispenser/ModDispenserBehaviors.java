package dev.trigam.collections.entity.dispenser;

import dev.trigam.collections.Collections;
import dev.trigam.collections.item.ModItems;
import net.minecraft.block.DispenserBlock;

public class ModDispenserBehaviors {

    public static void register() {
        DispenserBlock.registerProjectileBehavior(ModItems.DYNAMITE);

        Collections.LOGGER.info("Registering dispenser behaviors for {}...", Collections.ModId);
    }

}
