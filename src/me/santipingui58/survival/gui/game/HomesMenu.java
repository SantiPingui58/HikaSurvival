package me.santipingui58.survival.gui.game;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.game.home.HikaHome;
import me.santipingui58.survival.gui.GUIBuilder;
import me.santipingui58.survival.player.SurviPlayer;
import me.santipingui58.survival.utils.ItemBuilder;
import net.md_5.bungee.api.ChatColor;

public class HomesMenu extends GUIBuilder {

	
	public HomesMenu(HikaSurvival hikaSurvival,Player p) {
		super("§a§lLista de Homes",1,hikaSurvival);
		
		SurviPlayer sp = hikaSurvival.getPlayer(p);
		int  i = 0;
		
		for (HikaHome home : sp.getHomes()) {
		s(i,new ItemBuilder(Material.GRASS_BLOCK).setTitle("§b"+home.getName())
				.addLore("§aClick para teletransportarte")
				.addLore("§aPrecio: §6§l$" + home.getPrice(p)).build());
		i++;
		}
		
		buildInventory();
}
	@Override
	public void onClick(Player p, ItemStack stack, int slot) {
		
		if (stack!=null && !stack.getType().equals(Material.AIR)) {
		String homeName = ChatColor.stripColor(stack.getItemMeta().getDisplayName());
		SurviPlayer sp = hikaSurvival.getPlayer(p);
		HikaHome home = sp.getHome(homeName);
		double price = home.getPrice(p);
			if (sp.getMoney()>=price) {
				hikaSurvival.payToServer(p, (int) price);
				p.sendMessage("§6Teletransportandote...");
				p.teleport(home.getLocation());
				p.closeInventory();
			} else {
				p.sendMessage("§cNo tienes suficientes SurviCoins para hacer esto.");
				p.closeInventory();
			}
		}
		
	}
	
}
