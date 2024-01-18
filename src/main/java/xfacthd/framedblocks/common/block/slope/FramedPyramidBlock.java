package xfacthd.framedblocks.common.block.slope;

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
import net.minecraft.world.phys.shapes.*;
import xfacthd.framedblocks.api.block.*;
import xfacthd.framedblocks.api.shapes.ShapeProvider;
import xfacthd.framedblocks.api.shapes.ShapeUtils;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.common.block.FramedBlock;
import xfacthd.framedblocks.common.data.BlockType;

public class FramedPyramidBlock extends FramedBlock
{
    public FramedPyramidBlock(BlockType type)
    {
        super(type);
        registerDefaultState(defaultBlockState()
                .setValue(BlockStateProperties.FACING, Direction.UP)
                .setValue(FramedProperties.Y_SLOPE, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(
                BlockStateProperties.FACING, FramedProperties.SOLID, BlockStateProperties.WATERLOGGED,
                FramedProperties.Y_SLOPE
        );
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx)
    {
        return PlacementStateBuilder.of(this, ctx)
                .withTargetFacing(true)
                .withWater()
                .build();
    }

    @Override
    public boolean handleBlockLeftClick(BlockState state, Level level, BlockPos pos, Player player)
    {
        return IFramedBlock.toggleYSlope(state, level, pos, player);
    }

    @Override
    public BlockState rotate(BlockState state, Direction side, Rotation rot)
    {
        return state.cycle(BlockStateProperties.FACING);
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, Rotation rot)
    {
        Direction dir = rot.rotate(state.getValue(BlockStateProperties.FACING));
        return state.setValue(BlockStateProperties.FACING, dir);
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirror)
    {
        return Utils.mirrorFaceBlock(state, BlockStateProperties.FACING, mirror);
    }

    @Override
    public BlockState getItemModelSource()
    {
        return defaultBlockState();
    }



    public static ShapeProvider generateShapes(ImmutableList<BlockState> states)
    {
        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();

        VoxelShape shapeUp = ShapeUtils.orUnoptimized(
                box( 0,  0,  0,   16, .5,   16),
                box(.5, .5, .5, 15.5,  4, 15.5),
                box( 2,  4,  2,   14,  8,   14),
                box( 4,  8,  4,   12, 12,   12),
                box( 6, 12,  6,   10, 16,   10)
        );

        VoxelShape shapeDown = ShapeUtils.orUnoptimized(
                box( 0, 15.5,  0,   16,   16,   16),
                box(.5,   12, .5, 15.5, 15.5, 15.5),
                box( 2,    8,  2,   14,   12,   14),
                box( 4,    4,  4,   12,    8,   12),
                box( 6,    0,  6,   10,    4,   10)
        );

        VoxelShape shapeNorth = ShapeUtils.orUnoptimized(
                box( 0,  0, 15.5,   16,   16,   16),
                box(.5, .5,   12, 15.5, 15.5, 15.5),
                box( 2,  2,    8,   14,   14,   12),
                box( 4,  4,    4,   12,   12,    8),
                box( 6,  6,    0,   10,   10,    4)
        );

        VoxelShape[] horShapes = ShapeUtils.makeHorizontalRotations(shapeNorth, Direction.NORTH);

        for (BlockState state : states)
        {
            Direction facing = state.getValue(BlockStateProperties.FACING);
            VoxelShape shape = switch (facing)
            {
                case UP -> shapeUp;
                case DOWN -> shapeDown;
                default -> horShapes[facing.get2DDataValue()];
            };
            builder.put(state, shape);
        }

        return ShapeProvider.of(builder.build());
    }

    public static ShapeProvider generateSlabShapes(ImmutableList<BlockState> states)
    {
        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();

        VoxelShape shapeUp = ShapeUtils.orUnoptimized(
                box( 0,  0,  0,   16, .5,   16),
                box(.5, .5, .5, 15.5,  2, 15.5),
                box( 2,  2,  2,   14,  4,   14),
                box( 4,  4,  4,   12,  6,   12),
                box( 6,  6,  6,   10,  8,   10)
        );

        VoxelShape shapeDown = ShapeUtils.orUnoptimized(
                box( 0, 15.5,  0,   16,   16,   16),
                box(.5,   14, .5, 15.5, 15.5, 15.5),
                box( 2,   12,  2,   14,   14,   14),
                box( 4,   10,  4,   12,   12,   12),
                box( 6,    8,  6,   10,   10,   10)
        );

        VoxelShape shapeNorth = ShapeUtils.orUnoptimized(
                box( 0,  0, 15.5,   16,   16,   16),
                box(.5, .5,   14, 15.5, 15.5, 15.5),
                box( 2,  2,   12,   14,   14,   14),
                box( 4,  4,   10,   12,   12,   12),
                box( 6,  6,    8,   10,   10,   10)
        );

        VoxelShape[] horShapes = ShapeUtils.makeHorizontalRotations(shapeNorth, Direction.NORTH);

        for (BlockState state : states)
        {
            Direction facing = state.getValue(BlockStateProperties.FACING);
            VoxelShape shape = switch (facing)
            {
                case UP -> shapeUp;
                case DOWN -> shapeDown;
                default -> horShapes[facing.get2DDataValue()];
            };
            builder.put(state, shape);
        }

        return ShapeProvider.of(builder.build());
    }
}
