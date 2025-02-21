package xfacthd.framedblocks.common.block.stairs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.block.PlacementStateBuilder;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.block.AbstractFramedDoubleBlock;
import xfacthd.framedblocks.common.blockentity.doubled.FramedSlicedStairsBlockEntity;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.data.doubleblock.*;
import xfacthd.framedblocks.common.util.DoubleBlockTopInteractionMode;

public class FramedSlicedStairsBlock extends AbstractFramedDoubleBlock
{
    private final boolean panel;

    public FramedSlicedStairsBlock(BlockType type)
    {
        super(type);
        this.panel = type == BlockType.FRAMED_SLICED_STAIRS_PANEL;
        registerDefaultState(defaultBlockState().setValue(FramedProperties.TOP, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(FramedProperties.FACING_HOR, FramedProperties.TOP, BlockStateProperties.WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx)
    {
        return PlacementStateBuilder.of(this, ctx)
                .withHorizontalFacing()
                .withTop()
                .build();
    }

    @Override
    public BlockState rotate(BlockState state, Direction face, Rotation rot)
    {
        if (Utils.isY(face))
        {
            Direction dir = state.getValue(FramedProperties.FACING_HOR);
            return state.setValue(FramedProperties.FACING_HOR, rot.rotate(dir));
        }
        else if (rot != Rotation.NONE)
        {
            return state.cycle(FramedProperties.TOP);
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
        return Utils.mirrorFaceBlock(state, mirror);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new FramedSlicedStairsBlockEntity(pos, state);
    }

    @Override
    public DoubleBlockTopInteractionMode calculateTopInteractionMode(BlockState state)
    {
        if (panel || !state.getValue(FramedProperties.TOP))
        {
            return DoubleBlockTopInteractionMode.EITHER;
        }
        return DoubleBlockTopInteractionMode.FIRST;
    }

    @Override
    public Tuple<BlockState, BlockState> calculateBlockPair(BlockState state)
    {
        Direction dir = state.getValue(FramedProperties.FACING_HOR);
        boolean top = state.getValue(FramedProperties.TOP);
        if (panel)
        {
            return new Tuple<>(
                    FBContent.BLOCK_FRAMED_PANEL.get()
                            .defaultBlockState()
                            .setValue(FramedProperties.FACING_HOR, dir),
                    FBContent.BLOCK_FRAMED_SLAB_EDGE.get()
                            .defaultBlockState()
                            .setValue(FramedProperties.FACING_HOR, dir.getOpposite())
                            .setValue(FramedProperties.TOP, top)
            );
        }
        else
        {
            return new Tuple<>(
                    FBContent.BLOCK_FRAMED_SLAB.get()
                            .defaultBlockState()
                            .setValue(FramedProperties.TOP, top),
                    FBContent.BLOCK_FRAMED_SLAB_EDGE.get()
                            .defaultBlockState()
                            .setValue(FramedProperties.FACING_HOR, dir)
                            .setValue(FramedProperties.TOP, !top)
            );
        }
    }

    @Override
    public SolidityCheck calculateSolidityCheck(BlockState state, Direction side)
    {
        Direction dir = state.getValue(FramedProperties.FACING_HOR);
        boolean top = state.getValue(FramedProperties.TOP);
        Direction dirTwo = top ? Direction.UP : Direction.DOWN;
        if (panel)
        {
            if (side == dir)
            {
                return SolidityCheck.FIRST;
            }
            else if (side == dirTwo)
            {
                return SolidityCheck.BOTH;
            }
        }
        else
        {
            if (side == dirTwo)
            {
                return SolidityCheck.FIRST;
            }
            else if (side == dir)
            {
                return SolidityCheck.BOTH;
            }
        }
        return SolidityCheck.NONE;
    }

    @Override
    public CamoGetter calculateCamoGetter(BlockState state, Direction side, @Nullable Direction edge)
    {
        Direction dir = state.getValue(FramedProperties.FACING_HOR);
        boolean top = state.getValue(FramedProperties.TOP);
        Direction dirTwo = top ? Direction.UP : Direction.DOWN;
        if (panel)
        {
            if (side == dir || (side.getAxis() != dir.getAxis() && edge == dir))
            {
                return CamoGetter.FIRST;
            }
            else if (side == dirTwo && edge == dir.getOpposite())
            {
                return CamoGetter.SECOND;
            }
            else if (side == dir.getOpposite() && edge == dirTwo)
            {
                return CamoGetter.SECOND;
            }
        }
        else
        {
            if (side == dirTwo || (side.getAxis() != Direction.Axis.Y && edge == dirTwo))
            {
                return CamoGetter.FIRST;
            }
            else if (side == dir && edge == dirTwo.getOpposite())
            {
                return CamoGetter.SECOND;
            }
            else if (side == dirTwo.getOpposite() && edge == dir)
            {
                return CamoGetter.SECOND;
            }
        }
        return CamoGetter.NONE;
    }

    public static BlockState itemModelSourceSlab()
    {
        return FBContent.BLOCK_FRAMED_SLICED_STAIRS_SLAB.get()
                .defaultBlockState()
                .setValue(FramedProperties.FACING_HOR, Direction.SOUTH);
    }

    public static BlockState itemModelSourcePanel()
    {
        return FBContent.BLOCK_FRAMED_SLICED_STAIRS_PANEL.get()
                .defaultBlockState()
                .setValue(FramedProperties.FACING_HOR, Direction.SOUTH);
    }
}
