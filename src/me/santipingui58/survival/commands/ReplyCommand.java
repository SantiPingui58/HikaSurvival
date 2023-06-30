package me.santipingui58.survival.commands;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.player.SurviPlayer;

public class ReplyCommand implements CommandExecutor {

	private HikaSurvival hikaSurvival;
	public ReplyCommand(HikaSurvival hikaSurvival) {
		this.hikaSurvival = hikaSurvival;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
		
		/*
		if (!(sender instanceof ConsoleCommandSender)) {
			Player p = (Player) sender;

		
		
		if (!sp.hasAccess(SurviAccess.MSG)) {
			Structure structure =	hikaSurvival.getStructureManager().getNearestStructure(sp.getPlayer().getLocation(), StructureType.MAGE);
			p.sendMessage("§cNo tienes permiso para usar este comando.");
			if (structure!=null) {
				int x = structure.getLocation().getBlockX();
				int z = structure.getLocation().getBlockZ();
				p.sendMessage("§bDebes hablar con un Mensajero para que puedas hacerlo! (El más cercano se encuentra en: §5X"+x +" Z"+z );
			} else {
				p.sendMessage("§cActualmente no hay Mensajeros en el Servidor." );
			}
			return false;
		}
		
		
		} 
		*/
		
		if (!(sender instanceof ConsoleCommandSender)) {
			Player p = (Player) sender;
		SurviPlayer sp = hikaSurvival.getPlayer(p);
		
		/*
		if (!sp.hasAccess(SurviAccess.MSG)) {
			hikaSurvival.getStructureManager().noPermission(p,StructureType.MAIL);
			return false;
		}
		*/
		
		if (sp.getMoney()<=1) {
			p.sendMessage("§cNo tienes suficiente dinero para hacer esto!");
			return false;
		} else {
			hikaSurvival.payToServer(p, 1);
		}
		} 
		
		
		if (args.length >= 1) {
		if (MsgCommand.respond.containsKey(sender)) {
			if (Bukkit.getServer().getOnlinePlayers().contains(MsgCommand.respond.get(sender))) {
				
				
				
				StringBuilder builder = new StringBuilder();
			    for (int i = 0; i < args.length; i++)
			    {
			      builder.append(args[i]).append(" ");
			    }
			  String message = builder.toString();		
			  
			  CommandSender receptor = MsgCommand.respond.get(sender);			  
			  sender.sendMessage("§6[yo -> " + receptor.getName() + "] §f" + message);	
			  MsgCommand.respond.get(sender).sendMessage("§6[" + sender.getName() + " -> yo] §f" + message);

					
			} else {
					sender.sendMessage("§cNo tienes a nadie a quien responder.");		
			}
		} else {
				sender.sendMessage("§cNo tienes a nadie quien responder");		
			}
	} else {
			sender.sendMessage("§aUso del comando: /r <mensaje>");	
	}
		return false;
	}
	
	

}
