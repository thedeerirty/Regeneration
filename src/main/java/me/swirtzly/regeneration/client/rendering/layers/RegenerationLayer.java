package me.swirtzly.regeneration.client.rendering.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import me.swirtzly.regeneration.common.capability.IRegen;
import me.swirtzly.regeneration.common.capability.RegenCap;
import me.swirtzly.regeneration.common.types.RegenType;
import me.swirtzly.regeneration.util.client.RenderUtil;
import me.swirtzly.regeneration.util.common.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

import static me.swirtzly.regeneration.client.rendering.types.FieryRenderer.renderOverlay;
import static me.swirtzly.regeneration.util.client.RenderUtil.drawGlowingLine;

/**
 * Created by Sub on 16/09/2018.
 */
public class RegenerationLayer extends LayerRenderer {

    private final LivingRenderer livingEntityRenderer;

    public RegenerationLayer(LivingRenderer livingEntityRendererIn) {
        super(livingEntityRendererIn);
        this.livingEntityRenderer = livingEntityRendererIn;
    }

    public static void renderGlowingHands(MatrixStack matrixStack, LivingEntity player, IRegen handler, float scale, HandSide side) {
		Vec3d primaryColor = handler.getPrimaryColor();
		Vec3d secondaryColor = handler.getSecondaryColor();
		
		Random rand = player.world.rand;
		float factor = 0.2F;

        matrixStack.scale(scale, scale, scale);
        matrixStack.translate(0, 0.3F, 0);
        matrixStack.rotate(Vector3f.YP.rotationDegrees((player.ticksExisted + RenderUtil.renderTick) / 2F));
		for (int i = 0; i < 7; i++) {
            Quaternion quaternion = Vector3f.XP.rotationDegrees((player.ticksExisted + RenderUtil.renderTick) * i / 70F);
            quaternion.multiply(Vector3f.YP.rotationDegrees((player.ticksExisted + RenderUtil.renderTick) * i / 70F));
            matrixStack.rotate(quaternion);
			drawGlowingLine(new Vec3d((-factor / 2F) + rand.nextFloat() * factor, (-factor / 2F) + rand.nextFloat() * factor, (-factor / 2F) + rand.nextFloat() * factor), new Vec3d((-factor / 2F) + rand.nextFloat() * factor, (-factor / 2F) + rand.nextFloat() * factor, (-factor / 2F) + rand.nextFloat() * factor), 0.1F, primaryColor, 0);
			drawGlowingLine(new Vec3d((-factor / 2F) + rand.nextFloat() * factor, (-factor / 2F) + rand.nextFloat() * factor, (-factor / 2F) + rand.nextFloat() * factor), new Vec3d((-factor / 2F) + rand.nextFloat() * factor, (-factor / 2F) + rand.nextFloat() * factor, (-factor / 2F) + rand.nextFloat() * factor), 0.1F, secondaryColor, 0);
		}
	}


    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, Entity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        LivingEntity player = (LivingEntity) entity;
        RegenCap.get(player).ifPresent((data) -> {
            RegenType type = data.getType().create();
            if (data.getState() == PlayerUtil.RegenState.REGENERATING) {
                type.getRenderer().onRenderRegenerationLayer(type, livingEntityRenderer, data, player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            }

            if (data.getState() == PlayerUtil.RegenState.POST && player.hurtTime > 0) {
                renderOverlay(livingEntityRenderer, player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            }

        });

    }

	
}
