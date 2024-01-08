package xfacthd.framedblocks.common.block.slopepanel;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.*;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.block.IFramedBlock;
import xfacthd.framedblocks.api.shapes.*;
import xfacthd.framedblocks.api.util.*;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.block.FramedBlock;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.data.PropertyHolder;
import xfacthd.framedblocks.common.data.property.HorizontalRotation;

public class FramedFlatExtendedSlopePanelCornerBlock extends FramedBlock
{
    public FramedFlatExtendedSlopePanelCornerBlock(BlockType type)
    {
        super(type);
        registerDefaultState(defaultBlockState().setValue(FramedProperties.Y_SLOPE, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(
                FramedProperties.FACING_HOR, PropertyHolder.ROTATION, FramedProperties.SOLID,
                BlockStateProperties.WATERLOGGED, FramedProperties.Y_SLOPE
        );
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return FramedFlatSlopePanelCornerBlock.getStateForPlacement(this, false, context);
    }

    @Override
    public boolean handleBlockLeftClick(BlockState state, Level level, BlockPos pos, Player player)
    {
        return IFramedBlock.toggleYSlope(state, level, pos, player);
    }

    @Override
    public BlockState rotate(BlockState state, BlockHitResult hit, Rotation rot)
    {
        Direction face = hit.getDirection();

        Direction dir = state.getValue(FramedProperties.FACING_HOR);
        HorizontalRotation rotation = state.getValue(PropertyHolder.ROTATION);
        Direction rotDir = rotation.withFacing(dir);
        Direction perpRotDir = rotation.rotate(Rotation.COUNTERCLOCKWISE_90).withFacing(dir);

        if (face == rotDir || face == perpRotDir)
        {
            if (getBlockType() == BlockType.FRAMED_FLAT_EXT_SLOPE_PANEL_CORNER)
            {
                double xz = Utils.fractionInDir(hit.getLocation(), dir.getOpposite());
                if (xz > .5)
                {
                    face = dir.getOpposite();
                }
            }
            else //FRAMED_FLAT_EXT_INNER_SLOPE_PANEL_CORNER
            {
                Vec3 vec = Utils.fraction(hit.getLocation());

                double hor = Utils.isX(dir) ? vec.x() : vec.z();
                if (!Utils.isPositive(dir))
                {
                    hor = 1D - hor;
                }

                Direction perpDir = face == rotDir ? perpRotDir : rotDir;
                double perpHor = Utils.isY(perpDir) ? vec.y() : (Utils.isX(dir) ? vec.z() : vec.x());
                if (perpDir == Direction.DOWN || (!Utils.isY(perpDir) && !Utils.isPositive(perpDir)))
                {
                    perpHor = 1F - perpHor;
                }
                if ((hor * 2D) < perpHor)
                {
                    face = dir.getOpposite();
                }
            }
        }

        return rotate(state, face, rot);
    }

    @Override
    public BlockState rotate(BlockState state, Direction face, Rotation rot)
    {
        Direction dir = state.getValue(FramedProperties.FACING_HOR);
        if (face.getAxis() == dir.getAxis())
        {
            HorizontalRotation rotation = state.getValue(PropertyHolder.ROTATION);
            return state.setValue(PropertyHolder.ROTATION, rotation.rotate(rot));
        }
        else if (Utils.isY(face))
        {
            return state.setValue(FramedProperties.FACING_HOR, rot.rotate(dir));
        }
        return state;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, Rotation rot)
    {
        return rotate(state, Direction.UP, rot);
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirror)
    {
        return FramedFlatSlopePanelCornerBlock.mirrorCorner(state, mirror);
    }

    @Override
    public BlockState getItemModelSource()
    {
        return switch ((BlockType) getBlockType())
        {
            case FRAMED_FLAT_EXT_SLOPE_PANEL_CORNER ->
                    FBContent.BLOCK_FRAMED_FLAT_EXTENDED_SLOPE_PANEL_CORNER.value()
                            .defaultBlockState()
                            .setValue(FramedProperties.FACING_HOR, Direction.SOUTH)
                            .setValue(PropertyHolder.ROTATION, HorizontalRotation.RIGHT);
            case FRAMED_FLAT_EXT_INNER_SLOPE_PANEL_CORNER ->
                    FBContent.BLOCK_FRAMED_FLAT_EXTENDED_INNER_SLOPE_PANEL_CORNER.value()
                            .defaultBlockState()
                            .setValue(FramedProperties.FACING_HOR, Direction.SOUTH)
                            .setValue(PropertyHolder.ROTATION, HorizontalRotation.RIGHT);
            default -> throw new IllegalStateException("Invalid block type: " + getBlockType());
        };
    }



    private record ShapeKey(Direction dir, HorizontalRotation rot) { }

    private static final ShapeCache<ShapeKey> FINAL_SHAPES = new ShapeCache<>(map ->
    {
        VoxelShape panelShape = box(0, 0, 0, 16, 16, 8);
        for (HorizontalRotation rot : HorizontalRotation.values())
        {
            VoxelShape shape = ShapeUtils.orUnoptimized(
                    panelShape,
                    ShapeUtils.andUnoptimized(
                            FramedSlopePanelBlock.SHAPES.get(rot),
                            FramedSlopePanelBlock.SHAPES.get(rot.rotate(Rotation.COUNTERCLOCKWISE_90))
                    ).move(0, 0, .5)
            );
            ShapeUtils.makeHorizontalRotations(shape, Direction.NORTH, map, rot, ShapeKey::new);
        }
    });

    public static ShapeProvider generateShapes(ImmutableList<BlockState> states)
    {
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        for (BlockState state : states)
        {
            Direction dir = state.getValue(FramedProperties.FACING_HOR);
            HorizontalRotation rot = state.getValue(PropertyHolder.ROTATION);
            builder.put(state, FINAL_SHAPES.get(new ShapeKey(dir, rot)));
        }

        return ShapeProvider.of(builder.build());
    }

    private static final ShapeCache<ShapeKey> FINAL_INNER_SHAPES = new ShapeCache<>(map ->
    {
        VoxelShape panelShape = box(0, 0, 0, 16, 16, 8);
        for (HorizontalRotation rot : HorizontalRotation.values())
        {
            VoxelShape shape = ShapeUtils.orUnoptimized(
                    panelShape,
                    ShapeUtils.orUnoptimized(
                            FramedSlopePanelBlock.SHAPES.get(rot),
                            FramedSlopePanelBlock.SHAPES.get(rot.rotate(Rotation.COUNTERCLOCKWISE_90))
                    ).move(0, 0, .5)
            );
            ShapeUtils.makeHorizontalRotations(shape, Direction.NORTH, map, rot, ShapeKey::new);
        }
    });

    public static ShapeProvider generateInnerShapes(ImmutableList<BlockState> states)
    {
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        for (BlockState state : states)
        {
            Direction dir = state.getValue(FramedProperties.FACING_HOR);
            HorizontalRotation rot = state.getValue(PropertyHolder.ROTATION);
            builder.put(state, FINAL_INNER_SHAPES.get(new ShapeKey(dir, rot)));
        }

        return ShapeProvider.of(builder.build());
    }
}
