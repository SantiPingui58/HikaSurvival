package me.santipingui58.survival.gui.structures;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import dev.espi.protectionstones.ProtectionStones;
import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.gui.GUIBuilder;
import me.santipingui58.survival.player.SurviPlayer;
import me.santipingui58.survival.utils.ItemBuilder;

public class ReyMenu extends GUIBuilder {
	
	public ReyMenu(HikaSurvival hikaSurvival) {
		super("",4,hikaSurvival);
		
		
		
		s(11,new ItemBuilder(Material.COAL_ORE).setTitle("&a&lProtección de Carbón (9x9x9)")
				.addLore("§7Protege un área en forma de")
				.addLore("§7cubo de §b9x9x9")
				.addLore("")
				.addLore("§aPrecio: §6$1000")
				.build());
		
		s(12,new ItemBuilder(Material.IRON_ORE).setTitle("&a&lProtección de Hierro (19x19x19)")
				.addLore("§7Protege un área en forma de")
				.addLore("§7cubo de §b19x19x19")
				.addLore("")
				.addLore("§aPrecio: §6$4000")
				.build());
		
		s(13,new ItemBuilder(Material.GOLD_ORE).setTitle("&a&lProtección de Oro (29x29x29)")
				.addLore("§7Protege un área en forma de")
				.addLore("§7cubo de §b29x29x29")
				.addLore("")
				.addLore("§aPrecio: §6$10000")
				.build());
		
		s(14,new ItemBuilder(Material.DIAMOND_ORE).setTitle("&a&lProtección de Diamante (49x49x49)")
				.addLore("§7Protege un área en forma de")
				.addLore("§7cubo de §b49x49x49")
				.addLore("")
				.addLore("§aPrecio: §6$40000")
				.build());
		
		s(15,new ItemBuilder(Material.EMERALD_ORE).setTitle("&a&lProtección de Esmeralda (79x79x79)")
				.addLore("§7Protege un área en forma de")
				.addLore("§7cubo de §b79x79x79")
				.addLore("")
				.addLore("§aPrecio: §6$100000")
				.build());
		
		
		s(22,new ItemBuilder(Material.OAK_WALL_SIGN).setTitle("§a§lCartel de protección")
				.addLore("§7Protege tus cofres,puertas, etc.")
				.addLore("§7Este item te dará permiso para colocar 1 cartel")
				.addLore("§7Se pueden comprar infinitos carteles")
				.addLore("")
				.addLore("§aPrecio: §6$100")
				.build());
		
}
	@Override
	public void onClick(Player p, ItemStack stack, int slot) {
		
		SurviPlayer sp = hikaSurvival.getPlayer(p);
		
		if (slot==11) {
			if (sp.getMoney()>=1000) {
			ItemStack item = ProtectionStones.getProtectBlockFromAlias("carbon").createItem();
			p.getInventory().addItem(item);
			hikaSurvival.payToServer(p, 1000);
			p.sendMessage("§aHas comprado una §b§lProtección de Carbón");
		} else {
			p.sendMessage("§cNo tienes suficientes SurviCoins para comprar esto.");
			p.closeInventory();
		}
		} else if (slot==12) {
			if (sp.getMoney()>=4000) {
			ItemStack item = ProtectionStones.getProtectBlockFromAlias("hierro").createItem();
			p.getInventory().addItem(item);
			hikaSurvival.payToServer(p, 4000);
			p.sendMessage("§aHas comprado una §b§lProtección de Hierro");
		} else {
			p.sendMessage("§cNo tienes suficientes SurviCoins para comprar esto.");
			p.closeInventory();
		}
		} else if (slot==13) {
			if (sp.getMoney()>=10000) {
			ItemStack item = ProtectionStones.getProtectBlockFromAlias("oro").createItem();
			p.getInventory().addItem(item);
			p.sendMessage("§aHas comprado una §b§lProtección de Oro");
		} else {
			p.sendMessage("§cNo tienes suficientes SurviCoins para comprar esto.");
			p.closeInventory();
		}
		} else if (slot==14) {
			if (sp.getMoney()>=40000) {
			ItemStack item = ProtectionStones.getProtectBlockFromAlias("diamante").createItem();
			p.getInventory().addItem(item);
			p.sendMessage("§aHas comprado una §b§lProtección de Diamante");
		} else {
			p.sendMessage("§cNo tienes suficientes SurviCoins para comprar esto.");
			p.closeInventory();
		}
		} else if (slot==15) {
			if (sp.getMoney()>=100000) {
			ItemStack item = ProtectionStones.getProtectBlockFromAlias("esmeralda").createItem();
			p.getInventory().addItem(item);
			p.sendMessage("§aHas comprado una §b§lProtección de Esmeralda");
		} else {
			p.sendMessage("§cNo tienes suficientes SurviCoins para comprar esto.");
			p.closeInventory();
		}
		}
		
	}
	
}
