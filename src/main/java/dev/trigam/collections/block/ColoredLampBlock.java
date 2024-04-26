package dev.trigam.collections.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ColoredLampBlock extends Block {
    public static final MapCodec<ColoredLampBlock> CODEC = createCodec(ColoredLampBlock::new);
    public static final IntProperty POWER = Properties.POWER;
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    @Override
    public MapCodec<ColoredLampBlock> getCodec() {
        return CODEC;
    }

    public ColoredLampBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
            .with(POWER, 0)
            .with(LIT, false)
        );
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
            .with(POWER, getStrongestPower(ctx.getWorld(), ctx.getBlockPos()))
            .with(LIT, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
    }
    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient) {
            int power = state.get(POWER);
            int received = getStrongestPower(world, pos);
            boolean lit = state.get(LIT);
            boolean receiving = world.isReceivingRedstonePower(pos);

            // If lit state change, toggle
            if (lit != receiving) {
                if (lit) { world.scheduleBlockTick(pos, this, 0); }
                else { world.setBlockState(pos, state.with(LIT, receiving), Block.NOTIFY_LISTENERS); }
            }
            if (power != received) {
                world.setBlockState(pos, state.with(POWER, received), Block.NOTIFY_LISTENERS);
                world.scheduleBlockTick(pos, this, 0);
            }
        }
    }
    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int power = state.get(POWER);
        int received = getStrongestPower(world, pos);
        boolean lit = state.get(LIT);
        boolean receiving = world.isReceivingRedstonePower(pos);

        if (lit != receiving) {
            world.setBlockState(pos, state.with(LIT, receiving), Block.NOTIFY_LISTENERS);
        }
        if (power != received) {
            world.setBlockState(pos, state.with(POWER, received), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWER, LIT);
    }

    private int getStrongestPower(World world, BlockPos pos) {
        int received = world.getReceivedRedstonePower(pos);
        int strongReceived = world.getReceivedStrongRedstonePower(pos);
        return Math.max(received, strongReceived);
    }

    private int getStrongestPower(ServerWorld world, BlockPos pos) {
        int received = world.getReceivedRedstonePower(pos);
        int strongReceived = world.getReceivedStrongRedstonePower(pos);
        return Math.max(received, strongReceived);
    }
}