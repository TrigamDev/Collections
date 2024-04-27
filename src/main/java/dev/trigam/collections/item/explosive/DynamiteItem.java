package dev.trigam.collections.item.explosive;

import dev.trigam.collections.entity.explosive.DynamiteEntity;
import dev.trigam.collections.item.ThrowableProjectileItem;
import dev.trigam.collections.sound.ModSoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;

public class DynamiteItem extends ThrowableProjectileItem<DynamiteEntity> {

	public int bounces;
	public DynamiteItem(Settings settings, int bounces) {
		super(settings);
		this.bounces = bounces;
	}

	@Override
	public DynamiteEntity createEntity(World world, PlayerEntity user) {
		return new DynamiteEntity(world, user, this.bounces);
	}

	@Override
	public void playThrowSound(World world, PlayerEntity user) {
		world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSoundEvents.ENTITY_DYNAMITE_THROW, SoundCategory.PLAYERS, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
	}
}
