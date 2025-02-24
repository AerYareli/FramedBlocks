package xfacthd.framedblocks.common.blockentity.doubled;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.blockentity.FramedDoubleBlockEntity;
import xfacthd.framedblocks.common.data.PropertyHolder;
import xfacthd.framedblocks.common.data.property.HorizontalRotation;

public class FramedLargeDoubleCornerSlopePanelWallBlockEntity extends FramedDoubleBlockEntity
{
    public FramedLargeDoubleCornerSlopePanelWallBlockEntity(BlockPos pos, BlockState state)
    {
        super(FBContent.BE_TYPE_FRAMED_LARGE_DOUBLE_CORNER_SLOPE_PANEL_WALL.get(), pos, state);
    }

    @Override
    protected boolean hitSecondary(BlockHitResult hit)
    {
        Direction side = hit.getDirection();
        Direction dir = getBlockState().getValue(FramedProperties.FACING_HOR);
        if (side == dir)
        {
            return false;
        }
        else if (side == dir.getOpposite())
        {
            return true;
        }

        HorizontalRotation rot = getBlockState().getValue(PropertyHolder.ROTATION);
        Direction rotDir = rot.withFacing(dir);
        Direction perpRotDir = rot.rotate(Rotation.COUNTERCLOCKWISE_90).withFacing(dir);

        if (side == rotDir.getOpposite() || side == perpRotDir.getOpposite())
        {
            return false;
        }

        Vec3 hitVec = hit.getLocation();
        double xzDir = Utils.fractionInDir(hitVec, dir);
        double xzPerp;
        if (Utils.isY(side))
        {
            xzPerp = Utils.fractionInDir(hitVec, Utils.isY(rotDir) ? perpRotDir : rotDir);
        }
        else
        {
            xzPerp = Utils.fractionInDir(hitVec, Utils.isY(rotDir) ? rotDir : perpRotDir);
        }

        if (xzPerp > .5)
        {
            return true;
        }

        return (xzPerp * 2D) > xzDir;
    }
}
