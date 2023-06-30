package me.santipingui58.survival.gui.structures;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.gui.GUIBuilder;
import me.santipingui58.survival.gui.game.bank.DepositMenu;
import me.santipingui58.survival.gui.game.bank.WithdrawMenu;
import me.santipingui58.survival.player.SurviPlayer;
import me.santipingui58.survival.utils.ItemBuilder;

public class BancoMenu extends GUIBuilder {
	
	
	
	public BancoMenu(HikaSurvival hikaSurvival,Player p) {
		super("§a§lBanco",3,hikaSurvival);		
		SurviPlayer sp = hikaSurvival.getPlayer(p);
		double interes = p.hasPermission("hika.vip") ? 0.0175 : 0.01;
		double inter = interes*100;
		double dailyInteres =  (interes*sp.getBankMoney());
		int di = (int) dailyInteres;
		s(4,new ItemBuilder(Material.GOLD_BLOCK).setTitle("§a§lBanco de §b" + p.getName())
				.addLore("§7Saldo: §6§l$" + sp.getBankMoney() + " SurviCoins")
				.addLore("§7Interes diario: §b" + String.format("%.2f", inter) + "%")
				.addLore("§7Actualmente ganas: §6§l$" + di +" SurviCoins §7al dia")
				.build());
		
		ItemBuilder depositar = new ItemBuilder(Material.CHEST).setTitle("§6§lDepositar SurviCoins")
				.addLore("§7Depositar SurviCoins en el banco")
				.addLore("");
	s(11, depositar.build());
		
			ItemBuilder retirar = new ItemBuilder(Material.DISPENSER).setTitle("§6§lRetirar SurviCoins")
					.addLore("§7Retira tus SurviCoins del banco")
					.addLore("");
		s(13, retirar.build());
		

		s(15, new ItemBuilder(Material.EMERALD).setTitle("§a§lSolicitar Prestamo").addLore("§cProximamente").build());
		buildInventory();
		
}
	@Override
	public void onClick(Player p, ItemStack stack, int slot) {
		if (slot==11) {
			new DepositMenu(hikaSurvival).o(p);
		} else if (slot==13) {
			new WithdrawMenu(hikaSurvival).o(p);
		} else if (slot==15) {
			p.closeInventory();
			p.sendMessage("§cProximamente");
		} 
		
	}
	
}
