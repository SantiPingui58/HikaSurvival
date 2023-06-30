package me.santipingui58.survival.gui.structures;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.gui.GUIBuilder;
import me.santipingui58.survival.player.SurviPlayer;
import me.santipingui58.survival.utils.ItemBuilder;


public class HospitalMenu extends GUIBuilder {
	
	
	
	public HospitalMenu(HikaSurvival hikaSurvival,Player p) {
		super("§§lHospital",3,hikaSurvival);		
		
		SurviPlayer sp = hikaSurvival.getPlayer(p);
		
		
		
		ItemBuilder _1 = new ItemBuilder(Material.RED_DYE)
		.setTitle("§a§lSeguro de Muerte I")
		.addLore("");
		if (sp.getHospitalLevel()==1) {
		_1.addLore("§bPrecio: §6§l$500")
		.addLore("§bCuota diaria: §6§l$20")
		.addLore("")
		.addLore("§aBeneficios al morir:")
		.addLore("§7- Coordenadas de Muerte")
		.addLore("§7- Salva el 10% de tu EXP")
		.addLore("§7- Salva el 15% de tus SurviCoins");
		} else {
			_1.addLore("§bActualmente tienes este plan!");
		}
		
		s(9, _1.build());
		
		
		ItemBuilder _2 = new ItemBuilder(Material.RED_DYE)
				.setTitle("§a§lSeguro de Muerte II")
				.addLore("");
				if (sp.getHospitalLevel()==2) {
				_2.addLore("§bPrecio: §6§l$1500")
				.addLore("§bCuota diaria: §6§l$50")
				.addLore("")
				.addLore("§aBeneficios al morir:")
				.addLore("§7- Coordenadas de Muerte")
				.addLore("§7- Salva el 10% de tus Items")
				.addLore("§7- Salva el 15% de tu EXP")
				.addLore("§7- Salva el 20% de tus SurviCoins");
				} else {
					_2.addLore("§bActualmente tienes este plan!");
				}
				
				s(11, _2.build());
				
				ItemBuilder _3 = new ItemBuilder(Material.RED_DYE)
						.setTitle("§a§lSeguro de Muerte III")
						.addLore("");
						if (sp.getHospitalLevel()==2) {
						_3.addLore("§bPrecio: §6§l$2500")
						.addLore("§bCuota diaria: §6§l$75")
						.addLore("")
						.addLore("§aBeneficios al morir:")
						.addLore("§7- Coordenadas de Muerte")
						.addLore("§7- Salva el 20% de tus Items")
						.addLore("§7- Salva el 25% de tu EXP")
						.addLore("§7- Salva el 30% de tus SurviCoins");
						} else {
							_3.addLore("§bActualmente tienes este plan!");
						}
						
						s(13, _3.build());
						
						ItemBuilder _4 = new ItemBuilder(Material.RED_DYE)
								.setTitle("§a§lSeguro de Muerte IV")
								.addLore("");
								if (sp.getHospitalLevel()==2) {
								_4.addLore("§bPrecio: §6§l$5000")
								.addLore("§bCuota diaria: §6§l$100")
								.addLore("")
								.addLore("§aBeneficios al morir:")
								.addLore("§7- Coordenadas de Muerte")
								.addLore("§7- Cofre con items al morir")
								.addLore("§7- Salva el 30% de tus Items")
								.addLore("§7- Salva el 35% de tu EXP")
								.addLore("§7- Salva el 40% de tus SurviCoins");
								} else {
									_4.addLore("§bActualmente tienes este plan!");
								}
								
								s(13, _4.build());
								
								ItemBuilder _5 = new ItemBuilder(Material.RED_DYE)
										.setTitle("§a§lSeguro de Muerte III")
										.addLore("");
										if (sp.getHospitalLevel()==2) {
										_5.addLore("§bPrecio: §6§l$10000")
										.addLore("§bCuota diaria: §6§l$200")
										.addLore("")
										.addLore("§aBeneficios al morir:")
										.addLore("§7- Coordenadas de Muerte")
										.addLore("§7- Cofre con items al morir")
										.addLore("§7- Comando /back")
										.addLore("§7- Salva el 50%  de  tus Items")
										.addLore("§7- Salva el 55%  de  tu EXP")
										.addLore("§7- Salva el 60%  de  tus SurviCoins");

										} else {
											_5.addLore("§bActualmente tienes este plan!");
										}
										
										s(13, _5.build());
		
}
	@Override
	public void onClick(Player p, ItemStack stack, int slot) {
	SurviPlayer  sp = hikaSurvival.getPlayer(p);
	sp.getUuid();
	}
	
}
