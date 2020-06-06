package me.swirtzly.regeneration.client.rendering.tiles;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import me.swirtzly.regeneration.client.rendering.model.HandModel;
import me.swirtzly.regeneration.client.skinhandling.SkinInfo;
import me.swirtzly.regeneration.client.skinhandling.SkinManipulation;
import me.swirtzly.regeneration.common.item.HandItem;
import me.swirtzly.regeneration.common.tiles.HandInJarTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

/**
 * Created by Swirtzly on 22/08/2019 @ 16:30
 */
public class HandTileRenderer extends TileEntityRenderer<HandInJarTile> {

    public static HashMap<HandInJarTile, ResourceLocation> TEXTURES = new HashMap<>();
    public static EntityModel STEVE_ARM = new HandModel(false);
    public static EntityModel ALEX_ARM = new HandModel(true);

    public HandTileRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(HandInJarTile tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        if (tileEntity.hasHand()) {
            matrixStack.rotate(Vector3f.XP.rotationDegrees(180));
            matrixStack.translate(0.5, -1.5, -0.5);
            SkinInfo.SkinType skinType = HandItem.getSkinType(tileEntity.getHand());
            IVertexBuilder vertexBuilder = buffer.getBuffer(RenderType.getEntitySolid(getOrCreateTexture(tileEntity)));
            if (skinType == SkinInfo.SkinType.ALEX) {
                ALEX_ARM.render(matrixStack, vertexBuilder, combinedLight, combinedOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
            } else {
                STEVE_ARM.render(matrixStack, vertexBuilder, combinedLight, combinedOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
            }
        } else {
            TEXTURES.remove(tileEntity);
        }
    }

    public ResourceLocation getOrCreateTexture(HandInJarTile handInJarTile) {

        if (HandItem.getTextureString(handInJarTile.getHand()).equalsIgnoreCase("NONE")) {
            return new ResourceLocation(HandItem.getSkinType(handInJarTile.getHand()).getTexturePath());
        }

        if (!TEXTURES.containsKey(handInJarTile)) {
            NativeImage image = SkinManipulation.decodeToImage(HandItem.getTextureString(handInJarTile.getHand()));
            ResourceLocation res = Minecraft.getInstance().getTextureManager().getDynamicTextureLocation("hand_", new DynamicTexture(image));
            TEXTURES.put(handInJarTile, res);
            return res;
        }
        return TEXTURES.get(handInJarTile);
    }

}
