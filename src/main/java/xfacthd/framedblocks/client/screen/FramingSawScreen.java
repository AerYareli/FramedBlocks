package xfacthd.framedblocks.client.screen;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.lwjgl.glfw.GLFW;
import xfacthd.framedblocks.api.util.FramedConstants;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.client.util.ItemRenderHelper;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.compat.jei.JeiCompat;
import xfacthd.framedblocks.common.crafting.FramingSawRecipe;
import xfacthd.framedblocks.common.crafting.FramingSawRecipeCache;
import xfacthd.framedblocks.common.menu.FramingSawMenu;
import xfacthd.framedblocks.common.util.RecipeUtils;

import java.util.*;

public class FramingSawScreen extends AbstractContainerScreen<FramingSawMenu>
{
    public static final String TOOLTIP_MATERIAL = "tooltip." + FramedConstants.MOD_ID + ".framing_saw.material";
    public static final Component TOOLTIP_LOOSE_ADDITIVE = Utils.translate("tooltip", "framing_saw.loose_additive");
    public static final String TOOLTIP_HAVE_X_BUT_NEED_Y_ITEM = "tooltip." + FramedConstants.MOD_ID + ".framing_saw.have_x_but_need_y_item";
    public static final String TOOLTIP_HAVE_X_BUT_NEED_Y_TAG = "tooltip." + FramedConstants.MOD_ID + ".framing_saw.have_x_but_need_y_tag";
    public static final String TOOLTIP_HAVE_X_BUT_NEED_Y_ITEM_COUNT = "tooltip." + FramedConstants.MOD_ID + ".framing_saw.have_x_but_need_y_item_count";
    public static final String TOOLTIP_HAVE_X_BUT_NEED_Y_MATERIAL_COUNT = "tooltip." + FramedConstants.MOD_ID + ".framing_saw.have_x_but_need_y_material_count";
    public static final Component TOOLTIP_HAVE_ITEM_NONE = Utils.translate("tooltip", "framing_saw.have_item_none").withStyle(ChatFormatting.GOLD);
    public static final String TOOLTIP_PRESS_TO_SHOW = "tooltip." + FramedConstants.MOD_ID + ".framing_saw.press_to_show";
    private static final ResourceLocation BACKGROUND = Utils.rl("textures/gui/framing_saw.png");
    public static final ResourceLocation WARNING_ICON = new ResourceLocation("forge", "textures/gui/experimental_warning.png");
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
    private static final int WARNING_Y = 64;

    private final FramingSawRecipeCache cache = FramingSawRecipeCache.get(true);
    private final ItemStack cubeStack = new ItemStack(FBContent.blockFramedCube.get());
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
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
    {
        super.render(poseStack, mouseX, mouseY, partialTick);
        renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY)
    {
        renderBackground(poseStack);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

        RenderSystem.setShaderTexture(0, BACKGROUND);
        blit(poseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        int offset = (int) ((SCROLL_BAR_HEIGHT - SCROLL_BTN_HEIGHT) * scrollOffset);
        int scrollU = SCROLL_BTN_TEX_X + (isScrollBarActive() ? 0 : SCROLL_BTN_WIDTH);
        blit(poseStack, leftPos + SCROLL_BAR_X, topPos + SCROLL_BAR_Y + offset, scrollU, imageHeight, SCROLL_BTN_WIDTH, SCROLL_BTN_HEIGHT);

        ItemStack input = menu.getInputStack();
        if (!input.isEmpty() && cache.containsAdditive(input.getItem()))
        {
            RenderSystem.setShaderTexture(0, WARNING_ICON);
            blit(poseStack, leftPos + WARNING_X, topPos + WARNING_Y, 8, 8, 24, 24, 32, 32);
            RenderSystem.setShaderTexture(0, BACKGROUND);
        }

        int recX = leftPos + RECIPES_X;
        int recY = topPos + RECIPES_Y;
        int lastIndex = firstIndex + RECIPE_COUNT;
        renderButtons(poseStack, mouseX, mouseY, recX, recY, lastIndex);
        renderRecipes(recX, recY, lastIndex);

        List<FramingSawRecipe> recipes = cache.getRecipes();
        int idx = menu.getSelectedRecipeIndex();
        if (idx >= 0 && idx < recipes.size())
        {
            FramingSawRecipe recipe = recipes.get(idx);
            if (input.isEmpty())
            {
                ItemRenderHelper.renderFakeItemTransparent(cubeStack, leftPos + 20, topPos + 46, 127);
            }

            Ingredient additive = recipe.getAdditive();
            if (additive != null && !additive.isEmpty() && menu.getAdditiveStack().isEmpty())
            {
                ItemStack[] items = additive.getItems();
                int i = (int) (System.currentTimeMillis() / 1700) % items.length;
                ItemRenderHelper.renderFakeItemTransparent(items[i], leftPos + 20, topPos + 82, 127);
            }
        }
    }

    @Override
    protected void renderTooltip(PoseStack poseStack, int mouseX, int mouseY)
    {
        if (menu.getCarried().isEmpty() && hoveredSlot != null && hoveredSlot.hasItem())
        {
            renderItemTooltip(poseStack, mouseX, mouseY, hoveredSlot.getItem(), null);
            return;
        }

        ItemStack input = menu.getInputStack();
        if (!input.isEmpty() && isHovering(WARNING_X, WARNING_Y, 16, 16, mouseX, mouseY) && cache.containsAdditive(input.getItem()))
        {
            renderTooltip(poseStack, TOOLTIP_LOOSE_ADDITIVE, mouseX, mouseY);
            return;
        }

        int x = leftPos + RECIPES_X;
        int y = topPos + RECIPES_Y;
        int last = firstIndex + RECIPE_COUNT;
        List<FramingSawMenu.RecipeHolder> recipes = menu.getRecipes();

        for (int idx = firstIndex; idx < last && idx < recipes.size(); idx++)
        {
            int relIdx = idx - firstIndex;
            int recX = x + relIdx % RECIPE_COLS * RECIPE_WIDTH;
            int recY = y + relIdx / RECIPE_COLS * RECIPE_HEIGHT;
            if (mouseX >= recX && mouseX < recX + RECIPE_WIDTH && mouseY >= recY && mouseY < recY + RECIPE_HEIGHT)
            {
                FramingSawMenu.RecipeHolder recipe = recipes.get(idx);
                ItemStack result = recipe.getRecipe().getResultItem();
                renderItemTooltip(poseStack, mouseX, mouseY, result, recipe);
            }
        }
    }

    private void renderItemTooltip(PoseStack poseStack, int mouseX, int mouseY, ItemStack stack, FramingSawMenu.RecipeHolder recipeHolder)
    {
        List<Component> components = new ArrayList<>(getTooltipFromItem(stack));
        Optional<TooltipComponent> tooltip = stack.getTooltipImage();

        int material = cache.getMaterialValue(stack.getItem());
        if (material > 0)
        {
            components.add(new TranslatableComponent(TOOLTIP_MATERIAL, material));
        }

        if (recipeHolder != null)
        {
            appendRecipeFailure(components, recipeHolder);
        }

        renderTooltip(poseStack, components, tooltip, mouseX, mouseY, null, stack);
    }

    private void appendRecipeFailure(List<Component> components, FramingSawMenu.RecipeHolder recipeHolder)
    {
        FramingSawRecipe recipe = recipeHolder.getRecipe();
        FramingSawRecipe.FailReason failReason = recipeHolder.getFailReason();
        if (!failReason.success())
        {
            components.add(failReason.translation());

            ItemStack input = menu.getInputStack();
            boolean listAdditives = false;
            MutableComponent detail = switch (failReason)
            {
                case MATERIAL_VALUE ->
                {
                    int matIn = input.isEmpty() ? 0 : cache.getMaterialValue(input.getItem()) * input.getCount();
                    int matReq = recipe.getMaterialAmount();
                    yield new TranslatableComponent(
                            TOOLTIP_HAVE_X_BUT_NEED_Y_MATERIAL_COUNT,
                            new TextComponent(Integer.toString(matIn)).withStyle(ChatFormatting.GOLD),
                            new TextComponent(Integer.toString(matReq)).withStyle(ChatFormatting.GOLD)
                    );
                }
                case MATERIAL_LCM ->
                {
                    int cntIn = input.getCount();
                    int cntReq = recipe.getInputAndAdditiveCount(input, true).firstInt();
                    yield new TranslatableComponent(
                            TOOLTIP_HAVE_X_BUT_NEED_Y_ITEM_COUNT,
                            new TextComponent(Integer.toString(cntIn)).withStyle(ChatFormatting.GOLD),
                            new TextComponent(Integer.toString(cntReq)).withStyle(ChatFormatting.GOLD)
                    );
                }
                case MISSING_ADDITIVE ->
                {
                    listAdditives = true;
                    yield makeHaveButNeedTooltip(
                            TOOLTIP_HAVE_ITEM_NONE,
                            Objects.requireNonNull(recipe.getAdditive())
                    );
                }
                case UNEXPECTED_ADDITIVE ->
                {
                    Item itemIn = menu.getAdditiveStack().getItem();
                    yield new TranslatableComponent(
                            TOOLTIP_HAVE_X_BUT_NEED_Y_ITEM,
                            new TranslatableComponent(itemIn.getDescriptionId()).withStyle(ChatFormatting.GOLD),
                            TOOLTIP_HAVE_ITEM_NONE
                    );
                }
                case INCORRECT_ADDITIVE ->
                {
                    listAdditives = true;
                    Item itemIn = menu.getAdditiveStack().getItem();
                    yield makeHaveButNeedTooltip(
                            new TranslatableComponent(itemIn.getDescriptionId()).withStyle(ChatFormatting.GOLD),
                            Objects.requireNonNull(recipe.getAdditive())
                    );
                }
                case INSUFFICIENT_ADDITIVE ->
                {
                    int cntIn = menu.getAdditiveStack().getCount();
                    int cntReq = recipe.getInputAndAdditiveCount(input, true).secondInt();
                    yield new TranslatableComponent(
                            TOOLTIP_HAVE_X_BUT_NEED_Y_ITEM_COUNT,
                            new TextComponent(Integer.toString(cntIn)).withStyle(ChatFormatting.GOLD),
                            new TextComponent(Integer.toString(cntReq)).withStyle(ChatFormatting.GOLD)
                    );
                }
                case NONE -> throw new IllegalStateException("Unreachable");
            };
            components.add(detail.withStyle(ChatFormatting.RED));

            if (listAdditives && recipe.getAdditive().getItems().length > 1)
            {
                if (hasShiftDown())
                {
                    for (ItemStack option : recipe.getAdditive().getItems())
                    {
                        components.add(new TextComponent("- ").append(option.getItem().getDescription()).withStyle(ChatFormatting.GOLD));
                    }
                }
                else
                {
                    Component keyName = InputConstants.getKey(GLFW.GLFW_KEY_LEFT_SHIFT, -1).getDisplayName();
                    components.add(new TranslatableComponent(
                            TOOLTIP_PRESS_TO_SHOW,
                            new TextComponent("").append(keyName).withStyle(ChatFormatting.GOLD)
                    ).withStyle(ChatFormatting.RED));
                }
            }
        }
    }

    private static MutableComponent makeHaveButNeedTooltip(Component present, Ingredient additive)
    {
        ItemStack[] options = additive.getItems();
        if (options.length > 1 && RecipeUtils.getSingleIngredientValue(additive) instanceof Ingredient.TagValue value)
        {
            TagKey<Item> tag = RecipeUtils.getItemTagFromValue(value);
            return new TranslatableComponent(
                    TOOLTIP_HAVE_X_BUT_NEED_Y_TAG,
                    present,
                    new TextComponent("#" + tag.location()).withStyle(ChatFormatting.GOLD)
            );
        }
        else
        {
            return new TranslatableComponent(
                    TOOLTIP_HAVE_X_BUT_NEED_Y_ITEM,
                    present,
                    new TranslatableComponent(options[0].getItem().getDescriptionId()).withStyle(ChatFormatting.GOLD)
            );
        }
    }

    private void renderButtons(PoseStack poseStack, int mouseX, int mouseY, int x, int y, int lastIdx)
    {
        List<FramingSawMenu.RecipeHolder> recipes = menu.getRecipes();
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

            if (!hovered && !recipes.get(idx).getFailReason().success())
            {
                RenderSystem.setShaderColor(.9F, .3F, .3F, 1F);
            }
            else
            {
                RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
            }

            blit(poseStack, recX, recY, u, imageHeight, RECIPE_WIDTH, RECIPE_HEIGHT);
        }
    }

    private void renderRecipes(int pLeft, int pTop, int lastIndex)
    {
        List<FramingSawMenu.RecipeHolder> recipes = menu.getRecipes();

        for (int idx = firstIndex; idx < lastIndex && idx < recipes.size(); idx++)
        {
            int relIdx = idx - firstIndex;
            int x = pLeft + relIdx % RECIPE_COLS * RECIPE_WIDTH + 1;
            int y = pTop + relIdx / RECIPE_COLS * RECIPE_HEIGHT + 1;

            //noinspection ConstantConditions
            minecraft.getItemRenderer().renderAndDecorateItem(recipes.get(idx).getRecipe().getResultItem(), x, y);
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
                //noinspection ConstantConditions
                minecraft.gameMode.handleInventoryButtonClick(menu.containerId, idx);
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
            firstIndex = (int) (scrollOffset * getHiddenRows() + .5D) * RECIPE_COLS;

            return true;
        }

        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta)
    {
        if (isScrollBarActive())
        {
            int hiddenRows = getHiddenRows();
            float offset = (float) delta / (float) hiddenRows;
            scrollOffset = Mth.clamp(scrollOffset - offset, 0F, 1F);
            firstIndex = (int) ((double) (scrollOffset * (float) hiddenRows) + .5D) * RECIPE_COLS;
        }

        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (JeiCompat.isShowRecipePressed(InputConstants.getKey(keyCode, scanCode)))
        {
            Window window = Objects.requireNonNull(minecraft).getWindow();
            MouseHandler mouseHandler = minecraft.mouseHandler;
            double mouseX = mouseHandler.xpos() * (double)window.getGuiScaledWidth() / (double)window.getScreenWidth();
            double mouseY = mouseHandler.ypos() * (double)window.getGuiScaledHeight() / (double)window.getScreenHeight();

            double x = leftPos + RECIPES_X;
            double y = topPos + RECIPES_Y;

            if (mouseX >= x && mouseX <= x + (RECIPE_WIDTH * RECIPE_COLS) && mouseY >= y && mouseY <= y + (RECIPE_HEIGHT * RECIPE_ROWS))
            {
                int col = (int) ((mouseX - x) / RECIPE_WIDTH);
                int row = (int) ((mouseY - y) / RECIPE_HEIGHT);
                int idx = (row * RECIPE_COLS) + col + firstIndex;

                List<FramingSawRecipe> recipes = cache.getRecipes();
                if (idx > 0 && idx < recipes.size())
                {
                    if (JeiCompat.handleShowRecipeRequest(cache.getRecipes().get(idx).getResultItem()))
                    {
                        return true;
                    }
                }
            }
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private boolean isScrollBarActive()
    {
        return menu.getRecipes().size() > RECIPE_COUNT;
    }

    private int getHiddenRows()
    {
        return (menu.getRecipes().size() + RECIPE_COLS - 1) / RECIPE_COLS - RECIPE_ROWS;
    }
}
