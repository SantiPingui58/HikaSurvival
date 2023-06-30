package me.santipingui58.survival.game.warp;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;

public class HikaWarp {

	private String name;
	private Location location;
	private Material item;
	
	private HashMap<UUID,Integer> tempPrices = new HashMap<UUID,Integer>();
	
	public HikaWarp(String name, Location location, Material item) {
		this.name = name;
		this.location = location;
		this.item = item;
	}
	
	
	public String getName() {
		return this.name;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public Material getItem() {
		return this.item;
	}
	
	
	public HashMap<UUID,Integer> getTempPrices() {
		return this.tempPrices;
	}
	
}
