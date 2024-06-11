package dev.trigam.collections.event;

import dev.trigam.collections.Collections;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Util;

public class ModKeybinds {

    //public static KeyBinding enchantmentTest = register("enchantment_test", "test", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Y);

    private static KeyBinding register(String name, String category, InputUtil.Type inputType, int key) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(
            Util.createTranslationKey("key", Collections.id(name)),
            inputType, key,
            Util.createTranslationKey("key.categories", Collections.id(category))
        ));
    }

    public static void register() {
        Collections.LOGGER.debug("Registering keybinds for {}...", Collections.ModId);
    }

}
