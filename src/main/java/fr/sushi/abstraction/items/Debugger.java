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

public class Debugger extends Item {
    public Debugger(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
<<<<<<< HEAD
        if (!level.isClientSide) {

            player.getCapability(PlayerControlCapProvider.CONTROL_CAP).ifPresent(capability -> {
                if (capability.getCanShoot()) {
                    this.shootBullet(level, player, hand);
                    capability.setCanShoot(false);
                }
            });
=======
        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide) {
            return InteractionResultHolder.sidedSuccess(stack, true);
>>>>>>> 4cfbf5f7d0785f884c57e3a1b235c04e68d1fcd2
        }

        player.getCapability(PlayerControlCapProvider.CONTROL_CAP).ifPresent(capability -> {

            if (capability.isControllingEntity()) {
                this.retreiveBullet();
                capability.setCanShoot(true);
                capability.setControlled(null);
                // Playsound retreive bullet
            }
            else if (capability.getCanShoot()) {
                this.shootBullet(level, player, hand);
                // Playsound shoot bullet
                capability.setCanShoot(false);
            }
        });

        return InteractionResultHolder.sidedSuccess(stack, false);
    }

    private void shootBullet(Level level, Player player, InteractionHand hand) {
        /* Code de la snowballItem */
        ControlBullet bullet = new ControlBullet(level, player, hand);
        bullet.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 0.8f, 0.0f);
        level.addFreshEntity(bullet);

        player.getItemInHand(hand).hurtAndBreak(1, player, (playerEvent) -> playerEvent.broadcastBreakEvent(hand));
    }

    private void retreiveBullet() {

    }
}
