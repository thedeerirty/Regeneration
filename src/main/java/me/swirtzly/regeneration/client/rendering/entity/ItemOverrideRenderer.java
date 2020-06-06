package me.swirtzly.regeneration.client.rendering.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import me.swirtzly.regeneration.RegenConfig;
import me.swirtzly.regeneration.common.entity.OverrideEntity;
import me.swirtzly.regeneration.handlers.RegenObjects;
import me.swirtzly.regeneration.util.client.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.util.Random;

public class ItemOverrideRenderer extends EntityRenderer<OverrideEntity> {
	
	private Vec3d primaryColor = new Vec3d(0.93F, 0.61F, 0.0F);
	private Vec3d secondaryColor = new Vec3d(1F, 0.5F, 0.18F);
	
	public ItemOverrideRenderer(EntityRendererManager rm) {
		super(rm);
	}
	
	@Nullable
	@Override
	public ResourceLocation getEntityTexture(OverrideEntity entity) {
		return null;
	}


    static void makeGlowingBall(MatrixStack matrixStack, Minecraft mc, float f, Random rand, Vec3d primaryColor, Vec3d secondaryColor) {
        matrixStack.rotate(Vector3f.YP.rotationDegrees((mc.player.ticksExisted + RenderUtil.renderTick) / 2F));

        for (int i = 0; i < 3; i++) {
        	Quaternion quaternion = Vector3f.XP.rotationDegrees((mc.player.ticksExisted + RenderUtil.renderTick) * i / 70F);
        	quaternion.multiply(Vector3f.YP.rotationDegrees((mc.player.ticksExisted + RenderUtil.renderTick) * i / 70F));
            matrixStack.rotate(quaternion);
            RenderUtil.drawGlowingLine(new Vec3d((-f / 2F) + rand.nextFloat() * f, (-f / 2F) + rand.nextFloat() * f, (-f / 2F) + rand.nextFloat() * f), new Vec3d((-f / 2F) + rand.nextFloat() * f, (-f / 2F) + rand.nextFloat() * f, (-f / 2F) + rand.nextFloat() * f), 0.1F, primaryColor, 0);
            RenderUtil.drawGlowingLine(new Vec3d((-f / 2F) + rand.nextFloat() * f, (-f / 2F) + rand.nextFloat() * f, (-f / 2F) + rand.nextFloat() * f), new Vec3d((-f / 2F) + rand.nextFloat() * f, (-f / 2F) + rand.nextFloat() * f, (-f / 2F) + rand.nextFloat() * f), 0.1F, secondaryColor, 0);
        }
    }

	@Override
	public void render(OverrideEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLightIn) {
		if (entity.getItem().isEmpty()) return;
		Minecraft mc = Minecraft.getInstance();
		float f = 0.2f;
		Random rand = entity.world.rand;

		matrixStack.push();
		if (entity.getItem().getItem() == RegenObjects.Items.FOB_WATCH.get() && entity.getItem().getDamage() != RegenConfig.COMMON.regenCapacity.get()) {
			for (int j = 0; j < 2; j++) {
				matrixStack.translate(0, 0.20, 0);
				matrixStack.scale(0.7F, 0.7F, 0.7F);
				makeGlowingBall(matrixStack, mc, f, rand, primaryColor, secondaryColor);
			}
		}

		matrixStack.translate(0, 0.17F, 0);
		matrixStack.rotate(new Quaternion(-entity.rotationYaw, 0, 1, 0));
		Minecraft.getInstance().getItemRenderer().renderItem(entity.getItem(), ItemCameraTransforms.TransformType.GROUND, packedLightIn,0, matrixStack, buffer);
		matrixStack.pop();
	}


}
