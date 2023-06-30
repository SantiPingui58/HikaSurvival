package me.santipingui58.survival.commands;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.santipingui58.survival.HikaSurvival;


public class ByPassCommand implements CommandExecutor {

	HikaSurvival hikaSurvival;
	public ByPassCommand(HikaSurvival hikaSurvival) {
		this.hikaSurvival = hikaSurvival;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender.hasPermission("hika.streamer")) {
			if (args.length==0) {
				sender.sendMessage("§aUso del comando: /bypass <Jugador>");	
			} else {
				Player player = Bukkit.getPlayer(args[0]);
				if (Bukkit.getOnlinePlayers().contains(player)) {
			    	if (hikaSurvival.data.getConfig().contains("delay."+player.getUniqueId().toString())) {
			    		int i = hikaSurvival.data.getConfig().getInt("delay."+player.getUniqueId().toString());
			    		if (i>5) {
			    			hikaSurvival.data.getConfig().set("delay."+player.getUniqueId().toString(), 5);
			    			sender.sendMessage("§aHas reducido el delay del jugador §b" + player.getName() + "§aa 5 minutos!");
			    		} else {
			    			sender.sendMessage("§cEl jugador §b" + player.getName() + " §cle quedan menos de 5 minutos de delay.");
			    		}
			    	} else {
			    		sender.sendMessage("§cEl jugador §b" + player.getName() + " §cno tiene delay.");
			    	}
				} else {
					sender.sendMessage("§cEl jugador §b" + player.getName() + " §cno existe.");
				}
			}
		} else {
			sender.sendMessage("§cNo tienes permisos para usar este comando.");
		}
		
		return false;
		
	}

}
