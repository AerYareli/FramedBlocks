package xfacthd.framedblocks.common.data.skippreds.pillar;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.predicate.cull.SideSkipPredicate;
import xfacthd.framedblocks.api.util.*;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.data.skippreds.CullTest;

@CullTest(BlockType.FRAMED_LATTICE_BLOCK)
public final class LatticeSkipPredicate implements SideSkipPredicate
{
    @Override
    @CullTest.SingleTarget({ BlockType.FRAMED_LATTICE_BLOCK, BlockType.FRAMED_FENCE, BlockType.FRAMED_POST })
    public boolean test(BlockGetter level, BlockPos pos, BlockState state, BlockState adjState, Direction side)
    {
        Block block = adjState.getBlock();
        if (block == state.getBlock() && hasArm(state, side) && hasArm(adjState, side.getOpposite()))
        {
            return true;
        }
        else if (Utils.isY(side) && hasArm(state, side) && isFenceOrVerticalLattice(block, adjState))
        {
            return true;
        }
        return false;
    }

    private static boolean isFenceOrVerticalLattice(Block block, BlockState state)
    {
        if (block == FBContent.BLOCK_FRAMED_POST.get())
        {
            return state.getValue(BlockStateProperties.AXIS) == Direction.Axis.Y;
        }
        return block == FBContent.BLOCK_FRAMED_FENCE.get();
    }

    private static boolean hasArm(BlockState state, Direction side)
    {
        return switch (side.getAxis())
        {
            case X -> state.getValue(FramedProperties.X_AXIS);
            case Y -> state.getValue(FramedProperties.Y_AXIS);
            case Z -> state.getValue(FramedProperties.Z_AXIS);
        };
    }
}
