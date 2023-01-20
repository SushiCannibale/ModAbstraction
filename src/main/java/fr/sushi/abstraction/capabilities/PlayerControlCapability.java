package fr.sushi.abstraction.capabilities;

import fr.sushi.abstraction.ModAbstraction;
import fr.sushi.abstraction.entities.IControllable;
import fr.sushi.abstraction.util.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.util.UUID;

public class PlayerControlCapability {

    private static final String ENTITY_TAG = ModAbstraction.MODID + "." + "EntityControl";
    private static final String SHOOT_TAG = ModAbstraction.MODID + "." + "CanShoot";

    @Nullable
    private UUID controlled;

    private boolean canShoot;

    public PlayerControlCapability() {
        this.controlled = null;
        this.canShoot = true;
    }

    public void setControlled(UUID uuid) {
        this.controlled = uuid;
    }

    public boolean isControllingEntity() {
        return this.controlled != null;
    }

    @Nullable
    public UUID getControlled() {
        return this.controlled;
    }

    public void setCanShoot(boolean b) {
        this.canShoot = b;
    }

    public boolean getCanShoot() {
        return this.canShoot && this.controlled == null;
    }
}
