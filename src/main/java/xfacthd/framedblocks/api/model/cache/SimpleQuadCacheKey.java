package xfacthd.framedblocks.api.model.cache;

import org.jetbrains.annotations.Nullable;
import xfacthd.framedblocks.api.camo.CamoContent;

/**
 * @param camo The {@link CamoContent} of the camo applied to the block
 * @param ctCtx The connected textures context data used by the camo model, may be null
 */
public record SimpleQuadCacheKey(CamoContent<?> camo, @Nullable Object ctCtx) implements QuadCacheKey { }
