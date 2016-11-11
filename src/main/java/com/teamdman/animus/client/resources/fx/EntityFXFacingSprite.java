package com.teamdman.animus.client.resources.fx;

import com.teamdman.animus.client.resources.RenderingUtils;
import com.teamdman.animus.client.resources.SpriteSheetResource;
import com.teamdman.animus.common.util.data.Tuple;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class EntityFXFacingSprite extends EntityComplexFX {
	private final SpriteSheetResource spriteSheet;
    private final double x, y, z;
    private final float scale;

    public EntityFXFacingSprite(SpriteSheetResource spriteSheet, double x, double y, double z) {
        this(spriteSheet, x, y, z, 1F);
    }

    public EntityFXFacingSprite(SpriteSheetResource spriteSheet, double x, double y, double z, float scale) {
        this.spriteSheet = spriteSheet;
        this.x = x;
        this.y = y;
        this.z = z;
        this.scale = scale;
        this.maxAge = spriteSheet.getFrameCount();
    }

    protected float getULengthMultiplier() {
        return 1F;
    }

    protected float getVLengthMultiplier() {
        return 1F;
    }

    protected int getAgeBasedFrame() {
        float perc = ((float) age) / ((float) maxAge);
        return MathHelper.floor_float(spriteSheet.getFrameCount() * perc);
    }

    @Override
    public void render(float pTicks) {
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(false);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        int frame = getAgeBasedFrame();
        Tuple<Double, Double> uv = spriteSheet.getUVOffset(frame);
        spriteSheet.getResource().bind();
        RenderingUtils.renderFacingQuad(x, y, z, pTicks, scale, 0, uv.key, uv.value, spriteSheet.getULength() * getULengthMultiplier(), spriteSheet.getVLength() * getVLengthMultiplier());
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_CULL_FACE);
    }
}
