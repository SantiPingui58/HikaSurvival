package me.santipingui58.survival.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.player.SurviPlayer;


public class BankCommand implements CommandExecutor {

	HikaSurvival hikaSurvival;
	public BankCommand(HikaSurvival hikaSurvival) {
		this.hikaSurvival = hikaSurvival;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			
			sender.sendMessage("Solo los jugadores pueden hacer esto!");
			return true;
			
		} else {
			if (!sender.isOp()) return false;
		Player p = (Player) sender;
		SurviPlayer sp = hikaSurvival.getPlayer(p);
			
		if(args.length==0) {
			int bankMoney = hikaSurvival.getConfig().getInt("bank");
			p.sendMessage("Dinero del banco:" + bankMoney);
		} else if (args[0].equalsIgnoreCase("take")) {
			int bankMoney = hikaSurvival.getConfig().getInt("bank");
			int money = Integer.parseInt(args[1]);
			if (bankMoney>=money) {
				bankMoney = bankMoney - money;
			hikaSurvival.getConfig().set("bank", bankMoney);
			hikaSurvival.getEconomy().depositPlayer(p, money);
			hikaSurvival.saveConfig();
			p.sendMessage("Nuevo dinero del banco: " + bankMoney);
			} else {
				p.sendMessage("El banco no tiene suficiente dinero");
			}
		} else if (args[0].equalsIgnoreCase("deposit")) {
			int bankMoney = hikaSurvival.getConfig().getInt("bank");
			int money = Integer.parseInt(args[1]);
			if (sp.getMoney()>=money) {
			money = money + bankMoney;
			hikaSurvival.getConfig().set("bank", money);
			hikaSurvival.getEconomy().withdrawPlayer(p, money);
			hikaSurvival.saveConfig();
			p.sendMessage("Nuevo dinero del banco: " + money);
			} else {
				p.sendMessage("No tienes suficiente dinero");
			}
		} 
		}
		return false;
	}

}
