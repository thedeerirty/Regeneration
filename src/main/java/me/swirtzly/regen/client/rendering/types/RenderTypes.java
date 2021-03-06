package me.swirtzly.regen.client.rendering.types;

import me.swirtzly.regen.util.RConstants;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderTypes extends RenderType {

    public static final RenderType REGEN_FLAMES = makeType(RConstants.MODID + ":laser", DefaultVertexFormats.POSITION_COLOR_LIGHTMAP, GL11.GL_QUADS, 256, false, false, RenderType.State.getBuilder()
            .texture(RenderState.NO_TEXTURE)
            .cull(RenderState.CULL_ENABLED)
            .alpha(DEFAULT_ALPHA)
            .shadeModel(RenderState.SHADE_ENABLED)
            .lightmap(RenderState.LIGHTMAP_ENABLED)
            .transparency(RenderState.ADDITIVE_TRANSPARENCY)
            .build(true));

    public RenderTypes(String name, VertexFormat vertexFormat, int drawMode, int bufferSize, boolean useDelegate, boolean needsSorting, Runnable setupTask, Runnable clearTask) {
        super(name, vertexFormat, drawMode, bufferSize, useDelegate, needsSorting, setupTask, clearTask);
    }

    public static RenderType getGlowing(ResourceLocation locationIn) {
        RenderState.TextureState textureState = new RenderState.TextureState(locationIn, false, false);
        return makeType(RConstants.MODID + ":glowing", DefaultVertexFormats.ENTITY, 7, 256, false, true, RenderType.State.getBuilder().transparency(TRANSLUCENT_TRANSPARENCY).alpha(DEFAULT_ALPHA).cull(CULL_DISABLED).overlay(OVERLAY_ENABLED).texture(textureState).fog(BLACK_FOG).build(false));
    }

    public static RenderType getGlowingTransparent(ResourceLocation locationIn) {
        RenderState.TextureState textureState = new RenderState.TextureState(locationIn, false, false);
        return makeType(RConstants.MODID + ":glowing_transparent", DefaultVertexFormats.ENTITY, 7, 256, false, true, RenderType.State.getBuilder().transparency(RenderState.TRANSLUCENT_TRANSPARENCY).alpha(RenderState.HALF_ALPHA).cull(RenderState.CULL_ENABLED).overlay(RenderState.OVERLAY_DISABLED).texture(textureState).fog(BLACK_FOG).build(true));
    }

    public static RenderType getEntityTranslucentHalfAlpha(ResourceLocation LocationIn, boolean outlineIn) {
        RenderType.State rendertype$state = RenderType.State.getBuilder().texture(new RenderState.TextureState(LocationIn, false, false)).transparency(RenderState.LIGHTNING_TRANSPARENCY).alpha(RenderState.HALF_ALPHA).cull(CULL_ENABLED).lightmap(RenderState.LIGHTMAP_ENABLED).overlay(RenderState.OVERLAY_DISABLED).build(outlineIn);
        return makeType("entity_translucent", DefaultVertexFormats.ENTITY, 7, 256, true, true, rendertype$state);
    }

}

