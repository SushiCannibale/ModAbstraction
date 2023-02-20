package fr.sushi.abstraction.items;

import fr.sushi.abstraction.capabilities.PlayerControlCapProvider;
import fr.sushi.abstraction.entities.ControlBullet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Debugger extends Item {
    public Debugger(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        player.getCapability(PlayerControlCapProvider.CONTROL_CAP).ifPresent(cap -> {
            if (cap.getCanShoot()) {
                this.shootBullet(level, player, hand);
                cap.setCanShoot(false);
            }
        });

        return InteractionResultHolder.sidedSuccess(stack, true);
    }

    private void shootBullet(Level level, Player player, InteractionHand hand) {
        /* Code de la snowballItem */
        ControlBullet bullet = new ControlBullet(level, player, hand);
        bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 0.8f, 0.0f);
        level.addFreshEntity(bullet);
    }
}
