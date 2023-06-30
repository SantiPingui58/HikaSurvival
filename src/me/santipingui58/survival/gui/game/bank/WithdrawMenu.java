package me.santipingui58.survival.gui.game.bank;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.game.structures.StructureType;
import me.santipingui58.survival.gui.GUIBuilder;
import me.santipingui58.survival.player.SurviPlayer;
import me.santipingui58.survival.utils.ItemBuilder;

public class WithdrawMenu extends GUIBuilder {

	
	public WithdrawMenu(HikaSurvival hikaSurvival) {
		super("§6§lRetirar SurviCoins",3,hikaSurvival);
		
		s(10, new ItemBuilder(Material.CHEST).setTitle("§a§lRetirar 10%")
				.addLore("§7Retira el §b10% §7de")
				.addLore("§7tus §6SurviCoins §7del banco")
				.setAmount(8)
				.build());
		
		s(12, new ItemBuilder(Material.CHEST).setTitle("§a§lRetirar 25%")
				.addLore("§7Retira el §b25% §7de")
				.addLore("§7tus §6SurviCoins §7del banco")
				.setAmount(16)
				.build());
		
		s(14, new ItemBuilder(Material.CHEST).setTitle("§a§lRetirar 50%")
				.addLore("§7Retira el §b50% §7de")
				.addLore("§7tus §6SurviCoins §7del banco")
				.setAmount(32)
				.build());
		
		s(16, new ItemBuilder(Material.CHEST).setTitle("§a§lRetirar 100%")
				.addLore("§7Retira el §b100% §7de")
				.addLore("§7tus §6SurviCoins §7del banco")
				.setAmount(64)
				.build());
		
		s(26,new ItemBuilder(Material.ARROW).setTitle("§cVolver").build());
		
		buildInventory();
}
	@Override
	public void onClick(Player p, ItemStack stack, int slot) {
		if (stack!=null) {
		
		if (slot==26) {
			StructureType.BANK.getGUI(hikaSurvival, p).o(p);
		} else {
			SurviPlayer sp = hikaSurvival.getPlayer(p);
			int withdraw = (int) (slot == 10 ? sp.getBankMoney()*0.1 : slot == 12 ? sp.getBankMoney()*0.25 : slot == 14 ? sp.getBankMoney()*0.5 : slot == 16 ? sp.getBankMoney() : 0);
			
			int playerBankMoney = sp.getBankMoney();
			sp.setBankMoney(playerBankMoney-withdraw);
			
			hikaSurvival.getEconomy().depositPlayer(p, withdraw);
			
			int bankMoney = hikaSurvival.getBankManager().getBankMoney();
			hikaSurvival.getBankManager().setMoney(bankMoney-withdraw);
			p.closeInventory();
			p.sendMessage("§aHas retirado §6§l" + withdraw + " SurviCoins §aen el banco!");
		}
		}
		
	}
	
}
