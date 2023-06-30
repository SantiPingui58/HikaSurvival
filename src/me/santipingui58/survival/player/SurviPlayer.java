package me.santipingui58.survival.player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.game.home.HikaHome;
import me.santipingui58.survival.game.structures.Structure;

public class SurviPlayer {

	
	
	private HikaSurvival hikaSurvival;
	private int id;
	private UUID uuid;
	private String twitchId;
	private int onlineTime;
	private int signsAmount;
	private int maxHomes;
	private int bankMoney;
	private int hospitalLevel;
	
	private Set<HikaHome> homes = new HashSet<HikaHome>();
	
	private Set<Structure> sendingWelcomeMsg = new HashSet<Structure>();
	
	private Set<SurviAccess> access = new HashSet<SurviAccess>();
	
	public SurviPlayer(HikaSurvival hikaSurvival,UUID uuid) {
		this.uuid = uuid;
		this.hikaSurvival = hikaSurvival;
	}
	
	public int getBankMoney() {
		return this.bankMoney;
	}
	
	public void setBankMoney(int i) {
		this.bankMoney = i;
		if (hikaSurvival.getBankManager().getBankTop().containsKey(uuid)) 
			hikaSurvival.getBankManager().getBankTop().put(uuid, i);
	}
	
	
	public int getId() {
		return this.id;
	}
 	
	public void setId(int id) {
		this.id = id;
	}
	
	public void addSignsAmount() {
		this.signsAmount++;
	}
	
	public void removeSignsAmount() {
		this.signsAmount--;
	}
	
	public int getSignsAmount() {
		return this.signsAmount;
	}
	
	public void setSignsAmount(int i ) {
		 this.signsAmount = i;
	}
	
	public Set<Structure> getSendingWelcomeMsg() {
		return this.sendingWelcomeMsg;
	}

	public int getOnlineTime()  {
		return this.onlineTime;
	}
	
	public String getTwitchId() {
		return this.twitchId;
	}
	
	public void setTwitchId(String twitchId) {
		this.twitchId = twitchId;
	}
	
	
	public Set<SurviAccess> getAccess() {
		return this.access;
	}
	
	
	public boolean hasAccess(SurviAccess acces) {
		return this.access.contains(acces);
	}
	
	public void addAccess(SurviAccess access) {
		this.access.add(access);
		
		SurviPlayer sp = this;
		
		new BukkitRunnable() {
			public void run() {
				hikaSurvival.getSQLManager().saveData(sp,false);
			}
		}.runTaskAsynchronously(hikaSurvival);
	}
	
	public UUID getUuid() {
		return this.uuid;
	}

	public double getMoney() {
		return hikaSurvival.getEconomy().getBalance(getPlayer());
	}
	
	public OfflinePlayer getOfflinePlayer() {
		return Bukkit.getOfflinePlayer(uuid);
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(uuid);
	}

	public void addOnlineTime() {
		this.onlineTime++;
	}

	public void setOnlineTime(int i) {
		this.onlineTime = 0;
		
	}
	
	public Set<HikaHome> getHomes() {
		return this.homes;
	}
	
	public int getMaxHomes() {
		return this.maxHomes;
	}
	
	
	public HikaHome getHome(String name) {
		for (HikaHome home : this.homes) {
			if (home.getName().equalsIgnoreCase(name)) {
				return home;
			}
		}
		return null;
	}

	public void setMaxHomes(int maxhomes2) {
	this.maxHomes = maxhomes2;
	}
	
	public void setHomes(Set<HikaHome> homes) {
		this.homes = homes;
	}
	
	public int getHospitalLevel() {
		return this.hospitalLevel;
	}
	
	public void setHospitalLevel(int i) {
		this.hospitalLevel = i;
	}
	
}
