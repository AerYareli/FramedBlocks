package xfacthd.framedblocks.client.model.slopepanelcorner;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.model.FramedBlockModel;
import xfacthd.framedblocks.api.model.quad.Modifiers;
import xfacthd.framedblocks.api.model.quad.QuadModifier;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.client.model.slopepanel.FramedSlopePanelModel;
import xfacthd.framedblocks.common.data.PropertyHolder;
import xfacthd.framedblocks.common.data.property.HorizontalRotation;

import java.util.List;
import java.util.Map;

public class FramedSmallCornerSlopePanelWallModel extends FramedBlockModel
{
    private final Direction dir;
    private final Direction horRotDir;
    private final Direction vertRotDir;
    private final boolean ySlope;

    public FramedSmallCornerSlopePanelWallModel(BlockState state, BakedModel baseModel)
    {
        super(state, baseModel);
        this.dir = state.getValue(FramedProperties.FACING_HOR);
        HorizontalRotation rot = state.getValue(PropertyHolder.ROTATION);
        Direction rotDir = rot.withFacing(dir);
        Direction perpRotDir = rot.rotate(Rotation.COUNTERCLOCKWISE_90).withFacing(dir);
        this.horRotDir = Utils.isY(rotDir) ? perpRotDir : rotDir;
        this.vertRotDir = Utils.isY(rotDir) ? rotDir : perpRotDir;
        this.ySlope = state.getValue(FramedProperties.Y_SLOPE);
    }

    @Override
    protected void transformQuad(Map<Direction, List<BakedQuad>> quadMap, BakedQuad quad)
    {
        Direction quadDir = quad.getDirection();
        boolean cw = horRotDir == dir.getClockWise();
        boolean up = vertRotDir == Direction.UP;
        if (quadDir == dir)
        {
            QuadModifier.geometry(quad)
                    .apply(Modifiers.cutSideLeftRight(horRotDir.getOpposite(), .5F))
                    .apply(Modifiers.cutSideUpDown(vertRotDir == Direction.UP, .5F))
                    .export(quadMap.get(quadDir));
        }
        else if (quadDir == horRotDir)
        {
            QuadModifier.geometry(quad)
                    .apply(Modifiers.cutSideUpDown(up, cw ? 0F : .5F, cw ? .5F : 0F))
                    .export(quadMap.get(quadDir));
        }
        else if (quadDir == vertRotDir)
        {
            QuadModifier.geometry(quad)
                    .apply(Modifiers.cutTopBottom(horRotDir.getOpposite(), cw ? .5F : 0F, cw ? 0F : .5F))
                    .export(quadMap.get(quadDir));
        }
        else if (quadDir == horRotDir.getOpposite())
        {
            QuadModifier.geometry(quad)
                    .apply(Modifiers.cutSideUpDown(up, cw ? .5F : 0F, cw ? 0F : .5F))
                    .apply(Modifiers.makeHorizontalSlope(!cw, FramedSlopePanelModel.SLOPE_ANGLE))
                    .apply(Modifiers.offset(horRotDir, .5F))
                    .export(quadMap.get(null));
        }
        else if (!ySlope && quadDir == dir.getOpposite())
        {
            QuadModifier.geometry(quad)
                    .apply(Modifiers.cutSideLeftRight(horRotDir.getOpposite(), up ? 0F : .5F, up ? .5F : 0F))
                    .apply(Modifiers.makeVerticalSlope(!up, FramedSlopePanelModel.SLOPE_ANGLE_VERT))
                    .export(quadMap.get(null));
        }
        else if (ySlope && quadDir == vertRotDir.getOpposite())
        {
            QuadModifier.geometry(quad)
                    .apply(Modifiers.cutTopBottom(horRotDir.getOpposite(), cw ? .5F : 0F, cw ? 0F : .5F))
                    .apply(Modifiers.makeVerticalSlope(dir.getOpposite(), FramedSlopePanelModel.SLOPE_ANGLE))
                    .apply(Modifiers.offset(vertRotDir, .5F))
                    .export(quadMap.get(null));
        }
    }
}
