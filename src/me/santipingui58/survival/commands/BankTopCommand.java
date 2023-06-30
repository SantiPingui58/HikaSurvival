package me.santipingui58.survival.commands;


import java.text.NumberFormat;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.santipingui58.survival.HikaSurvival;


public class BankTopCommand implements CommandExecutor {

	HikaSurvival hikaSurvival;
	public BankTopCommand(HikaSurvival hikaSurvival) {
		this.hikaSurvival = hikaSurvival;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		int page = 1;
		
		if(args.length>0) {
			try {
			page = Integer.parseInt(args[0]);
			} catch(Exception ex) {
				sender.sendMessage("§b" + args[0]+"§b no es un número válido.");
			}
		} 
		
		page = page-1;
		int min = (10*page)+1;
		int max = min+9;
		int i = 0;
		
		NumberFormat fmt = NumberFormat.getCurrencyInstance();
			sender.sendMessage("§a§lRanking del Banco");
		for (Entry<UUID, Integer> e : hikaSurvival.getBankManager().getBankTop().entrySet()) {
			i++;
			if (i>=min && i<=max) {
				String player= Bukkit.getOfflinePlayer(e.getKey()).getName();
				int money = e.getValue();
				String m = fmt.format(money);
				m = m.substring(1);
				sender.sendMessage("§e"+i+". §a" + player + "§8- §6$"+m);
			}
		}
		
		int np = page+2;
		sender.sendMessage("§7Proxima pagina: §c/banktop " +  np);
		
		
		return false;
	}

}
