package me.santipingui58.survival.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.kingdoms.constants.land.location.SimpleChunkLocation;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.game.structures.StructureType;
import me.santipingui58.survival.player.SurviAccess;
import me.santipingui58.survival.player.SurviPlayer;

public class PlayerListener implements Listener {

	private HikaSurvival hikaSurvival;
	public PlayerListener(HikaSurvival hikaSurvival) {
		this.hikaSurvival = hikaSurvival;
	}
	
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		
		
		Player p = e.getPlayer();
		SurviPlayer sp = hikaSurvival.getPlayer(p);
		if (e.getMessage().startsWith("/k")) {
			if (!sp.hasAccess(SurviAccess.CASTILLO_REY)) {
				e.setCancelled(true);
				hikaSurvival.getStructureManager().noPermission(p, StructureType.KING);
			}
		} 
	}

	
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		SurviPlayer sp = hikaSurvival.getPlayer(p);
		double money = sp.getMoney();
		
		int min = 0;
		int max = p.hasPermission("hika.vip") ? 25 : 50;
		
		int random = (int)(Math.random() * ((max - min) + 1)) + min;
		int lossmoney = (int) (money*random/100);
		p.sendMessage("§cHas muerto! Has perdido §6§l$" + lossmoney + " SurviCoins§c!");
		hikaSurvival.getEconomy().withdrawPlayer(p, lossmoney);
		hikaSurvival.getBankManager().addMoney(lossmoney);
	}
	
	
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		for (SimpleChunkLocation scl : ServerListener.chunksInvaded) {
			if (scl.isInChunk(e.getPlayer().getLocation())) {
				e.setCancelled(true);
				return;
			}
		}
		if (hikaSurvival.data.getConfig().contains("delay."+p.getUniqueId().toString())) {
    		int s = hikaSurvival.data.getConfig().getInt("delay."+p.getUniqueId().toString());
    		p.sendMessage("§cDebes esperar §6" + s + " minutos §cpara poder jugar el Survival!");
    		e.setCancelled(true);
    }
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		for (SimpleChunkLocation scl : ServerListener.chunksInvaded) {
			if (scl.isInChunk(e.getPlayer().getLocation())) {
				e.setCancelled(true);
				return;
			}
		}
		if (hikaSurvival.data.getConfig().contains("delay."+p.getUniqueId().toString())) {
    		int s = hikaSurvival.data.getConfig().getInt("delay."+p.getUniqueId().toString());
    		p.sendMessage("§cDebes esperar §6" + s + " minutos §cpara poder jugar el Survival!");
    		e.setCancelled(true);
    }
	}
}



