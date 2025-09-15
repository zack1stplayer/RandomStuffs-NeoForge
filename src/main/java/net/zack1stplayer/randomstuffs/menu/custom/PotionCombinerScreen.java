package net.zack1stplayer.randomstuffs.menu.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.createmod.catnip.gui.UIRenderHelper;
import net.createmod.catnip.platform.NeoForgeCatnipServices;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.fluids.FluidStack;
import net.zack1stplayer.randomstuffs.RandomStuffs;

public class PotionCombinerScreen extends AbstractContainerScreen<PotionCombinerMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID, "textures/gui/potion_combiner/potion_combiner_gui.png");
    private static final ResourceLocation ARROW_PROGRESS_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(RandomStuffs.MOD_ID, "textures/gui/arrow_progress.png");

    private static final int OUTPUT_TANK_INDEX = 0;
    private static final int INLEFT_TANK_INDEX = 1;
    private static final int INRIGHT_TANK_INDEX = 2;


    public PotionCombinerScreen(PotionCombinerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
        renderFluidContent(guiGraphics, x, y);
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

    private void renderFluidContent(GuiGraphics guiGraphics, int x, int y) {
        PoseStack matrixStack = guiGraphics.pose();
        matrixStack.pushPose();
        matrixStack.translate(x + 9, y + 65, 100);
        UIRenderHelper.flipForGuiRender(matrixStack);
        matrixStack.scale(16, 16, 16);
        FluidStack fluidStack = menu.getTankFluid(INLEFT_TANK_INDEX);
        NeoForgeCatnipServices.FLUID_RENDERER.renderFluidBox(fluidStack,
                (0f / 16f), (0f / 16f), (0f),
                (16f / 16f), (menu.getScaledTankLevel(INLEFT_TANK_INDEX) / 16f), (0f),
                guiGraphics.bufferSource(), matrixStack, LightTexture.FULL_BRIGHT,
                false, false
        );

        fluidStack = menu.getTankFluid(INRIGHT_TANK_INDEX);
        NeoForgeCatnipServices.FLUID_RENDERER.renderFluidBox(fluidStack,
                (18f / 16f), (0f / 16f), (0f),
                (34f / 16f), (menu.getScaledTankLevel(INRIGHT_TANK_INDEX) / 16f), (0f),
                guiGraphics.bufferSource(), matrixStack, LightTexture.FULL_BRIGHT,
                false, false
        );

        fluidStack = menu.getTankFluid(OUTPUT_TANK_INDEX);
        NeoForgeCatnipServices.FLUID_RENDERER.renderFluidBox(fluidStack,
                (36f / 16f), (0f / 16f), (0f),
                (52f / 16f), (menu.getScaledTankLevel(OUTPUT_TANK_INDEX) / 16f), (0f),
                guiGraphics.bufferSource(), matrixStack, LightTexture.FULL_BRIGHT,
                false, false
        );

        matrixStack.popPose();

//        matrixStack.pushPose();
//        matrixStack.translate(x + 27, y + 65, 100);
//        UIRenderHelper.flipForGuiRender(matrixStack);
//        fluidStack = menu.getTankFluid(INRIGHT_TANK_INDEX);
//        NeoForgeCatnipServices.FLUID_RENDERER.renderFluidBox(fluidStack,
//                (0f / 16f), (0f / 16f), (0f),
//                (1f), (menu.getScaledTankLevel(INRIGHT_TANK_INDEX) / 16f), (0f),
//                guiGraphics.bufferSource(), matrixStack, LightTexture.FULL_BRIGHT,
//                false, false
//        );
//        matrixStack.popPose();

//        matrixStack.pushPose();
//        matrixStack.translate(x + 45, y + 65, 100);
//        UIRenderHelper.flipForGuiRender(matrixStack);
//        fluidStack = menu.getTankFluid(OUTPUT_TANK_INDEX);
//        NeoForgeCatnipServices.FLUID_RENDERER.renderFluidBox(fluidStack,
//                (0f / 16f), (0f / 16f), (0f),
//                (1f), (menu.getScaledTankLevel(OUTPUT_TANK_INDEX) / 16f), (0f),
//                guiGraphics.bufferSource(), matrixStack, LightTexture.FULL_BRIGHT,
//                false, false
//        );
//        matrixStack.popPose();
    }
}
