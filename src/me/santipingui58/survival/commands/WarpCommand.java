package me.santipingui58.survival.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.game.structures.StructureType;
import me.santipingui58.survival.game.warp.WarpManager;
import me.santipingui58.survival.gui.game.WarpMenu;
import me.santipingui58.survival.player.SurviAccess;
import me.santipingui58.survival.player.SurviPlayer;


public class WarpCommand implements CommandExecutor {

	HikaSurvival hikaSurvival;
	public WarpCommand(HikaSurvival hikaSurvival) {
		this.hikaSurvival = hikaSurvival;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			
			sender.sendMessage("Solo los jugadores pueden hacer esto!");
			return true;
			
		} else {
			WarpManager wm = hikaSurvival.getWarpManager();
			Player p = (Player) sender;
			
		
		if (args.length ==2 && args[0].equalsIgnoreCase("create")) {
			if (p.isOp()) {
				if (p.getItemInHand()==null) {
					p.sendMessage("§cNecesitas tener un item en la mano para definir el Warp!");
					return false;
				}
				String name = args[1];
			wm.createWarp(name, p.getLocation(), p.getItemInHand().getType());
			p.sendMessage("§aWarp creado");
			}
		} else if (args.length ==2 && args[0].equalsIgnoreCase("delete")) {
			if (p.isOp()) {
				String name = args[1];
				if (wm.getWarp(name)!=null) {
			wm.removeWarp(name);
			p.sendMessage("§cWarp eliminado");
				} else {
					p.sendMessage("§cNo existe un warp con el nombre §b" + name);
				}
			}
		}else {
			SurviPlayer sp = hikaSurvival.getPlayer(p);
			if (sp.hasAccess(SurviAccess.WARP)) {
			new WarpMenu(hikaSurvival, p).o(p);
			} else {
				hikaSurvival.getStructureManager().noPermission(p, StructureType.MAGE);
			}
		} 
		}
		return false;
	}

}
