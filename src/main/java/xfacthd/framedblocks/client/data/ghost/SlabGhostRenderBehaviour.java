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

public final class SlabGhostRenderBehaviour implements GhostRenderBehaviour
{
    @Override
    @Nullable
    public BlockState getRenderState(
            ItemStack stack,
            @Nullable ItemStack proxiedStack,
            BlockHitResult hit,
            BlockPlaceContext ctx,
            BlockState hitState,
            boolean secondPass
    )
    {
        if (hitState.getBlock() == FBContent.BLOCK_FRAMED_SLAB.get())
        {
            boolean top = hitState.getValue(FramedProperties.TOP);
            if ((top && hit.getDirection() == Direction.DOWN) || (!top && hit.getDirection() == Direction.UP))
            {
                return hitState.setValue(FramedProperties.TOP, !top);
            }
        }
        return GhostRenderBehaviour.super.getRenderState(stack, proxiedStack, hit, ctx, hitState, secondPass);
    }

    @Override
    public BlockPos getRenderPos(
            ItemStack stack,
            @Nullable ItemStack proxiedStack,
            BlockHitResult hit,
            BlockPlaceContext ctx,
            BlockState hitState,
            BlockPos defaultPos,
            boolean secondPass
    )
    {
        if (hitState.getBlock() == FBContent.BLOCK_FRAMED_SLAB.get())
        {
            boolean top = hitState.getValue(FramedProperties.TOP);
            if ((top && hit.getDirection() == Direction.DOWN) || (!top && hit.getDirection() == Direction.UP))
            {
                return hit.getBlockPos();
            }
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
