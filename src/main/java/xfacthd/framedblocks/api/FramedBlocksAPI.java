package xfacthd.framedblocks.api;

import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.IForgeRegistry;
import xfacthd.framedblocks.api.blueprint.BlueprintCopyBehaviour;
import xfacthd.framedblocks.api.camo.CamoContainerFactory;
import xfacthd.framedblocks.api.util.Utils;

@SuppressWarnings("unused")
public interface FramedBlocksAPI
{
    FramedBlocksAPI INSTANCE = Utils.loadService(FramedBlocksAPI.class);



    /**
     * Returns the default {@link BlockState} used as a camo source when the block's camo state is set to air
     */
    BlockState getDefaultModelState();

    /**
     * Returns the {@link CreativeModeTab} that contains the FramedBlocks items
     */
    CreativeModeTab getDefaultCreativeTab();

    /**
     * Returns the registry of camo container factories
     */
    IForgeRegistry<CamoContainerFactory> getCamoContainerFactoryRegistry();

    /**
     * Returns the camo container factory to use for the given {@link ItemStack}
     */
    CamoContainerFactory getCamoContainerFactory(ItemStack stack);

    /**
     * Register a custom {@link BlueprintCopyBehaviour} for the given {@link Block}s
     */
    void registerBlueprintCopyBehaviour(BlueprintCopyBehaviour behaviour, Block... blocks);
}