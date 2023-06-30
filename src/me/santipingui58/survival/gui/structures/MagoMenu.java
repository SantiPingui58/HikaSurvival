package me.santipingui58.survival.gui.structures;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.gui.GUIBuilder;
import me.santipingui58.survival.player.SurviAccess;
import me.santipingui58.survival.player.SurviPlayer;
import me.santipingui58.survival.utils.ItemBuilder;

public class MagoMenu extends GUIBuilder {
	
	
	HashMap<UUID,Integer> tempHomePrice = new HashMap<UUID,Integer>();
	
	public MagoMenu(HikaSurvival hikaSurvival,Player p) {
		super("§5§lMago",3,hikaSurvival);
		
		SurviPlayer sp = hikaSurvival.getPlayer(p);

		
		ItemBuilder nether = new ItemBuilder(Material.NETHERRACK).setTitle("§c§lAcceso a la Dimensión del Nether")
				.addLore("§7Permiso de un mago para ingresar ")
				.addLore("§7a la dimensión del end")
				.addLore("");
		
		if (sp.hasAccess(SurviAccess.NETHER)) {
			nether.addLore("§aYa tienes este acceso!");
		} else {
			nether.addLore("§aPrecio: §6$500");
		}
		
	s(10, nether.build());
		
			ItemBuilder end = new ItemBuilder(Material.END_STONE).setTitle("§e§lAcceso a la Dimensión del End")
					.addLore("§7Permiso de un mago para ingresar ")
					.addLore("§7a la dimensión del end")
					.addLore("");
			if (sp.hasAccess(SurviAccess.END)) {
				end.addLore("§aYa tienes este acceso!");
			} else {
				end.addLore("§aPrecio: §6$50");
			}
			
		s(12, end.build());
		

		int boughtHomes =	sp.getMaxHomes();
		ItemBuilder itemHome = new ItemBuilder(Material.OAK_DOOR).setTitle("§6§lSetHomes");
		int maxHomes = p.hasPermission("hika.sub") ? 7 : p.hasPermission("hika.vip") ? 5 : 4;
		
		if (boughtHomes<maxHomes) {
			//5000
			//10000
			//20000
			//40000
			//80000
			//160000
			//320000
			int precio = 0;
			switch(boughtHomes) {
			case 0: precio = 5000; break;
			case 1: precio = 10000; break;
			case 2: precio = 20000; break;
			case 3: precio = 40000; break;
			case 4: precio = 80000; break;
			case 5: precio = 160000; break;
			case 6: precio = 320000; break;
			}
			tempHomePrice.put(p.getUniqueId(), precio);
			itemHome.addLore("§7Compra la habilidad para setear");
			itemHome.addLore("§7un teleport en donde quieras.");
			itemHome.addLore("");
			itemHome.addLore("§aPrecio: §6§l$" + precio);
		} else {
			switch (maxHomes) {
			case 4: 
				itemHome.addLore("§cNo puedes comprar más SetHomes!");
				itemHome.addLore("§cNecesitas ser al menos §a§lVIP");
				break;
			case 5: 
				itemHome.addLore("§cNo puedes comprar más SetHomes!");
				itemHome.addLore("§cNecesitas ser al menos §3§lSUB");
				break;
			case 7: 
				itemHome.addLore("§cNo puedes comprar más SetHomes!");
				break;
			}
			
		}
		
		s(14,itemHome.build());
		
		
		
		ItemBuilder warps = new ItemBuilder(Material.ENDER_PEARL).setTitle("§5§lAcceso a Warps")
				.addLore("§7Permiso de un mago para teletransportarte")
				.addLore("");
				
		if (sp.hasAccess(SurviAccess.WARP)) {
			warps.addLore("§aYa tienes este acceso!");
		} else {
			warps.addLore("§aPrecio: §6$2000");
		}
		
		
		s(16, warps.build());
		
		buildInventory();
		
}
	@Override
	public void onClick(Player p, ItemStack stack, int slot) {
		
		SurviPlayer sp = hikaSurvival.getPlayer(p);
		
		if (slot==10) {
			if (!sp.hasAccess(SurviAccess.NETHER)) {
				if (sp.getMoney()>=500) {
					p.closeInventory();
					hikaSurvival.payToServer(p, 500);
					p.sendMessage("§aHas comprado " + stack.getItemMeta().getDisplayName() + "§a!");
					sp.addAccess(SurviAccess.NETHER);
				} else {
					p.sendMessage("§cNo tienes suficientes SurviCoins para comprar esto.");
					p.closeInventory();
				}
			}
		} else if (slot==12) {
			if (!sp.hasAccess(SurviAccess.END)) {
				if (sp.getMoney()>=50) {
					p.closeInventory();
					hikaSurvival.payToServer(p, 50);
					p.sendMessage("§aHas comprado " + stack.getItemMeta().getDisplayName() + "§a!");
					sp.addAccess(SurviAccess.END);
				} else {
					p.sendMessage("§cNo tienes suficientes SurviCoins para comprar esto.");
					p.closeInventory();
				}
			}
		} else if (slot==14) {
			
			int boughtHomes =	hikaSurvival.getEssentials().getUser(p).getHomes().size();
			int maxHomes = p.hasPermission("hika.sub") ? 7 : p.hasPermission("hika.vip") ? 5 : 4;
			
			if (boughtHomes<maxHomes) {
				if (sp.getMoney()>=this.tempHomePrice.get(p.getUniqueId())) {
					sp.addAccess(SurviAccess.HOME);
					hikaSurvival.payToServer(p, tempHomePrice.get(p.getUniqueId()));
					int homes = sp.getMaxHomes()+1;
					sp.setMaxHomes(homes);
					p.sendMessage("§aHas desbloqueado un nuevo Home! Ahora tienes: §b§l"+ homes);
					p.closeInventory();
				} else {
					p.sendMessage("§cNo tienes suficientes SurviCoins para comprar esto.");
					p.closeInventory();
				}
				
			} else {
				p.closeInventory();
				p.sendMessage("§cNo puedes comprar más SetHomes!");
			}
			
		} else if (slot==16) {
			if (!sp.hasAccess(SurviAccess.WARP)) {
				if (sp.getMoney()>=2000) {
					p.closeInventory();
					hikaSurvival.payToServer(p, 2000);
					p.sendMessage("§aHas comprado " + stack.getItemMeta().getDisplayName() + "§a!");
					sp.addAccess(SurviAccess.WARP);
				} else {
					p.sendMessage("§cNo tienes suficientes SurviCoins para comprar esto.");
					p.closeInventory();
				}
			}
		}
		
	}
	
}
