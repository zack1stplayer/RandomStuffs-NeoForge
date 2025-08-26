package net.zack1stplayer.randomstuffs.menu.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.zack1stplayer.randomstuffs.RandomStuffs;

public class PotionDispenserScreen extends AbstractContainerScreen<PotionDispenserMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID, "textures/gui/potion_dispenser/potion_dispenser_gui.png");
    private static final ResourceLocation ARROW_PROGRESS_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID, "textures/gui/arrow_progress.png");

    public PotionDispenserScreen(PotionDispenserMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()) {
            guiGraphics.blit(ARROW_PROGRESS_TEXTURE, x + 73, y + 35, 0, 0,
                    menu.getScaledArrowProgress(), 16, 24, 16);
        }
    }
}
