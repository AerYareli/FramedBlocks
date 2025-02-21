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
import xfacthd.framedblocks.common.blockentity.doubled.FramedVerticalDividedStairsBlockEntity;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.data.doubleblock.CamoGetter;
import xfacthd.framedblocks.common.data.doubleblock.SolidityCheck;
import xfacthd.framedblocks.common.util.DoubleBlockTopInteractionMode;

public class FramedVerticalDividedStairsBlock extends AbstractFramedDoubleBlock
{
    public FramedVerticalDividedStairsBlock()
    {
        super(BlockType.FRAMED_VERTICAL_DIVIDED_STAIRS);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(FramedProperties.FACING_HOR, BlockStateProperties.WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx)
    {
        return PlacementStateBuilder.of(this, ctx)
                .withHalfFacing()
                .withWater()
                .build();
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, Rotation rot)
    {
        Direction dir = state.getValue(FramedProperties.FACING_HOR);
        return state.setValue(FramedProperties.FACING_HOR, rot.rotate(dir));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirror)
    {
        return Utils.mirrorCornerBlock(state, mirror);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new FramedVerticalDividedStairsBlockEntity(pos, state);
    }

    @Override
    public Tuple<BlockState, BlockState> calculateBlockPair(BlockState state)
    {
        BlockState defState = FBContent.BLOCK_FRAMED_VERTICAL_HALF_STAIRS.get().defaultBlockState();
        Direction facing = state.getValue(FramedProperties.FACING_HOR);

        return new Tuple<>(
                defState.setValue(FramedProperties.FACING_HOR, facing)
                        .setValue(FramedProperties.TOP, false),
                defState.setValue(FramedProperties.FACING_HOR, facing)
                        .setValue(FramedProperties.TOP, true)
        );
    }

    @Override
    public DoubleBlockTopInteractionMode calculateTopInteractionMode(BlockState state)
    {
        return DoubleBlockTopInteractionMode.SECOND;
    }

    @Override
    public CamoGetter calculateCamoGetter(BlockState state, Direction side, @Nullable Direction edge)
    {
        Direction facing = state.getValue(FramedProperties.FACING_HOR);
        if (side == facing || side == facing.getCounterClockWise())
        {
            if (edge == Direction.DOWN) return CamoGetter.FIRST;
            if (edge == Direction.UP) return CamoGetter.SECOND;
            return CamoGetter.NONE;
        }
        if (edge == facing || edge == facing.getCounterClockWise())
        {
            if (side == Direction.DOWN) return CamoGetter.FIRST;
            if (side == Direction.UP) return CamoGetter.SECOND;
            return CamoGetter.NONE;
        }
        return CamoGetter.NONE;
    }

    @Override
    public SolidityCheck calculateSolidityCheck(BlockState state, Direction side)
    {
        Direction facing = state.getValue(FramedProperties.FACING_HOR);
        if (side == facing || side == facing.getCounterClockWise())
        {
            return SolidityCheck.BOTH;
        }
        return SolidityCheck.NONE;
    }
}
