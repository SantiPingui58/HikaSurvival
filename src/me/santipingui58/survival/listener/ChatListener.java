package me.santipingui58.survival.listener;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.utils.LuckPermsUtils;


public class ChatListener implements Listener {
	
	
	
	public HikaSurvival hikaSurvival;
	public ChatListener(HikaSurvival hikaSurvival) {
		this.hikaSurvival = hikaSurvival;
	}
	
	
	  @EventHandler
	    public void onChat(AsyncPlayerChatEvent e) {
		  e.setCancelled(true);
	    	Player p = e.getPlayer();
	    		String msg = e.getMessage().replace("%", "%%");
	    		String prefix = LuckPermsUtils.getPrefix(p);
	    		
	    		/*
	    		boolean global = msg.startsWith("!");
	    		if (global)  {
	    			
	    			if (!sp.hasAccess(SurviAccess.CHAT_GLOBAL)) {
	    				Structure structure =	hikaSurvival.getStructureManager().getNearestStructure(sp.getPlayer().getLocation(), StructureType.MAIL);
	    				p.sendMessage("§cNo tienes permiso para usar este comando.");
	    				if (structure!=null) {
	    					int x = structure.getLocation().getBlockX();
	    					int z = structure.getLocation().getBlockX();
	    					p.sendMessage("§bDebes hablar con un Mensajero para que puedas hacerlo! (El más cercano se encuentra en: §5X"+x +" Z"+z );
	    				} else {
	    					p.sendMessage("§cActualmente no hay Mensajeros en el Servidor." );
	    				}
	    				return;
	    			}
	    			
	    			if (sp.getMoney()<=3) {
	    				p.sendMessage("§cNo tienes suficiente dinero para hacer esto!");
	    				return;
	    			} else {
	    				hikaSurvival.payToServer(p, 3);
	    			}
	    			
	    			msg = msg.substring(1);
	    		}
	    		
	    		Collection<? extends Player> players = global ? Bukkit.getOnlinePlayers() : getClosePlayers(p);
	    		*/
	    		
	    		Collection<? extends Player> players = Bukkit.getOnlinePlayers();
	    			
	    		String msgVip = prefix + "§f"+p.getName()+"§8:§a " + ChatColor.translateAlternateColorCodes('&', msg);
	    		String msgDefault = prefix+ " §f" + p.getName() + "§8: §7" + msg;
	    		
	    		msg = p.hasPermission("hika.vip") ? msgVip : msgDefault;
	    		//msg = global ? "§b§l[G] " + msg : msg;
	    		
	    		

	    		 msg = msg.replaceAll("@everyone", "$everyone");
	    		msg = msg.replaceAll("@here", "$here");
	    		 String m = msg;
	    		new BukkitRunnable() {
	    			public void run() {
	    		for (Player pl : players) pl.sendMessage(m);
	    		Bukkit.getLogger().info(m);
	    		Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "discord broadcast #server-chat " + m);
	    		
		    		}
		    		}.runTask(hikaSurvival);
	    }
	  
	  
	  
	  
	  @SuppressWarnings("unused")
	private Collection<? extends Player> getClosePlayers(Player p) {
		  Set<Player> set = new HashSet<Player>();
		  
		  for (Player online : p.getWorld().getPlayers()) {
			  if (online.getLocation().distanceSquared(p.getLocation())<=1000*1000) set.add(online);
		  }
		  
		  return set;
		  
	  }
	  
	  
}
