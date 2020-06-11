package me.swirtzly.regeneration.client.rendering.tiles;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import me.swirtzly.regeneration.common.tiles.ArchTile;
import me.swirtzly.regeneration.handlers.RegenObjects;
import me.swirtzly.regeneration.util.client.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

/**
 * Created by Swirtzly
 * on 22/04/2020 @ 17:46
 */
public class ArchRender extends TileEntityRenderer<ArchTile> {

    public ArchRender(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(ArchTile tileEntityIn, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        matrixStack.translate(0.5, 0.5, 0.5);
        RenderUtil.drawGlowingLine(new Vec3d(0.0D, 10D, 0D), new Vec3d(0.5, 0.5, 0.5), 0.5F, new Vec3d(1, 1, 1), 1);
        Minecraft.getInstance().getItemRenderer().renderItem(new ItemStack(RegenObjects.Blocks.ARCH.get()), ItemCameraTransforms.TransformType.GUI, combinedLightIn, combinedOverlayIn, matrixStack, bufferIn);
    }
}
