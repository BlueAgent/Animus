package com.teamdman.animus.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityGenericDemon<T extends EntityCreature> implements LayerRenderer<T>
{
    private static final ResourceLocation RAW_TEXTURE = new ResourceLocation("animus", "textures/entities/overlay/demonoverlay.png");
    private final RenderLiving<T> renderer;
    private final ModelBase model;

    public EntityGenericDemon(RenderLiving<T> rendererIn, ModelBase model)
    {
        this.renderer = rendererIn;
        this.model = model;
    }

    @Override
    public void doRenderLayer(EntityCreature demon, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {

        boolean flag = demon.isInvisible();
        GlStateManager.depthMask(!flag);
        this.renderer.bindTexture(RAW_TEXTURE);
        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        float f = (float) demon.ticksExisted + partialTicks;
        GlStateManager.translate(f * 0.01F, f * 0.01F, 0.0F);
        GlStateManager.matrixMode(5888);
        GlStateManager.enableBlend();
        float f1 = 0.5F;
        GlStateManager.color(f1, f1, f1, 1.0F);
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        this.model.setModelAttributes(this.renderer.getMainModel());
        this.model.render(demon, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(flag);
    }

    @Override
    public boolean shouldCombineTextures()
    {
        return false;
    }


}