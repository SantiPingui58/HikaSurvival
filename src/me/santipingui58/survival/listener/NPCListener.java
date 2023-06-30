package me.santipingui58.survival.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.game.structures.Structure;
import me.santipingui58.survival.game.structures.StructureType;
import me.santipingui58.survival.gui.GUIBuilder;
import me.santipingui58.survival.gui.structures.TrainMenu;
import me.santipingui58.survival.player.SurviAccess;
import me.santipingui58.survival.player.SurviPlayer;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.md_5.bungee.api.ChatColor;

public class NPCListener implements Listener {

	
	private HikaSurvival hikaSurvival;
	public NPCListener(HikaSurvival hikaSurvival) {
		this.hikaSurvival = hikaSurvival;
	}
	
	
	
	@EventHandler
	public void onNPC(NPCRightClickEvent e) {
		Player p = e.getClicker();
		SurviPlayer sp = hikaSurvival.getPlayer(p);
		if (ChatColor.stripColor(e.getNPC().getName()).equalsIgnoreCase("COMERCIANTE")) {
			p.performCommand("ds shop Tienda");
			return;
		} else  if (ChatColor.stripColor(e.getNPC().getName()).equalsIgnoreCase("BOLETERIA")) {
			new TrainMenu(hikaSurvival).o(p);
			return;
		}  
			
		
		for (Structure structure : hikaSurvival.getStructureManager().getStructures()) {
			if (structure.getNPC().equals(e.getNPC())) {
				if (sp.hasAccess(SurviAccess.fromStructureType(structure.getType()))) {
					if (ChatColor.stripColor(e.getNPC().getName()).equalsIgnoreCase("VENDEDOR")) {
							p.performCommand("petshop");
						}else if (structure.getType().equals(StructureType.KING)) {
							p.sendMessage("§ePara acceder a los Reinos, debes usar el comando §b/k");
						} else {
					GUIBuilder menu = structure.getMenu(p);
					menu.o(p);
						}
				return;
				} else {
					if (!sp.getSendingWelcomeMsg().contains(structure)) {
						structure.sendWelcomeMessage(sp);
						sp.getSendingWelcomeMsg().add(structure);
					}
				}
			}
		}
	}
	
	
	
	
}
