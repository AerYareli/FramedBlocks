package xfacthd.framedblocks.common.datagen.providers;

import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import xfacthd.framedblocks.api.util.FramedConstants;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.client.render.item.BlueprintPropertyOverride;
import xfacthd.framedblocks.common.FBContent;

@SuppressWarnings({ "SameParameterValue", "UnusedReturnValue" })
public final class FramedItemModelProvider extends ItemModelProvider
{
    public FramedItemModelProvider(PackOutput output, ExistingFileHelper fileHelper)
    {
        super(output, FramedConstants.MOD_ID, fileHelper);
    }

    @Override
    protected void registerModels()
    {
        handheldItem(FBContent.ITEM_FRAMED_HAMMER, "cutout");
        handheldItem(FBContent.ITEM_FRAMED_WRENCH, "cutout");
        handheldItem(FBContent.ITEM_FRAMED_KEY, "cutout");
        handheldItem(FBContent.ITEM_FRAMED_SCREWDRIVER, "cutout");

        simpleItem(FBContent.ITEM_FRAMED_REINFORCEMENT, "cutout");
        singleTexture("framing_saw_pattern", mcLoc("item/generated"), "layer0", new ResourceLocation("ae2", "item/crafting_pattern"));

        ItemModelBuilder modelNormal = simpleItem(FBContent.ITEM_FRAMED_BLUEPRINT, "cutout");
        ModelFile modelWritten = simpleItem("framed_blueprint_written", "cutout");

        modelNormal.override()
                    .predicate(BlueprintPropertyOverride.HAS_DATA, 0)
                    .model(modelNormal)
                    .end()
                .override()
                    .predicate(BlueprintPropertyOverride.HAS_DATA, 1)
                    .model(modelWritten)
                    .end();
    }

    private ItemModelBuilder handheldItem(Holder<Item> item, String renderType)
    {
        String name = Utils.getKeyOrThrow(item).location().getPath();
        return singleTexture(name, mcLoc("item/handheld"), "layer0", modLoc("item/" + name)).renderType(renderType);
    }

    private ItemModelBuilder simpleItem(Holder<Item> item, String renderType)
    {
        return simpleItem(Utils.getKeyOrThrow(item).location().getPath(), renderType);
    }

    private ItemModelBuilder simpleItem(String name, String renderType)
    {
        return singleTexture(name, mcLoc("item/generated"), "layer0", modLoc("item/" + name)).renderType(renderType);
    }
}