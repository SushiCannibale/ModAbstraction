package fr.sushi.abstraction.network;

import fr.sushi.abstraction.capabilities.PlayerControlCapProvider;
import fr.sushi.abstraction.entities.IControllable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerSyncC2SPacket {
    private static final double PLAYER_BASE_SPEED_MOD = 1.825D;
    private double dX;
    private double dZ;
    private float yaw;



    public PlayerSyncC2SPacket(Player player) {
        Vec3 dM = player.getDeltaMovement();
        this.dX = dM.x;
        this.dZ = dM.z;
        this.yaw = player.getYRot();

    }

    public PlayerSyncC2SPacket(FriendlyByteBuf buf) {
        this.dX = buf.readDouble();
        this.dZ = buf.readDouble();
        this.yaw = buf.readFloat();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(this.dX);
        buf.writeDouble(this.dZ);
        buf.writeFloat(this.yaw);
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            /* Côté server */
            ServerPlayer player = ctx.getSender();

            player.getCapability(PlayerControlCapProvider.CONTROL_CAP).ifPresent(capability -> {
                if (!capability.isControllingEntity())
                    return;

                ServerLevel level = player.getLevel();
                Entity entity = level.getEntity(capability.getControlled());


                Vec3 dM = new Vec3(this.dX * PLAYER_BASE_SPEED_MOD, entity.getDeltaMovement().y, this.dZ * PLAYER_BASE_SPEED_MOD);
                entity.setYRot(this.yaw);
                ((IControllable)entity).move(dM);
            });
        });
    }
}
