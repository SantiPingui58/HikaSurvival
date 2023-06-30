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



public class SetHomeCommand implements CommandExecutor {

	HikaSurvival hikaSurvival;
	public SetHomeCommand(HikaSurvival hikaSurvival) {
		this.hikaSurvival = hikaSurvival;
	}
	@SuppressWarnings("deprecation")
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
			if (sp.getHomes().size()>=sp.getMaxHomes()) {
				p.sendMessage("§cYa no tienes más SetHomes permitidos.");
			} else {
				if (args.length==1) {
					if (p.isOnGround()) {
					String name = args[0];
					HikaHome home = sp.getHome(name);
					if (home==null) {
						new BukkitRunnable() {
							public void run() {
						HikaHome home = new HikaHome(p.getUniqueId(), name, p.getLocation());
						int id = hikaSurvival.getSQLManager().createHome(sp, home);
						home.setId(id);
						 sp.getHomes().add(home);
						}
						}.runTaskAsynchronously(hikaSurvival);
						p.sendMessage("§aHome creado!");
					} else {
						p.sendMessage("§cYa tienes un Home con el nombre §b" +name);
					}
					} else {
						p.sendMessage("§cDebes estar en el suelo!");
					}
				} else {
					p.sendMessage("§b/sethome <nombre>");
				}
			}
		
		}
		return false;
	}

}
