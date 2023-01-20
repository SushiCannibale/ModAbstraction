package fr.sushi.abstraction.entities.renderers;

import fr.sushi.abstraction.ModAbstraction;
import fr.sushi.abstraction.entities.Argarok;
import fr.sushi.abstraction.entities.models.ModelArgarok;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/* The renderer is what will take the texture and apply it to the model */
public class RendererArgarok extends MobRenderer<Argarok, ModelArgarok>
{
    public static final ResourceLocation ARGAROK = new ResourceLocation(ModAbstraction.MODID, "textures/entity/argarok/argarok.png");

    public RendererArgarok(EntityRendererProvider.Context pContext) {
        super(pContext, new ModelArgarok(pContext.bakeLayer(ModelArgarok.LAYER_LOCATION)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(Argarok pEntity) {
        return ARGAROK;
    }
}