package fr.sushi.abstraction.items;

import fr.sushi.abstraction.capabilities.PlayerControlCapProvider;
import fr.sushi.abstraction.capabilities.PlayerControlCapability;
import fr.sushi.abstraction.entities.ControlBullet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;

/**
 * -> Sauvegarder l'entité controlée dans un NBT
 * -> Si le joueur meurt ou se déconnecte, l'entité controlée est unbound
 */

public class Debugger extends Item {
    public Debugger(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            LazyOptional<PlayerControlCapability> opt = player.getCapability(PlayerControlCapProvider.CONTROL_CAP);

            opt.ifPresent(capability -> {
                if (capability.getCanShoot()) {
                    this.shootBullet(level, player, hand);
                    capability.setCanShoot(false);
                }
            });
        }

        return InteractionResultHolder.sidedSuccess(new ItemStack(this), !level.isClientSide);
    }

    private void shootBullet(Level level, Player player, InteractionHand hand) {
        /* Code de la snowballItem */
        ControlBullet bullet = new ControlBullet(level, player, hand);
        bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 0.8f, 0.0f);
        level.addFreshEntity(bullet);

        player.getItemInHand(hand).hurtAndBreak(1, player, (playerEvent) -> playerEvent.broadcastBreakEvent(hand));
    }
}
