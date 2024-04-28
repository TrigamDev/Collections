package dev.trigam.collections.entity.dispenser;

import dev.trigam.collections.entity.explosive.DynamiteEntity;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DynamiteDispenserBehavior implements DispenserBehavior {

    @Override
    public ItemStack dispense(BlockPointer pointer, ItemStack stack) {
        World world = pointer.world();
        Vec3d pos = pointer.centerPos();
        Direction direction = pointer.state().get(DispenserBlock.FACING);

        DynamiteEntity dynamite = new DynamiteEntity(world, 0, pos.offset(direction, 1));
        dynamite.setVelocity(direction.getOffsetX(), direction.getOffsetY(), direction.getOffsetZ());
        world.spawnEntity(dynamite);
        stack.decrement(1);

        return stack;
    }

}
