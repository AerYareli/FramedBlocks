package xfacthd.framedblocks.client.model.stairs;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.Direction;
import xfacthd.framedblocks.api.model.data.QuadMap;
import xfacthd.framedblocks.api.model.geometry.Geometry;
import xfacthd.framedblocks.api.model.wrapping.GeometryFactory;
import xfacthd.framedblocks.api.model.quad.Modifiers;
import xfacthd.framedblocks.api.model.quad.QuadModifier;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.util.Utils;

public class FramedVerticalHalfStairsGeometry extends Geometry
{
    private final Direction dir;
    private final boolean top;

    public FramedVerticalHalfStairsGeometry(GeometryFactory.Context ctx)
    {
        this.dir = ctx.state().getValue(FramedProperties.FACING_HOR);
        this.top = ctx.state().getValue(FramedProperties.TOP);
    }

    @Override
    public void transformQuad(QuadMap quadMap, BakedQuad quad)
    {
        Direction quadDir = quad.getDirection();
        if (quadDir == dir.getOpposite() || quadDir == dir.getClockWise())
        {
            QuadModifier.geometry(quad)
                    .apply(Modifiers.cutSideLeftRight(quadDir == dir.getOpposite() ? dir.getClockWise() : dir.getOpposite(), .5F))
                    .apply(Modifiers.cutSideUpDown(top, .5F))
                    .export(quadMap.get(quadDir));

            QuadModifier.geometry(quad)
                    .apply(Modifiers.cutSideLeftRight(quadDir == dir.getOpposite() ? dir.getCounterClockWise() : dir, .5F))
                    .apply(Modifiers.cutSideUpDown(top, .5F))
                    .apply(Modifiers.setPosition(.5F))
                    .export(quadMap.get(null));
        }
        else if (quadDir == dir || quadDir == dir.getCounterClockWise())
        {
            QuadModifier.geometry(quad)
                    .apply(Modifiers.cutSideUpDown(top, .5F))
                    .export(quadMap.get(quadDir));
        }
        else if (Utils.isY(quadDir))
        {
            boolean inset = (quadDir == Direction.UP) != top;

            QuadModifier.geometry(quad)
                    .apply(Modifiers.cutTopBottom(dir.getOpposite(), .5F))
                    .applyIf(Modifiers.setPosition(.5F), inset)
                    .export(quadMap.get(inset ? null : quadDir));

            QuadModifier.geometry(quad)
                    .apply(Modifiers.cutTopBottom(dir, .5F))
                    .apply(Modifiers.cutTopBottom(dir.getClockWise(), .5F))
                    .applyIf(Modifiers.setPosition(.5F), inset)
                    .export(quadMap.get(inset ? null : quadDir));
        }
    }
}
