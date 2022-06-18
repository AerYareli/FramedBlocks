package xfacthd.framedblocks.tests;

import com.google.common.base.Preconditions;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.registries.ForgeRegistries;
import xfacthd.framedblocks.FramedBlocks;
import xfacthd.framedblocks.api.block.IFramedBlock;
import xfacthd.framedblocks.api.type.IBlockType;
import xfacthd.framedblocks.api.util.FramedProperties;
import xfacthd.framedblocks.api.util.test.TestUtils;
import xfacthd.framedblocks.common.data.BlockType;

import java.util.*;

@GameTestHolder(FramedBlocks.MODID)
public final class LightSourceTests
{
    private static final String BATCH_NAME = "lightsource";
    private static final String STRUCTURE_NAME = FramedBlocks.MODID + ":lightsourcetests.box";

    @GameTestGenerator
    public static Collection<TestFunction> generateLightSourceTests()
    {
        return Arrays.stream(BlockType.values())
                .filter(LightSourceTests::isNotSelfEmitting)
                .map(type -> new ResourceLocation(FramedBlocks.MODID, type.getName()))
                .map(ForgeRegistries.BLOCKS::getValue)
                .filter(Objects::nonNull)
                .map(LightSourceTests::getTestState)
                .map(state -> new TestFunction(
                        BATCH_NAME,
                        getTestName(state),
                        STRUCTURE_NAME,
                        100,
                        0,
                        true,
                        helper -> TestUtils.testBlockLightEmission(helper, state, getCamoSides(state.getBlock()))
                ))
                .toList();
    }

    private static boolean isNotSelfEmitting(BlockType type)
    {
        return type != BlockType.FRAMED_TORCH &&
                type != BlockType.FRAMED_WALL_TORCH &&
                type != BlockType.FRAMED_SOUL_TORCH &&
                type != BlockType.FRAMED_SOUL_WALL_TORCH;
    }

    private static BlockState getTestState(Block block)
    {
        Preconditions.checkArgument(block instanceof IFramedBlock);

        IBlockType type = ((IFramedBlock) block).getBlockType();
        if (type == BlockType.FRAMED_DOUBLE_STAIRS)
        {
            return block.defaultBlockState().setValue(FramedProperties.FACING_HOR, Direction.SOUTH);
        }
        if (type == BlockType.FRAMED_VERTICAL_DOUBLE_STAIRS)
        {
            return block.defaultBlockState().setValue(FramedProperties.FACING_HOR, Direction.SOUTH);
        }
        return block.defaultBlockState();
    }

    private static String getTestName(BlockState state)
    {
        ResourceLocation regName = state.getBlock().getRegistryName();
        Preconditions.checkState(regName != null);
        return String.format("lightsourcetests.test_%s", regName.getPath());
    }

    private static List<Direction> getCamoSides(Block block)
    {
        Preconditions.checkArgument(block instanceof IFramedBlock);

        IBlockType type = ((IFramedBlock) block).getBlockType();
        if (!type.isDoubleBlock())
        {
            return List.of(Direction.UP);
        }

        if (type == BlockType.FRAMED_DOUBLE_PANEL ||
            type == BlockType.FRAMED_DOUBLE_SLOPE_PANEL ||
            type == BlockType.FRAMED_INV_DOUBLE_SLOPE_PANEL
        )
        {
            return List.of(Direction.NORTH, Direction.SOUTH);
        }
        else if (type == BlockType.FRAMED_VERTICAL_DOUBLE_STAIRS)
        {
            return List.of(Direction.EAST, Direction.WEST);
        }

        return List.of(Direction.UP, Direction.DOWN);
    }

    private LightSourceTests() { }
}
