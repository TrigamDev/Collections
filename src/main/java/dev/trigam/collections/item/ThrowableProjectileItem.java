package dev.trigam.collections.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public abstract class ThrowableProjectileItem<EntityType extends ThrownItemEntity> extends Item {

	public ThrowableProjectileItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		playThrowSound(world, user);
		if (!world.isClient) {
			EntityType entity = createEntity(world, user);
			entity.setItem(itemStack);
			entity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
			world.spawnEntity(entity);
		}
		user.incrementStat(Stats.USED.getOrCreateStat(this));
		if (!user.getAbilities().creativeMode) {
			itemStack.decrement(1);
		}
		return TypedActionResult.success(itemStack, world.isClient());
	}

	public abstract EntityType createEntity(World world, PlayerEntity user);
	public abstract void playThrowSound(World world, PlayerEntity user);

}
