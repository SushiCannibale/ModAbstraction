package fr.sushi.abstraction.entities;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public interface IControllable {

    default boolean isFree() {
        return getController() == null;
    }
    @Nullable
    Player getController();

    void setController(Player player);

    void move(Vec3 playerDeltaM);

    void attack();
}
