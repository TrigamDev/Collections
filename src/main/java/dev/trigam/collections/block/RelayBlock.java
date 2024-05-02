package dev.trigam.collections.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class RelayBlock extends FacingBlock {
    public static final MapCodec<RelayBlock> CODEC = createCodec(RelayBlock::new);
    public static final IntProperty POWER = Properties.POWER;
    public int delay = 2;

    @Override
    public MapCodec<RelayBlock> getCodec() {
        return CODEC;
    }

    public RelayBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
            .with(POWER, 0)
            .with(FACING, Direction.SOUTH)
        );
    }

    // Placement
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
            .with(FACING, ctx.getPlayerLookDirection());
    }
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        this.scheduleTick(world, pos);
    }
    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        this.updateTarget(world, pos, state);
    }
    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (moved || state.isOf(newState.getBlock())) return;
        super.onStateReplaced(state, world, pos, newState, moved);
        this.updateTarget(world, pos, state);
    }
    // Update
    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (world.isClient) return;
        //Collections.sendChatMessage("§2Neighbor update!\n§7(x: " + pos.getX() + ", y: " + pos.getY() + ", z: " + pos.getZ() + ")");
        this.scheduleTick(world, pos);
    }
    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.updateRelayState(state, world, pos);
        //Collections.sendChatMessage("§6Scheduled tick!\n§7(x: " + pos.getX() + ", y: " + pos.getY() + ", z: " + pos.getZ() + ")");
        this.updateNeighbors(world, pos, state);
    }
    private void updateRelayState(BlockState state, ServerWorld world, BlockPos pos) {
        Direction facing = state.get(FACING);
        // Receive power level state
        int currentPower = state.get(POWER);
        int inputPower = this.getInputPower(world, pos, facing);

        // Update to correct state
        if (currentPower != inputPower) {
            world.setBlockState(pos, state.with(POWER, inputPower), Block.NOTIFY_LISTENERS);
        }
    }

    protected void updateTarget(World world, BlockPos pos, BlockState state) {
        //Collections.sendChatMessage("§dUpdating target!\n§7(x: " + pos.getX() + ", y: " + pos.getY() + ", z: " + pos.getZ() + ")");
        this.updateNeighbors(world, pos, state);
    }

    protected void updateNeighbors(World world, BlockPos pos, BlockState state) {
        Direction direction = state.get(FACING);
        BlockPos blockPos = pos.offset(direction.getOpposite());
        world.updateNeighbor(blockPos, this, pos);
        world.updateNeighborsExcept(blockPos, this, direction);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(FACING) == direction) this.scheduleTick(world, pos);
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    private void scheduleTick(WorldAccess world, BlockPos pos) {
        // If on the server and not busy, update
        if (!world.isClient() && !world.getBlockTickScheduler().isQueued(pos, this)) {
            world.scheduleBlockTick(pos, this, this.delay);
        }
    }

    // Input
    private int getInputPower(World world, BlockPos pos, Direction facing) {
        int input = world.getEmittedRedstonePower(pos.offset(facing), facing);

        BlockState facingState = world.getBlockState(pos.offset(facing));
        int facingPower = 0;
        if (facingState.contains(POWER)) facingPower = facingState.get(POWER);

        return Math.max(input, facingPower);
    }

    // Output
    @Override
    protected int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return this.getOutputLevel(state, direction);
    }
    @Override
    protected int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return this.getOutputLevel(state, direction);
    }
    protected int getOutputLevel(BlockState state, Direction direction) {
        if (state.get(FACING) == direction) return state.get(POWER);
        return 0;
    }

    // State shit
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }
    protected boolean emitsRedstonePower(BlockState state) {
        return true;
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWER, FACING);
    }

}
