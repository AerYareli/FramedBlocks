package xfacthd.framedblocks.client.data.ghost;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import xfacthd.framedblocks.api.ghost.GhostRenderBehaviour;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.util.FramedUtils;

public final class RailSlopeGhostRenderBehaviour implements GhostRenderBehaviour
{
    public static final RailSlopeGhostRenderBehaviour INSTANCE = new RailSlopeGhostRenderBehaviour();

    private RailSlopeGhostRenderBehaviour() { }

    @Override
    public boolean mayRender(ItemStack stack, @Nullable ItemStack proxiedStack)
    {
        return FramedUtils.isRailItem(stack.getItem());
    }

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
        if (hitState.getBlock() == FBContent.BLOCK_FRAMED_SLOPE.value())
        {
            RailShape shape = FramedUtils.getAscendingRailShapeFromDirection(hitState.getValue(FramedProperties.FACING_HOR));
            if (!(stack.getItem() instanceof BlockItem item) || !(item.getBlock() instanceof BaseRailBlock block))
            {
                return null;
            }
            //noinspection deprecation
            return block.defaultBlockState().setValue(block.getShapeProperty(), shape);
        }
        return null;
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
        return hit.getBlockPos();
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
        return true;
    }
}
