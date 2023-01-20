package fr.sushi.abstraction.entities;

import net.minecraft.core.NonNullList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * L'idée est de faire une statue qui puisse être animée à l'aide d'un bâton (le bâton anima de zelda TP)
 * Lorsque la statue est controllée, elle suit le mouvment du joueur et ses attaques
 * Sinon, elle reste immobile
 */
public class GuardianStatue extends LivingEntity implements IControllable {

    /* L'arme de la statue (à voir si on peut l'échanger avec une autre) */
    private final NonNullList<ItemStack> handheld = NonNullList.withSize(1, ItemStack.EMPTY);
    private Player controller;
    public GuardianStatue(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.controller = null;
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return handheld;
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot pSlot) {
        return pSlot.getType() == EquipmentSlot.Type.HAND && pSlot.getIndex() == 0 ? handheld.get(0) : ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) {
        if (pSlot.getType() == EquipmentSlot.Type.HAND && pSlot.getIndex() == 0)
            this.onEquipItem(pSlot, this.handheld.set(pSlot.getIndex(), pStack), pStack);

    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Nullable
    @Override
    public Player getController() {
        return this.controller;
    }

    @Override
    public void setController(Player player) {
        this.controller = player;
    }

    @Override
    public void move(Vec3 playerDeltaM) {
//        System.out.println(playerDeltaM);
    }

    @Override
    public void attack() {
        System.out.println("Attack >:O");
    }

    public static AttributeSupplier setAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 80.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.5D)
                .build();
    }

    @Override
    public InteractionResult interactAt(Player pPlayer, Vec3 pVec, InteractionHand pHand) {

        return super.interactAt(pPlayer, pVec, pHand);
    }

    @Override
    public void push(Entity pEntity) { }
}
