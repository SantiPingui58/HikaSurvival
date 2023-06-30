package me.santipingui58.survival.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.game.home.HikaHome;
import me.santipingui58.survival.game.structures.StructureType;
import me.santipingui58.survival.gui.game.HomesMenu;
import me.santipingui58.survival.player.SurviAccess;
import me.santipingui58.survival.player.SurviPlayer;



public class HomeCommand implements CommandExecutor {

	HikaSurvival hikaSurvival;
	public HomeCommand(HikaSurvival hikaSurvival) {
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
			} else {
				if (args.length==0) {
				new HomesMenu(hikaSurvival, p).o(p);
			} else {
				HikaHome home = sp.getHome(args[0]);
				if (home!=null) {
				double price = home.getPrice(p);
					if (sp.getMoney()>=price) {
						hikaSurvival.payToServer(p, (int) price);
						p.sendMessage("§6Teletransportandote...");
						p.teleport(home.getLocation());
						p.closeInventory();
					} else {
						p.sendMessage("§cNo tienes suficientes SurviCoins para hacer esto.");
					}
				} else {
					p.sendMessage("§cNo tienes un home con el nombre §b" + args[0]);
				}
			}
			}
			
			
			
		
		}
		return false;
	}

}
