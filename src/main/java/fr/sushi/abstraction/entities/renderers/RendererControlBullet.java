package fr.sushi.abstraction.entities.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import fr.sushi.abstraction.ModAbstraction;
import fr.sushi.abstraction.entities.ControlBullet;
import fr.sushi.abstraction.entities.models.ModelControlBullet;
import net.minecraft.client.model.ShulkerBulletModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.ShulkerBullet;

public class RendererControlBullet extends EntityRenderer<ControlBullet> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(ModAbstraction.MODID, "textures/entity/control_bullet/control_bullet.png");
    private static final RenderType RENDER_TYPE = RenderType.entityTranslucent(TEXTURE);

    private final ModelControlBullet model;

    public RendererControlBullet(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new ModelControlBullet(pContext.bakeLayer(ModelControlBullet.BULLET));
    }

    @Override
    public ResourceLocation getTextureLocation(ControlBullet pEntity) {
        return TEXTURE;
    }

    /* Code de <ShulkerBulletRenderer> */
    @Override
    public void render(ControlBullet pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        float f = Mth.rotLerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot());
        float f1 = Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot());
        float f2 = (float)pEntity.tickCount + pPartialTicks;
        pMatrixStack.translate(0.0F, 0.15F, 0.0F);
        pMatrixStack.mulPose(Axis.YP.rotationDegrees(Mth.sin(f2 * 0.2F) * 180.0F));
        pMatrixStack.mulPose(Axis.XP.rotationDegrees(Mth.cos(f2 * 0.2F) * 180.0F));
        pMatrixStack.mulPose(Axis.ZP.rotationDegrees(Mth.sin(f2 * 0.3F) * 360.0F));
        pMatrixStack.scale(-0.5F, -0.5F, 0.5F);
        this.model.setupAnim(pEntity, 0.0F, 0.0F, 0.0F, f, 0.0f);
        VertexConsumer vertexconsumer = pBuffer.getBuffer(this.model.renderType(TEXTURE));
        this.model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pMatrixStack.scale(1.5F, 1.5F, 1.5F);
        VertexConsumer vertexconsumer1 = pBuffer.getBuffer(RENDER_TYPE);
        this.model.renderToBuffer(pMatrixStack, vertexconsumer1, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.15F);
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
