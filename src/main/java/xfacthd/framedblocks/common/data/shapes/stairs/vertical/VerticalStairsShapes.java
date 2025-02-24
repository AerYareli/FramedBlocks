package xfacthd.framedblocks.common.data.shapes.stairs.vertical;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.shapes.*;
import xfacthd.framedblocks.common.data.PropertyHolder;
import xfacthd.framedblocks.common.data.property.StairsType;

public final class VerticalStairsShapes
{
    public static final ShapeCache<ShapeKey> SHAPES = ShapeCache.create(map ->
    {
        VoxelShape topBothShape = ShapeUtils.orUnoptimized(
                Block.box(8, 0, 8, 16, 16, 16),
                Block.box(8, 0, 0, 16, 8, 8),
                Block.box(0, 0, 8, 8, 8, 16)
        );

        VoxelShape bottomBothShape = ShapeUtils.orUnoptimized(
                Block.box(8, 0, 8, 16, 16, 16),
                Block.box(8, 8, 0, 16, 16, 8),
                Block.box(0, 8, 8, 8, 16, 16)
        );

        CommonShapes.STRAIGHT_VERTICAL_STAIRS.forEach((dir, shape) ->
                map.put(new ShapeKey(dir, StairsType.VERTICAL), shape)
        );

        ShapeUtils.makeHorizontalRotations(topBothShape, Direction.SOUTH, map, StairsType.TOP_CORNER, ShapeKey::new);
        ShapeUtils.makeHorizontalRotations(bottomBothShape, Direction.SOUTH, map, StairsType.BOTTOM_CORNER, ShapeKey::new);
    });

    public static ShapeProvider generate(ImmutableList<BlockState> states)
    {
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        for (BlockState state : states)
        {
            Direction dir = state.getValue(FramedProperties.FACING_HOR);
            StairsType type = state.getValue(PropertyHolder.STAIRS_TYPE);
            builder.put(state, SHAPES.get(new ShapeKey(dir, type)));
        }

        return ShapeProvider.of(builder.build());
    }



    public record ShapeKey(Direction dir, StairsType type) { }



    private VerticalStairsShapes() { }
}
