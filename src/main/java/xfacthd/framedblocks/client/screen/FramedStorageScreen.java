package xfacthd.framedblocks.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.common.menu.FramedStorageMenu;

public class FramedStorageScreen extends AbstractContainerScreen<FramedStorageMenu>
{
    private static final ResourceLocation CHEST_GUI_TEXTURE = Utils.rl("minecraft", "textures/gui/container/generic_54.png");

    public FramedStorageScreen(FramedStorageMenu container, Inventory inv, Component title)
    {
        super(container, inv, title);

        this.imageHeight = 168;
        this.inventoryLabelY = getYSize() - 94;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks)
    {
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int x, int y)
    {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int left = (this.width - this.imageWidth) / 2;
        int top = (this.height - this.imageHeight) / 2;

        graphics.blit(CHEST_GUI_TEXTURE, left, top, 0, 0, imageWidth, 71);
        graphics.blit(CHEST_GUI_TEXTURE, left, top + 71, 0, 126, imageWidth, 96);
    }
}