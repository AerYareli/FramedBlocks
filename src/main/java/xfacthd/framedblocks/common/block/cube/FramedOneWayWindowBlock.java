package xfacthd.framedblocks.common.block.cube;

import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.*;
import net.minecraftforge.client.extensions.common.IClientBlockExtensions;
import org.jetbrains.annotations.Nullable;
import xfacthd.framedblocks.api.block.FramedBlockEntity;
import xfacthd.framedblocks.api.block.render.FramedBlockRenderProperties;
import xfacthd.framedblocks.api.block.render.ParticleHelper;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.block.FramedBlock;
import xfacthd.framedblocks.common.blockentity.special.FramedOwnableBlockEntity;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.data.PropertyHolder;
import xfacthd.framedblocks.common.data.property.NullableDirection;
import xfacthd.framedblocks.common.util.ServerConfig;

import java.util.function.Consumer;

public class FramedOneWayWindowBlock extends FramedBlock
{
    public FramedOneWayWindowBlock()
    {
        super(BlockType.FRAMED_ONE_WAY_WINDOW);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(PropertyHolder.NULLABLE_FACE);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (placer instanceof Player player && level.getBlockEntity(pos) instanceof FramedOwnableBlockEntity be)
        {
            be.setOwner(player.getUUID(), true);
        }
    }

    @Override
    public boolean handleBlockLeftClick(BlockState state, Level level, BlockPos pos, Player player)
    {
        if (player.getMainHandItem().is(FBContent.ITEM_FRAMED_WRENCH.get()) && isOwnedBy(level, pos, player))
        {
            if (!level.isClientSide())
            {
                if (player.isShiftKeyDown())
                {
                    level.setBlockAndUpdate(pos, state.setValue(PropertyHolder.NULLABLE_FACE, NullableDirection.NONE));
                }
                else
                {
                    HitResult hit = player.pick(10D, 0, false);
                    if (!(hit instanceof BlockHitResult blockHit))
                    {
                        return false;
                    }

                    NullableDirection face =  NullableDirection.fromDirection(blockHit.getDirection());
                    level.setBlockAndUpdate(pos, state.setValue(PropertyHolder.NULLABLE_FACE, face));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos)
    {
        if (state.getValue(PropertyHolder.NULLABLE_FACE) != NullableDirection.NONE)
        {
            return Shapes.empty();
        }
        return super.getOcclusionShape(state, level, pos);
    }

    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx)
    {
        if (isIntangible(state, level, pos, ctx))
        {
            return Shapes.empty();
        }
        return super.getShape(state, level, pos, ctx);
    }

    @Override
    public boolean shouldPreventNeighborCulling(
            BlockGetter level, BlockPos pos, BlockState state, BlockPos adjPos, BlockState adjState
    )
    {
        if (adjState.getBlock() != FBContent.BLOCK_FRAMED_ONE_WAY_WINDOW.get())
        {
            return true;
        }
        return state.getValue(PropertyHolder.NULLABLE_FACE) != adjState.getValue(PropertyHolder.NULLABLE_FACE);
    }

    @Override
    public boolean addRunningEffects(BlockState state, Level level, BlockPos pos, Entity entity)
    {
        if (state.getValue(PropertyHolder.NULLABLE_FACE) == NullableDirection.UP)
        {
            ParticleHelper.spawnRunningParticles(Blocks.TINTED_GLASS.defaultBlockState(), level, pos, entity);
            return true;
        }
        return super.addRunningEffects(state, level, pos, entity);
    }

    @Override
    public boolean addLandingEffects(
            BlockState state, ServerLevel level, BlockPos pos, BlockState sameState, LivingEntity entity, int count
    )
    {
        if (state.getValue(PropertyHolder.NULLABLE_FACE) == NullableDirection.UP)
        {
            ParticleHelper.spawnLandingParticles(Blocks.TINTED_GLASS.defaultBlockState(), level, pos, entity, count);
            return true;
        }
        return super.addLandingEffects(state, level, pos, sameState, entity, count);
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, Rotation rotation)
    {
        Direction dir = state.getValue(PropertyHolder.NULLABLE_FACE).toDirection();
        if (dir != null && !Utils.isY(dir))
        {
            dir = rotation.rotate(dir);
            state = state.setValue(PropertyHolder.NULLABLE_FACE, NullableDirection.fromDirection(dir));
        }
        return state;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirror)
    {
        Direction dir = state.getValue(PropertyHolder.NULLABLE_FACE).toDirection();
        if (dir != null && !Utils.isY(dir))
        {
            dir = mirror.mirror(dir);
            state = state.setValue(PropertyHolder.NULLABLE_FACE, NullableDirection.fromDirection(dir));
        }
        return state;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new FramedOwnableBlockEntity(pos, state);
    }

    @Override
    public void initializeClient(Consumer<IClientBlockExtensions> consumer)
    {
        consumer.accept(new FramedBlockRenderProperties()
        {
            @Override
            protected boolean addHitEffectsUnsuppressed(
                    BlockState state, Level level, BlockHitResult hit, FramedBlockEntity be, ParticleEngine engine
            )
            {
                if (state.getValue(PropertyHolder.NULLABLE_FACE) != NullableDirection.NONE)
                {
                    ParticleHelper.Client.addHitEffects(state, level, hit, Blocks.TINTED_GLASS.defaultBlockState(), engine);
                }
                return super.addHitEffectsUnsuppressed(state, level, hit, be, engine);
            }

            @Override
            protected boolean addDestroyEffectsUnsuppressed(
                    BlockState state, Level level, BlockPos pos, FramedBlockEntity be, ParticleEngine engine
            )
            {
                if (state.getValue(PropertyHolder.NULLABLE_FACE) != NullableDirection.NONE)
                {
                    ParticleHelper.Client.addDestroyEffects(state, level, pos, Blocks.TINTED_GLASS.defaultBlockState(), engine);
                }
                return super.addDestroyEffectsUnsuppressed(state, level, pos, be, engine);
            }
        });
    }



    public static boolean isOwnedBy(BlockGetter level, BlockPos pos, Player player)
    {
        if (!ServerConfig.oneWayWindowOwnable)
        {
            return true;
        }
        if (level.getBlockEntity(pos) instanceof FramedOwnableBlockEntity be)
        {
            return player.getUUID().equals(be.getOwner());
        }
        return false;
    }
}
