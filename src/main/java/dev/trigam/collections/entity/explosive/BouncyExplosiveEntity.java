package dev.trigam.collections.entity.explosive;

import dev.trigam.collections.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.joml.Vector3f;

public abstract class BouncyExplosiveEntity extends ThrownItemEntity {
	public float explosionStrength = 0.5f;
	public float bounciness = 0.75f;
	public float fuseTime = 0f;
	private float fuseTimer = 0f;
	private boolean timed = false;
	public int bounces = 0;

	public BouncyExplosiveEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
		super(entityType, world);
	}

	// Ownerless
	public BouncyExplosiveEntity(EntityType<? extends ThrownItemEntity> entityType, World world,
		 float explosionStrength, float bounciness, int bounces
	) {
		super(entityType, world);
		this.explosionStrength = explosionStrength;
		this.bounciness = bounciness;
		this.bounces = bounces;
		this.timed = false;
	}

	public BouncyExplosiveEntity(EntityType<? extends ThrownItemEntity> entityType, World world,
		 float explosionStrength, float bounciness, int bounces, Vec3d pos
	) {
		super(entityType, world);
		this.explosionStrength = explosionStrength;
		this.bounciness = bounciness;
		this.bounces = bounces;
		this.timed = false;
		this.setPos(pos.getX(), pos.getY(), pos.getZ());
	}

	public BouncyExplosiveEntity(EntityType<? extends ThrownItemEntity> entityType, World world,
		 float explosionStrength, float bounciness, int bounces, float fuseTime
	) {
		super(entityType, world);
		this.explosionStrength = explosionStrength;
		this.bounciness = bounciness;
		this.bounces = bounces;
		this.fuseTime = fuseTime;
		this.fuseTimer = fuseTime;
		this.timed = true;
	}

	public BouncyExplosiveEntity(EntityType<? extends ThrownItemEntity> entityType, World world,
		 float explosionStrength, float bounciness, int bounces, float fuseTime, Vec3d pos
	) {
		super(entityType, world);
		this.explosionStrength = explosionStrength;
		this.bounciness = bounciness;
		this.bounces = bounces;
		this.fuseTime = fuseTime;
		this.fuseTimer = fuseTime;
		this.timed = true;
		this.setPos(pos.getX(), pos.getY(), pos.getZ());
	}

	// Owner
	public BouncyExplosiveEntity(EntityType<? extends ThrownItemEntity> entityType, World world, LivingEntity owner,
		 float explosionStrength, float bounciness, int bounces
	) {
		super(entityType, owner, world);
		this.explosionStrength = explosionStrength;
		this.bounciness = bounciness;
		this.bounces = bounces;
		this.timed = false;
	}

	public BouncyExplosiveEntity(EntityType<? extends ThrownItemEntity> entityType, World world, LivingEntity owner,
		 float explosionStrength, float bounciness, int bounces, Vec3d pos
	) {
		super(entityType, owner, world);
		this.explosionStrength = explosionStrength;
		this.bounciness = bounciness;
		this.bounces = bounces;
		this.timed = false;
		this.setPos(pos.getX(), pos.getY(), pos.getZ());
	}

	public BouncyExplosiveEntity(EntityType<? extends ThrownItemEntity> entityType, World world, LivingEntity owner,
		 float explosionStrength, float bounciness, int bounces, float fuseTime
	) {
		super(entityType, owner, world);
		this.explosionStrength = explosionStrength;
		this.bounciness = bounciness;
		this.bounces = bounces;
		this.fuseTime = fuseTime;
		this.fuseTimer = fuseTime;
		this.timed = true;
	}

	public BouncyExplosiveEntity(EntityType<? extends ThrownItemEntity> entityType, World world, LivingEntity owner,
		 float explosionStrength, float bounciness, int bounces, float fuseTime, Vec3d pos
	) {
		super(entityType, owner, world);
		this.explosionStrength = explosionStrength;
		this.bounciness = bounciness;
		this.bounces = bounces;
		this.fuseTime = fuseTime;
		this.fuseTimer = fuseTime;
		this.timed = true;
		this.setPos(pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	protected Item getDefaultItem() { return ModItems.DYNAMITE; }

	@Override
	protected void onCollision(HitResult hitResult) {
		World world = this.getWorld();
		if (!world.isClient) {
			super.onCollision(hitResult);
			// Kablooery
			if (hitResult.getType() == HitResult.Type.ENTITY && !this.timed) detonate(world);
		}
	}

	@Override
	protected void onBlockHit(BlockHitResult hitBlock) {
		World world = this.getWorld();
		if (!world.isClient) {
			super.onBlockHit(hitBlock);
			// Bounce
			if (this.bounces > 0 || this.timed) {
				BlockPos blockPos = hitBlock.getBlockPos();
				BlockState blockState = this.getWorld().getBlockState(blockPos);

				if (blockState.blocksMovement()) {
					Vector3f reflected = bounceVec(world, hitBlock);
					if (magnitude(reflected) > 0.06) {
						playBounceSound(world, blockPos);
						this.setVelocity(reflected.x, reflected.y, reflected.z);
					} else {
						this.setVelocity(0.0, 0.0, 0.0);
						Vec3d pos = this.getPos();
						this.setPos(pos.x, pos.y + 0.0625, pos.z);
					}
				}
				this.bounces--;
			} else if (!this.timed && this.bounces <= 0) detonate(world);
		}
	}

	private Vector3f bounceVec(World world, BlockHitResult hitBlock) {
		// Get hit info
		Vector3f moveVec = this.getVelocity().toVector3f();
		Vec3i norm = hitBlock.getSide().getVector();
		BlockPos blockPos = hitBlock.getBlockPos();
		// Play sound
		playBounceSound(world, blockPos);
		// Reflect
		return moveVec.reflect(norm.getX(), norm.getY(), norm.getZ()).mul(this.bounciness);
	}

	@Override
	public void tick() {
		World world = this.getWorld();
		Entity entity = this.getOwner();
		if (entity instanceof PlayerEntity && !entity.isAlive()) this.discard();
		else super.tick();
		// Fuse
		if (this.timed) {
			if (this.fuseTimer <= 0) detonate(world);
			this.fuseTimer -= 0.05f;
		}
	}

	public void detonate(World world) {
		if (!world.isClient) {
			world.createExplosion(this, this.getX(), this.getY(), this.getZ(), this.explosionStrength, World.ExplosionSourceType.TNT);
			this.kill();
		}
	}

	private double magnitude(Vector3f vec) {
		return Math.sqrt(Math.pow(vec.x, 2) + Math.pow(vec.y, 2) + Math.pow(vec.z, 2));
	}

	public abstract void playBounceSound(World world, BlockPos pos);
}
