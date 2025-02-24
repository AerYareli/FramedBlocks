package xfacthd.framedblocks.common.data.skippreds;

import xfacthd.framedblocks.api.predicate.cull.SideSkipPredicate;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.data.skippreds.door.*;
import xfacthd.framedblocks.common.data.skippreds.misc.*;
import xfacthd.framedblocks.common.data.skippreds.pane.*;
import xfacthd.framedblocks.common.data.skippreds.pillar.*;
import xfacthd.framedblocks.common.data.skippreds.prism.*;
import xfacthd.framedblocks.common.data.skippreds.slab.*;
import xfacthd.framedblocks.common.data.skippreds.slope.*;
import xfacthd.framedblocks.common.data.skippreds.slopeedge.*;
import xfacthd.framedblocks.common.data.skippreds.slopepanel.*;
import xfacthd.framedblocks.common.data.skippreds.slopepanelcorner.*;
import xfacthd.framedblocks.common.data.skippreds.slopeslab.*;
import xfacthd.framedblocks.common.data.skippreds.stairs.*;
import xfacthd.framedblocks.common.util.BlockTypeMap;

public final class SideSkipPredicates extends BlockTypeMap<SideSkipPredicate>
{
    public static final SideSkipPredicates PREDICATES = new SideSkipPredicates();

    private SideSkipPredicates()
    {
        super(SideSkipPredicate.FALSE);
    }

    @Override
    protected void fill()
    {
        put(BlockType.FRAMED_CUBE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_SLOPE, SlopeSkipPredicate.INSTANCE);
        put(BlockType.FRAMED_CORNER_SLOPE, new CornerSkipPredicate());
        put(BlockType.FRAMED_INNER_CORNER_SLOPE, new InnerCornerSkipPredicate());
        put(BlockType.FRAMED_PRISM_CORNER, new ThreewayCornerSkipPredicate());
        put(BlockType.FRAMED_INNER_PRISM_CORNER, new InnerThreewayCornerSkipPredicate());
        put(BlockType.FRAMED_THREEWAY_CORNER, new ThreewayCornerSkipPredicate());
        put(BlockType.FRAMED_INNER_THREEWAY_CORNER, new InnerThreewayCornerSkipPredicate());
        put(BlockType.FRAMED_SLOPE_EDGE, new SlopeEdgeSkipPredicate());
        put(BlockType.FRAMED_ELEVATED_SLOPE_EDGE, new ElevatedSlopeEdgeSkipPredicate());
        put(BlockType.FRAMED_ELEVATED_DOUBLE_SLOPE_EDGE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_STACKED_SLOPE_EDGE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_SLAB, new SlabSkipPredicate());
        put(BlockType.FRAMED_SLAB_EDGE, new SlabEdgeSkipPredicate());
        put(BlockType.FRAMED_SLAB_CORNER, new SlabCornerSkipPredicate());
        put(BlockType.FRAMED_DIVIDED_SLAB, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_PANEL, new PanelSkipPredicate());
        put(BlockType.FRAMED_CORNER_PILLAR, new CornerPillarSkipPredicate());
        put(BlockType.FRAMED_DIVIDED_PANEL_HORIZONTAL, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_DIVIDED_PANEL_VERTICAL, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_MASONRY_CORNER_SEGMENT, new MasonryCornerSegmentSkipPredicate());
        put(BlockType.FRAMED_MASONRY_CORNER, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_STAIRS, new StairsSkipPredicate());
        put(BlockType.FRAMED_DOUBLE_STAIRS, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_HALF_STAIRS, new HalfStairsSkipPredicate());
        put(BlockType.FRAMED_DIVIDED_STAIRS, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_DOUBLE_HALF_STAIRS, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_SLICED_STAIRS_SLAB, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_SLICED_STAIRS_PANEL, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_VERTICAL_STAIRS, new VerticalStairsSkipPredicate());
        put(BlockType.FRAMED_VERTICAL_DOUBLE_STAIRS, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_VERTICAL_HALF_STAIRS, new VerticalHalfStairsSkipPredicate());
        put(BlockType.FRAMED_VERTICAL_DIVIDED_STAIRS, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_VERTICAL_DOUBLE_HALF_STAIRS, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_VERTICAL_SLICED_STAIRS, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_THREEWAY_CORNER_PILLAR, new ThreewayCornerPillarSkipPredicate());
        put(BlockType.FRAMED_DOUBLE_THREEWAY_CORNER_PILLAR, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_WALL, new WallSkipPredicate());
        put(BlockType.FRAMED_FENCE, new FenceSkipPredicate());
        put(BlockType.FRAMED_FENCE_GATE, new FenceGateSkipPredicate());
        put(BlockType.FRAMED_DOOR, new DoorSkipPredicate());
        put(BlockType.FRAMED_IRON_DOOR, new DoorSkipPredicate());
        put(BlockType.FRAMED_TRAPDOOR, new TrapdoorSkipPredicate());
        put(BlockType.FRAMED_IRON_TRAPDOOR, new TrapdoorSkipPredicate());
        put(BlockType.FRAMED_PRESSURE_PLATE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_WATERLOGGABLE_PRESSURE_PLATE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_STONE_PRESSURE_PLATE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_WATERLOGGABLE_STONE_PRESSURE_PLATE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_OBSIDIAN_PRESSURE_PLATE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_WATERLOGGABLE_OBSIDIAN_PRESSURE_PLATE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_GOLD_PRESSURE_PLATE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_WATERLOGGABLE_GOLD_PRESSURE_PLATE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_IRON_PRESSURE_PLATE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_WATERLOGGABLE_IRON_PRESSURE_PLATE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_LADDER, new LadderSkipPredicate());
        put(BlockType.FRAMED_BUTTON, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_STONE_BUTTON, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_LARGE_BUTTON, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_LARGE_STONE_BUTTON, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_LEVER, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_SIGN, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_WALL_SIGN, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_HANGING_SIGN, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_WALL_HANGING_SIGN, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_DOUBLE_SLAB, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_ADJ_DOUBLE_SLAB, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_ADJ_DOUBLE_COPYCAT_SLAB, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_DOUBLE_PANEL, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_ADJ_DOUBLE_PANEL, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_ADJ_DOUBLE_COPYCAT_PANEL, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_DOUBLE_SLOPE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_DOUBLE_CORNER, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_DOUBLE_PRISM_CORNER, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_DOUBLE_THREEWAY_CORNER, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_TORCH, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_WALL_TORCH, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_SOUL_TORCH, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_SOUL_WALL_TORCH, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_REDSTONE_TORCH, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_REDSTONE_WALL_TORCH, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_FLOOR_BOARD, new FloorBoardSkipPredicate());
        put(BlockType.FRAMED_WALL_BOARD, new WallBoardSkipPredicate());
        put(BlockType.FRAMED_CORNER_STRIP, new CornerStripSkipPredicate());
        put(BlockType.FRAMED_LATTICE_BLOCK, new LatticeSkipPredicate());
        put(BlockType.FRAMED_THICK_LATTICE, new ThickLatticeSkipPredicate());
        put(BlockType.FRAMED_CHEST, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_SECRET_STORAGE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_BARS, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_PANE, new PaneSkipPredicate());
        put(BlockType.FRAMED_HORIZONTAL_PANE, new HorizontalPaneSkipPredicate());
        put(BlockType.FRAMED_RAIL_SLOPE, SlopeSkipPredicate.INSTANCE);
        put(BlockType.FRAMED_POWERED_RAIL_SLOPE, SlopeSkipPredicate.INSTANCE);
        put(BlockType.FRAMED_DETECTOR_RAIL_SLOPE, SlopeSkipPredicate.INSTANCE);
        put(BlockType.FRAMED_ACTIVATOR_RAIL_SLOPE, SlopeSkipPredicate.INSTANCE);
        put(BlockType.FRAMED_FLOWER_POT, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_PILLAR, new PillarSkipPredicate());
        put(BlockType.FRAMED_HALF_PILLAR, new HalfPillarSkipPredicate());
        put(BlockType.FRAMED_POST, new PostSkipPredicate());
        put(BlockType.FRAMED_COLLAPSIBLE_BLOCK, new CollapsibleBlockSkipPredicate());
        put(BlockType.FRAMED_COLLAPSIBLE_COPYCAT_BLOCK, new CollapsibleCopycatBlockSkipPredicate());
        put(BlockType.FRAMED_BOUNCY_CUBE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_REDSTONE_BLOCK, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_PRISM, new PrismSkipPredicate());
        put(BlockType.FRAMED_INNER_PRISM, new InnerPrismSkipPredicate());
        put(BlockType.FRAMED_DOUBLE_PRISM, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_SLOPED_PRISM, new SlopedPrismSkipPredicate());
        put(BlockType.FRAMED_INNER_SLOPED_PRISM, new InnerSlopedPrismSkipPredicate());
        put(BlockType.FRAMED_DOUBLE_SLOPED_PRISM, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_SLOPE_SLAB, new SlopeSlabSkipPredicate());
        put(BlockType.FRAMED_ELEVATED_SLOPE_SLAB, new ElevatedSlopeSlabSkipPredicate());
        put(BlockType.FRAMED_COMPOUND_SLOPE_SLAB, new CompoundSlopeSlabSkipPredicate());
        put(BlockType.FRAMED_DOUBLE_SLOPE_SLAB, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_INV_DOUBLE_SLOPE_SLAB, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_ELEVATED_DOUBLE_SLOPE_SLAB,SideSkipPredicate.FALSE );
        put(BlockType.FRAMED_STACKED_SLOPE_SLAB, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_FLAT_SLOPE_SLAB_CORNER, new FlatSlopeSlabCornerSkipPredicate());
        put(BlockType.FRAMED_FLAT_INNER_SLOPE_SLAB_CORNER, new FlatInnerSlopeSlabCornerSkipPredicate());
        put(BlockType.FRAMED_FLAT_ELEV_SLOPE_SLAB_CORNER, new FlatElevatedSlopeSlabCornerSkipPredicate());
        put(BlockType.FRAMED_FLAT_ELEV_INNER_SLOPE_SLAB_CORNER, new FlatElevatedInnerSlopeSlabCornerSkipPredicate());
        put(BlockType.FRAMED_FLAT_DOUBLE_SLOPE_SLAB_CORNER, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_FLAT_INV_DOUBLE_SLOPE_SLAB_CORNER, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_FLAT_ELEV_DOUBLE_SLOPE_SLAB_CORNER, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_FLAT_ELEV_INNER_DOUBLE_SLOPE_SLAB_CORNER, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_FLAT_STACKED_SLOPE_SLAB_CORNER, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_FLAT_STACKED_INNER_SLOPE_SLAB_CORNER, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_SLOPE_PANEL, new SlopePanelSkipPredicate());
        put(BlockType.FRAMED_EXTENDED_SLOPE_PANEL, new ExtendedSlopePanelSkipPredicate());
        put(BlockType.FRAMED_COMPOUND_SLOPE_PANEL, new CompoundSlopePanelSkipPredicate());
        put(BlockType.FRAMED_DOUBLE_SLOPE_PANEL, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_INV_DOUBLE_SLOPE_PANEL, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_EXTENDED_DOUBLE_SLOPE_PANEL, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_STACKED_SLOPE_PANEL, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_FLAT_SLOPE_PANEL_CORNER, new FlatSlopePanelCornerSkipPredicate());
        put(BlockType.FRAMED_FLAT_INNER_SLOPE_PANEL_CORNER, new FlatInnerSlopePanelCornerSkipPredicate());
        put(BlockType.FRAMED_FLAT_EXT_SLOPE_PANEL_CORNER, new FlatExtendedSlopePanelCornerSkipPredicate());
        put(BlockType.FRAMED_FLAT_EXT_INNER_SLOPE_PANEL_CORNER, new FlatExtendedInnerSlopePanelCornerSkipPredicate());
        put(BlockType.FRAMED_FLAT_DOUBLE_SLOPE_PANEL_CORNER, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_FLAT_INV_DOUBLE_SLOPE_PANEL_CORNER, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_FLAT_EXT_DOUBLE_SLOPE_PANEL_CORNER, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_FLAT_EXT_INNER_DOUBLE_SLOPE_PANEL_CORNER, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_FLAT_STACKED_SLOPE_PANEL_CORNER, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_FLAT_STACKED_INNER_SLOPE_PANEL_CORNER, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_SMALL_CORNER_SLOPE_PANEL, new SmallCornerSlopePanelSkipPredicate());
        put(BlockType.FRAMED_SMALL_CORNER_SLOPE_PANEL_W, new SmallCornerSlopePanelWallSkipPredicate());
        put(BlockType.FRAMED_LARGE_CORNER_SLOPE_PANEL, new LargeCornerSlopePanelSkipPredicate());
        put(BlockType.FRAMED_LARGE_CORNER_SLOPE_PANEL_W, new LargeCornerSlopePanelWallSkipPredicate());
        put(BlockType.FRAMED_SMALL_INNER_CORNER_SLOPE_PANEL, new SmallInnerCornerSlopePanelSkipPredicate());
        put(BlockType.FRAMED_SMALL_INNER_CORNER_SLOPE_PANEL_W, new SmallInnerCornerSlopePanelWallSkipPredicate());
        put(BlockType.FRAMED_LARGE_INNER_CORNER_SLOPE_PANEL, new LargeInnerCornerSlopePanelSkipPredicate());
        put(BlockType.FRAMED_LARGE_INNER_CORNER_SLOPE_PANEL_W, new LargeInnerCornerSlopePanelWallSkipPredicate());
        put(BlockType.FRAMED_EXT_CORNER_SLOPE_PANEL, new ExtendedCornerSlopePanelSkipPredicate());
        put(BlockType.FRAMED_EXT_CORNER_SLOPE_PANEL_W, new ExtendedCornerSlopePanelWallSkipPredicate());
        put(BlockType.FRAMED_EXT_INNER_CORNER_SLOPE_PANEL, new ExtendedInnerCornerSlopePanelSkipPredicate());
        put(BlockType.FRAMED_EXT_INNER_CORNER_SLOPE_PANEL_W, new ExtendedInnerCornerSlopePanelWallSkipPredicate());
        put(BlockType.FRAMED_SMALL_DOUBLE_CORNER_SLOPE_PANEL, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_SMALL_DOUBLE_CORNER_SLOPE_PANEL_W, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_LARGE_DOUBLE_CORNER_SLOPE_PANEL, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_LARGE_DOUBLE_CORNER_SLOPE_PANEL_W, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_INV_DOUBLE_CORNER_SLOPE_PANEL, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_INV_DOUBLE_CORNER_SLOPE_PANEL_W, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_EXT_DOUBLE_CORNER_SLOPE_PANEL, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_EXT_DOUBLE_CORNER_SLOPE_PANEL_W, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_EXT_INNER_DOUBLE_CORNER_SLOPE_PANEL, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_EXT_INNER_DOUBLE_CORNER_SLOPE_PANEL_W, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_STACKED_CORNER_SLOPE_PANEL, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_STACKED_CORNER_SLOPE_PANEL_W, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_STACKED_INNER_CORNER_SLOPE_PANEL, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_STACKED_INNER_CORNER_SLOPE_PANEL_W, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_GLOWING_CUBE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_PYRAMID, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_PYRAMID_SLAB, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_TARGET, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_GATE, new GateSkipPredicate());
        put(BlockType.FRAMED_IRON_GATE, new GateSkipPredicate());
        put(BlockType.FRAMED_ITEM_FRAME, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_GLOWING_ITEM_FRAME, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_FANCY_RAIL, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_FANCY_POWERED_RAIL, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_FANCY_DETECTOR_RAIL, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_FANCY_ACTIVATOR_RAIL, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_FANCY_RAIL_SLOPE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_FANCY_POWERED_RAIL_SLOPE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_FANCY_DETECTOR_RAIL_SLOPE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_FANCY_ACTIVATOR_RAIL_SLOPE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_HALF_SLOPE, new HalfSlopeSkipPredicate());
        put(BlockType.FRAMED_VERTICAL_HALF_SLOPE, new VerticalHalfSlopeSkipPredicate());
        put(BlockType.FRAMED_DIVIDED_SLOPE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_DOUBLE_HALF_SLOPE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_VERTICAL_DOUBLE_HALF_SLOPE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_SLOPED_STAIRS, new SlopedStairsSkipPredicate());
        put(BlockType.FRAMED_VERTICAL_SLOPED_STAIRS, new VerticalSlopedStairsSkipPredicate());
        put(BlockType.FRAMED_MINI_CUBE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_ONE_WAY_WINDOW, new OneWayWindowSkipPredicate());
        put(BlockType.FRAMED_BOOKSHELF, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_CHISELED_BOOKSHELF, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_CENTERED_SLAB, new CenteredSlabSkipPredicate());
        put(BlockType.FRAMED_CENTERED_PANEL, new CenteredPanelSkipPredicate());
        put(BlockType.FRAMED_CHECKERED_CUBE_SEGMENT, new CheckeredCubeSegmentSkipPredicate());
        put(BlockType.FRAMED_CHECKERED_CUBE, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_CHECKERED_SLAB_SEGMENT, new CheckeredSlabSegmentSkipPredicate());
        put(BlockType.FRAMED_CHECKERED_SLAB, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_CHECKERED_PANEL_SEGMENT, new CheckeredPanelSegmentSkipPredicate());
        put(BlockType.FRAMED_CHECKERED_PANEL, SideSkipPredicate.FALSE);
        put(BlockType.FRAMED_TUBE, new TubeSkipPredicate());
    }
}
