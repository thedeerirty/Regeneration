package me.swirtzly.regeneration.client.rendering.types;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import me.swirtzly.animateme.AnimationManager;
import me.swirtzly.regeneration.common.capability.IRegen;
import me.swirtzly.regeneration.common.types.RegenType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.HandSide;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderPlayerEvent.Pre;

public abstract class ATypeRenderer<T> implements AnimationManager.IAnimate {

    protected abstract void renderRegeneratingPlayerPre(T type, Pre event, IRegen capability);

    protected abstract void renderRegeneratingPlayerPost(T type, RenderPlayerEvent.Post event, IRegen capability);

	protected abstract void renderRegenerationLayer(T type, LivingRenderer renderer, IRegen cap, LivingEntity playerEntity, MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float r, float g, float b, float a);

	// Generic casting convenience methods:
	
	@SuppressWarnings("unchecked")
    public final void onRenderRegeneratingPlayerPre(RegenType<?> type, Pre event, IRegen capability) {
		try {
			renderRegeneratingPlayerPre((T) type, event, capability);
		} catch (ClassCastException e) {
			throw new IllegalStateException("RegenType <-> RegenRenderType mismatch", e);
		}
	}
	
	@SuppressWarnings("unchecked")
    public final void onRenderRegeneratingPlayerPost(RegenType<?> type, RenderPlayerEvent.Post event, IRegen capability) {
		try {
			renderRegeneratingPlayerPost((T) type, event, capability);
		} catch (ClassCastException e) {
			throw new IllegalStateException("RegenType <-> RegenRenderType mismatch", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public final void onRenderRegenerationLayer(RegenType<?> type, LivingRenderer renderLivingBase, IRegen capability, LivingEntity entityPlayer, MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		try {
			renderRegenerationLayer((T) type, renderLivingBase, capability, entityPlayer, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		} catch (ClassCastException e) {
			throw new IllegalStateException("RegenType <-> RegenRenderType mismatch", e);
		}
	}

	public abstract void renderHand(LivingEntity player, HandSide handSide, LivingRenderer render);
	
}
