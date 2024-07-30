package xfacthd.framedblocks.api.type;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.ApiStatus;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.block.render.FramedBlockRenderProperties;
import xfacthd.framedblocks.api.predicate.contex.ConTexMode;
import xfacthd.framedblocks.api.predicate.contex.ConnectionPredicate;
import xfacthd.framedblocks.api.predicate.cull.SideSkipPredicate;
import xfacthd.framedblocks.api.predicate.fullface.FullFacePredicate;
import xfacthd.framedblocks.api.shapes.ShapeProvider;

@SuppressWarnings("deprecation")
public interface IBlockType
{
    boolean canOccludeWithSolidCamo();

    boolean hasSpecialHitbox();

    @ApiStatus.OverrideOnly
    FullFacePredicate getFullFacePredicate();

    SideSkipPredicate getSideSkipPredicate();

    @ApiStatus.OverrideOnly
    ConnectionPredicate getConnectionPredicate();

    /**
     * {return a {@link ShapeProvider} used to provide the main shapes of the block, to be returned from
     * {@link Block#getShape(BlockState, BlockGetter, BlockPos, CollisionContext)}}
     */
    ShapeProvider generateShapes(ImmutableList<BlockState> states);

    /**
     * {@return the {@link ShapeProvider} used to provide the occlusion shapes of the block, to be returned from
     * {@link Block#getOcclusionShape(BlockState, BlockGetter, BlockPos)}, or the given provider if the main shapes
     * should be used as the occlusion shapes}
     */
    default ShapeProvider generateOcclusionShapes(ImmutableList<BlockState> states, ShapeProvider shapes)
    {
        return shapes;
    }

    boolean hasSpecialTile();

    boolean hasBlockItem();

    boolean supportsWaterLogging();

    boolean supportsConnectedTextures();

    /**
     * {@return the minimum {@link ConTexMode } required for this block to react to texture connections}
     */
    ConTexMode getMinimumConTexMode();

    /**
     * @implNote If this method returns true, then the associated block must override {@link Block#initializeClient(java.util.function.Consumer)}
     * and pass an instance of {@link FramedBlockRenderProperties} to the consumer to avoid crashing when the block is
     * hit while it can be passed through
     */
    boolean allowMakingIntangible();

    /**
     * @return true if this type represents a block that combines two models into one and allows those to have separate
     * camos applied.
     * @apiNote Returning true doesn't imply that the {@link Block}, {@link BlockEntity} or {@link BakedModel} extends
     * any specific class, it should only ideally guarantee compliance with the data layout used by the reference
     * implementation in FramedBlocks
     */
    default boolean isDoubleBlock()
    {
        return false;
    }

    /**
     * Return true if this block allows locking the state in order to suppress state changes from neighbor updates.
     * Useful to allow blocks like stairs to reside in impossible states, like a corner without neighbors
     * @implNote If this method returns true, then the associated block must have the {@link FramedProperties#STATE_LOCKED} property.
     * The actual update suppression needs to be handled by each block and is not automated
     */
    boolean canLockState();

    String getName();

    int compareTo(IBlockType other);
}