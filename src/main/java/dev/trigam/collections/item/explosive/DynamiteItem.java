package dev.trigam.collections.item.explosive;

import dev.trigam.collections.entity.explosive.DynamiteEntity;
import dev.trigam.collections.sound.ModSoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DynamiteItem extends Item implements ProjectileItem {

	public int bounces;
	public DynamiteItem(Item.Settings settings, int bounces) {
		super(settings);
		this.bounces = bounces;
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSoundEvents.ENTITY_DYNAMITE_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
		if (!world.isClient) {
			DynamiteEntity dynamiteEntity = new DynamiteEntity(world, user, this.bounces);
			dynamiteEntity.setItem(itemStack);
			dynamiteEntity.setVelocity(user, user.getPitch(), user.getYaw(), -20.0F, 0.7F, 1.0F);
			world.spawnEntity(dynamiteEntity);
		}

		user.incrementStat(Stats.USED.getOrCreateStat(this));
		itemStack.decrementUnlessCreative(1, user);
		return TypedActionResult.success(itemStack, world.isClient());
	}

	public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
		DynamiteEntity dynamiteEntity = new DynamiteEntity(world, this.bounces, new Vec3d(pos.getX(), pos.getY(), pos.getZ()));
		dynamiteEntity.setItem(stack);
		return dynamiteEntity;
	}

	public ProjectileItem.Settings getProjectileSettings() {
		return ProjectileItem.Settings.builder().uncertainty(ProjectileItem.Settings.DEFAULT.uncertainty() * 0.5F).power(ProjectileItem.Settings.DEFAULT.power() * 1.25F).build();
	}
}
