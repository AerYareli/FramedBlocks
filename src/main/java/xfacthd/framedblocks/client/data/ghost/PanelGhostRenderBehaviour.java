package xfacthd.framedblocks.client.data.ghost;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import xfacthd.framedblocks.api.ghost.GhostRenderBehaviour;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.common.FBContent;

public final class PanelGhostRenderBehaviour implements GhostRenderBehaviour
{
    @Override
    @Nullable
    public BlockState getRenderState(
            ItemStack stack,
            @Nullable ItemStack proxiedStack,
            BlockHitResult hit,
            BlockPlaceContext ctx,
            BlockState hitState,
            int renderPass
    )
    {
        BlockState state = ctx.getLevel().getBlockState(ctx.getClickedPos());
        if (state.getBlock() == FBContent.BLOCK_FRAMED_PANEL.value())
        {
            Direction dir = state.getValue(FramedProperties.FACING_HOR);
            return state.setValue(FramedProperties.FACING_HOR, dir.getOpposite());
        }
        return GhostRenderBehaviour.super.getRenderState(stack, proxiedStack, hit, ctx, hitState, renderPass);
    }

    @Override
    public BlockPos getRenderPos(
            ItemStack stack,
            @Nullable ItemStack proxiedStack,
            BlockHitResult hit,
            BlockPlaceContext ctx,
            BlockState hitState,
            BlockPos defaultPos,
            int renderPass
    )
    {
        BlockState state = ctx.getLevel().getBlockState(ctx.getClickedPos());
        if (state.getBlock() == FBContent.BLOCK_FRAMED_PANEL.value())
        {
            return ctx.getClickedPos();
        }
        return defaultPos;
    }

    @Override
    public boolean canRenderAt(
            ItemStack stack,
            @Nullable ItemStack proxiedStack,
            BlockHitResult hit,
            BlockPlaceContext ctx,
            BlockState hitState,
            BlockState renderState,
            BlockPos renderPos
    )
    {
        if (renderPos.equals(hit.getBlockPos()))
        {
            return true;
        }
        return GhostRenderBehaviour.super.canRenderAt(stack, proxiedStack, hit, ctx, hitState, renderState, renderPos);
    }
}
