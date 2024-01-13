package xfacthd.framedblocks.common.data.doubleblock;

import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import xfacthd.framedblocks.api.block.render.FramedBlockRenderProperties;
import xfacthd.framedblocks.api.block.render.ParticleHelper;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.blockentity.doubled.FramedDoubleBlockEntity;

public final class FramedDoubleBlockRenderProperties extends FramedBlockRenderProperties
{
    public static final FramedDoubleBlockRenderProperties INSTANCE = new FramedDoubleBlockRenderProperties();

    private FramedDoubleBlockRenderProperties() { }

    @Override
    public boolean addHitEffects(BlockState state, Level level, HitResult target, ParticleEngine engine)
    {
        BlockHitResult hit = (BlockHitResult) target;
        boolean suppressed = suppressParticles(state, level, hit.getBlockPos());
        if (!suppressed && level.getBlockEntity(hit.getBlockPos()) instanceof FramedDoubleBlockEntity be)
        {
            ParticleHelper.Client.addHitEffects(state, level, hit, be.getCamo().getState(), engine);
            ParticleHelper.Client.addHitEffects(state, level, hit, be.getCamoTwo().getState(), engine);
            return true;
        }
        return suppressed;
    }

    @Override
    public boolean addDestroyEffects(BlockState state, Level level, BlockPos pos, ParticleEngine engine)
    {
        boolean suppressed = suppressParticles(state, level, pos);
        if (!suppressed && level.getBlockEntity(pos) instanceof FramedDoubleBlockEntity be)
        {
            ParticleHelper.Client.addDestroyEffects(state, level, pos, be.getCamo().getState(), engine);
            ParticleHelper.Client.addDestroyEffects(state, level, pos, be.getCamoTwo().getState(), engine);
            return true;
        }
        return suppressed;
    }

    @Override
    public boolean playBreakSound(BlockState state, Level level, BlockPos pos)
    {
        if (level.getBlockEntity(pos) instanceof FramedDoubleBlockEntity be)
        {
            BlockState stateOne = be.getCamo().getState();
            if (stateOne.isAir())
            {
                stateOne = FBContent.BLOCK_FRAMED_CUBE.value().defaultBlockState();
            }
            playCamoBreakSound(level, pos, stateOne);

            BlockState stateTwo = be.getCamoTwo().getState();
            if (stateTwo.isAir())
            {
                stateTwo = FBContent.BLOCK_FRAMED_CUBE.value().defaultBlockState();
            }
            if (stateTwo.getSoundType() != stateOne.getSoundType())
            {
                playCamoBreakSound(level, pos, stateTwo);
            }

            return true;
        }
        return false;
    }
}
