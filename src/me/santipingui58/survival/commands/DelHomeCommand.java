package me.santipingui58.survival.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.game.home.HikaHome;
import me.santipingui58.survival.game.structures.StructureType;
import me.santipingui58.survival.player.SurviAccess;
import me.santipingui58.survival.player.SurviPlayer;



public class DelHomeCommand implements CommandExecutor {

	HikaSurvival hikaSurvival;
	public DelHomeCommand(HikaSurvival hikaSurvival) {
		this.hikaSurvival = hikaSurvival;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			
			sender.sendMessage("Solo los jugadores pueden hacer esto!");
			return true;
			
		} else {
			Player p = (Player) sender;
			SurviPlayer sp = hikaSurvival.getPlayer(p);
			if (!sp.hasAccess(SurviAccess.HOME)) {
				hikaSurvival.getStructureManager().noPermission(p, StructureType.MAGE);
				return false;
			}
			
			if (args.length==0) {
				p.sendMessage("§a/delhome <home>");
			} else {
				String homeName = args[0];
				HikaHome home = sp.getHome(homeName);
				if (home!=null) {
					sp.getHomes().remove(home);
					p.sendMessage("§aHas eliminado el home §b" + homeName + " §acorrectamente");
					new BukkitRunnable() {
						public void run() {
							hikaSurvival.getSQLManager().deleteHome(sp, home);
						}
					}.runTaskAsynchronously(hikaSurvival);
					
					
				} else {
					p.sendMessage("§cNo tienes un home con el nombre §b" + homeName);
				}
			}
		
		
		}
		return false;
	}

}
