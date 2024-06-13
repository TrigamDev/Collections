package dev.trigam.collections.mixin.enchanting.enchantments;

import dev.trigam.collections.screen.ModEnchantmentScreenHandler;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EnchantingTableBlockEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EnchantingTableBlock.class)
public abstract class ReplaceEnchantingTableScreen extends BlockWithEntity {
    public ReplaceEnchantingTableScreen(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof EnchantingTableBlockEntity) {
            Text text = ((Nameable)blockEntity).getDisplayName();
            return new SimpleNamedScreenHandlerFactory(
                (syncId, inventory, player) -> new ModEnchantmentScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos)), text
            );
        } else {
            return null;
        }
    }
}
