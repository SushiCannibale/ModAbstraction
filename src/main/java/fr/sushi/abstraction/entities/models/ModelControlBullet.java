package fr.sushi.abstraction.entities.models;

import fr.sushi.abstraction.ModAbstraction;
import fr.sushi.abstraction.entities.ControlBullet;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class ModelControlBullet extends HierarchicalModel<ControlBullet> {

    public static final ModelLayerLocation BULLET = new ModelLayerLocation(new ResourceLocation(ModAbstraction.MODID, "control_bullet"), "main");
    private final ModelPart root;
    private final ModelPart main;

    public ModelControlBullet(ModelPart pRoot) {
        this.root = pRoot;
        this.main = pRoot.getChild("main");
    }
    //REMOVE ME PLS
    /* From <ShulkerBulletModel> */
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -1.0F, 8.0F, 8.0F, 2.0F).texOffs(0, 10).addBox(-1.0F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F).texOffs(20, 0).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public ModelPart root() {
        return this.root;
    }

    /**
     * Sets this entity's model rotation angles
     */
    public void setupAnim(ControlBullet pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.main.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.main.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }
}
