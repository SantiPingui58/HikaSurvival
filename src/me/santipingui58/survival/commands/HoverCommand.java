package me.santipingui58.survival.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.game.structures.StructureType;
import me.santipingui58.survival.player.SurviAccess;
import me.santipingui58.survival.player.SurviPlayer;


public class HoverCommand implements CommandExecutor {

	HikaSurvival hikaSurvival;
	public HoverCommand(HikaSurvival hikaSurvival) {
		this.hikaSurvival = hikaSurvival;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			
			sender.sendMessage("Solo los jugadores pueden hacer esto!");
			return true;
			
		} else {
			Player p = (Player) sender;
			SurviPlayer sp = hikaSurvival.getPlayer(p);
			StructureType type = StructureType.valueOf(args[0].toUpperCase());
			SurviAccess access = SurviAccess.fromStructureType(type);
			if (!sp.hasAccess(access)) {
				if (sp.getMoney()>=type.getPrice()) {
					hikaSurvival.payToServer(p, type.getPrice());	
					p.sendMessage("§aHas comprado el acceso a " + type.getName()+"§a! Has click al NPC para abrir el menu");
					 sp.addAccess(access);
				} else {
					p.sendMessage("§cNo tienes suficientes SurviCoins para hacer esto!");
				}		
			} else {
				p.sendMessage("§cTu ya tienes acceso a " + type.getName()+"§c!");
			}
 		}
		return false;
	}

}
