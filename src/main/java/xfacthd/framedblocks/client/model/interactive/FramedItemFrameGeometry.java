package xfacthd.framedblocks.client.model.interactive;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.data.ModelData;
import xfacthd.framedblocks.api.model.data.QuadMap;
import xfacthd.framedblocks.api.model.geometry.Geometry;
import xfacthd.framedblocks.api.model.wrapping.GeometryFactory;
import xfacthd.framedblocks.api.model.quad.Modifiers;
import xfacthd.framedblocks.api.model.quad.QuadModifier;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.api.util.ClientUtils;
import xfacthd.framedblocks.api.model.util.ModelUtils;
import xfacthd.framedblocks.common.data.PropertyHolder;

import java.util.List;

public class FramedItemFrameGeometry extends Geometry
{
    private static final int GLOWING_BRIGHTNESS = 5;

    private final BlockState state;
    private final BakedModel baseModel;
    private final Direction facing;
    private final boolean leather;
    private final boolean mapFrame;
    private final boolean glowing;
    private final float innerLength;
    private final float innerPos;
    private final float innerMin;
    private final float innerMax;
    private final float outerMin;
    private final float outerMax;

    private FramedItemFrameGeometry(GeometryFactory.Context ctx, boolean glowing)
    {
        this.state = ctx.state();
        this.baseModel = ctx.baseModel();
        this.facing = ctx.state().getValue(BlockStateProperties.FACING);
        this.leather = ctx.state().getValue(PropertyHolder.LEATHER);
        this.mapFrame = ctx.state().getValue(PropertyHolder.MAP_FRAME);
        this.glowing = glowing;

        this.innerLength = mapFrame ? 15F/16F : 13F/16F;
        this.innerPos = mapFrame ? 1F/16F : 3F/16F;
        this.innerMin = mapFrame ? 1F/16F : 3F/16F;
        this.innerMax = mapFrame ? 15F/16F : 13F/16F;
        this.outerMin = mapFrame ? 0F : 2F/16F;
        this.outerMax = mapFrame ? 1F : 14F/16F;
    }

    @Override
    public void transformQuad(QuadMap quadMap, BakedQuad quad)
    {
        Direction quadFace = quad.getDirection();
        if (Utils.isY(facing))
        {
            makeVerticalFrame(quadMap, quad, quadFace);
        }
        else
        {
            makeHorizontalFrame(quadMap, quad, quadFace);
        }
    }

    private void makeVerticalFrame(QuadMap quadMap, BakedQuad quad, Direction quadFace)
    {
        if (quadFace == facing)
        {
            QuadModifier.full(quad)
                    .applyIf(Modifiers.cutTopBottom(outerMin, outerMin, outerMax, outerMax), !mapFrame)
                    .applyIf(Modifiers.applyLightmap(GLOWING_BRIGHTNESS, 0), glowing)
                    .export(quadMap.get(quadFace));
        }
        else if (quadFace == facing.getOpposite())
        {
            if (!leather && !mapFrame)
            {
                QuadModifier.full(quad)
                        .apply(Modifiers.cutTopBottom(innerMin, innerMin, innerMax, innerMax))
                        .applyIf(Modifiers.applyLightmap(GLOWING_BRIGHTNESS, 0), glowing)
                        .apply(Modifiers.setPosition(.5F/16F))
                        .export(quadMap.get(null));
            }

            if (!mapFrame || leather)
            {
                QuadModifier.full(quad)
                        .apply(Modifiers.cutTopBottom(outerMin, outerMin, innerMin, outerMax))
                        .applyIf(Modifiers.applyLightmap(GLOWING_BRIGHTNESS, 0), glowing)
                        .apply(Modifiers.setPosition(1F / 16F))
                        .export(quadMap.get(null));

                QuadModifier.full(quad)
                        .apply(Modifiers.cutTopBottom(innerMax, outerMin, outerMax, outerMax))
                        .applyIf(Modifiers.applyLightmap(GLOWING_BRIGHTNESS, 0), glowing)
                        .apply(Modifiers.setPosition(1F / 16F))
                        .export(quadMap.get(null));

                QuadModifier.full(quad)
                        .apply(Modifiers.cutTopBottom(innerMin, outerMin, innerMax, innerMin))
                        .applyIf(Modifiers.applyLightmap(GLOWING_BRIGHTNESS, 0), glowing)
                        .apply(Modifiers.setPosition(1F / 16F))
                        .export(quadMap.get(null));

                QuadModifier.full(quad)
                        .apply(Modifiers.cutTopBottom(innerMin, innerMax, innerMax, outerMax))
                        .applyIf(Modifiers.applyLightmap(GLOWING_BRIGHTNESS, 0), glowing)
                        .apply(Modifiers.setPosition(1F / 16F))
                        .export(quadMap.get(null));
            }

            if (mapFrame && !leather)
            {
                QuadModifier.full(quad)
                        .applyIf(Modifiers.applyLightmap(GLOWING_BRIGHTNESS, 0), glowing)
                        .apply(Modifiers.setPosition(1F/16F))
                        .export(quadMap.get(quadFace));
            }
        }
        else
        {
            boolean down = facing == Direction.UP;

            QuadModifier.full(quad)
                    .apply(Modifiers.cutSideUpDown(down, 1F/16F))
                    .applyIf(Modifiers.cutSideLeftRight(outerMax), !mapFrame)
                    .applyIf(Modifiers.applyLightmap(GLOWING_BRIGHTNESS, 0), glowing)
                    .applyIf(Modifiers.setPosition(outerMax), !mapFrame)
                    .export(quadMap.get(null));

            if (!mapFrame)
            {
                QuadModifier.full(quad)
                        .apply(Modifiers.cutSideUpDown(!down, 15.5F / 16F))
                        .apply(Modifiers.cutSideUpDown(down, 1F / 16F))
                        .apply(Modifiers.cutSideLeftRight(innerLength))
                        .applyIf(Modifiers.applyLightmap(GLOWING_BRIGHTNESS, 0), glowing)
                        .apply(Modifiers.setPosition(innerPos))
                        .export(quadMap.get(null));
            }
        }
    }

    private void makeHorizontalFrame(QuadMap quadMap, BakedQuad quad, Direction quadFace)
    {
        if (quadFace == facing)
        {
            QuadModifier.full(quad)
                    .applyIf(Modifiers.cutSide(outerMin, outerMin, outerMax, outerMax), !mapFrame)
                    .applyIf(Modifiers.applyLightmap(GLOWING_BRIGHTNESS, 0), glowing)
                    .export(quadMap.get(quadFace));
        }
        else if (quadFace == facing.getOpposite())
        {
            if (!leather && !mapFrame)
            {
                QuadModifier.full(quad)
                        .apply(Modifiers.cutSide(innerMin, innerMin, innerMax, innerMax))
                        .applyIf(Modifiers.applyLightmap(GLOWING_BRIGHTNESS, 0), glowing)
                        .apply(Modifiers.setPosition(.5F/16F))
                        .export(quadMap.get(null));
            }

            if (!mapFrame || leather)
            {
                QuadModifier.full(quad)
                        .apply(Modifiers.cutSide(outerMin, outerMin, innerMin, outerMax))
                        .applyIf(Modifiers.applyLightmap(GLOWING_BRIGHTNESS, 0), glowing)
                        .apply(Modifiers.setPosition(1F/16F))
                        .export(quadMap.get(null));

                QuadModifier.full(quad)
                        .apply(Modifiers.cutSide(innerMax, outerMin, outerMax, outerMax))
                        .applyIf(Modifiers.applyLightmap(GLOWING_BRIGHTNESS, 0), glowing)
                        .apply(Modifiers.setPosition(1F/16F))
                        .export(quadMap.get(null));

                QuadModifier.full(quad)
                        .apply(Modifiers.cutSide(innerMin, outerMin, innerMax, innerMin))
                        .applyIf(Modifiers.applyLightmap(GLOWING_BRIGHTNESS, 0), glowing)
                        .apply(Modifiers.setPosition(1F/16F))
                        .export(quadMap.get(null));

                QuadModifier.full(quad)
                        .apply(Modifiers.cutSide(innerMin, innerMax, innerMax, outerMax))
                        .applyIf(Modifiers.applyLightmap(GLOWING_BRIGHTNESS, 0), glowing)
                        .apply(Modifiers.setPosition(1F/16F))
                        .export(quadMap.get(null));
            }

            if (mapFrame && !leather)
            {
                QuadModifier.full(quad)
                        .applyIf(Modifiers.applyLightmap(GLOWING_BRIGHTNESS, 0), glowing)
                        .apply(Modifiers.setPosition(1F/16F))
                        .export(quadMap.get(quadFace));
            }
        }
        else if (Utils.isY(quadFace))
        {
            QuadModifier.full(quad)
                    .apply(Modifiers.cutTopBottom(facing.getOpposite(), 1F/16F))
                    .applyIf(Modifiers.cutTopBottom(facing.getClockWise().getAxis(), outerMax), !mapFrame)
                    .applyIf(Modifiers.applyLightmap(GLOWING_BRIGHTNESS, 0), glowing)
                    .applyIf(Modifiers.setPosition(outerMax), !mapFrame)
                    .export(quadMap.get(null));

            if (!mapFrame)
            {
                QuadModifier.full(quad)
                        .apply(Modifiers.cutTopBottom(facing, 15.5F/16F))
                        .apply(Modifiers.cutTopBottom(facing.getOpposite(), 1F/16F))
                        .apply(Modifiers.cutTopBottom(facing.getClockWise().getAxis(), innerLength))
                        .applyIf(Modifiers.applyLightmap(GLOWING_BRIGHTNESS, 0), glowing)
                        .apply(Modifiers.setPosition(innerPos))
                        .export(quadMap.get(null));
            }
        }
        else
        {
            QuadModifier.full(quad)
                    .apply(Modifiers.cutSideLeftRight(facing.getOpposite(), 1F/16F))
                    .applyIf(Modifiers.cutSideUpDown(outerMax), !mapFrame)
                    .applyIf(Modifiers.applyLightmap(GLOWING_BRIGHTNESS, 0), glowing)
                    .applyIf(Modifiers.setPosition(outerMax), !mapFrame)
                    .export(quadMap.get(null));

            if (!mapFrame)
            {
                QuadModifier.full(quad)
                        .apply(Modifiers.cutSideLeftRight(facing, 15.5F/16F))
                        .apply(Modifiers.cutSideLeftRight(facing.getOpposite(), 1F/16F))
                        .apply(Modifiers.cutSideUpDown(innerLength))
                        .applyIf(Modifiers.applyLightmap(GLOWING_BRIGHTNESS, 0), glowing)
                        .apply(Modifiers.setPosition(innerPos))
                        .export(quadMap.get(null));
            }
        }
    }

    @Override
    public ChunkRenderTypeSet getAdditionalRenderTypes(RandomSource rand, ModelData extraData)
    {
        if (leather)
        {
            return ModelUtils.SOLID;
        }
        return ChunkRenderTypeSet.none();
    }

    @Override
    public void getAdditionalQuads(QuadMap quadMap, RandomSource rand, ModelData data, RenderType renderType)
    {
        if (leather)
        {
            List<BakedQuad> quads = baseModel.getQuads(state, null, rand, data, renderType);
            for (BakedQuad quad : quads)
            {
                if (!ClientUtils.isDummyTexture(quad))
                {
                    if (glowing)
                    {
                        QuadModifier.full(quad)
                                .apply(Modifiers.applyLightmap(GLOWING_BRIGHTNESS, 0))
                                .modifyInPlace();
                    }
                    quadMap.get(null).add(quad);
                }
            }
        }
    }



    public static FramedItemFrameGeometry normal(GeometryFactory.Context ctx)
    {
        return new FramedItemFrameGeometry(ctx, false);
    }

    public static FramedItemFrameGeometry glowing(GeometryFactory.Context ctx)
    {
        return new FramedItemFrameGeometry(ctx, true);
    }
}
