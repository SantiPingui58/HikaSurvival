package me.santipingui58.survival.task;


import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.player.SurviPlayer;

public class TimeTask {

	private HikaSurvival hikaSurvival;
	
	public TimeTask(HikaSurvival hikaSurvival) {
		this.hikaSurvival = hikaSurvival;
		new BukkitRunnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				//Utils.debug("aa",hikaSurvival);
				
				delayCheck();
				
				for (SurviPlayer sp : hikaSurvival.getPlayers()) {
					//Utils.debug("bb",hikaSurvival);
					if (!sp.getOfflinePlayer().isOnline()) continue;
					//if (sp.getOfflinePlayer().getName().equalsIgnoreCase("SantiPingui58")) Utils.debug("Online Time " +sp.getOnlineTime(),hikaSurvival);
					if (sp.getPlayer().hasPermission("hika.vip") || sp.getPlayer().hasPermission("hika.sub")) {
						if (hikaSurvival.getEssentials().getUser(sp.getPlayer()).isAfk()) continue;
						sp.addOnlineTime();
					
						if (sp.getOnlineTime()>=30) {
							sp.setOnlineTime(0);
							hikaSurvival.getEconomy().depositPlayer(sp.getPlayer(), 50);
						}
					}
				}
				
				Date now = new Date();
				
				if (now.getHours()==0 && now.getMinutes()==0) {
					hikaSurvival.getBankManager().interest();
				}
				
			}
		}.runTaskTimerAsynchronously(hikaSurvival, 0L, 20L*60);
	}
	
	
	private void delayCheck() {

		FileConfiguration data = hikaSurvival.data.getConfig();
		if (!data.contains("delay")) return;
		HashMap<String,Integer> hashmap = new HashMap<String,Integer>();
		for (String e : data.getConfigurationSection("delay").getKeys(false)) {
			int s = data.getInt("delay."+e);
			s = s-1;
			if (s==0) {
				UUID uuid = UUID.fromString(e);
				if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
					Player p = Bukkit.getPlayer(uuid);
					new BukkitRunnable() {
						public void run() {
							p.sendMessage("§aYa puedes jugar el Survival!");
						}
					}.runTask(hikaSurvival);
					
				}
				
			} else {
			hashmap.put(e, s);
			}
		}
		
		data.set("delay", null);
		for (Entry<String, Integer> e : hashmap.entrySet()) 
			data.set("delay."+e.getKey(), e.getValue());
		hikaSurvival.data.saveConfig();
		
	}
}
