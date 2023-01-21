package fr.sushi.abstraction.network;

import fr.sushi.abstraction.ModAbstraction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModPacketManager {
    public static final String PROTOCOL_VERSION = "1";
    public static int ID = 0;
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ModAbstraction.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);

    private static int id() {
        ID++;
        return ID;
    }

    public static void registerPackets() {
        INSTANCE.messageBuilder(PlayerSwingC2SPacket.class, id())
                .encoder(PlayerSwingC2SPacket::encode)
                .decoder(PlayerSwingC2SPacket::new)
                .consumerMainThread(PlayerSwingC2SPacket::handle)
                .add();

        INSTANCE.messageBuilder(PlayerMoveC2SPacket.class, id())
                .encoder(PlayerMoveC2SPacket::encode)
                .decoder(PlayerMoveC2SPacket::new)
                .consumerMainThread(PlayerMoveC2SPacket::handle)
                .add();
    }

    public static <MSG> void sendToServeur(MSG message) {
        INSTANCE.sendToServer(message);
    }

//    public static <MSG> void sendToClient(MSG message, ServerPlayer player) {
//        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
//    }
}
