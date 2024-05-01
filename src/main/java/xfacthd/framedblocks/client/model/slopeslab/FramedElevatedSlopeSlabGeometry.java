package xfacthd.framedblocks.client.model.slopeslab;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.Direction;
import xfacthd.framedblocks.api.model.data.QuadMap;
import xfacthd.framedblocks.api.model.geometry.Geometry;
import xfacthd.framedblocks.api.model.wrapping.GeometryFactory;
import xfacthd.framedblocks.api.model.quad.Modifiers;
import xfacthd.framedblocks.api.model.quad.QuadModifier;
import xfacthd.framedblocks.api.block.FramedProperties;

public class FramedElevatedSlopeSlabGeometry extends Geometry
{
    private final Direction facing;
    private final boolean top;
    private final boolean ySlope;

    public FramedElevatedSlopeSlabGeometry(GeometryFactory.Context ctx)
    {
        this.facing = ctx.state().getValue(FramedProperties.FACING_HOR);
        this.top = ctx.state().getValue(FramedProperties.TOP);
        this.ySlope = ctx.state().getValue(FramedProperties.Y_SLOPE);
    }

    @Override
    public void transformQuad(QuadMap quadMap, BakedQuad quad)
    {
        Direction face = quad.getDirection();
        if (face == facing.getOpposite())
        {
            if (!ySlope)
            {
                QuadModifier.of(quad)
                        .apply(Modifiers.makeVerticalSlope(!top, FramedSlopeSlabGeometry.SLOPE_ANGLE))
                        .apply(Modifiers.offset(top ? Direction.DOWN : Direction.UP, .5F))
                        .export(quadMap.get(null));
            }

            QuadModifier.of(quad)
                    .apply(Modifiers.cutSideUpDown(top, .5F))
                    .export(quadMap.get(face));
        }
        else if (ySlope && ((!top && face == Direction.UP) || (top && face == Direction.DOWN)))
        {
            QuadModifier.of(quad)
                    .apply(Modifiers.makeVerticalSlope(facing.getOpposite(), FramedSlopeSlabGeometry.SLOPE_ANGLE_VERT))
                    .export(quadMap.get(null));
        }
        else if (face == facing.getClockWise() || face == facing.getCounterClockWise())
        {
            boolean rightFace = face == facing.getClockWise();
            QuadModifier.of(quad)
                    .apply(Modifiers.cutSideUpDown(top, rightFace ? .5F : 1, rightFace ? 1 : .5F))
                    .export(quadMap.get(face));
        }
    }
}
