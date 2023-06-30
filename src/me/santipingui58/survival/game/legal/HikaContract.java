package me.santipingui58.survival.game.legal;

import java.util.UUID;

public class HikaContract {

	
	private UUID uuid;
	private UUID player1;
	private UUID player2;
	private int days;
	private int interest;
	private String description;
	private int initialMoney;
	
	
	public HikaContract(UUID uuid) {
		this.uuid = uuid;
	}


	public UUID getUuid() {
		return uuid;
	}

	public UUID getPlayer1() {
		return player1;
	}


	public UUID getPlayer2() {
		return player2;
	}


	public int getDays() {
		return days;
	}


	public void setDays(int days) {
		this.days = days;
	}


	public int getInterest() {
		return interest;
	}


	public void setInterest(int interest) {
		this.interest = interest;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getInitialMoney() {
		return initialMoney;
	}


	public void setInitialMoney(int initialMoney) {
		this.initialMoney = initialMoney;
	}
	
}
