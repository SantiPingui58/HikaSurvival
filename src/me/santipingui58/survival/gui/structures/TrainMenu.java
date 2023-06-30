package me.santipingui58.survival.gui.structures;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.bergerkiller.bukkit.tc.tickets.Ticket;
import com.bergerkiller.bukkit.tc.tickets.TicketStore;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.gui.GUIBuilder;
import me.santipingui58.survival.player.SurviPlayer;
import me.santipingui58.survival.utils.ItemBuilder;

public class TrainMenu extends GUIBuilder {
	
	
	
	public TrainMenu(HikaSurvival hikaSurvival) {
		super("§a§lCompra tu Ticket",1,hikaSurvival);
		
		
		s(0, new ItemBuilder(Material.NAME_TAG).setTitle("§a§lTren Cobblestone")
				.addLore("§aPrecio: §6§l5 SurviCoins")
				.addLore("")
				.addLore("§bEstaciones:")
				//.addLore("§7- Hika")
				.addLore("§7- Villa Atrox")
				.addLore("§7- Montes")
				.addLore("§7- Zona Roja")
				.addLore("§7- Ciudad Centro")
				.addLore("§7- Pueblo Tostada")
				.addLore("§7- Aldea Cuchiturrino")
				//.addLore("§7- Rodrario")
				//.addLore("§7- Punta Mogotes")
				//.addLore("§7- End")
				.build());
		
		
		buildInventory();
}
	@Override
	public void onClick(Player p, ItemStack stack, int slot) {
		SurviPlayer sp = hikaSurvival.getPlayer(p);
		if (slot==0) {
			if (sp.getMoney()>=5) {
				hikaSurvival.payToServer(p, 5);
		    Ticket tcTicket = TicketStore.getTicket("Cobblestone");
		    p.getInventory().addItem(tcTicket.createItem(p));
		    p.closeInventory();
		    p.sendMessage("§aHas comprado un ticket para el §b§lTren Cobblestone");
		} else {
			p.sendMessage("§cNo tienes suficientes SurviCoins.");
		}
		}
}
	
}
