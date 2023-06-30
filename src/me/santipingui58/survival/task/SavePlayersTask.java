package me.santipingui58.survival.task;

import org.bukkit.scheduler.BukkitRunnable;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.io.database.SQLManager;
import me.santipingui58.survival.player.SurviPlayer;

public class SavePlayersTask {

	public SavePlayersTask(HikaSurvival hikaSurvival) {
		
		
		SQLManager sql = hikaSurvival.getSQLManager();
		new BukkitRunnable() {
			
			public void run() {
				
			hikaSurvival.getBankManager().setBankTop(sql.getBankTop());
			for (SurviPlayer sp : hikaSurvival.getPlayers()) sql.saveData(sp, !sp.getOfflinePlayer().isOnline());
				
				
			}
		}.runTaskTimerAsynchronously(hikaSurvival, 0L, 20L*60*10);
	}
	
	
}


