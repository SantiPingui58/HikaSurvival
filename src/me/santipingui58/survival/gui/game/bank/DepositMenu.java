package me.santipingui58.survival.gui.game.bank;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.game.structures.StructureType;
import me.santipingui58.survival.gui.GUIBuilder;
import me.santipingui58.survival.player.SurviPlayer;
import me.santipingui58.survival.utils.ItemBuilder;

public class DepositMenu extends GUIBuilder {

	
	public DepositMenu(HikaSurvival hikaSurvival) {
		super("§6§lDepositar SurviCoins",3,hikaSurvival);
		
		s(10, new ItemBuilder(Material.CHEST).setTitle("§a§lDepositar 10%")
				.addLore("§7Deposita el §b10% §7de")
				.addLore("§7tus §6SurviCoins §7en el banco")
				.setAmount(8)
				.build());
		
		s(12, new ItemBuilder(Material.CHEST).setTitle("§a§lDepositar 25%")
				.addLore("§7Deposita el §b25% §7de")
				.addLore("§7tus §6SurviCoins §7en el banco")
				.setAmount(16)
				.build());
		
		s(14, new ItemBuilder(Material.CHEST).setTitle("§a§lDepositar 50%")
				.addLore("§7Deposita el §b50% §7de")
				.addLore("§7tus §6SurviCoins §7en el banco")
				.setAmount(32)
				.build());
		
		s(16, new ItemBuilder(Material.CHEST).setTitle("§a§lDepositar 100%")
				.addLore("§7Deposita el §b100% §7de")
				.addLore("§7tus §6SurviCoins §7en el banco")
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
			int deposit = (int) (slot == 10 ? sp.getMoney()*0.1 : slot == 12 ? sp.getMoney()*0.25 : slot == 14 ? sp.getMoney()*0.5 : slot == 16 ? sp.getMoney() : 0);
			
			hikaSurvival.getBankManager().addMoney(deposit);
			
			int bankMoney = sp.getBankMoney();
			sp.setBankMoney(bankMoney+deposit);
			
			hikaSurvival.getEconomy().withdrawPlayer(p, deposit);
			
			p.closeInventory();
			p.sendMessage("§aHas depositado §6§l" + deposit + " SurviCoins §aen el banco!");
		}
		}
		
	}
	
}
