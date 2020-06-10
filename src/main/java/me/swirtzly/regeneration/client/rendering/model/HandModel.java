package me.swirtzly.regeneration.client.rendering.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import me.swirtzly.regeneration.common.capability.IRegen;
import me.swirtzly.regeneration.common.capability.RegenCap;
import me.swirtzly.regeneration.common.entity.TimelordEntity;
import me.swirtzly.regeneration.common.item.GunItem;
import me.swirtzly.regeneration.util.client.RenderUtil;
import me.swirtzly.regeneration.util.common.PlayerUtil;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.HandSide;

/**
 * Created by Swirtzly on 22/08/2019 @ 16:46
 */
public class HandModel extends EntityModel<Entity> {
    private final ModelRenderer arm;

    public HandModel(boolean isAlex) {
        textureWidth = 64;
        textureHeight = 64;

        if (isAlex) {
            arm = new ModelRenderer(this);
            arm.setRotationPoint(-2.0F, 12.0F, 0.0F);
            setRotationAngle(arm, 0.0F, 0.0F, 3.1416F);
            arm.setTextureOffset(40, 32).addBox(-3.0f, 11.5f, 0.0f, 3.0f, 12.0f, 4.0f, 0.0f, false);
            arm.setTextureOffset(40, 32).addBox(-3.0f, -11.5f, 0.0f, 3.0f, 12.0f, 4.0f, 0.375f, false);
        } else {
            arm = new ModelRenderer(this);
            arm.setRotationPoint(-2.0F, 12.0F, 0.0F);
            setRotationAngle(arm, 0.0F, 0.0F, 3.1416F);
            arm.setTextureOffset(40, 16).addBox(-3.0f, -11.5f, -2.0f, 3.0f, 12.0f, 4.0f, 0.0f, false);
            arm.setTextureOffset(40, 32).addBox(-3.0f, -11.5f, -2.0f, 3.0f, 12.0f, 4.0f, 0.375f, false);
        }
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        arm.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    }

    @Override
    public void setRotationAngles(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
