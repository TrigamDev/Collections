package dev.trigam.collections.entity;

import dev.trigam.collections.Collections;
import dev.trigam.collections.entity.explosive.DynamiteEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntityTypes {

	public static final EntityType<DynamiteEntity> DynamiteEntityType = Registry.register(
		Registries.ENTITY_TYPE,
		new Identifier(Collections.ModId, "dynamite"),
		EntityType.Builder.<DynamiteEntity>create(DynamiteEntity::new, SpawnGroup.MISC).dimensions(0.25f, 0.25f)
			.build(Collections.ModId + ":dynamite")
	);

	public static void register() {
		Collections.LOGGER.debug("Registering entities for {}...", Collections.ModId);
	}

}
