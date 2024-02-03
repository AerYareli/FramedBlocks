package xfacthd.framedblocks.client.model.slope;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.Direction;
import xfacthd.framedblocks.api.model.data.QuadMap;
import xfacthd.framedblocks.api.model.geometry.Geometry;
import xfacthd.framedblocks.api.model.wrapping.GeometryFactory;
import xfacthd.framedblocks.api.model.quad.Modifiers;
import xfacthd.framedblocks.api.model.quad.QuadModifier;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.common.data.PropertyHolder;
import xfacthd.framedblocks.common.data.property.CornerType;

public class FramedCornerSlopeGeometry extends Geometry
{
    private final Direction dir;
    private final CornerType type;
    private final boolean ySlope;

    public FramedCornerSlopeGeometry(GeometryFactory.Context ctx)
    {
        this.dir = ctx.state().getValue(FramedProperties.FACING_HOR);
        this.type = ctx.state().getValue(PropertyHolder.CORNER_TYPE);
        this.ySlope = ctx.state().getValue(FramedProperties.Y_SLOPE);
    }

    @Override
    public void transformQuad(QuadMap quadMap, BakedQuad quad)
    {
        if (type.isHorizontal())
        {
            createHorizontalCornerSlope(quadMap, quad);
        }
        else
        {
            createVerticalCornerSlope(quadMap, quad);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void createHorizontalCornerSlope(QuadMap quadMap, BakedQuad quad)
    {
        Direction quadDir = quad.getDirection();
        boolean top = type.isTop();
        boolean right = type.isRight();

        if ((quadDir == dir.getClockWise() && right) || (quadDir == dir.getCounterClockWise() && !right))
        {
            QuadModifier.geometry(quad)
                    .apply(Modifiers.cutSideLeftRight(dir.getOpposite(), top ? 1 : 0, top ? 0 : 1))
                    .export(quadMap.get(quadDir));
        }
        else if ((quadDir == Direction.UP && top) || (quadDir == Direction.DOWN && !top))
        {
            QuadModifier.geometry(quad)
                    .apply(Modifiers.cutTopBottom(dir.getOpposite(), right ? 0 : 1, right ? 1 : 0))
                    .export(quadMap.get(quadDir));
        }
        else if ((quadDir == dir.getCounterClockWise() && right) || (quadDir == dir.getClockWise() && !right))
        {
            QuadModifier.geometry(quad)
                    .apply(Modifiers.cutSideLeftRight(dir.getOpposite(), top ? 1 : 0, top ? 0 : 1))
                    .apply(Modifiers.makeHorizontalSlope(!right, 45))
                    .export(quadMap.get(null));
        }
        else if (!ySlope && quadDir == dir.getOpposite())
        {
            QuadModifier.geometry(quad)
                    .apply(Modifiers.cutSideUpDown(!top, right ? 0 : 1, right ? 1 : 0))
                    .apply(Modifiers.makeVerticalSlope(!top, 45))
                    .export(quadMap.get(null));
        }
        else if (ySlope && ((!top && quadDir == Direction.UP) || (top && quadDir == Direction.DOWN)))
        {
            QuadModifier.geometry(quad)
                    .apply(Modifiers.cutTopBottom(dir.getOpposite(), right ? 0 : 1, right ? 1 : 0))
                    .apply(Modifiers.makeVerticalSlope(dir.getOpposite(), 45))
                    .export(quadMap.get(null));
        }
    }

    private void createVerticalCornerSlope(QuadMap quadMap, BakedQuad quad)
    {
        Direction quadDir = quad.getDirection();
        boolean yQuad = Utils.isY(quadDir);
        if (!ySlope && yQuad)
        {
            return;
        }

        boolean top = type.isTop();
        Direction cutDir = quadDir.getAxis() == dir.getAxis() ? dir.getClockWise() : dir.getOpposite();
        boolean slope = quadDir == dir.getOpposite() || quadDir == dir.getClockWise();

        if ((!slope && !yQuad) || !ySlope)
        {
            QuadModifier.geometry(quad)
                    .apply(Modifiers.cutSideLeftRight(cutDir, top ? 1 : 0, top ? 0 : 1))
                    .applyIf(Modifiers.makeVerticalSlope(!top, 45), slope)
                    .export(quadMap.get(slope ? null : quadDir));
        }
        else if (yQuad)
        {
            QuadModifier.geometry(quad)
                    .apply(Modifiers.cutTopBottom(dir.getOpposite(), 0, 1))
                    .apply(Modifiers.makeVerticalSlope(dir.getClockWise(), 45))
                    .export(quadMap.get(null));

            QuadModifier.geometry(quad)
                    .apply(Modifiers.cutTopBottom(dir.getClockWise(), 1, 0))
                    .apply(Modifiers.makeVerticalSlope(dir.getOpposite(), 45))
                    .export(quadMap.get(null));
        }
    }
}