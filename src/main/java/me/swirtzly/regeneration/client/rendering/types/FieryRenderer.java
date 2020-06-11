package me.swirtzly.regeneration.client.rendering.types;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import me.swirtzly.regeneration.common.capability.IRegen;
import me.swirtzly.regeneration.common.capability.RegenCap;
import me.swirtzly.regeneration.common.types.FieryType;
import me.swirtzly.regeneration.common.types.RegenTypes;
import me.swirtzly.regeneration.util.client.RenderUtil;
import me.swirtzly.regeneration.util.common.PlayerUtil;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.ModList;

public class FieryRenderer extends ATypeRenderer<FieryType> {
	
	public static final FieryRenderer INSTANCE = new FieryRenderer();


	public static void renderOverlay(LivingEntity player, LivingRenderer renderer, MatrixStack matrixStack, IVertexBuilder buffer, int packedLightIn, int packedOverlayIn, float partialTicks) {
		RegenCap.get(player).ifPresent((data) -> {
			Vec3d color = data.getPrimaryColor();
			float opacity = MathHelper.clamp(MathHelper.sin((player.ticksExisted + partialTicks) / 10F) * 0.1F + 0.1F, 0.11F, 1F);
			renderer.getEntityModel().render(matrixStack, buffer, packedLightIn, packedOverlayIn, (float) color.x, (float) color.y, (float) color.z, opacity);
		});
	}

	public static void renderCone(MatrixStack matrixStack, LivingEntity entityPlayer, float scale, float scale2, Vec3d color) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexBuffer = tessellator.getBuffer();

        for (int i = 0; i < 8; i++) {
			matrixStack.push();
			matrixStack.rotate(Vector3f.YP.rotationDegrees(entityPlayer.ticksExisted * 4 + i * 45));
			matrixStack.scale(1.0f, 1.0f, 0.65f);
			vertexBuffer.begin(6, DefaultVertexFormats.POSITION_COLOR);
			vertexBuffer.pos(0.0D, 0.0D, 0.0D).color((float) color.x, (float) color.y, (float) color.z, 55).endVertex();
			vertexBuffer.pos(-0.266D * scale, scale, -0.5F * scale).color((float) color.x, (float) color.y, (float) color.z, 55).endVertex();
			vertexBuffer.pos(0.266D * scale, scale, -0.5F * scale).color((float) color.x, (float) color.y, (float) color.z, 55).endVertex();
			vertexBuffer.pos(0.0D, scale2, 1.0F * scale).color((float) color.x, (float) color.y, (float) color.z, 55).endVertex();
			vertexBuffer.pos(-0.266D * scale, scale, -0.5F * scale).color((float) color.x, (float) color.y, (float) color.z, 55).endVertex();
			tessellator.draw();
			matrixStack.pop();
		}
	}

    public static void renderConeAtArms(MatrixStack matrixStack, LivingEntity player, HandSide side) {
		RegenCap.get(player).ifPresent((data) -> {
            double x = data.getType().create().getAnimationProgress(data);
            double p = 109.89010989010987; // see the wiki for the explanation of these "magic" numbers
            double r = 0.09890109890109888;
            double f = p * Math.pow(x, 2) - r;
            float cf = MathHelper.clamp((float) f, 0F, 1F);
            float primaryScale = data.isSyncingToJar() ? 100 : cf * 4F;
            float secondaryScale = data.isSyncingToJar() ? 100 : cf * 6.4F;

            CompoundNBT style = data.getStyle();
            Vec3d primaryColor = new Vec3d(style.getFloat("PrimaryRed"), style.getFloat("PrimaryGreen"), style.getFloat("PrimaryBlue"));
            Vec3d secondaryColor = new Vec3d(style.getFloat("SecondaryRed"), style.getFloat("SecondaryGreen"), style.getFloat("SecondaryBlue"));

            if (data.isSyncingToJar()) {
                matrixStack.rotate(Vector3f.XN.rotationDegrees(20));
            }


            double animationProgress = data.getAnimationTicks();
            float armRotY = (float) animationProgress * 1.5F;
            float armRotZ = (float) animationProgress * 1.5F;

            if (armRotY > 90) {
                armRotY = 90;
            }

            if (armRotZ > 95) {
                armRotZ = 95;
            }
            if (ModList.get().isLoaded("quark")) {
                matrixStack.translate(0.0D, 0.1D, 0.0D);
                Quaternion quaternion;
                if (side == HandSide.LEFT) {
                	quaternion = Vector3f.ZP.rotationDegrees(armRotZ);
                	quaternion.multiply(Vector3f.YP.rotationDegrees(armRotY));
				} else {
					quaternion = Vector3f.ZN.rotationDegrees(armRotZ);
					quaternion.multiply(Vector3f.YN.rotationDegrees(armRotY));
				}
                matrixStack.rotate(quaternion);
            }

            renderCone(matrixStack, player, primaryScale, primaryScale, primaryColor);
            renderCone(matrixStack, player, secondaryScale, secondaryScale * 1.5f, secondaryColor);

        });
    }
	
	@Override
    public void renderRegeneratingPlayerPre(FieryType type, RenderPlayerEvent.Pre ev, IRegen cap) {
    }
	
	@Override
	protected void renderRegeneratingPlayerPost(FieryType type, RenderPlayerEvent.Post event, IRegen capability) {

    }

	@Override
	protected void renderRegenerationLayer(FieryType type, LivingRenderer renderer, IRegen capability, LivingEntity playerEntity, MatrixStack matrixStack, IVertexBuilder buffer, float partialTicks, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		CompoundNBT style = capability.getStyle();
		Vec3d primaryColor = new Vec3d(style.getFloat("PrimaryRed"), style.getFloat("PrimaryGreen"), style.getFloat("PrimaryBlue"));
		Vec3d secondaryColor = new Vec3d(style.getFloat("SecondaryRed"), style.getFloat("SecondaryGreen"), style.getFloat("SecondaryBlue"));

		double x = type.getAnimationProgress(capability);
		double p = 109.89010989010987; // see the wiki for the explanation of these "magic" numbers
		double r = 0.09890109890109888;
		double f = p * Math.pow(x, 2) - r;

		float cf = MathHelper.clamp((float) f, 0F, 1F);
		float primaryScale = cf * 4F;
		float secondaryScale = cf * 6.4F;

		// Render head cone
		matrixStack.push();

		matrixStack.translate(0.0f, 0.09f, 0.0f);
		matrixStack.rotate(Vector3f.XP.rotationDegrees(180));

		renderCone(matrixStack, playerEntity, primaryScale / 1.6F, primaryScale * .75F, primaryColor);
		renderCone(matrixStack, playerEntity, secondaryScale / 1.6F, secondaryScale / 1.5F, secondaryColor);
		matrixStack.pop();

		if (!capability.isSyncingToJar()) {
			// Render glowing overlay
			renderOverlay(playerEntity, renderer, matrixStack, buffer, packedLight, packedOverlay, partialTicks);
		}

		if (ModList.get().isLoaded("quark")) {
			renderConeAtArms(matrixStack, playerEntity, HandSide.LEFT);
			renderConeAtArms(matrixStack, playerEntity, HandSide.RIGHT);
		}
	}

	@Override
	public void renderHand(LivingEntity player, HandSide handSide, LivingRenderer render, MatrixStack matrixStack) {
		if (!ModList.get().isLoaded("quark")) {
			renderConeAtArms(matrixStack, player, handSide);
		}
	}


    @Override
    public void preRenderCallBack(LivingRenderer renderer, LivingEntity entity) {

    }
	
	@Override
	public void preAnimation(BipedModel model, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

    }

	@Override
	public void postAnimation(BipedModel model, LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		RegenCap.get(entity).ifPresent((data) -> {
			if (data.getState() == PlayerUtil.RegenState.REGENERATING && data.getType() == RegenTypes.FIERY) {

				int animationProgress = data.getAnimationTicks();
				double armShake = entity.getRNG().nextDouble();
				float armRotY = animationProgress * 1.5F;
				float armRotZ = animationProgress * 1.5F;
				float headRot = animationProgress * 1.5F;

				if (armRotY > 90) {
					armRotY = 90;
				}

				if (armRotZ > 95) {
					armRotZ = 95;
				}

				if (headRot > 45) {
					headRot = 45;
				}

				// ARMS
				model.bipedLeftArm.rotateAngleY = 0;
				model.bipedRightArm.rotateAngleY = 0;

				model.bipedLeftArm.rotateAngleX = 0;
				model.bipedRightArm.rotateAngleX = 0;

				model.bipedLeftArm.rotateAngleZ = (float) -Math.toRadians(armRotZ + armShake);
				model.bipedRightArm.rotateAngleZ = (float) Math.toRadians(armRotZ + armShake);
				model.bipedLeftArm.rotateAngleY = (float) -Math.toRadians(armRotY);
				model.bipedRightArm.rotateAngleY = (float) Math.toRadians(armRotY);

				// BODY
				model.bipedBody.rotateAngleX = 0;
				model.bipedBody.rotateAngleY = 0;
				model.bipedBody.rotateAngleZ = 0;

				// LEGS
				model.bipedLeftLeg.rotateAngleY = 0;
				model.bipedRightLeg.rotateAngleY = 0;

				model.bipedLeftLeg.rotateAngleX = 0;
				model.bipedRightLeg.rotateAngleX = 0;

				model.bipedLeftLeg.rotateAngleZ = (float) -Math.toRadians(5);
				model.bipedRightLeg.rotateAngleZ = (float) Math.toRadians(5);


				model.bipedHead.rotateAngleX = (float) Math.toRadians(-headRot);
				model.bipedHead.rotateAngleY = (float) Math.toRadians(0);
				model.bipedHead.rotateAngleZ = (float) Math.toRadians(0);

			}
		});
	}

    @Override
	public boolean useVanilla() {
		return true;
	}
}
