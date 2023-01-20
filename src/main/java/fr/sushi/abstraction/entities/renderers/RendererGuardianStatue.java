package fr.sushi.abstraction.entities.renderers;

import fr.sushi.abstraction.ModAbstraction;
import fr.sushi.abstraction.entities.GuardianStatue;
import fr.sushi.abstraction.entities.models.ModelGuardianStatue;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class RendererGuardianStatue extends LivingEntityRenderer<GuardianStatue, ModelGuardianStatue> {

    public RendererGuardianStatue(EntityRendererProvider.Context pContext) {
        super(pContext, new ModelGuardianStatue(pContext.bakeLayer(ModelGuardianStatue.LAYER_LOCATION)), 0.7f);
    }

    @Override
    public ResourceLocation getTextureLocation(GuardianStatue pEntity) {
        return new ResourceLocation(ModAbstraction.MODID, "textures/entity/guardian_statue/guardian_statue.png");
    }
}
