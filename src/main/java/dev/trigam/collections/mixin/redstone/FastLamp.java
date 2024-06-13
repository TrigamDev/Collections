package dev.trigam.collections.mixin.redstone;

import net.minecraft.block.RedstoneLampBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(RedstoneLampBlock.class)
public class FastLamp {
    @ModifyConstant(constant = @Constant(intValue = 4), method = "neighborUpdate")
    public int updateTickDelay(int constant) {
        return 1;
    }
}
