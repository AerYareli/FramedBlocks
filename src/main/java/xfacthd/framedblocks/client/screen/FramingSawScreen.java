package xfacthd.framedblocks.client.screen;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.client.render.item.ItemRenderHelper;
import xfacthd.framedblocks.client.util.RecipeViewer;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.crafting.*;
import xfacthd.framedblocks.common.menu.FramingSawMenu;
import xfacthd.framedblocks.common.menu.IFramingSawMenu;
import xfacthd.framedblocks.common.net.payload.SelectFramingSawRecipePayload;
import xfacthd.framedblocks.common.util.FramedUtils;

import java.util.*;

public class FramingSawScreen extends AbstractContainerScreen<FramingSawMenu>
{
    public static final String TOOLTIP_MATERIAL = Utils.translationKey("tooltip", "framing_saw.material");
    public static final Component TOOLTIP_LOOSE_ADDITIVE = Utils.translate("tooltip", "framing_saw.loose_additive");
    public static final String TOOLTIP_HAVE_X_BUT_NEED_Y_ITEM = Utils.translationKey("tooltip", "framing_saw.have_x_but_need_y_item");
    public static final String TOOLTIP_HAVE_X_BUT_NEED_Y_TAG = Utils.translationKey("tooltip", "framing_saw.have_x_but_need_y_tag");
    public static final String TOOLTIP_HAVE_X_BUT_NEED_Y_ITEM_COUNT = Utils.translationKey("tooltip", "framing_saw.have_x_but_need_y_item_count");
    public static final String TOOLTIP_HAVE_X_BUT_NEED_Y_MATERIAL_COUNT = Utils.translationKey("tooltip", "framing_saw.have_x_but_need_y_material_count");
    public static final Component TOOLTIP_HAVE_ITEM_NONE = Utils.translate("tooltip", "framing_saw.have_item_none").withStyle(ChatFormatting.GOLD);
    public static final String TOOLTIP_PRESS_TO_SHOW = Utils.translationKey("tooltip", "framing_saw.press_to_show");
    private static final ResourceLocation BACKGROUND = Utils.rl("textures/gui/framing_saw.png");
    public static final ResourceLocation WARNING_ICON = new ResourceLocation("neoforge", "textures/gui/experimental_warning.png");
    private static final int IMAGE_WIDTH = 256;
    private static final int IMAGE_HEIGHT = 233;
    private static final int RECIPES_X = 48;
    private static final int RECIPES_Y = 18;
    private static final int RECIPE_ROWS = 6;
    private static final int RECIPE_COLS = 8;
    private static final int RECIPE_COUNT = RECIPE_ROWS * RECIPE_COLS;
    private static final int RECIPE_WIDTH = 18;
    private static final int RECIPE_HEIGHT = 18;
    private static final int SCROLL_BAR_X = 195;
    private static final int SCROLL_BAR_Y = 18;
    private static final int SCROLL_BTN_WIDTH = 12;
    private static final int SCROLL_BTN_HEIGHT = 15;
    private static final int SCROLL_BTN_TEX_X = RECIPE_WIDTH * 3;
    private static final int SCROLL_BAR_HEIGHT = 108;
    private static final int WARNING_X = 20;
    private static final int WARNING_Y = 46;
    private static final RecipeViewer RECIPE_VIEWER = RecipeViewer.get();

    private final FramingSawRecipeCache cache = FramingSawRecipeCache.get(true);
    private final ItemStack cubeStack = new ItemStack(FBContent.BLOCK_FRAMED_CUBE.value());
    private int firstIndex = 0;
    private boolean scrolling = false;
    private float scrollOffset = 0F;

    public FramingSawScreen(FramingSawMenu menu, Inventory inv, Component title)
    {
        super(menu, inv, title);
        titleLabelY -= 1;
        inventoryLabelX = 47;
        inventoryLabelY = 139;
        imageWidth = IMAGE_WIDTH;
        imageHeight = IMAGE_HEIGHT;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

        graphics.blit(BACKGROUND, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        int offset = (int) ((SCROLL_BAR_HEIGHT - SCROLL_BTN_HEIGHT) * scrollOffset);
        int scrollU = SCROLL_BTN_TEX_X + (isScrollBarActive() ? 0 : SCROLL_BTN_WIDTH);
        graphics.blit(BACKGROUND, leftPos + SCROLL_BAR_X, topPos + SCROLL_BAR_Y + offset, scrollU, imageHeight, SCROLL_BTN_WIDTH, SCROLL_BTN_HEIGHT);

        ItemStack input = menu.getInputStack();
        if (!input.isEmpty() && cache.containsAdditive(input.getItem()))
        {
            graphics.blit(WARNING_ICON, leftPos + WARNING_X, topPos + WARNING_Y, 8, 8, 24, 24, 32, 32);
        }

        int idx = menu.getSelectedRecipeIndex();
        if (menu.hasRecipeChanged())
        {
            tryScrollToRecipe(idx);
        }

        int recX = leftPos + RECIPES_X;
        int recY = topPos + RECIPES_Y;
        int lastIndex = firstIndex + RECIPE_COUNT;
        renderButtons(graphics, mouseX, mouseY, recX, recY, lastIndex);
        renderRecipes(graphics, recX, recY, lastIndex);

        List<RecipeHolder<FramingSawRecipe>> recipes = cache.getRecipes();
        if (idx >= 0 && idx < recipes.size())
        {
            FramingSawRecipe recipe = recipes.get(idx).value();
            if (input.isEmpty())
            {
                ItemRenderHelper.renderFakeItemTransparent(graphics.pose(), cubeStack, leftPos + 20, topPos + 28, 127);
            }

            List<FramingSawRecipeAdditive> additives = recipe.getAdditives();
            for (int i = 0; i < additives.size(); i++)
            {
                if (!menu.getAdditiveStack(i).isEmpty())
                {
                    continue;
                }

                ItemStack[] items = additives.get(i).ingredient().getItems();
                int t = (int) (System.currentTimeMillis() / 1700) % items.length;
                int y = topPos + 64 + (18 * i);
                ItemRenderHelper.renderFakeItemTransparent(graphics.pose(), items[t], leftPos + 20, y, 127);
            }
        }
    }

    private void tryScrollToRecipe(int idx)
    {
        if (idx != -1 && (idx < firstIndex || idx >= firstIndex + RECIPE_COUNT))
        {
            int row = (idx / RECIPE_COLS) - 2; // Center the selected recipe if possible
            int hidden = getHiddenRows();
            scrollOffset = (float) row / (float) hidden;
            scrollOffset = Mth.clamp(scrollOffset, 0, 1);
            firstIndex = calculateFirstIndex(hidden);
        }
    }

    @Override
    protected void renderTooltip(GuiGraphics graphics, int mouseX, int mouseY)
    {
        if (menu.getCarried().isEmpty() && hoveredSlot != null && hoveredSlot.hasItem())
        {
            renderItemTooltip(graphics, mouseX, mouseY, hoveredSlot.getItem(), null);
            return;
        }

        ItemStack input = menu.getInputStack();
        if (!input.isEmpty() && isHovering(WARNING_X, WARNING_Y, 16, 16, mouseX, mouseY) && cache.containsAdditive(input.getItem()))
        {
            graphics.renderTooltip(font, TOOLTIP_LOOSE_ADDITIVE, mouseX, mouseY);
            return;
        }

        int x = leftPos + RECIPES_X;
        int y = topPos + RECIPES_Y;
        int last = firstIndex + RECIPE_COUNT;
        List<FramingSawMenu.FramedRecipeHolder> recipes = menu.getRecipes();

        for (int idx = firstIndex; idx < last && idx < recipes.size(); idx++)
        {
            int relIdx = idx - firstIndex;
            int recX = x + relIdx % RECIPE_COLS * RECIPE_WIDTH;
            int recY = y + relIdx / RECIPE_COLS * RECIPE_HEIGHT;
            if (mouseX >= recX && mouseX < recX + RECIPE_WIDTH && mouseY >= recY && mouseY < recY + RECIPE_HEIGHT)
            {
                FramingSawMenu.FramedRecipeHolder recipe = recipes.get(idx);
                ItemStack result = recipe.getRecipe().getResult();
                renderItemTooltip(graphics, mouseX, mouseY, result, recipe);
            }
        }
    }

    private void renderItemTooltip(GuiGraphics graphics, int mouseX, int mouseY, ItemStack stack, FramingSawMenu.FramedRecipeHolder recipeHolder)
    {
        //noinspection ConstantConditions
        List<Component> components = new ArrayList<>(getTooltipFromItem(minecraft, stack));
        Optional<TooltipComponent> tooltip = stack.getTooltipImage();

        int material = cache.getMaterialValue(stack.getItem());
        if (material > 0)
        {
            components.add(Component.translatable(TOOLTIP_MATERIAL, material));
        }

        if (recipeHolder != null)
        {
            appendRecipeFailure(components, recipeHolder);
        }

        graphics.renderTooltip(font, components, tooltip, stack, mouseX, mouseY);
    }

    private void appendRecipeFailure(List<Component> components, FramingSawMenu.FramedRecipeHolder recipeHolder)
    {
        appendRecipeFailure(components, cache, recipeHolder.getRecipe(), recipeHolder.getMatchResult(), menu);
    }

    public static List<Component> appendRecipeFailure(
            List<Component> components,
            FramingSawRecipeCache cache,
            FramingSawRecipe recipe,
            FramingSawRecipeMatchResult matchResult,
            IFramingSawMenu menu
    )
    {
        if (!matchResult.success())
        {
            components.add(matchResult.translation());

            ItemStack input = menu.getInputStack();
            int listAdditives = -1;
            MutableComponent detail = switch (matchResult)
            {
                case MATERIAL_VALUE ->
                {
                    int matIn = input.isEmpty() ? 0 : cache.getMaterialValue(input.getItem()) * input.getCount();
                    int matReq = recipe.getMaterialAmount();
                    yield Component.translatable(
                            TOOLTIP_HAVE_X_BUT_NEED_Y_MATERIAL_COUNT,
                            Component.literal(Integer.toString(matIn)).withStyle(ChatFormatting.GOLD),
                            Component.literal(Integer.toString(matReq)).withStyle(ChatFormatting.GOLD)
                    );
                }
                case MATERIAL_LCM ->
                {
                    FramingSawRecipeCalculation calc = recipe.makeCraftingCalculation(
                            menu.getInputContainer(), true
                    );
                    yield Component.translatable(
                            TOOLTIP_HAVE_X_BUT_NEED_Y_ITEM_COUNT,
                            Component.literal(Integer.toString(input.getCount())).withStyle(ChatFormatting.GOLD),
                            Component.literal(Integer.toString(calc.getInputCount())).withStyle(ChatFormatting.GOLD)
                    );
                }
                case MISSING_ADDITIVE_0, MISSING_ADDITIVE_1, MISSING_ADDITIVE_2 ->
                {
                    listAdditives = matchResult.additiveSlot();
                    Ingredient additive = recipe.getAdditives().get(matchResult.additiveSlot()).ingredient();
                    yield makeHaveButNeedTooltip(TOOLTIP_HAVE_ITEM_NONE, additive);
                }
                case UNEXPECTED_ADDITIVE_0, UNEXPECTED_ADDITIVE_1, UNEXPECTED_ADDITIVE_2 ->
                {
                    Item itemIn = menu.getAdditiveStack(matchResult.additiveSlot()).getItem();
                    yield Component.translatable(
                            TOOLTIP_HAVE_X_BUT_NEED_Y_ITEM,
                            Component.translatable(itemIn.getDescriptionId()).withStyle(ChatFormatting.GOLD),
                            TOOLTIP_HAVE_ITEM_NONE
                    );
                }
                case INCORRECT_ADDITIVE_0, INCORRECT_ADDITIVE_1, INCORRECT_ADDITIVE_2 ->
                {
                    listAdditives = matchResult.additiveSlot();
                    Item itemIn = menu.getAdditiveStack(matchResult.additiveSlot()).getItem();
                    Ingredient additive = recipe.getAdditives().get(matchResult.additiveSlot()).ingredient();
                    yield makeHaveButNeedTooltip(
                            Component.translatable(itemIn.getDescriptionId()).withStyle(ChatFormatting.GOLD),
                            additive
                    );
                }
                case INSUFFICIENT_ADDITIVE_0, INSUFFICIENT_ADDITIVE_1, INSUFFICIENT_ADDITIVE_2 ->
                {
                    FramingSawRecipeCalculation calc = recipe.makeCraftingCalculation(
                            menu.getInputContainer(), true
                    );
                    int cntIn = menu.getAdditiveStack(matchResult.additiveSlot()).getCount();
                    int cntReq = calc.getAdditiveCount(matchResult.additiveSlot());
                    yield Component.translatable(
                            TOOLTIP_HAVE_X_BUT_NEED_Y_ITEM_COUNT,
                            Component.literal(Integer.toString(cntIn)).withStyle(ChatFormatting.GOLD),
                            Component.literal(Integer.toString(cntReq)).withStyle(ChatFormatting.GOLD)
                    );
                }
                case SUCCESS -> throw new IllegalStateException("Unreachable");
            };
            components.add(detail.withStyle(ChatFormatting.RED));

            if (listAdditives > -1)
            {
                appendAdditiveItemOptions(components, recipe, listAdditives);
            }
        }
        return components;
    }

    private static void appendAdditiveItemOptions(List<Component> components, FramingSawRecipe recipe, int additiveSlot)
    {
        Ingredient additive = recipe.getAdditives().get(additiveSlot).ingredient();
        if (additive.getItems().length <= 1)
        {
            return;
        }

        if (hasShiftDown())
        {
            for (ItemStack option : additive.getItems())
            {
                components.add(Component.literal("- ").append(option.getItem().getDescription()).withStyle(ChatFormatting.GOLD));
            }
        }
        else
        {
            Component keyName = InputConstants.getKey(GLFW.GLFW_KEY_LEFT_SHIFT, -1).getDisplayName();
            components.add(Component.translatable(
                    TOOLTIP_PRESS_TO_SHOW,
                    Component.literal("").append(keyName).withStyle(ChatFormatting.GOLD)
            ).withStyle(ChatFormatting.RED));
        }
    }

    private static MutableComponent makeHaveButNeedTooltip(Component present, Ingredient additive)
    {
        ItemStack[] options = additive.getItems();
        if (options.length > 1 && FramedUtils.getSingleIngredientValue(additive) instanceof Ingredient.TagValue value)
        {
            TagKey<Item> tag = FramedUtils.getItemTagFromValue(value);
            return Component.translatable(
                    TOOLTIP_HAVE_X_BUT_NEED_Y_TAG,
                    present,
                    Component.literal("#" + tag.location()).withStyle(ChatFormatting.GOLD)
            );
        }
        else
        {
            return Component.translatable(
                    TOOLTIP_HAVE_X_BUT_NEED_Y_ITEM,
                    present,
                    Component.translatable(options[0].getItem().getDescriptionId()).withStyle(ChatFormatting.GOLD)
            );
        }
    }

    private void renderButtons(GuiGraphics graphics, int mouseX, int mouseY, int x, int y, int lastIdx)
    {
        List<FramingSawMenu.FramedRecipeHolder> recipes = menu.getRecipes();
        for (int idx = firstIndex; idx < lastIdx && idx < recipes.size(); ++idx)
        {
            int relIdx = idx - firstIndex;
            int recX = x + relIdx % RECIPE_COLS * RECIPE_WIDTH;
            int recY = y + relIdx / RECIPE_COLS * RECIPE_HEIGHT;

            int u = 0;
            boolean hovered = false;
            if (idx == menu.getSelectedRecipeIndex())
            {
                u += RECIPE_WIDTH;
            }
            else if (mouseX >= recX && mouseY >= recY && mouseX < recX + RECIPE_WIDTH && mouseY < recY + RECIPE_HEIGHT)
            {
                u += (RECIPE_WIDTH * 2);
                hovered = true;
            }

            if (!hovered && !recipes.get(idx).getMatchResult().success())
            {
                RenderSystem.setShaderColor(.9F, .3F, .3F, 1F);
            }
            else
            {
                RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
            }

            graphics.blit(BACKGROUND, recX, recY, u, imageHeight, RECIPE_WIDTH, RECIPE_HEIGHT);
        }
    }

    private void renderRecipes(GuiGraphics graphics, int pLeft, int pTop, int lastIndex)
    {
        List<FramingSawMenu.FramedRecipeHolder> recipes = menu.getRecipes();

        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        for (int idx = firstIndex; idx < lastIndex && idx < recipes.size(); idx++)
        {
            int relIdx = idx - firstIndex;
            int x = pLeft + relIdx % RECIPE_COLS * RECIPE_WIDTH + 1;
            int y = pTop + relIdx / RECIPE_COLS * RECIPE_HEIGHT + 1;

            ItemStack stack = recipes.get(idx).getRecipe().getResult();
            graphics.renderItem(stack, x, y, x * y * imageWidth);
            graphics.renderItemDecorations(font, stack, x, y);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        scrolling = false;

        int x = leftPos + RECIPES_X;
        int y = topPos + RECIPES_Y;
        int lastIdx = firstIndex + RECIPE_COUNT;

        for (int idx = firstIndex; idx < lastIdx; ++idx)
        {
            int relIdx = idx - firstIndex;
            double recRelX = mouseX - (double)(x + relIdx % RECIPE_COLS * RECIPE_WIDTH);
            double recRelY = mouseY - (double)(y + relIdx / RECIPE_COLS * RECIPE_HEIGHT);
            if (recRelX < 0 || recRelY < 0 || recRelX > RECIPE_WIDTH || recRelY > RECIPE_HEIGHT)
            {
                continue;
            }

            //noinspection ConstantConditions
            if (menu.clickMenuButton(minecraft.player, idx))
            {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                PacketDistributor.SERVER.noArg().send(new SelectFramingSawRecipePayload(menu.containerId, idx));
                return true;
            }
        }

        if (isScrollBarActive())
        {
            x = leftPos + SCROLL_BAR_X;
            y = topPos + SCROLL_BAR_Y;
            if (mouseX >= (double) x && mouseX < (double) (x + SCROLL_BTN_WIDTH) && mouseY >= (double) y && mouseY < (double) (y + SCROLL_BAR_HEIGHT))
            {
                scrolling = true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY)
    {
        if (scrolling && isScrollBarActive())
        {
            float topY = topPos + RECIPES_Y;
            float botY = topY + SCROLL_BAR_HEIGHT;
            float freeScrollHeight = botY - topY - SCROLL_BTN_HEIGHT;

            scrollOffset = ((float) mouseY - topY - (SCROLL_BTN_HEIGHT / 2F)) / freeScrollHeight;
            scrollOffset = Mth.clamp(scrollOffset, 0F, 1F);
            firstIndex = calculateFirstIndex(getHiddenRows());

            return true;
        }

        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY)
    {
        if (isScrollBarActive())
        {
            int hiddenRows = getHiddenRows();
            float offset = (float) deltaY / (float) hiddenRows;
            scrollOffset = Mth.clamp(scrollOffset - offset, 0F, 1F);
            firstIndex = calculateFirstIndex(hiddenRows);
        }

        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        RecipeViewer.LookupTarget target;
        if (RECIPE_VIEWER != null && (target = RECIPE_VIEWER.isShowRecipePressed(keyCode, scanCode)) != null)
        {
            Window window = Objects.requireNonNull(minecraft).getWindow();
            MouseHandler mouseHandler = minecraft.mouseHandler;
            double mouseX = mouseHandler.xpos() * (double)window.getGuiScaledWidth() / (double)window.getScreenWidth();
            double mouseY = mouseHandler.ypos() * (double)window.getGuiScaledHeight() / (double)window.getScreenHeight();

            RecipeHolder<FramingSawRecipe> recipe = getRecipeAt(mouseX, mouseY);
            if (recipe != null && RECIPE_VIEWER.handleShowRecipeRequest(recipe.value().getResult(), target))
            {
                return true;
            }
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public RecipeHolder<FramingSawRecipe> getRecipeAt(double mouseX, double mouseY)
    {
        double x = leftPos + RECIPES_X;
        double y = topPos + RECIPES_Y;

        if (mouseX >= x && mouseX <= x + (RECIPE_WIDTH * RECIPE_COLS) && mouseY >= y && mouseY <= y + (RECIPE_HEIGHT * RECIPE_ROWS))
        {
            int col = (int) ((mouseX - x) / RECIPE_WIDTH);
            int row = (int) ((mouseY - y) / RECIPE_HEIGHT);
            int idx = (row * RECIPE_COLS) + col + firstIndex;

            List<RecipeHolder<FramingSawRecipe>> recipes = cache.getRecipes();
            if (idx > 0 && idx < recipes.size())
            {
                return recipes.get(idx);
            }
        }
        return null;
    }

    private boolean isScrollBarActive()
    {
        return menu.getRecipes().size() > RECIPE_COUNT;
    }

    private int getHiddenRows()
    {
        return (menu.getRecipes().size() + RECIPE_COLS - 1) / RECIPE_COLS - RECIPE_ROWS;
    }

    private int calculateFirstIndex(int hiddenRows)
    {
        return (int) ((double) (scrollOffset * (float) hiddenRows) + .5D) * RECIPE_COLS;
    }
}
