package xfacthd.framedblocks.common.block.rail;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.*;
import net.minecraftforge.client.extensions.common.IClientBlockExtensions;
import org.jetbrains.annotations.Nullable;
import xfacthd.framedblocks.api.block.*;
import xfacthd.framedblocks.api.block.render.FramedBlockRenderProperties;
import xfacthd.framedblocks.api.shapes.ShapeProvider;
import xfacthd.framedblocks.api.type.IBlockType;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.block.ISlopeBlock;
import xfacthd.framedblocks.common.blockentity.doubled.FramedFancyRailSlopeBlockEntity;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.data.PropertyHolder;
import xfacthd.framedblocks.common.util.FramedUtils;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

@SuppressWarnings("deprecation")
public class FramedDetectorRailSlopeBlock extends DetectorRailBlock implements IFramedBlock, ISlopeBlock.IRailSlopeBlock
{
    private final BlockType type;
    private final ShapeProvider shapes;
    private final ShapeProvider occlusionShapes;
    private final BiFunction<BlockPos, BlockState, FramedBlockEntity> beFactory;

    protected FramedDetectorRailSlopeBlock(BlockType type, BiFunction<BlockPos, BlockState, FramedBlockEntity> beFactory)
    {
        super(IFramedBlock.createProperties(type));
        this.type = type;
        this.shapes = type.generateShapes(getStateDefinition().getPossibleStates());
        this.occlusionShapes = type.generateOcclusionShapes(getStateDefinition().getPossibleStates(), shapes);
        this.beFactory = beFactory;
        registerDefaultState(defaultBlockState()
                .setValue(BlockStateProperties.WATERLOGGED, false)
                .setValue(FramedProperties.SOLID, false)
                .setValue(FramedProperties.GLOWING, false)
                .setValue(POWERED, false)
                .setValue(FramedProperties.Y_SLOPE, false)
                .setValue(FramedProperties.PROPAGATES_SKYLIGHT, false)
        );
    }

    @Override
    protected void registerDefaultState() { }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(
                PropertyHolder.ASCENDING_RAIL_SHAPE, BlockStateProperties.POWERED, BlockStateProperties.WATERLOGGED,
                FramedProperties.SOLID, FramedProperties.GLOWING, FramedProperties.Y_SLOPE,
                FramedProperties.PROPAGATES_SKYLIGHT
        );
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx)
    {
        return PlacementStateBuilder.of(this, ctx)
                .withCustom((state, modCtx) -> state.setValue(
                        PropertyHolder.ASCENDING_RAIL_SHAPE,
                        FramedUtils.getAscendingRailShapeFromDirection(modCtx.getHorizontalDirection())
                ))
                .withWater()
                .build();
    }

    @Override
    public BlockState updateShape(
            BlockState state,
            Direction direction,
            BlockState neighborState,
            LevelAccessor level,
            BlockPos currentPos,
            BlockPos neighborPos
    )
    {
        BlockState newState = super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
        if (newState == state)
        {
            updateCulling(level, currentPos);
        }
        return newState;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        return true;
    }

    @Override //Copy of AbstractRailBlock#neighborChanged() to disable removal
    public void neighborChanged(
            BlockState state, Level level, BlockPos pos, Block block, BlockPos pFromPos, boolean isMoving
    )
    {
        updateCulling(level, pos);
        if (!level.isClientSide() && level.getBlockState(pos).is(this))
        {
            updateState(state, level, pos, block);
        }
    }

    @Override
    public Property<RailShape> getShapeProperty()
    {
        return PropertyHolder.ASCENDING_RAIL_SHAPE;
    }

    @Override
    public boolean isValidRailShape(RailShape shape)
    {
        return shape.isAscending();
    }

    @Override
    public final InteractionResult use(
            BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit
    )
    {
        return handleUse(state, level, pos, player, hand, hit);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        tryApplyCamoImmediately(level, pos, placer, stack);
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state)
    {
        return useCamoOcclusionShapeForLightOcclusion(state);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos)
    {
        return getCamoOcclusionShape(state, level, pos, occlusionShapes);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx)
    {
        return getCamoVisualShape(state, level, pos, ctx);
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos)
    {
        return getCamoShadeBrightness(state, level, pos, super.getShadeBrightness(state, level, pos));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos)
    {
        return state.getValue(FramedProperties.PROPAGATES_SKYLIGHT);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder)
    {
        return getCamoDrops(super.getDrops(state, builder), builder);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx)
    {
        return shapes.get(state);
    }

    @Override //The default implementation defers to the AbstractBlock#getShape() overload without ISelectionContext argument
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context)
    {
        if (context instanceof EntityCollisionContext ctx && ctx.getEntity() instanceof AbstractMinecart)
        {
            return Shapes.empty();
        }
        return getShape(state, worldIn, pos, context);
    }

    @Override
    public boolean handleBlockLeftClick(BlockState state, Level level, BlockPos pos, Player player)
    {
        return IFramedBlock.toggleYSlope(state, level, pos, player);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot)
    {
        Direction dir = FramedUtils.getDirectionFromAscendingRailShape(state.getValue(PropertyHolder.ASCENDING_RAIL_SHAPE));
        dir = rot.rotate(dir);
        return state.setValue(PropertyHolder.ASCENDING_RAIL_SHAPE, FramedUtils.getAscendingRailShapeFromDirection(dir));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror)
    {
        if (mirror == Mirror.NONE) { return state; }

        Direction dir = FramedUtils.getDirectionFromAscendingRailShape(state.getValue(PropertyHolder.ASCENDING_RAIL_SHAPE));

        if ((mirror == Mirror.FRONT_BACK && Utils.isZ(dir)) || (mirror == Mirror.LEFT_RIGHT && Utils.isX(dir)))
        {
            dir = dir.getOpposite();
            return state.setValue(PropertyHolder.ASCENDING_RAIL_SHAPE, FramedUtils.getAscendingRailShapeFromDirection(dir));
        }
        return state;
    }

    @Override
    public boolean doesBlockOccludeBeaconBeam(BlockState state, LevelReader level, BlockPos pos)
    {
        return true;
    }

    @Override
    public final BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return beFactory.apply(pos, state);
    }

    @Override
    public IBlockType getBlockType()
    {
        return type;
    }

    @Override
    public void initializeClient(Consumer<IClientBlockExtensions> consumer)
    {
        consumer.accept(FramedBlockRenderProperties.INSTANCE);
    }



    public static FramedDetectorRailSlopeBlock normal()
    {
        return new FramedDetectorRailSlopeBlock(
                BlockType.FRAMED_DETECTOR_RAIL_SLOPE,
                FramedBlockEntity::new
        );
    }

    public static FramedDetectorRailSlopeBlock fancy()
    {
        return new FramedFancyDetectorRailSlopeBlock(
                BlockType.FRAMED_FANCY_DETECTOR_RAIL_SLOPE,
                FramedFancyRailSlopeBlockEntity::new
        );
    }



    public static BlockState itemModelSourceFancy()
    {
        return FBContent.BLOCK_FRAMED_FANCY_DETECTOR_RAIL_SLOPE.get()
                .defaultBlockState()
                .setValue(PropertyHolder.ASCENDING_RAIL_SHAPE, RailShape.ASCENDING_SOUTH);
    }
}
