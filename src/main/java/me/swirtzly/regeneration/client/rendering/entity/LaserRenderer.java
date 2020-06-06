package me.swirtzly.regeneration.client.rendering.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import me.swirtzly.regeneration.common.entity.LaserEntity;
import me.swirtzly.regeneration.util.client.RenderUtil;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class LaserRenderer extends EntityRenderer<LaserEntity> {

    public LaserRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(LaserEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLightIn) {
        Vec3d vec1 = new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ);
        Vec3d vec2 = new Vec3d(entity.getPosX(), entity.getPosY(), entity.getPosZ());
        vec1 = vec2.subtract(vec1);
        vec2 = vec2.subtract(vec2);
        vec1 = vec1.normalize();
        double x_ = vec2.x - vec1.x;
        double y_ = vec2.y - vec1.y;
        double z_ = vec2.z - vec1.z;
        double diff = MathHelper.sqrt(x_ * x_ + z_ * z_);
        float yaw = (float) (Math.atan2(z_, x_) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) (-(Math.atan2(y_, diff) * 180.0D / 3.141592653589793D));

        matrixStack.push();
        matrixStack.rotate(new Quaternion(-yaw, 0.0F, 1.0F, 0.0F));
        matrixStack.rotate(new Quaternion(pitch, 1.0F, 0.0F, 0.0F));
        RenderUtil.drawGlowingLine(Vec3d.ZERO, new Vec3d(0.0D, 0.0D, 1.0D), entity.scale, entity.getColor(), 1.0F);
        matrixStack.pop();
    }

    @Override
    public ResourceLocation getEntityTexture(LaserEntity entity) {
        return null;
    }
}
