package me.santipingui58.survival.discord;

public class TwitchMinecraftBridgeMessage {

	private TwitchMinecraftBridgeType type;
	
	
	
	public TwitchMinecraftBridgeMessage(TwitchMinecraftBridgeType type) {
		this.type = type;
	}

	
	public TwitchMinecraftBridgeType getType() {
		return this.type;
	}
	
}
