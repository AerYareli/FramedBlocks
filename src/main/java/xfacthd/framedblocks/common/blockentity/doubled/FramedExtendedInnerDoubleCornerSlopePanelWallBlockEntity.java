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

public class FramedExtendedInnerDoubleCornerSlopePanelWallBlockEntity extends FramedDoubleBlockEntity
{
    public FramedExtendedInnerDoubleCornerSlopePanelWallBlockEntity(BlockPos pos, BlockState state)
    {
        super(FBContent.BE_TYPE_FRAMED_EXTENDED_INNER_DOUBLE_CORNER_SLOPE_PANEL_WALL.get(), pos, state);
    }

    @Override
    protected boolean hitSecondary(BlockHitResult hit)
    {
        Direction side = hit.getDirection();
        Direction dir = getBlockState().getValue(FramedProperties.FACING_HOR);
        HorizontalRotation rot = getBlockState().getValue(PropertyHolder.ROTATION);
        Direction rotDir = rot.withFacing(dir);
        Direction perpRotDir = rot.rotate(Rotation.COUNTERCLOCKWISE_90).withFacing(dir);

        if (side == dir || side == rotDir.getOpposite() || side == perpRotDir.getOpposite())
        {
            return false;
        }

        Vec3 hitVec = hit.getLocation();
        if (side == dir.getOpposite())
        {
            double xz1 = Utils.fractionInDir(hitVec, rotDir);
            double xz2 = Utils.fractionInDir(hitVec, perpRotDir);
            return xz1 > .5 && xz2 > .5;
        }

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

        if (xzPerp < .5)
        {
            return false;
        }
        return ((xzPerp - .5) * 2D) > xzDir;
    }
}
