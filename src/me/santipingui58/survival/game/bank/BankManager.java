package me.santipingui58.survival.game.bank;

import java.util.LinkedHashMap;
import java.util.UUID;

import org.bukkit.scheduler.BukkitRunnable;

import me.santipingui58.survival.HikaSurvival;

public class BankManager {

	private HikaSurvival hikaSurvival;
	public BankManager(HikaSurvival hikaSurvival) {
		this.hikaSurvival = hikaSurvival;
	}
	
	private LinkedHashMap<UUID,Integer> bankTop  = new LinkedHashMap<UUID,Integer>();
	
	
	public LinkedHashMap<UUID,Integer> getBankTop() {
		return  this.bankTop;
	}
	
	
	public void setMoney(int i) {

		hikaSurvival.getConfig().set("bank", i);
		hikaSurvival.saveConfig();
	}
	
	
	public void addMoney(int amount) {
		int money = 0;
		if (hikaSurvival.getConfig().contains("bank")) money = hikaSurvival.getConfig().getInt("bank");
		money = money + amount;
		setMoney(money);
		
	}
	
	public int getBankMoney() {
		return hikaSurvival.getConfig().contains("bank") ? hikaSurvival.getConfig().getInt("bank") : 0;
	}


	public void interest() {
		new BukkitRunnable() {
			public void run() {
				hikaSurvival.getSQLManager().interest();
				setMoney(hikaSurvival.getSQLManager().getBankMoney());
			}
		}.runTaskAsynchronously(hikaSurvival);
		
	}


	public void setBankTop(LinkedHashMap<UUID, Integer> bankTop2) {
		this.bankTop = bankTop2;
	}


	
	
	
}
