package dev.trigam.collections.mixin.boatStep;

import dev.trigam.collections.CollectionsConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BoatEntity.class)
public abstract class BoatStep extends Entity {
    public BoatStep(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public float getStepHeight() {
        return CollectionsConfig.BOAT_STEP_HEIGHT;
    }

    @ModifyConstant(constant = @Constant(floatValue = 0.9F, ordinal = 2), method = "updateVelocity()V")
    public float updateVelocity(float f) {
        return 0.99F;
    }

}
