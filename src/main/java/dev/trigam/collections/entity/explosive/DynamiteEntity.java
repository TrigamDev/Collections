package dev.trigam.collections.entity.explosive;

import dev.trigam.collections.entity.ModEntityTypes;
import dev.trigam.collections.sound.ModSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DynamiteEntity extends BouncyExplosiveEntity {

	public DynamiteEntity(World world, int bounces) {
		super(ModEntityTypes.DynamiteEntityType, world, 1.0f, 0.75f, bounces);
	}

	public DynamiteEntity(World world, LivingEntity owner, int bounces) {
		super(ModEntityTypes.DynamiteEntityType, world, owner, 1.0f, 0.75f, bounces);
	}

	public DynamiteEntity(World world, int bounces, Vec3d pos) {
		super(ModEntityTypes.DynamiteEntityType, world, 1.0f, 0.75f, bounces, pos);
	}

	public DynamiteEntity(World world, LivingEntity owner, int bounces, Vec3d pos) {
		super(ModEntityTypes.DynamiteEntityType, world, owner, 1.0f, 0.75f, bounces, pos);
	}

	public DynamiteEntity(EntityType<DynamiteEntity> entityType, World world) {
		super(entityType, world);
	}

    @Override
	public void playBounceSound(World world, BlockPos pos) {
		world.playSound(null, pos, ModSoundEvents.ENTITY_DYNAMITE_BOUNCE, SoundCategory.BLOCKS, 1.0f, 1.0f);
	}
}
