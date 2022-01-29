package xfacthd.framedblocks.common.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.*;
import net.minecraftforge.common.ToolType;
import team.chisel.ctm.api.IFacade;
import xfacthd.framedblocks.FramedBlocks;
import xfacthd.framedblocks.client.util.ClientConfig;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.item.FramedBlueprintItem;
import xfacthd.framedblocks.common.tileentity.FramedDoubleTileEntity;
import xfacthd.framedblocks.common.tileentity.FramedTileEntity;
import xfacthd.framedblocks.common.util.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings({ "deprecation", "unused" })
public interface IFramedBlock extends IFacade
{
    BlockType getBlockType();

    static Block.Properties createProperties()
    {
        return Block.Properties.create(Material.WOOD)
                .notSolid()
                .harvestTool(ToolType.AXE)
                .hardnessAndResistance(2F)
                .sound(SoundType.WOOD)
                .setBlocksVision(IFramedBlock::isViewBlocking);
    }

    static boolean isViewBlocking(BlockState state, IBlockReader level, BlockPos pos)
    {
        return ((IFramedBlock) state.getBlock()).isViewBlocked(state, level, pos);
    }

    default BlockItem createItemBlock()
    {
        Block block = (Block)this;
        BlockItem item = new BlockItem(block, new Item.Properties().group(FramedBlocks.FRAMED_GROUP));
        //noinspection ConstantConditions
        item.setRegistryName(block.getRegistryName());
        return item;
    }

    default void tryApplyCamoImmediately(World world, BlockPos pos, @Nullable LivingEntity placer, ItemStack stack)
    {
        if (!world.isRemote() && placer instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) placer;

            if (player.getHeldItemMainhand() != stack) { return; }

            ItemStack otherStack = player.getHeldItemOffhand();
            if (otherStack.getItem() instanceof BlockItem && !(((BlockItem) otherStack.getItem()).getBlock() instanceof IFramedBlock))
            {
                TileEntity te = world.getTileEntity(pos);
                if (te instanceof FramedTileEntity && !(te instanceof FramedDoubleTileEntity))
                {
                    Vector3d hitVec = new Vector3d(pos.getX(), pos.getY(), pos.getZ());
                    ((FramedTileEntity) te).handleInteraction(player, Hand.OFF_HAND, new BlockRayTraceResult(hitVec, Direction.UP, pos, false));
                }
            }
        }
    }

    default ActionResultType handleBlockActivated(World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit)
    {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof FramedTileEntity)
        {
            return ((FramedTileEntity) te).handleInteraction(player, hand, hit);
        }
        return ActionResultType.FAIL;
    }

    default int getLight(IBlockReader world, BlockPos pos)
    {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof FramedTileEntity)
        {
            return ((FramedTileEntity) te).getLightValue();
        }
        return 0;
    }

    default SoundType getSound(BlockState state, IWorldReader world, BlockPos pos)
    {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof FramedTileEntity)
        {
            BlockState camoState = ((FramedTileEntity) te).getCamoState();
            if (!camoState.isAir())
            {
                return camoState.getSoundType();
            }
        }
        return ((Block)this).getSoundType(state);
    }

    default List<ItemStack> getDrops(List<ItemStack> drops, LootContext.Builder builder)
    {
        TileEntity te = builder.get(LootParameters.BLOCK_ENTITY);
        if (te instanceof FramedTileEntity)
        {
            ((FramedTileEntity) te).addCamoDrops(drops);
        }

        return drops;
    }

    default CtmPredicate getCtmPredicate() { return getBlockType().getCtmPredicate(); }

    @Nonnull
    @Override
    @Deprecated
    default BlockState getFacade(@Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nullable Direction side)
    {
        return Blocks.AIR.getDefaultState();
    }

    @Nonnull
    @Override
    default BlockState getFacade(@Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nullable Direction side, @Nonnull BlockPos connection)
    {
        BlockState state = world.getBlockState(pos);
        if (getCtmPredicate().test(state, side))
        {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof FramedTileEntity)
            {
                return ((FramedTileEntity) te).getCamoState();
            }
        }
        return Blocks.AIR.getDefaultState();
    }

    default boolean isSideHidden(IBlockReader world, BlockPos pos, BlockState state, Direction side)
    {
        if (world == null) { return false; } //Block had no camo when loaded => world in data not set

        BlockPos neighborPos = pos.offset(side);
        BlockState neighborState = world.getBlockState(neighborPos);

        if (!isPassThrough(state, world, pos, null))
        {
            if (neighborState.getBlock() instanceof IFramedBlock && ((IFramedBlock) neighborState.getBlock()).getBlockType().allowPassthrough())
            {
                TileEntity te = world.getTileEntity(neighborPos);
                if (te instanceof FramedTileEntity && ((FramedTileEntity) te).isPassThrough(null))
                {
                    return false;
                }
            }
        }

        SideSkipPredicate pred = ClientConfig.detailedCulling ? getBlockType().getSideSkipPredicate() : SideSkipPredicate.CTM;
        return pred.test(world, pos, state, world.getBlockState(pos.offset(side)), side);
    }

    default float getCamoSlipperiness(BlockState state, IWorldReader world, BlockPos pos, @Nullable Entity entity)
    {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof FramedTileEntity)
        {
            BlockState camoState = ((FramedTileEntity) te).getCamoState(Direction.UP);
            if (!camoState.isAir())
            {
                return camoState.getSlipperiness(world, pos, entity);
            }
        }
        return state.getBlock().getSlipperiness();
    }

    default float getCamoBlastResistance(BlockState state, IBlockReader world, BlockPos pos, Explosion explosion)
    {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof FramedTileEntity)
        {
            float resistance = ((FramedTileEntity) te).getCamoBlastResistance(explosion);
            if (resistance > 0F)
            {
                return resistance;
            }
        }
        return state.getBlock().getExplosionResistance();
    }

    default boolean isCamoFlammable(IBlockReader world, BlockPos pos, Direction face)
    {
        if (CommonConfig.fireproofBlocks) { return false; }

        TileEntity te = world.getTileEntity(pos);
        if (te instanceof FramedTileEntity)
        {
            return ((FramedTileEntity) te).isCamoFlammable(face);
        }
        return true;
    }

    default int getCamoFlammability(IBlockReader world, BlockPos pos, Direction face)
    {
        if (CommonConfig.fireproofBlocks) { return 0; }

        TileEntity te = world.getTileEntity(pos);
        if (te instanceof FramedTileEntity)
        {
            int flammability = ((FramedTileEntity) te).getCamoFlammability(face);
            if (flammability > -1)
            {
                return flammability;
            }
        }
        return 20;
    }

    default boolean isPassThrough(BlockState state, IBlockReader world, BlockPos pos, @Nullable ISelectionContext ctx)
    {
        if (!getBlockType().allowPassthrough()) { return false; }

        TileEntity te = world.getTileEntity(pos);
        return te instanceof FramedTileEntity && ((FramedTileEntity) te).isPassThrough(ctx);
    }

    default boolean isViewBlocked(BlockState state, IBlockReader level, BlockPos pos)
    {
        return !getBlockType().allowPassthrough() || !isPassThrough(state, level, pos, null);
    }

    default IFormattableTextComponent printCamoBlock(CompoundNBT beTag)
    {
        BlockState camoState = NBTUtil.readBlockState(beTag.getCompound("camo_state"));
        return camoState.isAir() ? FramedBlueprintItem.BLOCK_NONE : camoState.getBlock().getTranslatedName().mergeStyle(TextFormatting.WHITE);
    }

    static boolean suppressParticles(BlockState state, World world, BlockPos pos)
    {
        if (state.getBlock() instanceof IFramedBlock && ((IFramedBlock) state.getBlock()).getBlockType().allowPassthrough())
        {
            return ((IFramedBlock) state.getBlock()).isPassThrough(state, world, pos, null);
        }
        return false;
    }
}