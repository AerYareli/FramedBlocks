package xfacthd.framedblocks.common.data.skippreds.prism;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedblocks.api.block.IFramedBlock;
import xfacthd.framedblocks.api.predicate.cull.SideSkipPredicate;
import xfacthd.framedblocks.common.block.AbstractFramedDoubleBlock;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.data.PropertyHolder;
import xfacthd.framedblocks.common.data.property.CompoundDirection;
import xfacthd.framedblocks.common.data.property.DirectionAxis;
import xfacthd.framedblocks.common.data.skippreds.CullTest;
import xfacthd.framedblocks.common.data.skippreds.HalfDir;

@CullTest(BlockType.FRAMED_INNER_PRISM)
public final class InnerPrismSkipPredicate implements SideSkipPredicate
{
    @Override
    public boolean test(BlockGetter level, BlockPos pos, BlockState state, BlockState adjState, Direction side)
    {
        DirectionAxis dirAxis = state.getValue(PropertyHolder.FACING_AXIS);
        if (side.getAxis() != dirAxis.axis())
        {
            return SideSkipPredicate.FULL_FACE.test(level, pos, state, adjState, side);
        }
        else if (dirAxis.axis() == dirAxis.direction().getAxis())
        {
            return false;
        }

        if (adjState.getBlock() instanceof IFramedBlock block && block.getBlockType() instanceof BlockType type)
        {
            return switch (type)
            {
                case FRAMED_INNER_PRISM -> testAgainstInnerPrism(dirAxis, adjState, side);
                case FRAMED_INNER_SLOPED_PRISM -> testAgainstInnerSlopedPrism(dirAxis, adjState, side);
                case FRAMED_DOUBLE_PRISM -> testAgainstDoublePrism(dirAxis, adjState, side);
                case FRAMED_DOUBLE_SLOPED_PRISM -> testAgainstDoubleSlopedPrism(dirAxis, adjState, side);
                default -> false;
            };
        }

        return false;
    }

    @CullTest.SingleTarget(BlockType.FRAMED_INNER_PRISM)
    private static boolean testAgainstInnerPrism(DirectionAxis dirAxis, BlockState adjState, Direction side)
    {
        DirectionAxis adjDirAxis = adjState.getValue(PropertyHolder.FACING_AXIS);
        return getTriDir(dirAxis, side).isEqualTo(getTriDir(adjDirAxis, side.getOpposite()));
    }

    @CullTest.SingleTarget(BlockType.FRAMED_INNER_SLOPED_PRISM)
    private static boolean testAgainstInnerSlopedPrism(DirectionAxis dirAxis, BlockState adjState, Direction side)
    {
        CompoundDirection adjCmpDir = adjState.getValue(PropertyHolder.FACING_DIR);
        return getTriDir(dirAxis, side).isEqualTo(InnerSlopedPrismSkipPredicate.getTriDir(adjCmpDir, side.getOpposite()));
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_DOUBLE_PRISM,
            partTargets = BlockType.FRAMED_INNER_PRISM
    )
    private static boolean testAgainstDoublePrism(DirectionAxis dirAxis, BlockState adjState, Direction side)
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstInnerPrism(dirAxis, states.getA(), side);
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_DOUBLE_SLOPED_PRISM,
            partTargets = BlockType.FRAMED_INNER_SLOPED_PRISM
    )
    private static boolean testAgainstDoubleSlopedPrism(DirectionAxis dirAxis, BlockState adjState, Direction side)
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstInnerSlopedPrism(dirAxis, states.getA(), side);
    }



    public static HalfDir getTriDir(DirectionAxis dirAxis, Direction side)
    {
        Direction dir = dirAxis.direction();
        Direction.Axis axis = dirAxis.axis();
        if (dir.getAxis() != axis && side.getAxis() == axis)
        {
            return HalfDir.fromDirections(side, dir);
        }
        return HalfDir.NULL;
    }
}
