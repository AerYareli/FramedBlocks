package xfacthd.framedblocks.common.datagen.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.RegistryObject;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.datagen.builders.recipe.ExtShapedRecipeBuilder;
import xfacthd.framedblocks.common.datagen.builders.recipe.ExtShapelessRecipeBuilder;

import java.util.function.Consumer;

public final class FramedRecipeProvider extends RecipeProvider
{
    public FramedRecipeProvider(PackOutput output) { super(output); }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer)
    {
        shapedBuildingBlock(FBContent.BLOCK_FRAMED_CUBE.get(), 4)
                .pattern("PSP")
                .pattern("S S")
                .pattern("PSP")
                .define('P', ItemTags.PLANKS)
                .define('S', Items.STICK)
                .unlockedBy(ItemTags.PLANKS)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_SLOPE.get(), 3)
                .pattern("F ")
                .pattern("FF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_CORNER_SLOPE.get())
                .pattern("HF ")
                .pattern("  F")
                .define('F', FBContent.BLOCK_FRAMED_SLOPE.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_INNER_CORNER_SLOPE.get())
                .pattern("H F")
                .pattern(" F ")
                .define('F', FBContent.BLOCK_FRAMED_SLOPE.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_PRISM_CORNER.get())
                .pattern("F F")
                .pattern(" F ")
                .define('F', FBContent.BLOCK_FRAMED_SLOPE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_INNER_PRISM_CORNER.get())
                .pattern(" F ")
                .pattern("F F")
                .define('F', FBContent.BLOCK_FRAMED_SLOPE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_THREEWAY_CORNER.get())
                .pattern("F ")
                .pattern("FF")
                .define('F', FBContent.BLOCK_FRAMED_SLOPE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_INNER_THREEWAY_CORNER.get())
                .pattern("FF")
                .pattern("F ")
                .define('F', FBContent.BLOCK_FRAMED_SLOPE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_SLOPE_EDGE.get(), 6)
                .pattern("FFF")
                .pattern(" H ")
                .define('F', FBContent.BLOCK_FRAMED_SLOPE.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_ELEVATED_SLOPE_EDGE.get())
                .pattern("E")
                .pattern("S")
                .define('E', FBContent.BLOCK_FRAMED_SLOPE_EDGE.get())
                .define('S', FBContent.BLOCK_FRAMED_STAIRS.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE_EDGE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_ELEVATED_DOUBLE_SLOPE_EDGE.get())
                .pattern("S")
                .pattern("E")
                .define('S', FBContent.BLOCK_FRAMED_SLOPE_EDGE.get())
                .define('E', FBContent.BLOCK_FRAMED_ELEVATED_SLOPE_EDGE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE_EDGE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_STACKED_SLOPE_EDGE.get())
                .pattern("H")
                .pattern("E")
                .pattern("S")
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .define('E', FBContent.BLOCK_FRAMED_SLOPE_EDGE.get())
                .define('S', FBContent.BLOCK_FRAMED_STAIRS.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE_EDGE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_SLAB.get(), 6)
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_SLAB_EDGE.get(), 6)
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_SLAB.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLAB)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_SLAB_CORNER.get(), 8)
                .pattern("FF")
                .pattern("FF")
                .define('F', FBContent.BLOCK_FRAMED_SLAB.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLAB)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DIVIDED_SLAB.get())
                .pattern("EE")
                .define('E', FBContent.BLOCK_FRAMED_SLAB_EDGE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLAB_EDGE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_PANEL.get(), 6)
                .pattern("F")
                .pattern("F")
                .pattern("F")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_CORNER_PILLAR.get(), 4)
                .pattern("F")
                .pattern("F")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DIVIDED_PANEL_HOR.get())
                .pattern("E")
                .pattern("E")
                .define('E', FBContent.BLOCK_FRAMED_SLAB_EDGE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLAB_EDGE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DIVIDED_PANEL_VERT.get())
                .pattern("PP")
                .define('P', FBContent.BLOCK_FRAMED_CORNER_PILLAR.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CORNER_PILLAR)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_MASONRY_CORNER.get())
                .pattern("EE")
                .pattern("EE")
                .define('E', FBContent.BLOCK_FRAMED_SLAB_EDGE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLAB_EDGE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_STAIRS.get(), 4)
                .pattern("F  ")
                .pattern("FF ")
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_THREEWAY_CORNER_PILLAR.get())
                .pattern("P")
                .pattern("E")
                .define('P', FBContent.BLOCK_FRAMED_CORNER_PILLAR.get())
                .define('E', FBContent.BLOCK_FRAMED_SLAB_EDGE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CORNER_PILLAR)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DOUBLE_THREEWAY_CORNER_PILLAR.get())
                .pattern("F")
                .pattern("F")
                .define('F', FBContent.BLOCK_FRAMED_THREEWAY_CORNER_PILLAR.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_THREEWAY_CORNER_PILLAR)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_WALL.get(), 6)
                .pattern("FFF")
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FENCE.get(), 3)
                .pattern("FSF")
                .pattern("FSF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .define('S', Items.STICK)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FENCE_GATE.get())
                .pattern("SFS")
                .pattern("SFS")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .define('S', Items.STICK)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DOOR.get(), 3)
                .pattern("FF")
                .pattern("FF")
                .pattern("FF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_IRON_DOOR.get())
                .pattern("IDI")
                .define('D', FBContent.BLOCK_FRAMED_DOOR.get())
                .define('I', Items.IRON_INGOT)
                .unlockedBy(FBContent.BLOCK_FRAMED_DOOR)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_TRAP_DOOR.get())
                .pattern("FFF")
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_SLAB.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLAB)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_IRON_TRAP_DOOR.get())
                .requires(FBContent.BLOCK_FRAMED_TRAP_DOOR.get())
                .requires(Items.IRON_INGOT)
                .unlockedBy(FBContent.BLOCK_FRAMED_TRAP_DOOR)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_PRESSURE_PLATE.get())
                .pattern("FF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_STONE_PRESSURE_PLATE.get())
                .pattern("FF")
                .pattern("SS")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .define('S', Tags.Items.STONE)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_OBSIDIAN_PRESSURE_PLATE.get())
                .pattern("FF")
                .pattern("OO")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .define('O', Tags.Items.OBSIDIAN)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_GOLD_PRESSURE_PLATE.get())
                .pattern("FF")
                .pattern("GG")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .define('G', Tags.Items.INGOTS_GOLD)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_IRON_PRESSURE_PLATE.get())
                .pattern("FF")
                .pattern("II")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_LADDER.get(), 3)
                .pattern("F F")
                .pattern("FSF")
                .pattern("F F")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .define('S', Items.STICK)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_BUTTON.get())
                .requires(FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_STONE_BUTTON.get())
                .requires(FBContent.BLOCK_FRAMED_CUBE.get())
                .requires(Tags.Items.STONE)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_LEVER.get())
                .pattern("S")
                .pattern("F")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .define('S', Items.STICK)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_SIGN.get(), 3)
                .pattern("FFF")
                .pattern("FFF")
                .pattern(" S ")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .define('S', Items.STICK)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_HANGING_SIGN.get(), 6)
                .pattern("C C")
                .pattern("FFF")
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .define('C', Items.CHAIN)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_ADJ_DOUBLE_SLAB.get(), 2)
                .pattern("FF")
                .define('F', FBContent.BLOCK_FRAMED_COLLAPSIBLE_BLOCK.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_COLLAPSIBLE_BLOCK)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_ADJ_DOUBLE_COPYCAT_SLAB.get(), 2)
                .pattern("FF")
                .define('F', FBContent.BLOCK_FRAMED_COLLAPSIBLE_COPYCAT_BLOCK.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_COLLAPSIBLE_COPYCAT_BLOCK)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_ADJ_DOUBLE_PANEL.get(), 2)
                .pattern("F")
                .pattern("F")
                .define('F', FBContent.BLOCK_FRAMED_COLLAPSIBLE_BLOCK.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_COLLAPSIBLE_BLOCK)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_ADJ_DOUBLE_COPYCAT_PANEL.get(), 2)
                .pattern("F")
                .pattern("F")
                .define('F', FBContent.BLOCK_FRAMED_COLLAPSIBLE_COPYCAT_BLOCK.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_COLLAPSIBLE_COPYCAT_BLOCK)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DOUBLE_SLOPE.get(), 1)
                .pattern("FF")
                .define('F', FBContent.BLOCK_FRAMED_SLOPE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DOUBLE_CORNER.get(), 1)
                .pattern("IC")
                .define('C', FBContent.BLOCK_FRAMED_CORNER_SLOPE.get())
                .define('I', FBContent.BLOCK_FRAMED_INNER_CORNER_SLOPE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CORNER_SLOPE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DOUBLE_PRISM_CORNER.get(), 1)
                .pattern("IC")
                .define('C', FBContent.BLOCK_FRAMED_PRISM_CORNER.get())
                .define('I', FBContent.BLOCK_FRAMED_INNER_PRISM_CORNER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_PRISM_CORNER)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DOUBLE_THREEWAY_CORNER.get(), 1)
                .pattern("IC")
                .define('C', FBContent.BLOCK_FRAMED_THREEWAY_CORNER.get())
                .define('I', FBContent.BLOCK_FRAMED_INNER_THREEWAY_CORNER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_THREEWAY_CORNER)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_TORCH.get(), 4)
                .pattern("C")
                .pattern("F")
                .define('C', ItemTags.COALS)
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_SOUL_TORCH.get(), 4)
                .pattern("C")
                .pattern("F")
                .pattern("S")
                .define('C', ItemTags.COALS)
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .define('S', ItemTags.SOUL_FIRE_BASE_BLOCKS)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_REDSTONE_TORCH.get(), 4)
                .pattern("R")
                .pattern("F")
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FLOOR.get(), 4)
                .pattern("FFH")
                .define('F', FBContent.BLOCK_FRAMED_SLAB.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLAB)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_LATTICE.get(), 3)
                .pattern(" F ")
                .pattern("FFF")
                .pattern(" F ")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_THICK_LATTICE.get(), 2)
                .pattern("HF ")
                .pattern("FFF")
                .pattern(" F ")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_VERTICAL_STAIRS.get(), 4)
                .pattern("FFF")
                .pattern("FF ")
                .pattern("F  ")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_CHEST.get(), 1)
                .pattern("FFF")
                .pattern("F F")
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_BARS.get(), 16)
                .pattern("F F")
                .pattern("FFF")
                .pattern("F F")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_PANE.get(), 12)
                .pattern("FF")
                .pattern("FF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_RAIL_SLOPE.get(), 1)
                .requires(FBContent.BLOCK_FRAMED_SLOPE.get())
                .requires(Items.RAIL)
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_POWERED_RAIL_SLOPE.get(), 1)
                .requires(FBContent.BLOCK_FRAMED_SLOPE.get())
                .requires(Items.POWERED_RAIL)
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_DETECTOR_RAIL_SLOPE.get(), 1)
                .requires(FBContent.BLOCK_FRAMED_SLOPE.get())
                .requires(Items.DETECTOR_RAIL)
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_ACTIVATOR_RAIL_SLOPE.get(), 1)
                .requires(FBContent.BLOCK_FRAMED_SLOPE.get())
                .requires(Items.ACTIVATOR_RAIL)
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FLOWER_POT.get(), 1)
                .pattern("F F")
                .pattern(" F ")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_PILLAR.get(), 1)
                .requires(FBContent.BLOCK_FRAMED_CORNER_PILLAR.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CORNER_PILLAR)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_CORNER_PILLAR.get(), 1)
                .requires(FBContent.BLOCK_FRAMED_PILLAR.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_PILLAR)
                .save(consumer, Utils.rl("framed_corner_pillar_from_pillar"));

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_HALF_PILLAR.get(), 1)
                .requires(FBContent.BLOCK_FRAMED_SLAB_CORNER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLAB_CORNER)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_SLAB_CORNER.get(), 1)
                .requires(FBContent.BLOCK_FRAMED_HALF_PILLAR.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_HALF_PILLAR)
                .save(consumer, Utils.rl("framed_slab_corner_from_half_pillar"));

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_POST.get(), 1)
                .requires(FBContent.BLOCK_FRAMED_FENCE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_FENCE)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_FENCE.get(), 1)
                .requires(FBContent.BLOCK_FRAMED_POST.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_POST)
                .save(consumer, Utils.rl("framed_fence_from_post"));

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_COLLAPSIBLE_BLOCK.get(), 4)
                .pattern("FFF")
                .pattern("FFF")
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_COLLAPSIBLE_COPYCAT_BLOCK.get(), 4)
                .pattern("FCF")
                .pattern("C C")
                .pattern("FCF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .define('C', Tags.Items.INGOTS_COPPER)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_HALF_STAIRS.get(), 2)
                .requires(FBContent.ITEM_FRAMED_HAMMER.get())
                .requires(FBContent.BLOCK_FRAMED_STAIRS.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_STAIRS)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DIVIDED_STAIRS.get())
                .pattern("SS")
                .define('S', FBContent.BLOCK_FRAMED_HALF_STAIRS.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_HALF_STAIRS)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DOUBLE_HALF_STAIRS.get())
                .pattern("SC")
                .define('S', FBContent.BLOCK_FRAMED_HALF_STAIRS.get())
                .define('C', FBContent.BLOCK_FRAMED_SLAB_CORNER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_HALF_STAIRS)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_SLICED_STAIRS_SLAB.get())
                .pattern("E")
                .pattern("S")
                .define('S', FBContent.BLOCK_FRAMED_SLAB.get())
                .define('E', FBContent.BLOCK_FRAMED_SLAB_EDGE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLAB)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_SLICED_STAIRS_PANEL.get())
                .pattern("PE")
                .define('P', FBContent.BLOCK_FRAMED_PANEL.get())
                .define('E', FBContent.BLOCK_FRAMED_SLAB_EDGE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_PANEL)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_BOUNCY_CUBE.get())
                .pattern(" S ")
                .pattern("SCS")
                .pattern(" S ")
                .define('S', Tags.Items.SLIMEBALLS)
                .define('C', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_SECRET_STORAGE.get())
                .pattern(" F ")
                .pattern("FCF")
                .pattern(" F ")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .define('C', FBContent.BLOCK_FRAMED_CHEST.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_REDSTONE_BLOCK.get())
                .pattern("RRR")
                .pattern("RCR")
                .pattern("RRR")
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('C', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_PRISM.get(), 2)
                .pattern("FFH")
                .define('F', FBContent.BLOCK_FRAMED_SLOPE.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_INNER_PRISM.get(), 2)
                .pattern("FF")
                .pattern("SS")
                .define('F', FBContent.BLOCK_FRAMED_SLOPE.get())
                .define('S', FBContent.BLOCK_FRAMED_SLAB.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DOUBLE_PRISM.get())
                .pattern("P")
                .pattern("I")
                .define('P', FBContent.BLOCK_FRAMED_PRISM.get())
                .define('I', FBContent.BLOCK_FRAMED_INNER_PRISM.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_PRISM)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_SLOPED_PRISM.get(), 2)
                .pattern("FF")
                .define('F', FBContent.BLOCK_FRAMED_CORNER_SLOPE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CORNER_SLOPE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_INNER_SLOPED_PRISM.get(), 2)
                .pattern("FF")
                .pattern("SS")
                .define('F', FBContent.BLOCK_FRAMED_CORNER_SLOPE.get())
                .define('S', FBContent.BLOCK_FRAMED_SLAB.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CORNER_SLOPE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DOUBLE_SLOPED_PRISM.get())
                .pattern("P")
                .pattern("I")
                .define('P', FBContent.BLOCK_FRAMED_SLOPED_PRISM.get())
                .define('I', FBContent.BLOCK_FRAMED_INNER_SLOPED_PRISM.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPED_PRISM)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_SLOPE_SLAB.get(), 6)
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_SLOPE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_ELEVATED_SLOPE_SLAB.get())
                .pattern("S")
                .pattern("F")
                .define('S', FBContent.BLOCK_FRAMED_SLOPE_SLAB.get())
                .define('F', FBContent.BLOCK_FRAMED_SLAB.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE_SLAB)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_COMPOUND_SLOPE_SLAB.get())
                .pattern("SSH")
                .define('S', FBContent.BLOCK_FRAMED_SLOPE_SLAB.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE_SLAB)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DOUBLE_SLOPE_SLAB.get())
                .pattern("FF")
                .define('F', FBContent.BLOCK_FRAMED_SLOPE_SLAB.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE_SLAB)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_INVERSE_DOUBLE_SLOPE_SLAB.get())
                .requires(FBContent.BLOCK_FRAMED_DOUBLE_SLOPE_SLAB.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_DOUBLE_SLOPE_SLAB)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_DOUBLE_SLOPE_SLAB.get())
                .requires(FBContent.BLOCK_FRAMED_INVERSE_DOUBLE_SLOPE_SLAB.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_INVERSE_DOUBLE_SLOPE_SLAB)
                .save(consumer, Utils.rl("framed_double_slope_slab_from_inverse"));

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_ELEVATED_DOUBLE_SLOPE_SLAB.get())
                .pattern("S")
                .pattern("E")
                .define('S', FBContent.BLOCK_FRAMED_SLOPE_SLAB.get())
                .define('E', FBContent.BLOCK_FRAMED_ELEVATED_SLOPE_SLAB.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE_SLAB)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_STACKED_SLOPE_SLAB.get())
                .pattern("H")
                .pattern("S")
                .pattern("F")
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .define('S', FBContent.BLOCK_FRAMED_SLOPE_SLAB.get())
                .define('F', FBContent.BLOCK_FRAMED_SLAB.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE_SLAB)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_VERTICAL_HALF_STAIRS.get(), 2)
                .requires(FBContent.BLOCK_FRAMED_VERTICAL_STAIRS.get())
                .requires(FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_VERTICAL_STAIRS)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_VERTICAL_DIVIDED_STAIRS.get())
                .pattern("S")
                .pattern("S")
                .define('S', FBContent.BLOCK_FRAMED_VERTICAL_HALF_STAIRS.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_VERTICAL_HALF_STAIRS)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_VERTICAL_DOUBLE_HALF_STAIRS.get())
                .pattern("SC")
                .define('S', FBContent.BLOCK_FRAMED_VERTICAL_HALF_STAIRS.get())
                .define('C', FBContent.BLOCK_FRAMED_SLAB_CORNER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_VERTICAL_HALF_STAIRS)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_VERTICAL_SLICED_STAIRS.get())
                .pattern("PEW")
                .define('P', FBContent.BLOCK_FRAMED_PANEL.get())
                .define('E', FBContent.BLOCK_FRAMED_SLAB_EDGE.get())
                .define('W', FBContent.ITEM_FRAMED_WRENCH.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_PANEL)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FLAT_SLOPE_SLAB_CORNER.get())
                .pattern("HF ")
                .pattern("  F")
                .define('F', FBContent.BLOCK_FRAMED_SLOPE_SLAB.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE_SLAB)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FLAT_INNER_SLOPE_SLAB_CORNER.get())
                .pattern("H F")
                .pattern(" F ")
                .define('F', FBContent.BLOCK_FRAMED_SLOPE_SLAB.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE_SLAB)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FLAT_ELEVATED_SLOPE_SLAB_CORNER.get())
                .pattern("C")
                .pattern("S")
                .define('C', FBContent.BLOCK_FRAMED_FLAT_SLOPE_SLAB_CORNER.get())
                .define('S', FBContent.BLOCK_FRAMED_SLAB.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_FLAT_SLOPE_SLAB_CORNER)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FLAT_ELEVATED_INNER_SLOPE_SLAB_CORNER.get())
                .pattern("C")
                .pattern("S")
                .define('C', FBContent.BLOCK_FRAMED_FLAT_INNER_SLOPE_SLAB_CORNER.get())
                .define('S', FBContent.BLOCK_FRAMED_SLAB.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_FLAT_INNER_SLOPE_SLAB_CORNER)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FLAT_DOUBLE_SLOPE_SLAB_CORNER.get())
                .pattern("C")
                .pattern("I")
                .define('C', FBContent.BLOCK_FRAMED_FLAT_SLOPE_SLAB_CORNER.get())
                .define('I', FBContent.BLOCK_FRAMED_FLAT_INNER_SLOPE_SLAB_CORNER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_FLAT_SLOPE_SLAB_CORNER)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_FLAT_INVERSE_DOUBLE_SLOPE_SLAB_CORNER.get())
                .requires(FBContent.BLOCK_FRAMED_FLAT_DOUBLE_SLOPE_SLAB_CORNER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_FLAT_DOUBLE_SLOPE_SLAB_CORNER)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_FLAT_DOUBLE_SLOPE_SLAB_CORNER.get())
                .requires(FBContent.BLOCK_FRAMED_FLAT_INVERSE_DOUBLE_SLOPE_SLAB_CORNER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_FLAT_INVERSE_DOUBLE_SLOPE_SLAB_CORNER)
                .save(consumer, Utils.rl("framed_flat_double_slope_slab_corner_from_inverse"));

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FLAT_ELEVATED_DOUBLE_SLOPE_SLAB_CORNER.get())
                .pattern("C")
                .pattern("E")
                .define('C', FBContent.BLOCK_FRAMED_FLAT_INNER_SLOPE_SLAB_CORNER.get())
                .define('E', FBContent.BLOCK_FRAMED_FLAT_ELEVATED_SLOPE_SLAB_CORNER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_FLAT_ELEVATED_SLOPE_SLAB_CORNER)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FLAT_ELEVATED_INNER_DOUBLE_SLOPE_SLAB_CORNER.get())
                .pattern("C")
                .pattern("E")
                .define('C', FBContent.BLOCK_FRAMED_FLAT_SLOPE_SLAB_CORNER.get())
                .define('E', FBContent.BLOCK_FRAMED_FLAT_ELEVATED_INNER_SLOPE_SLAB_CORNER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_FLAT_ELEVATED_INNER_SLOPE_SLAB_CORNER)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FLAT_STACKED_SLOPE_SLAB_CORNER.get())
                .pattern("H")
                .pattern("C")
                .pattern("S")
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .define('C', FBContent.BLOCK_FRAMED_FLAT_SLOPE_SLAB_CORNER.get())
                .define('S', FBContent.BLOCK_FRAMED_SLAB.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_FLAT_SLOPE_SLAB_CORNER)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FLAT_STACKED_INNER_SLOPE_SLAB_CORNER.get())
                .pattern("H")
                .pattern("C")
                .pattern("S")
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .define('C', FBContent.BLOCK_FRAMED_FLAT_INNER_SLOPE_SLAB_CORNER.get())
                .define('S', FBContent.BLOCK_FRAMED_SLAB.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_FLAT_INNER_SLOPE_SLAB_CORNER)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_SLOPE_PANEL.get(), 6)
                .pattern("S")
                .pattern("S")
                .pattern("S")
                .define('S', FBContent.BLOCK_FRAMED_SLOPE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_EXTENDED_SLOPE_PANEL.get())
                .pattern("PS")
                .define('P', FBContent.BLOCK_FRAMED_PANEL.get())
                .define('S', FBContent.BLOCK_FRAMED_SLOPE_PANEL.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE_PANEL)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_COMPOUND_SLOPE_PANEL.get())
                .pattern("PPH")
                .define('P', FBContent.BLOCK_FRAMED_SLOPE_PANEL.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE_PANEL)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DOUBLE_SLOPE_PANEL.get())
                .pattern("PP")
                .define('P', FBContent.BLOCK_FRAMED_SLOPE_PANEL.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE_PANEL)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_INVERSE_DOUBLE_SLOPE_PANEL.get())
                .requires(FBContent.BLOCK_FRAMED_DOUBLE_SLOPE_PANEL.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_DOUBLE_SLOPE_PANEL)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_DOUBLE_SLOPE_PANEL.get())
                .requires(FBContent.BLOCK_FRAMED_INVERSE_DOUBLE_SLOPE_PANEL.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_INVERSE_DOUBLE_SLOPE_PANEL)
                .save(consumer, Utils.rl("framed_double_slope_panel_from_inverse_double_slope_panel"));

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_EXTENDED_DOUBLE_SLOPE_PANEL.get())
                .pattern("ES")
                .define('S', FBContent.BLOCK_FRAMED_SLOPE_PANEL.get())
                .define('E', FBContent.BLOCK_FRAMED_EXTENDED_SLOPE_PANEL.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE_PANEL)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_STACKED_SLOPE_PANEL.get())
                .pattern("PSH")
                .define('P', FBContent.BLOCK_FRAMED_PANEL.get())
                .define('S', FBContent.BLOCK_FRAMED_SLOPE_PANEL.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE_PANEL)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FLAT_SLOPE_PANEL_CORNER.get())
                .pattern("HF ")
                .pattern("  F")
                .define('F', FBContent.BLOCK_FRAMED_SLOPE_PANEL.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE_PANEL)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FLAT_INNER_SLOPE_PANEL_CORNER.get())
                .pattern("H F")
                .pattern(" F ")
                .define('F', FBContent.BLOCK_FRAMED_SLOPE_PANEL.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE_PANEL)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FLAT_EXTENDED_SLOPE_PANEL_CORNER.get())
                .pattern("PC")
                .define('C', FBContent.BLOCK_FRAMED_FLAT_SLOPE_PANEL_CORNER.get())
                .define('P', FBContent.BLOCK_FRAMED_PANEL.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_FLAT_SLOPE_PANEL_CORNER)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FLAT_EXTENDED_INNER_SLOPE_PANEL_CORNER.get())
                .pattern("PC")
                .define('C', FBContent.BLOCK_FRAMED_FLAT_INNER_SLOPE_PANEL_CORNER.get())
                .define('P', FBContent.BLOCK_FRAMED_PANEL.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_FLAT_INNER_SLOPE_PANEL_CORNER)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FLAT_DOUBLE_SLOPE_PANEL_CORNER.get())
                .pattern("IC")
                .define('C', FBContent.BLOCK_FRAMED_FLAT_SLOPE_PANEL_CORNER.get())
                .define('I', FBContent.BLOCK_FRAMED_FLAT_INNER_SLOPE_PANEL_CORNER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_FLAT_SLOPE_PANEL_CORNER)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_FLAT_INVERSE_DOUBLE_SLOPE_PANEL_CORNER.get())
                .requires(FBContent.BLOCK_FRAMED_FLAT_DOUBLE_SLOPE_PANEL_CORNER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_FLAT_DOUBLE_SLOPE_PANEL_CORNER)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_FLAT_DOUBLE_SLOPE_PANEL_CORNER.get())
                .requires(FBContent.BLOCK_FRAMED_FLAT_INVERSE_DOUBLE_SLOPE_PANEL_CORNER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_FLAT_INVERSE_DOUBLE_SLOPE_PANEL_CORNER)
                .save(consumer, Utils.rl("framed_flat_double_slope_panel_corner_from_flat_inverse_double_slope_panel_corner"));

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FLAT_EXTENDED_DOUBLE_SLOPE_PANEL_CORNER.get())
                .pattern("CI")
                .define('C', FBContent.BLOCK_FRAMED_FLAT_EXTENDED_SLOPE_PANEL_CORNER.get())
                .define('I', FBContent.BLOCK_FRAMED_FLAT_INNER_SLOPE_PANEL_CORNER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_FLAT_EXTENDED_SLOPE_PANEL_CORNER)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FLAT_EXTENDED_INNER_DOUBLE_SLOPE_PANEL_CORNER.get())
                .pattern("IC")
                .define('I', FBContent.BLOCK_FRAMED_FLAT_EXTENDED_INNER_SLOPE_PANEL_CORNER.get())
                .define('C', FBContent.BLOCK_FRAMED_FLAT_SLOPE_PANEL_CORNER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_FLAT_EXTENDED_INNER_SLOPE_PANEL_CORNER)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FLAT_STACKED_SLOPE_PANEL_CORNER.get())
                .pattern("PCH")
                .define('C', FBContent.BLOCK_FRAMED_FLAT_SLOPE_PANEL_CORNER.get())
                .define('P', FBContent.BLOCK_FRAMED_PANEL.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_FLAT_SLOPE_PANEL_CORNER)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FLAT_STACKED_INNER_SLOPE_PANEL_CORNER.get())
                .pattern("PCH")
                .define('C', FBContent.BLOCK_FRAMED_FLAT_INNER_SLOPE_PANEL_CORNER.get())
                .define('P', FBContent.BLOCK_FRAMED_PANEL.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_FLAT_INNER_SLOPE_PANEL_CORNER)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_SMALL_CORNER_SLOPE_PANEL.get(), 6)
                .pattern("HP")
                .pattern("PP")
                .define('P', FBContent.BLOCK_FRAMED_SLOPE_PANEL.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE_PANEL)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_LARGE_CORNER_SLOPE_PANEL.get(), 2)
                .pattern("H  ")
                .pattern(" PP")
                .pattern(" P ")
                .define('P', FBContent.BLOCK_FRAMED_SLOPE_PANEL.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE_PANEL)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_SMALL_INNER_CORNER_SLOPE_PANEL.get())
                .pattern("PP")
                .pattern("PH")
                .define('P', FBContent.BLOCK_FRAMED_SLOPE_PANEL.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE_PANEL)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_LARGE_INNER_CORNER_SLOPE_PANEL.get())
                .pattern(" P ")
                .pattern("PP ")
                .pattern("  H")
                .define('P', FBContent.BLOCK_FRAMED_SLOPE_PANEL.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE_PANEL)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_EXTENDED_CORNER_SLOPE_PANEL.get())
                .pattern("HCP")
                .define('C', FBContent.BLOCK_FRAMED_LARGE_CORNER_SLOPE_PANEL.get())
                .define('P', FBContent.BLOCK_FRAMED_CORNER_PILLAR.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_LARGE_CORNER_SLOPE_PANEL)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_EXTENDED_INNER_CORNER_SLOPE_PANEL.get())
                .pattern("HCS")
                .define('C', FBContent.BLOCK_FRAMED_SMALL_INNER_CORNER_SLOPE_PANEL.get())
                .define('S', FBContent.BLOCK_FRAMED_VERTICAL_STAIRS.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SMALL_INNER_CORNER_SLOPE_PANEL)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_SMALL_DOUBLE_CORNER_SLOPE_PANEL.get())
                .pattern("HCI")
                .define('C', FBContent.BLOCK_FRAMED_SMALL_CORNER_SLOPE_PANEL.get())
                .define('I', FBContent.BLOCK_FRAMED_SMALL_INNER_CORNER_SLOPE_PANEL.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SMALL_CORNER_SLOPE_PANEL)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_LARGE_DOUBLE_CORNER_SLOPE_PANEL.get())
                .pattern("HCI")
                .define('C', FBContent.BLOCK_FRAMED_LARGE_CORNER_SLOPE_PANEL.get())
                .define('I', FBContent.BLOCK_FRAMED_LARGE_INNER_CORNER_SLOPE_PANEL.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_LARGE_CORNER_SLOPE_PANEL)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_INVERSE_DOUBLE_CORNER_SLOPE_PANEL.get())
                .pattern("CI")
                .define('C', FBContent.BLOCK_FRAMED_LARGE_CORNER_SLOPE_PANEL.get())
                .define('I', FBContent.BLOCK_FRAMED_SMALL_INNER_CORNER_SLOPE_PANEL.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_LARGE_CORNER_SLOPE_PANEL)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_EXTENDED_DOUBLE_CORNER_SLOPE_PANEL.get())
                .pattern("CI")
                .define('C', FBContent.BLOCK_FRAMED_EXTENDED_CORNER_SLOPE_PANEL.get())
                .define('I', FBContent.BLOCK_FRAMED_LARGE_INNER_CORNER_SLOPE_PANEL.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_EXTENDED_CORNER_SLOPE_PANEL)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_EXTENDED_INNER_DOUBLE_CORNER_SLOPE_PANEL.get())
                .pattern("IC")
                .define('I', FBContent.BLOCK_FRAMED_EXTENDED_INNER_CORNER_SLOPE_PANEL.get())
                .define('C', FBContent.BLOCK_FRAMED_SMALL_CORNER_SLOPE_PANEL.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_EXTENDED_INNER_CORNER_SLOPE_PANEL)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_STACKED_CORNER_SLOPE_PANEL.get())
                .pattern("CP")
                .define('C', FBContent.BLOCK_FRAMED_LARGE_CORNER_SLOPE_PANEL.get())
                .define('P', FBContent.BLOCK_FRAMED_CORNER_PILLAR.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_LARGE_CORNER_SLOPE_PANEL)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_STACKED_INNER_CORNER_SLOPE_PANEL.get())
                .pattern("CS")
                .define('C', FBContent.BLOCK_FRAMED_SMALL_INNER_CORNER_SLOPE_PANEL.get())
                .define('S', FBContent.BLOCK_FRAMED_VERTICAL_STAIRS.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SMALL_INNER_CORNER_SLOPE_PANEL)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DOUBLE_STAIRS.get())
                .pattern("SE")
                .define('S', FBContent.BLOCK_FRAMED_STAIRS.get())
                .define('E', FBContent.BLOCK_FRAMED_SLAB_EDGE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_STAIRS)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_VERTICAL_DOUBLE_STAIRS.get())
                .pattern("SE")
                .define('S', FBContent.BLOCK_FRAMED_VERTICAL_STAIRS.get())
                .define('E', FBContent.BLOCK_FRAMED_CORNER_PILLAR.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_VERTICAL_STAIRS)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_WALL_BOARD.get(), 4)
                .pattern("FFH")
                .define('F', FBContent.BLOCK_FRAMED_PANEL.get())
                .define('H', FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_PANEL)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_CORNER_STRIP.get(), 16)
                .requires(FBContent.BLOCK_FRAMED_FLOOR.get())
                .requires(FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_FLOOR)
                .save(consumer, Utils.rl("framed_corner_strip_from_framed_floor_board"));

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_CORNER_STRIP.get(), 16)
                .requires(FBContent.BLOCK_FRAMED_WALL_BOARD.get())
                .requires(FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_WALL_BOARD)
                .save(consumer, Utils.rl("framed_corner_strip_from_framed_wall_board"));

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_GLOWING_CUBE.get())
                .pattern(" G ")
                .pattern("GCG")
                .pattern(" G ")
                .define('G', Tags.Items.DUSTS_GLOWSTONE)
                .define('C', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_PYRAMID.get(), 4)
                .pattern("SS")
                .pattern("SS")
                .define('S', FBContent.BLOCK_FRAMED_SLOPE_PANEL.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE_PANEL)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_PYRAMID_SLAB.get(), 4)
                .pattern("SS")
                .pattern("SS")
                .define('S', FBContent.BLOCK_FRAMED_SLOPE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_HORIZONTAL_PANE.get(), 4)
                .pattern("PP")
                .pattern("PP")
                .define('P', FBContent.BLOCK_FRAMED_PANE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_PANE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_LARGE_BUTTON.get())
                .pattern("BB")
                .pattern("BB")
                .define('B', FBContent.BLOCK_FRAMED_BUTTON.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_BUTTON)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_LARGE_STONE_BUTTON.get())
                .pattern("BB")
                .pattern("BB")
                .define('B', FBContent.BLOCK_FRAMED_STONE_BUTTON.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_STONE_BUTTON)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_TARGET.get())
                .pattern("FRF")
                .pattern("RHR")
                .pattern("FRF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('H', Items.HAY_BLOCK)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_GATE.get(), 4)
                .pattern("DD")
                .pattern("DD")
                .define('D', FBContent.BLOCK_FRAMED_DOOR.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_DOOR)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_IRON_GATE.get(), 4)
                .pattern("DD")
                .pattern("DD")
                .define('D', FBContent.BLOCK_FRAMED_IRON_DOOR.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_IRON_DOOR)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_ITEM_FRAME.get())
                .pattern("FFF")
                .pattern("FLF")
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .define('L', Tags.Items.LEATHER)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_GLOWING_ITEM_FRAME.get())
                .requires(FBContent.BLOCK_FRAMED_ITEM_FRAME.get())
                .requires(Items.GLOW_INK_SAC)
                .unlockedBy(FBContent.BLOCK_FRAMED_ITEM_FRAME)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FANCY_RAIL.get(), 16)
                .pattern("I I")
                .pattern("IFI")
                .pattern("I I")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FANCY_POWERED_RAIL.get(), 6)
                .pattern("G G")
                .pattern("GFG")
                .pattern("GRG")
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FANCY_DETECTOR_RAIL.get(), 6)
                .pattern("IPI")
                .pattern("IFI")
                .pattern("IRI")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('P', Items.STONE_PRESSURE_PLATE)
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FANCY_ACTIVATOR_RAIL.get(), 6)
                .pattern("IFI")
                .pattern("IRI")
                .pattern("IFI")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('R', Items.REDSTONE_TORCH)
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FANCY_RAIL_SLOPE.get())
                .pattern("R")
                .pattern("S")
                .define('R', FBContent.BLOCK_FRAMED_FANCY_RAIL.get())
                .define('S', FBContent.BLOCK_FRAMED_SLOPE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FANCY_POWERED_RAIL_SLOPE.get())
                .pattern("R")
                .pattern("S")
                .define('R', FBContent.BLOCK_FRAMED_FANCY_POWERED_RAIL.get())
                .define('S', FBContent.BLOCK_FRAMED_SLOPE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FANCY_DETECTOR_RAIL_SLOPE.get())
                .pattern("R")
                .pattern("S")
                .define('R', FBContent.BLOCK_FRAMED_FANCY_DETECTOR_RAIL.get())
                .define('S', FBContent.BLOCK_FRAMED_SLOPE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_FANCY_ACTIVATOR_RAIL_SLOPE.get())
                .pattern("R")
                .pattern("S")
                .define('R', FBContent.BLOCK_FRAMED_FANCY_ACTIVATOR_RAIL.get())
                .define('S', FBContent.BLOCK_FRAMED_SLOPE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_HALF_SLOPE.get(), 2)
                .requires(FBContent.BLOCK_FRAMED_SLOPE.get())
                .requires(FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLOPE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DIVIDED_SLOPE.get())
                .pattern("SS")
                .define('S', FBContent.BLOCK_FRAMED_HALF_SLOPE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_HALF_SLOPE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_DOUBLE_HALF_SLOPE.get())
                .pattern("S")
                .pattern("S")
                .define('S', FBContent.BLOCK_FRAMED_HALF_SLOPE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_HALF_SLOPE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_SLOPED_STAIRS.get())
                .pattern("H")
                .pattern("S")
                .define('H', FBContent.BLOCK_FRAMED_HALF_SLOPE.get())
                .define('S', FBContent.BLOCK_FRAMED_SLAB.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLAB)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_VERTICAL_SLOPED_STAIRS.get())
                .pattern("PH")
                .define('H', FBContent.BLOCK_FRAMED_HALF_SLOPE.get())
                .define('P', FBContent.BLOCK_FRAMED_PANEL.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_PANEL)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_MINI_CUBE.get(), 1)
                .requires(FBContent.BLOCK_FRAMED_CUBE.get())
                .requires(FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_HALF_PILLAR)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_ONE_WAY_WINDOW.get(), 4)
                .pattern("GFG")
                .pattern("F F")
                .pattern("GFG")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .define('G', Blocks.TINTED_GLASS)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_BOOKSHELF.get())
                .pattern("FFF")
                .pattern("BBB")
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .define('B', Items.BOOK)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_CHISELED_BOOKSHELF.get())
                .pattern("FFF")
                .pattern("SSS")
                .pattern("FFF")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .define('S', FBContent.BLOCK_FRAMED_SLAB.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_CENTERED_SLAB.get())
                .requires(FBContent.BLOCK_FRAMED_SLAB.get())
                .requires(FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLAB)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_SLAB.get())
                .requires(FBContent.BLOCK_FRAMED_CENTERED_SLAB.get())
                .requires(FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CENTERED_SLAB)
                .save(consumer, Utils.rl("framed_slab_from_framed_centered_slab"));

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_CENTERED_PANEL.get())
                .requires(FBContent.BLOCK_FRAMED_PANEL.get())
                .requires(FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_PANEL)
                .save(consumer);

        shapelessBuildingBlock(FBContent.BLOCK_FRAMED_PANEL.get())
                .requires(FBContent.BLOCK_FRAMED_CENTERED_PANEL.get())
                .requires(FBContent.ITEM_FRAMED_HAMMER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CENTERED_PANEL)
                .save(consumer, Utils.rl("framed_panel_from_framed_centered_panel"));



        shapedRecipe(RecipeCategory.TOOLS, FBContent.BLOCK_FRAMING_SAW.get())
                .pattern(" I ")
                .pattern("FFF")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedRecipe(RecipeCategory.TOOLS, FBContent.BLOCK_POWERED_FRAMING_SAW.get())
                .pattern("RIR")
                .pattern("FFF")
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_CHECKERED_CUBE.get())
                .pattern("CCC")
                .pattern("C C")
                .pattern("CCC")
                .define('C', FBContent.BLOCK_FRAMED_SLAB_CORNER.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_SLAB_CORNER)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_CHECKERED_SLAB.get(), 6)
                .pattern("CCC")
                .define('C', FBContent.BLOCK_FRAMED_CHECKERED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CHECKERED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_CHECKERED_PANEL.get(), 6)
                .pattern("C")
                .pattern("C")
                .pattern("C")
                .define('C', FBContent.BLOCK_FRAMED_CHECKERED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CHECKERED_CUBE)
                .save(consumer);

        shapedBuildingBlock(FBContent.BLOCK_FRAMED_TUBE.get(), 4)
                .pattern(" C ")
                .pattern("C C")
                .pattern(" C ")
                .define('C', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);



        shapedRecipe(RecipeCategory.TOOLS, FBContent.ITEM_FRAMED_HAMMER.get())
                .pattern(" F ")
                .pattern(" SF")
                .pattern("S  ")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .define('S', Items.STICK)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedRecipe(RecipeCategory.TOOLS, FBContent.ITEM_FRAMED_WRENCH.get())
                .pattern("F F")
                .pattern(" S ")
                .pattern(" S ")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .define('S', Items.STICK)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedRecipe(RecipeCategory.TOOLS, FBContent.ITEM_FRAMED_BLUEPRINT.get())
                .pattern(" F ")
                .pattern("FPF")
                .pattern(" F ")
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .define('P', Items.PAPER)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedRecipe(RecipeCategory.TOOLS, FBContent.ITEM_FRAMED_KEY.get())
                .pattern("SSF")
                .pattern("NN ")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .define('N', Tags.Items.NUGGETS_IRON)
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedRecipe(RecipeCategory.TOOLS, FBContent.ITEM_FRAMED_SCREWDRIVER.get())
                .pattern("S ")
                .pattern(" F")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);

        shapedRecipe(RecipeCategory.MISC, FBContent.ITEM_FRAMED_REINFORCEMENT.get(), 16)
                .pattern("OSO")
                .pattern("SFS")
                .pattern("OSO")
                .define('O', Tags.Items.OBSIDIAN)
                .define('S', Items.STICK)
                .define('F', FBContent.BLOCK_FRAMED_CUBE.get())
                .unlockedBy(FBContent.BLOCK_FRAMED_CUBE)
                .save(consumer);



        makeRotationRecipe(FBContent.BLOCK_FRAMED_SLAB, FBContent.BLOCK_FRAMED_PANEL, consumer);
        makeRotationRecipe(FBContent.BLOCK_FRAMED_DIVIDED_SLAB, FBContent.BLOCK_FRAMED_DIVIDED_PANEL_HOR, consumer);
        makeRotationRecipe(FBContent.BLOCK_FRAMED_STAIRS, FBContent.BLOCK_FRAMED_VERTICAL_STAIRS, consumer);
        makeRotationRecipe(FBContent.BLOCK_FRAMED_DOUBLE_STAIRS, FBContent.BLOCK_FRAMED_VERTICAL_DOUBLE_STAIRS, consumer);
        makeRotationRecipe(FBContent.BLOCK_FRAMED_FLOOR, FBContent.BLOCK_FRAMED_WALL_BOARD, consumer);
        makeRotationRecipe(FBContent.BLOCK_FRAMED_SLOPE_SLAB, FBContent.BLOCK_FRAMED_SLOPE_PANEL, consumer);
        makeRotationRecipe(FBContent.BLOCK_FRAMED_ELEVATED_SLOPE_SLAB, FBContent.BLOCK_FRAMED_EXTENDED_SLOPE_PANEL, consumer);
        makeRotationRecipe(FBContent.BLOCK_FRAMED_DOUBLE_SLOPE_SLAB, FBContent.BLOCK_FRAMED_DOUBLE_SLOPE_PANEL, consumer);
        makeRotationRecipe(FBContent.BLOCK_FRAMED_INVERSE_DOUBLE_SLOPE_SLAB, FBContent.BLOCK_FRAMED_INVERSE_DOUBLE_SLOPE_PANEL, consumer);
        makeRotationRecipe(FBContent.BLOCK_FRAMED_ELEVATED_DOUBLE_SLOPE_SLAB, FBContent.BLOCK_FRAMED_EXTENDED_DOUBLE_SLOPE_PANEL, consumer);
        makeRotationRecipe(FBContent.BLOCK_FRAMED_STACKED_SLOPE_SLAB, FBContent.BLOCK_FRAMED_STACKED_SLOPE_PANEL, consumer);
        makeRotationRecipe(FBContent.BLOCK_FRAMED_FLAT_SLOPE_SLAB_CORNER, FBContent.BLOCK_FRAMED_FLAT_SLOPE_PANEL_CORNER, consumer);
        makeRotationRecipe(FBContent.BLOCK_FRAMED_FLAT_INNER_SLOPE_SLAB_CORNER, FBContent.BLOCK_FRAMED_FLAT_INNER_SLOPE_PANEL_CORNER, consumer);
        makeRotationRecipe(FBContent.BLOCK_FRAMED_FLAT_ELEVATED_SLOPE_SLAB_CORNER, FBContent.BLOCK_FRAMED_FLAT_EXTENDED_SLOPE_PANEL_CORNER, consumer);
        makeRotationRecipe(FBContent.BLOCK_FRAMED_FLAT_ELEVATED_INNER_SLOPE_SLAB_CORNER, FBContent.BLOCK_FRAMED_FLAT_EXTENDED_INNER_SLOPE_PANEL_CORNER, consumer);
        makeRotationRecipe(FBContent.BLOCK_FRAMED_FLAT_DOUBLE_SLOPE_SLAB_CORNER, FBContent.BLOCK_FRAMED_FLAT_DOUBLE_SLOPE_PANEL_CORNER, consumer);
        makeRotationRecipe(FBContent.BLOCK_FRAMED_FLAT_INVERSE_DOUBLE_SLOPE_SLAB_CORNER, FBContent.BLOCK_FRAMED_FLAT_INVERSE_DOUBLE_SLOPE_PANEL_CORNER, consumer);
        makeRotationRecipe(FBContent.BLOCK_FRAMED_FLAT_ELEVATED_DOUBLE_SLOPE_SLAB_CORNER, FBContent.BLOCK_FRAMED_FLAT_EXTENDED_DOUBLE_SLOPE_PANEL_CORNER, consumer);
        makeRotationRecipe(FBContent.BLOCK_FRAMED_FLAT_ELEVATED_INNER_DOUBLE_SLOPE_SLAB_CORNER, FBContent.BLOCK_FRAMED_FLAT_EXTENDED_INNER_DOUBLE_SLOPE_PANEL_CORNER, consumer);
        makeRotationRecipe(FBContent.BLOCK_FRAMED_FLAT_STACKED_SLOPE_SLAB_CORNER, FBContent.BLOCK_FRAMED_FLAT_STACKED_SLOPE_PANEL_CORNER, consumer);
        makeRotationRecipe(FBContent.BLOCK_FRAMED_FLAT_STACKED_INNER_SLOPE_SLAB_CORNER, FBContent.BLOCK_FRAMED_FLAT_STACKED_INNER_SLOPE_PANEL_CORNER, consumer);
        makeRotationRecipe(FBContent.BLOCK_FRAMED_CENTERED_SLAB, FBContent.BLOCK_FRAMED_CENTERED_PANEL, consumer);
    }

    private static void makeRotationRecipe(RegistryObject<Block> first, RegistryObject<Block> second, Consumer<FinishedRecipe> consumer)
    {
        String name = first.getId().getPath() + "_rotate_to_" + second.getId().getPath();
        shapelessBuildingBlock(second.get())
                .requires(first.get())
                .requires(FBContent.ITEM_FRAMED_WRENCH.get())
                .unlockedBy(first)
                .save(consumer, Utils.rl(name));

        name = second.getId().getPath() + "_rotate_to_" + first.getId().getPath();
        shapelessBuildingBlock(first.get())
                .requires(second.get())
                .requires(FBContent.ITEM_FRAMED_WRENCH.get())
                .unlockedBy(second)
                .save(consumer, Utils.rl(name));
    }

    private static ExtShapedRecipeBuilder shapedBuildingBlock(ItemLike output)
    {
        return shapedBuildingBlock(output, 1);
    }

    private static ExtShapedRecipeBuilder shapedBuildingBlock(ItemLike output, int count)
    {
        return shapedRecipe(RecipeCategory.BUILDING_BLOCKS, output, count);
    }

    private static ExtShapedRecipeBuilder shapedRecipe(RecipeCategory category, ItemLike output)
    {
        return shapedRecipe(category, output, 1);
    }

    private static ExtShapedRecipeBuilder shapedRecipe(RecipeCategory category, ItemLike output, int count)
    {
        return new ExtShapedRecipeBuilder(category, output, count);
    }

    private static ExtShapelessRecipeBuilder shapelessBuildingBlock(ItemLike output)
    {
        return shapelessBuildingBlock(output, 1);
    }

    private static ExtShapelessRecipeBuilder shapelessBuildingBlock(ItemLike output, int count)
    {
        return shapelessRecipe(RecipeCategory.BUILDING_BLOCKS, output, count);
    }

    private static ExtShapelessRecipeBuilder shapelessRecipe(RecipeCategory category, ItemLike output)
    {
        return shapelessRecipe(category, output, 1);
    }

    private static ExtShapelessRecipeBuilder shapelessRecipe(RecipeCategory category, ItemLike output, int count)
    {
        return new ExtShapelessRecipeBuilder(category, output, count);
    }
}