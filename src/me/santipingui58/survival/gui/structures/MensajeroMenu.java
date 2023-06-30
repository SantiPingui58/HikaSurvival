package me.santipingui58.survival.gui.structures;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.gui.GUIBuilder;
import me.santipingui58.survival.player.SurviAccess;
import me.santipingui58.survival.player.SurviPlayer;
import me.santipingui58.survival.utils.ItemBuilder;


public class MensajeroMenu extends GUIBuilder {
	
	public MensajeroMenu(Player p, HikaSurvival hikaSurvival) {
		super("",3,hikaSurvival);
		
		SurviPlayer sp = hikaSurvival.getPlayer(p);
		ItemBuilder msg = new ItemBuilder(Material.PAPER).setTitle("§f§lAcceso a comando /msg")
				.addLore("§7Comando para enviar ")
				.addLore("§7mensajes privados a jugadores")
				.addLore("§7que se encuentren lejos")
				.addLore("");
		
		if (sp.hasAccess(SurviAccess.MSG)) {
			msg.addLore("§aYa tienes este acceso!");
		} else {
			msg.addLore("§aPrecio: §6$200");
		}
		
		
		s(12,msg.build());
		
		
		
		ItemBuilder global = new ItemBuilder(Material.WRITABLE_BOOK).setTitle("§b§lAcceso a Chat Global")
				.addLore("§7Habilidad para")
				.addLore("§7hablar en el chat global")
				.addLore("§7con jugadores lejanos")
				.addLore("");
		
		if (sp.hasAccess(SurviAccess.CHAT_GLOBAL)) {
			global.addLore("§aYa tienes este acceso!");
		} else {
			global.addLore("§aPrecio: §6$500");
		}
		
		
		s(14,global.build());
		
		
		buildInventory();
		
		}
		
	
		

	@Override
	public void onClick(Player p, ItemStack stack, int slot) {
		
		SurviPlayer sp = hikaSurvival.getPlayer(p);
		
		if (slot==12) {
			if (!sp.hasAccess(SurviAccess.MSG)) {
				if (sp.getMoney()>=200) {
					p.closeInventory();
					p.sendMessage("§aHas comprado " + stack.getItemMeta().getDisplayName() + "§a!");
					hikaSurvival.payToServer(p, 200);
					sp.addAccess(SurviAccess.MSG);
				} else {
					p.sendMessage("§cNo tienes suficientes SurviCoins para comprar esto.");
					p.closeInventory();
				}
			}
		} else if (slot==14) {
			if (!sp.hasAccess(SurviAccess.CHAT_GLOBAL)) {
				if (sp.getMoney()>=500) {
					p.closeInventory();
					p.sendMessage("§aHas comprado " + stack.getItemMeta().getDisplayName() + "§a!");
					hikaSurvival.payToServer(p, 500);
					sp.addAccess(SurviAccess.CHAT_GLOBAL);
				} else {
					p.sendMessage("§cNo tienes suficientes SurviCoins para comprar esto.");
					p.closeInventory();
				}
			}
		}
		
		
	
	
	}
	
}
