package xfacthd.framedblocks.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import xfacthd.framedblocks.api.util.FramedProperties;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.data.PropertyHolder;
import xfacthd.framedblocks.common.data.SlopeType;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.common.util.DoubleSoundMode;

public class FramedDoubleSlopeBlockEntity extends FramedDoubleBlockEntity
{
    public FramedDoubleSlopeBlockEntity(BlockPos pos, BlockState state)
    {
        super(FBContent.blockEntityTypeDoubleFramedSlope.get(), pos, state);
    }

    @Override
    protected boolean hitSecondary(BlockHitResult hit)
    {
        SlopeType type = getBlockState().getValue(PropertyHolder.SLOPE_TYPE);
        Direction facing = getBlockState().getValue(PropertyHolder.FACING_HOR);
        Direction side = hit.getDirection();

        Vec3 vec = Utils.fraction(hit.getLocation());

        if (type == SlopeType.HORIZONTAL)
        {
            if (side == facing || side == facing.getCounterClockWise()) { return false; }
            if (side == facing.getOpposite() || side == facing.getClockWise()) { return true; }

            boolean secondary = Utils.isX(facing) ? vec.x() >= vec.z() : vec.z() >= (1D - vec.x());

            if (Utils.isPositive(facing)) { secondary = !secondary; }
            return secondary;
        }
        else
        {
            double hor = Utils.isX(facing) ? vec.x() : vec.z();
            if (!Utils.isPositive(facing))
            {
                hor = 1D - hor;
            }

            if (type == SlopeType.TOP)
            {
                if (side == facing || side == Direction.UP) { return false; }
                if (side == facing.getOpposite() || side == Direction.DOWN) { return true; }
                return vec.y() <= (1D - hor);
            }
            else if (type == SlopeType.BOTTOM)
            {
                if (side == facing || side == Direction.DOWN) { return false; }
                if (side == facing.getOpposite() || side == Direction.UP) { return true; }
                return vec.y() >= hor;
            }
        }

        return false;
    }

    @Override
    public DoubleSoundMode getSoundMode()
    {
        SlopeType type = getBlockState().getValue(PropertyHolder.SLOPE_TYPE);
        if (type == SlopeType.BOTTOM)
        {
            return DoubleSoundMode.SECOND;
        }
        else if (type == SlopeType.TOP)
        {
            return DoubleSoundMode.FIRST;
        }
        return DoubleSoundMode.EITHER;
    }

    @Override
    public BlockState getCamoState(Direction side)
    {
        SlopeType type = getBlockState().getValue(PropertyHolder.SLOPE_TYPE);
        Direction facing = getBlockState().getValue(PropertyHolder.FACING_HOR);

        if (type == SlopeType.HORIZONTAL)
        {
            if (side == facing || side == facing.getCounterClockWise()) { return getCamoState(); }
            if (side == facing.getOpposite() || side == facing.getClockWise()) { return getCamoStateTwo(); }
        }
        else if (type == SlopeType.TOP)
        {
            if (side == facing || side == Direction.UP) { return getCamoState(); }
            if (side == facing.getOpposite() || side == Direction.DOWN) { return getCamoStateTwo(); }
        }
        else if (type == SlopeType.BOTTOM)
        {
            if (side == facing || side == Direction.DOWN) { return getCamoState(); }
            if (side == facing.getOpposite() || side == Direction.UP) { return getCamoStateTwo(); }
        }

        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean isSolidSide(Direction side)
    {
        BlockState state = getCamoState(side);
        if (!state.isAir())
        {
            //noinspection ConstantConditions
            return state.isSolidRender(level, worldPosition);
        }
        //noinspection ConstantConditions
        return getCamoState().isSolidRender(level, worldPosition) && getCamoStateTwo().isSolidRender(level, worldPosition);
    }

    @Override
    protected Tuple<BlockState, BlockState> getBlockPair(BlockState state)
    {
        return getBlockPair(state.getValue(PropertyHolder.SLOPE_TYPE), state.getValue(FramedProperties.FACING_HOR));
    }

    public static Tuple<BlockState, BlockState> getBlockPair(SlopeType type, Direction facing)
    {
        BlockState defState = FBContent.blockFramedSlope.get().defaultBlockState();
        return new Tuple<>(
                defState.setValue(PropertyHolder.SLOPE_TYPE, type)
                        .setValue(PropertyHolder.FACING_HOR, facing),
                defState.setValue(PropertyHolder.SLOPE_TYPE, type == SlopeType.HORIZONTAL ? type : type.getOpposite())
                        .setValue(PropertyHolder.FACING_HOR, facing.getOpposite())
        );
    }
}