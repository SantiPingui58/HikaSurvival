package me.santipingui58.survival.task;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.discord.TwitchMinecraftBridgeType;
import me.santipingui58.survival.player.SurviPlayer;

public class RedisTask {

	
	
	public RedisTask(HikaSurvival hikaSurvival) {
		new BukkitRunnable() {
			@SuppressWarnings("deprecation")
			public void run() {

				
				Set<String> errorMessages = new HashSet<String>();
				Set<String> sucessfullMessages = new HashSet<String>();
					for (String s : hikaSurvival.getRedisManager().getSet("twitch-minecraft-bridge")) {
					String[] data = s.split(" ");
					String prefix = data[0];
					try {
						TwitchMinecraftBridgeType.valueOf(prefix);
					} catch(Exception ex) {
						Bukkit.getLogger().info("[DiscordBridge] Se recibió un mensaje inválido: " + s);
						errorMessages.add(s);
						continue;
					}
					
					TwitchMinecraftBridgeType type = TwitchMinecraftBridgeType.valueOf(prefix);
					Bukkit.getLogger().info("[DiscordBridge] Se recibió un mensaje del tipo:" + type.toString());
					
					try {
					switch (type) {
					
					//BUY_SURVICOINS twitchId 100
					case BUY_SURVICOINS:
						String twitchId = data[1];
						int amount = Integer.parseInt(data[2]);
						
						new BukkitRunnable() {

							public void run() {
								SurviPlayer sp = hikaSurvival.getPlayerByTwitchId(twitchId);
								if (sp==null)  sp = hikaSurvival.getSQLManager().loadData(twitchId);
								if (sp!=null) {
									Bukkit.getLogger().info("[DiscordBridge] Dando 100 SurviCoins a: " + sp.getOfflinePlayer().getName());
								hikaSurvival.getEconomy().depositPlayer(sp.getOfflinePlayer().getName(), amount);						
								errorMessages.add(s);
								} else {
									Bukkit.getLogger().info("[DiscordBridge] No se pudo dar 100 SurviCoins a la cuenta de TwitchID: " + twitchId);
									errorMessages.add(s);
									
								}
							}
						}.runTaskAsynchronously(hikaSurvival);
						
						break;
						
						
						//WHITELIST mcNombre twitchId
					case WHITELIST:
						String name = data[1];
						name = name.replaceAll(",", ".");
						 UUID uuid = Bukkit.getOfflinePlayer(name).getUniqueId();
						 twitchId = data[2];
						hikaSurvival.data.saveConfig();
						hikaSurvival.getRedisManager().addToSet("whitelist", name);
						hikaSurvival.getRedisManager().addToSet("whitelist", uuid.toString());
						hikaSurvival.data.getConfig().set("minecraft-twitch."+name, twitchId);
						Bukkit.getLogger().info("[DiscordBridge] Añadiendo a la whitelist a: " + name);
						hikaSurvival.data.saveConfig();
						sucessfullMessages.add(s);
					default:
						
						break;
					
					}
					
				
					} catch(Exception ex) {
						ex.printStackTrace();
						errorMessages.add(s);
					}
					
					}
					
					new BukkitRunnable() {
					public void run() {
					for (String s : sucessfullMessages) {
					hikaSurvival.getRedisManager().removeFromSet("twitch-minecraft-bridge",s);
					hikaSurvival.getRedisManager().addToSet("sucessfull-messages",s);
					}
					
					for (String s : errorMessages) {
						hikaSurvival.getRedisManager().removeFromSet("twitch-minecraft-bridge",s);
						hikaSurvival.getRedisManager().addToSet("error-messages",s);
						}
					}
					}.runTaskLaterAsynchronously(hikaSurvival, 5L);
				
			}
		}.runTaskTimerAsynchronously(hikaSurvival, 0L, 20L*5);
		
	}
	 
	
}
