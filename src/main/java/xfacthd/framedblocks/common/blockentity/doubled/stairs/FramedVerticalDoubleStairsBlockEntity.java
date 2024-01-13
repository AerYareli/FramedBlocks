package xfacthd.framedblocks.common.blockentity.doubled.stairs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.blockentity.doubled.FramedDoubleBlockEntity;

public class FramedVerticalDoubleStairsBlockEntity extends FramedDoubleBlockEntity
{
    public FramedVerticalDoubleStairsBlockEntity(BlockPos worldPosition, BlockState blockState)
    {
        super(FBContent.BE_TYPE_FRAMED_VERTICAL_DOUBLE_STAIRS.value(), worldPosition, blockState);
    }

    @Override
    protected boolean hitSecondary(BlockHitResult hit)
    {
        Direction facing = getBlockState().getValue(FramedProperties.FACING_HOR);
        Direction side = hit.getDirection();

        if (side == facing || side == facing.getCounterClockWise())
        {
            return false;
        }

        Vec3 vec = Utils.fraction(hit.getLocation());

        if (side == facing.getOpposite())
        {
            double xz = Utils.isX(facing) ? vec.z : vec.x;
            boolean positive = Utils.isPositive(facing.getCounterClockWise());
            return xz > .5 != positive;
        }
        else if (side == facing.getClockWise())
        {
            double xz = Utils.isX(facing) ? vec.x : vec.z;
            boolean positive = Utils.isPositive(facing);
            return xz > .5 != positive;
        }
        else
        {
            double xz = Utils.isX(facing) ? vec.x : vec.z;
            double xzCCW = Utils.isX(facing) ? vec.z : vec.x;

            boolean positive = Utils.isPositive(facing);
            boolean positiveCCW = Utils.isPositive(facing.getCounterClockWise());

            return (xzCCW > .5 != positiveCCW) && (xz > .5 != positive);
        }
    }
}
