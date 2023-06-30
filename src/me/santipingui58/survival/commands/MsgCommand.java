package me.santipingui58.survival.commands;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.player.SurviPlayer;




public class MsgCommand implements CommandExecutor {
	
	public static HashMap<CommandSender,CommandSender> respond = new HashMap<CommandSender,CommandSender>();
	
	private HikaSurvival hikaSurvival;
	public MsgCommand(HikaSurvival hikaSurvival) {
		this.hikaSurvival = hikaSurvival;
	}
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
		
		if (args.length == 0 || args.length == 1) {
				sender.sendMessage("§aUso del comando: /msg <nombre> <mensaje>");
			
			
		} else {
			
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
			
			Player receptor = Bukkit.getServer().getPlayer(args[0]);
		
			if (Bukkit.getOnlinePlayers().contains(receptor)) {
				StringBuilder builder = new StringBuilder();
			    for (int i = 1; i < args.length; i++)
			    {
			      builder.append(args[i]).append(" ");
			    }
			  String message = builder.toString();
 
			  sender.sendMessage("§6[yo -> " + receptor.getName() + "] §f" + message);
			 receptor.sendMessage("§6[" + sender.getName() + " -> yo] §f" + message);
				respond.put(receptor, sender);
				respond.put(sender, receptor);
			} else {
				sender.sendMessage("§cEl jugador §b" + args[0] + "§c no existe o no está conectado.");
				}
			
		}
	
	return false;
	}

}
