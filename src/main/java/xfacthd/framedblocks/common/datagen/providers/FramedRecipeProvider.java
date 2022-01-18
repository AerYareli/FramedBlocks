package xfacthd.framedblocks.common.datagen.providers;

import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import xfacthd.framedblocks.FramedBlocks;
import xfacthd.framedblocks.common.FBContent;

import java.util.function.Consumer;

public class FramedRecipeProvider extends RecipeProvider
{
    private final ICriterionInstance HAS_FRAMED_BLOCK = hasItem(FBContent.blockFramedCube.get());
    private final ICriterionInstance HAS_FRAMED_SLOPE = hasItem(FBContent.blockFramedSlope.get());

    public FramedRecipeProvider(DataGenerator gen) { super(gen); }

    @Override
    public String getName() { return super.getName() + ": " + FramedBlocks.MODID; }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
    {
        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedCube.get(), 4)
                .patternLine("PSP")
                .patternLine("S S")
                .patternLine("PSP")
                .key('P', ItemTags.PLANKS)
                .key('S', Items.STICK)
                .addCriterion("hasPlanks", hasItem(ItemTags.PLANKS))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedSlope.get(), 3)
                .patternLine("F ")
                .patternLine("FF")
                .key('F', FBContent.blockFramedCube.get())
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedCornerSlope.get())
                .patternLine("HF ")
                .patternLine("  F")
                .key('F', FBContent.blockFramedSlope.get())
                .key('H', FBContent.itemFramedHammer.get())
                .addCriterion("hasFramedSlope", HAS_FRAMED_SLOPE)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedInnerCornerSlope.get())
                .patternLine("H F")
                .patternLine(" F ")
                .key('F', FBContent.blockFramedSlope.get())
                .key('H', FBContent.itemFramedHammer.get())
                .addCriterion("hasFramedSlope", HAS_FRAMED_SLOPE)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedPrismCorner.get())
                .patternLine("F F")
                .patternLine(" F ")
                .key('F', FBContent.blockFramedSlope.get())
                .addCriterion("hasFramedSlope", HAS_FRAMED_SLOPE)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedInnerPrismCorner.get())
                .patternLine(" F ")
                .patternLine("F F")
                .key('F', FBContent.blockFramedSlope.get())
                .addCriterion("hasFramedSlope", HAS_FRAMED_SLOPE)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedThreewayCorner.get())
                .patternLine("F ")
                .patternLine("FF")
                .key('F', FBContent.blockFramedSlope.get())
                .addCriterion("hasFramedSlope", HAS_FRAMED_SLOPE)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedInnerThreewayCorner.get())
                .patternLine("FF")
                .patternLine("F ")
                .key('F', FBContent.blockFramedSlope.get())
                .addCriterion("hasFramedSlope", HAS_FRAMED_SLOPE)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedSlab.get(), 6)
                .patternLine("FFF")
                .key('F', FBContent.blockFramedCube.get())
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedSlabEdge.get(), 6)
                .patternLine("FFF")
                .key('F', FBContent.blockFramedSlab.get())
                .addCriterion("hasFramedSlab", hasItem(FBContent.blockFramedSlab.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedSlabCorner.get(), 8)
                .patternLine("FF")
                .patternLine("FF")
                .key('F', FBContent.blockFramedSlab.get())
                .addCriterion("hasFramedSlab", hasItem(FBContent.blockFramedSlab.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedPanel.get(), 6)
                .patternLine("F")
                .patternLine("F")
                .patternLine("F")
                .key('F', FBContent.blockFramedCube.get())
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedCornerPillar.get(), 4)
                .patternLine("F")
                .patternLine("F")
                .key('F', FBContent.blockFramedCube.get())
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedStairs.get(), 4)
                .patternLine("F  ")
                .patternLine("FF ")
                .patternLine("FFF")
                .key('F', FBContent.blockFramedCube.get())
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedWall.get(), 6)
                .patternLine("CFC")
                .patternLine("CFC")
                .key('C', FBContent.blockFramedCube.get())
                .key('F', FBContent.blockFramedFence.get())
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedFence.get(), 3)
                .patternLine("FSF")
                .patternLine("FSF")
                .key('F', FBContent.blockFramedCube.get())
                .key('S', Items.STICK)
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedGate.get())
                .patternLine("SFS")
                .patternLine("SFS")
                .key('F', FBContent.blockFramedCube.get())
                .key('S', Items.STICK)
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedDoor.get())
                .patternLine("FF")
                .patternLine("FF")
                .patternLine("FF")
                .key('F', FBContent.blockFramedCube.get())
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedTrapDoor.get())
                .patternLine("FFF")
                .patternLine("FFF")
                .key('F', FBContent.blockFramedSlab.get())
                .addCriterion("hasFramedSlab", hasItem(FBContent.blockFramedSlab.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedPressurePlate.get())
                .patternLine("FF")
                .key('F', FBContent.blockFramedCube.get())
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedLadder.get(), 3)
                .patternLine("F F")
                .patternLine("FSF")
                .patternLine("F F")
                .key('F', FBContent.blockFramedCube.get())
                .key('S', Items.STICK)
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FBContent.blockFramedButton.get())
                .addIngredient(FBContent.blockFramedCube.get())
                .addCriterion("hasFramedBlock", hasItem(FBContent.blockFramedCube.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedLever.get())
                .patternLine("S")
                .patternLine("F")
                .key('F', FBContent.blockFramedCube.get())
                .key('S', Items.STICK)
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedSign.get(), 3)
                .patternLine("FFF")
                .patternLine("FFF")
                .patternLine(" S ")
                .key('F', FBContent.blockFramedCube.get())
                .key('S', Items.STICK)
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedDoubleSlope.get(), 1)
                .patternLine("FF")
                .key('F', FBContent.blockFramedSlope.get())
                .addCriterion("hasFramedSlope", HAS_FRAMED_SLOPE)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedDoubleCorner.get(), 1)
                .patternLine("IC")
                .key('C', FBContent.blockFramedCornerSlope.get())
                .key('I', FBContent.blockFramedInnerCornerSlope.get())
                .addCriterion("hasFramedCorner", hasItem(FBContent.blockFramedCornerSlope.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedDoublePrismCorner.get(), 1)
                .patternLine("IC")
                .key('C', FBContent.blockFramedPrismCorner.get())
                .key('I', FBContent.blockFramedInnerPrismCorner.get())
                .addCriterion("hasFramedCorner", hasItem(FBContent.blockFramedPrismCorner.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedDoubleThreewayCorner.get(), 1)
                .patternLine("IC")
                .key('C', FBContent.blockFramedThreewayCorner.get())
                .key('I', FBContent.blockFramedInnerThreewayCorner.get())
                .addCriterion("hasFramedCorner", hasItem(FBContent.blockFramedThreewayCorner.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedTorch.get(), 4)
                .patternLine("C")
                .patternLine("F")
                .key('C', ItemTags.COALS)
                .key('F', FBContent.blockFramedCube.get())
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedSoulTorch.get(), 4)
                .patternLine("C")
                .patternLine("F")
                .patternLine("S")
                .key('C', ItemTags.COALS)
                .key('F', FBContent.blockFramedCube.get())
                .key('S', ItemTags.SOUL_FIRE_BASE_BLOCKS)
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedFloor.get(), 3)
                .patternLine("FFH")
                .key('F', FBContent.blockFramedCube.get())
                .key('H', FBContent.itemFramedHammer.get())
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedLattice.get(), 3)
                .patternLine(" F ")
                .patternLine("FFF")
                .patternLine(" F ")
                .key('F', FBContent.blockFramedCube.get())
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedVerticalStairs.get(), 4)
                .patternLine("FFF")
                .patternLine("FF ")
                .patternLine("F  ")
                .key('F', FBContent.blockFramedCube.get())
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedChest.get(), 1)
                .patternLine("FFF")
                .patternLine("F F")
                .patternLine("FFF")
                .key('F', FBContent.blockFramedCube.get())
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedBars.get(), 16)
                .patternLine("F F")
                .patternLine("FFF")
                .patternLine("F F")
                .key('F', FBContent.blockFramedCube.get())
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedPane.get(), 12)
                .patternLine("FF")
                .patternLine("FF")
                .key('F', FBContent.blockFramedCube.get())
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FBContent.blockFramedRailSlope.get(), 1)
                .addIngredient(FBContent.blockFramedSlope.get())
                .addIngredient(Items.RAIL)
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedFlowerPot.get(), 1)
                .patternLine("F F")
                .patternLine(" F ")
                .key('F', FBContent.blockFramedCube.get())
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FBContent.blockFramedPillar.get(), 1)
                .addIngredient(FBContent.blockFramedCornerPillar.get())
                .addCriterion("hasFramedCornerPillar", hasItem(FBContent.blockFramedCornerPillar.get()))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FBContent.blockFramedCornerPillar.get(), 1)
                .addIngredient(FBContent.blockFramedPillar.get())
                .addCriterion("hasFramedPillar", hasItem(FBContent.blockFramedPillar.get()))
                .build(consumer, FramedBlocks.MODID + ":framed_corner_pillar_from_pillar");

        ShapelessRecipeBuilder.shapelessRecipe(FBContent.blockFramedHalfPillar.get(), 1)
                .addIngredient(FBContent.blockFramedSlabCorner.get())
                .addCriterion("hasFramedSlabCorner", hasItem(FBContent.blockFramedSlabCorner.get()))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FBContent.blockFramedSlabCorner.get(), 1)
                .addIngredient(FBContent.blockFramedHalfPillar.get())
                .addCriterion("hasFramedHalfPillar", hasItem(FBContent.blockFramedHalfPillar.get()))
                .build(consumer, FramedBlocks.MODID + ":framed_slab_corner_from_half_pillar");

        ShapelessRecipeBuilder.shapelessRecipe(FBContent.blockFramedPost.get(), 1)
                .addIngredient(FBContent.blockFramedFence.get())
                .addCriterion("hasFramedFence", hasItem(FBContent.blockFramedFence.get()))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FBContent.blockFramedFence.get(), 1)
                .addIngredient(FBContent.blockFramedPost.get())
                .addCriterion("hasFramedPost", hasItem(FBContent.blockFramedPost.get()))
                .build(consumer, FramedBlocks.MODID + ":framed_fence_from_post");

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedCollapsibleBlock.get(), 4)
                .patternLine("FFF")
                .patternLine("FFF")
                .patternLine("FFF")
                .key('F', FBContent.blockFramedCube.get())
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(FBContent.blockFramedHalfStairs.get(), 2)
                .addIngredient(FBContent.itemFramedHammer.get())
                .addIngredient(FBContent.blockFramedStairs.get())
                .addCriterion("hasFramedStairs", hasItem(FBContent.blockFramedStairs.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.blockFramedStairs.get())
                .patternLine("SS")
                .key('S', FBContent.blockFramedHalfStairs.get())
                .addCriterion("hasFramedHalfStairs", hasItem(FBContent.blockFramedHalfStairs.get()))
                .build(consumer, FramedBlocks.MODID + ":framed_stairs_from_half_stairs");



        ShapedRecipeBuilder.shapedRecipe(FBContent.itemFramedHammer.get())
                .patternLine(" F ")
                .patternLine(" SF")
                .patternLine("S  ")
                .key('F', FBContent.blockFramedCube.get())
                .key('S', Items.STICK)
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.itemFramedWrench.get())
                .patternLine("F F")
                .patternLine(" S ")
                .patternLine(" S ")
                .key('F', FBContent.blockFramedCube.get())
                .key('S', Items.STICK)
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(FBContent.itemFramedBlueprint.get())
                .patternLine(" F ")
                .patternLine("FPF")
                .patternLine(" F ")
                .key('F', FBContent.blockFramedCube.get())
                .key('P', Items.PAPER)
                .addCriterion("hasFramedBlock", HAS_FRAMED_BLOCK)
                .build(consumer);
    }
}