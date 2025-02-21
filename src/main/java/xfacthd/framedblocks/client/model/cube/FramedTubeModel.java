package xfacthd.framedblocks.client.model.cube;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import xfacthd.framedblocks.api.model.FramedBlockModel;
import xfacthd.framedblocks.api.model.quad.Modifiers;
import xfacthd.framedblocks.api.model.quad.QuadModifier;
import xfacthd.framedblocks.api.util.Utils;

import java.util.List;
import java.util.Map;

public class FramedTubeModel extends FramedBlockModel
{
    private final Direction.Axis axis;

    public FramedTubeModel(BlockState state, BakedModel baseModel)
    {
        super(state, baseModel);
        this.axis = state.getValue(BlockStateProperties.AXIS);
    }

    @Override
    protected void transformQuad(Map<Direction, List<BakedQuad>> quadMap, BakedQuad quad)
    {
        Direction quadDir = quad.getDirection();
        if (axis == Direction.Axis.Y)
        {
            if (quadDir.getAxis() == axis)
            {
                QuadModifier.geometry(quad)
                        .apply(Modifiers.cutTopBottom(Direction.NORTH, 2F / 16F))
                        .export(quadMap.get(quadDir));
                QuadModifier.geometry(quad)
                        .apply(Modifiers.cutTopBottom(Direction.SOUTH, 2F / 16F))
                        .export(quadMap.get(quadDir));
                QuadModifier.geometry(quad)
                        .apply(Modifiers.cutTopBottom(Direction.EAST, 2F / 16F))
                        .apply(Modifiers.cutTopBottom(Direction.Axis.Z, 14F / 16F))
                        .export(quadMap.get(quadDir));
                QuadModifier.geometry(quad)
                        .apply(Modifiers.cutTopBottom(Direction.WEST, 2F / 16))
                        .apply(Modifiers.cutTopBottom(Direction.Axis.Z, 14F / 16F))
                        .export(quadMap.get(quadDir));
            }
            else
            {
                QuadModifier.geometry(quad)
                        .apply(Modifiers.cutSideLeftRight(14F/16F))
                        .apply(Modifiers.setPosition(2F/16F))
                        .export(quadMap.get(null));
            }
        }
        else
        {
            if (quadDir.getAxis() == axis)
            {
                QuadModifier.geometry(quad)
                        .apply(Modifiers.cutSideUpDown(false, 2F / 16F))
                        .export(quadMap.get(quadDir));
                QuadModifier.geometry(quad)
                        .apply(Modifiers.cutSideUpDown(true, 2F / 16F))
                        .export(quadMap.get(quadDir));
                QuadModifier.geometry(quad)
                        .apply(Modifiers.cutSideLeftRight(quadDir.getClockWise(), 2F / 16F))
                        .apply(Modifiers.cutSideUpDown(14F / 16F))
                        .export(quadMap.get(quadDir));
                QuadModifier.geometry(quad)
                        .apply(Modifiers.cutSideLeftRight(quadDir.getCounterClockWise(), 2F / 16F))
                        .apply(Modifiers.cutSideUpDown(14F / 16F))
                        .export(quadMap.get(quadDir));
            }
            else if (Utils.isY(quadDir))
            {
                QuadModifier.geometry(quad)
                        .apply(Modifiers.cutTopBottom(Utils.nextAxisNotEqualTo(axis, Direction.Axis.Y), 14F/16F))
                        .apply(Modifiers.setPosition(2F/16F))
                        .export(quadMap.get(null));
            }
            else
            {
                QuadModifier.geometry(quad)
                        .apply(Modifiers.cutSideUpDown(14F/16F))
                        .apply(Modifiers.setPosition(2F/16F))
                        .export(quadMap.get(null));
            }
        }
    }

    @Override
    protected boolean transformAllQuads(BlockState state)
    {
        return true;
    }
}
