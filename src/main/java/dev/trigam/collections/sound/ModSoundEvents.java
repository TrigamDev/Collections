package dev.trigam.collections.sound;

import dev.trigam.collections.Collections;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSoundEvents {

	public static SoundEvent ENTITY_DYNAMITE_THROW = register(new Identifier(Collections.ModId, "entity.dynamite.throw"));
	public static SoundEvent ENTITY_DYNAMITE_BOUNCE = register(new Identifier(Collections.ModId, "entity.dynamite.bounce"));

	private static SoundEvent register(Identifier id) {
		return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
	}

	public static void register() {
		Collections.LOGGER.debug("Registering sound events for {}...", Collections.ModId);
	}

}
