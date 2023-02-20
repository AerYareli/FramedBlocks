package xfacthd.framedblocks.client.model.slope;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.client.model.FramedDoubleBlockModel;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.blockentity.FramedDoubleBlockEntity;
import xfacthd.framedblocks.common.data.PropertyHolder;
import xfacthd.framedblocks.common.data.property.SlopeType;

public class FramedDoubleSlopeModel extends FramedDoubleBlockModel
{
    private final SlopeType type;

    public FramedDoubleSlopeModel(BlockState state, BakedModel baseModel)
    {
        super(state, baseModel, true);
        this.type = state.getValue(PropertyHolder.SLOPE_TYPE);
    }

    @Override
    public TextureAtlasSprite getParticleIcon(@NotNull ModelData data)
    {
        return switch (type)
        {
            case BOTTOM -> getSpriteOrDefault(data, FramedDoubleBlockEntity.DATA_RIGHT, getModels().getB());
            case TOP -> getSpriteOrDefault(data, FramedDoubleBlockEntity.DATA_LEFT, getModels().getA());
            case HORIZONTAL -> super.getParticleIcon(data);
        };
    }



    public static BlockState itemSource()
    {
        return FBContent.blockFramedDoubleSlope.get()
                .defaultBlockState()
                .setValue(FramedProperties.FACING_HOR, Direction.WEST)
                .setValue(PropertyHolder.SLOPE_TYPE, SlopeType.HORIZONTAL);
    }
}