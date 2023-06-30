package me.santipingui58.survival.gui.game;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.game.warp.HikaWarp;
import me.santipingui58.survival.gui.GUIBuilder;
import me.santipingui58.survival.player.SurviPlayer;
import me.santipingui58.survival.utils.ItemBuilder;
import net.md_5.bungee.api.ChatColor;

public class WarpMenu extends GUIBuilder {

	
	public WarpMenu(HikaSurvival hikaSurvival,Player p) {
		super("§d§lWarps",6,hikaSurvival);
		int i = 0;
		for (HikaWarp warp : hikaSurvival.getWarpManager().getWarps()) {
			int price = hikaSurvival.getWarpManager().getWarpPrice(p,warp);
			warp.getTempPrices().put(p.getUniqueId(), price);
			boolean sameWorld =  warp.getLocation().getWorld().equals(p.getWorld());
			Material item = sameWorld ?  warp.getItem() : Material.BARRIER;
			String distancia = sameWorld? "§b"+
					(int) warp.getLocation().distance(p.getLocation()) + " bloques" : "§cDistinta dimensión";
		s(i,new ItemBuilder(item).setTitle("§aWarp a §5§l"+ warp.getName())
				.addLore("§8"+warp.getName())
				.addLore("§7Distancia: " + distancia)
				.addLore("§6Precio: §6§l$" + price)
				.build());
		i++;
		}
		
		buildInventory();
}
	@Override
	public void onClick(Player p, ItemStack stack, int slot) {
		
		if (stack!=null && !stack.getType().equals(Material.AIR)) {
			if (stack.getType().equals(Material.BARRIER)) {
				p.closeInventory();
				p.sendMessage("§cNo puedes warpear a otra dimensión.");
				return;
			}
			
		String warpName = ChatColor.stripColor(stack.getItemMeta().getLore().get(0));
		
		HikaWarp warp = hikaSurvival.getWarpManager().getWarp(warpName);
		int price = warp.getTempPrices().get(p.getUniqueId());
			SurviPlayer sp = hikaSurvival.getPlayer(p);
			if (sp.getMoney()>=price) {
				hikaSurvival.payToServer(p, price);
				p.sendMessage("§6Teletransportandote...");
				p.teleport(warp.getLocation());
				p.closeInventory();
			} else {
				p.sendMessage("§cNo tienes suficientes SurviCoins para hacer esto.");
				p.closeInventory();
			}
		}
		
	}
	
}
