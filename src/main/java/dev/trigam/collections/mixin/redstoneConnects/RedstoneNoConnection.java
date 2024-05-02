package dev.trigam.collections.mixin.redstoneConnects;

import dev.trigam.collections.block.ModBlocks;
import dev.trigam.collections.block.RelayBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedstoneWireBlock.class)
public class RedstoneNoConnection {
    @Inject(at = @At("HEAD"), method = "connectsTo(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;)Z", cancellable = true)
    private static void connectsTo(BlockState state, Direction dir, CallbackInfoReturnable<Boolean> cir) {
        if (state.isOf(ModBlocks.RELAY)) {
            boolean front = (dir == state.get(RelayBlock.FACING));
            boolean back = (dir == state.get(RelayBlock.FACING).getOpposite());
            cir.setReturnValue(front || back);
        }
    }

}
