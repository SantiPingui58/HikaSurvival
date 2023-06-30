package me.santipingui58.survival.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.player.SurviPlayer;
import me.santipingui58.survival.utils.LuckPermsUtils;


public class PlayerConnectListener implements Listener {
	
	
	private HikaSurvival hikaSurvival;
	public PlayerConnectListener(HikaSurvival hikaSurvival) {
		this.hikaSurvival = hikaSurvival;
	}
	
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    if (!p.hasPlayedBefore()) {
    	if (!hikaSurvival.data.getConfig().contains("delay."+p.getUniqueId().toString())) {
    		hikaSurvival.data.getConfig().set("delay."+p.getUniqueId().toString(), 20);
    	}
    }
    
    if (hikaSurvival.data.getConfig().contains("delay."+p.getUniqueId().toString())) {
    		int s = hikaSurvival.data.getConfig().getInt("delay."+p.getUniqueId().toString());
    		p.sendMessage("§cDebes esperar §6" + s + " minutos §cpara poder jugar el Survival!");
    }
    
	 e.setJoinMessage(null);
    new BukkitRunnable() {
    	public void run() {
    	 hikaSurvival.getSQLManager().createData(p.getUniqueId());
    	 hikaSurvival.getRedisManager().addToSet("whitelist", p.getName());
    	 hikaSurvival.getRedisManager().addToSet("whitelist", p.getUniqueId().toString());

    	 if (p.hasPermission("hika.sub") || p.hasPermission("hika.vip")) {
    		 Bukkit.broadcastMessage("§f "+ LuckPermsUtils.getPrefix(p) + p.getName() + " §aha entrado al servidor!");
    	 } else {
    	
    		  Bukkit.broadcastMessage("§a[+] §7"+ p.getName());
    	 }
    	 
    		
    	}
    }.runTaskAsynchronously(hikaSurvival);
    
    }
    
    
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
    	Player p = e.getPlayer();
    	
    	 e.setQuitMessage(null);
    	 
    	 if (p.hasPermission("hika.sub") || p.hasPermission("hika.vip")) {
    		 Bukkit.broadcastMessage("§f "+ LuckPermsUtils.getPrefix(p) + p.getName() + " §cha salido del servidor!");
    	 } else {
    	
    		 Bukkit.broadcastMessage("§c[-] §7"+ p.getName());
    	 }
    	 
    	 
    	SurviPlayer sp = hikaSurvival.getPlayer(p);
    	hikaSurvival.getSQLManager().saveData(sp,true);
    }
    
    
}
