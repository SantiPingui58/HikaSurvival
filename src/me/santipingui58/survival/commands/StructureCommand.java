package me.santipingui58.survival.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.game.structures.StructureType;


public class StructureCommand implements CommandExecutor {

	HikaSurvival hikaSurvival;
	public StructureCommand(HikaSurvival hikaSurvival) {
		this.hikaSurvival = hikaSurvival;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			
			sender.sendMessage("Solo los jugadores pueden hacer esto!");
			return true;
			
		} else {
			
			//structure create nombre tipo
		if (args[0].equalsIgnoreCase("create")) {
			Player p = (Player) sender;
			StructureType type = StructureType.valueOf(args[2]);
			String name = args[1];
			hikaSurvival.getStructureManager().createStructure(name, p.getLocation(), type);
			p.sendMessage("§aEstructura creada");
		}
		}
		return false;
	}

}
