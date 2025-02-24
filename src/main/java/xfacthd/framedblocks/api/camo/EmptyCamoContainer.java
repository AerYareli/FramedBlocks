package xfacthd.framedblocks.api.camo;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import xfacthd.framedblocks.api.FramedBlocksAPI;

public final class EmptyCamoContainer extends CamoContainer
{
    public static final EmptyCamoContainer EMPTY = new EmptyCamoContainer();

    private EmptyCamoContainer()
    {
        super(Blocks.AIR.defaultBlockState());
    }

    @Override
    public int getColor(BlockAndTintGetter level, BlockPos pos, int tintIdx)
    {
        return -1;
    }

    @Override
    public MapColor getMapColor(BlockGetter level, BlockPos pos)
    {
        return null;
    }

    @Override
    public float[] getBeaconColorMultiplier(LevelReader level, BlockPos pos, BlockPos beaconPos)
    {
        return null;
    }

    @Override
    public ItemStack toItemStack(ItemStack stack)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canRotateCamo()
    {
        return false;
    }

    @Override
    public boolean rotateCamo()
    {
        return false;
    }

    @Override
    public SoundType getSoundType()
    {
        return SoundType.EMPTY;
    }

    @Override
    public boolean isSolid(BlockGetter level, BlockPos pos)
    {
        return false;
    }

    @Override
    public boolean isEmpty()
    {
        return true;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj == this;
    }

    @Override
    public int hashCode()
    {
        return state.hashCode();
    }

    @Override
    public ContainerType getType()
    {
        return ContainerType.EMPTY;
    }

    @Override
    public CamoContainer.Factory getFactory()
    {
        return FramedBlocksAPI.getInstance().emptyCamoContainerFactory();
    }

    @Override
    public void save(CompoundTag tag) { }

    @Override
    public void toNetwork(CompoundTag tag) { }



    public static final class Factory extends CamoContainer.Factory
    {
        @Override
        public CamoContainer fromNbt(CompoundTag tag)
        {
            return EMPTY;
        }

        @Override
        public CamoContainer fromNetwork(CompoundTag tag)
        {
            return EMPTY;
        }

        @Override
        public CamoContainer fromItem(ItemStack stack)
        {
            throw new UnsupportedOperationException("Empty camo container cannot be created from ItemStack");
        }
    }
}
