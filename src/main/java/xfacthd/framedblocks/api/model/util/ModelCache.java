package xfacthd.framedblocks.api.model.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.data.ModelData;
import xfacthd.framedblocks.api.internal.InternalClientAPI;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ModelCache
{
    public static final Duration DEFAULT_CACHE_DURATION = Duration.ofMinutes(10);
    private static final Map<Fluid, BakedModel> fluidModelCache = new ConcurrentHashMap<>();

    public static void clear()
    {
        fluidModelCache.clear();
    }

    public static BakedModel getModel(BlockState state)
    {
        if (state.getBlock() instanceof LiquidBlock fluidBlock)
        {
            return fluidModelCache.computeIfAbsent(
                    fluidBlock.getFluid(),
                    InternalClientAPI.INSTANCE::createFluidModel
            );
        }
        return Minecraft.getInstance().getBlockRenderer().getBlockModel(state);
    }

    public static ChunkRenderTypeSet getRenderTypes(BlockState state, RandomSource random, ModelData data)
    {
        if (state.getBlock() instanceof LiquidBlock)
        {
            return ChunkRenderTypeSet.of(ItemBlockRenderTypes.getRenderLayer(state.getFluidState()));
        }
        return getModel(state).getRenderTypes(state, random, data);
    }

    public static ChunkRenderTypeSet getCamoRenderTypes(BlockState state, RandomSource random, ModelData data)
    {
        if (state.getBlock() instanceof LiquidBlock)
        {
            return ChunkRenderTypeSet.of(ItemBlockRenderTypes.getRenderLayer(state.getFluidState()));
        }
        BakedModel model = getModel(state);
        data = ModelUtils.getCamoModelData(data);
        return model.getRenderTypes(state, random, data);
    }



    private ModelCache() { }
}
