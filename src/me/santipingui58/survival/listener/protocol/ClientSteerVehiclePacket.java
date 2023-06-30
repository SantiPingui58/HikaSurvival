package me.santipingui58.survival.listener.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.game.boat.FastBoat;

import java.util.Objects;

public class ClientSteerVehiclePacket {

    private final ProtocolManager protocolManager;

    public ClientSteerVehiclePacket(HikaSurvival hikaSurvival) {
        protocolManager = ProtocolLibrary.getProtocolManager();

        protocolManager.addPacketListener(
                new PacketAdapter(hikaSurvival, PacketType.Play.Client.STEER_VEHICLE) {
                    @Override
                    public void onPacketReceiving(PacketEvent event) {
                        for (FastBoat bmo : hikaSurvival.getBoatManager().getFastBoats()) {
                            if (bmo.getBoat().getEntityId() == Objects.requireNonNull(event.getPlayer().getVehicle()).getEntityId()) {
                                // Update current controller
                                bmo.setNewPacketData(event);
                            }
                        }
                    }
                });
    }
}