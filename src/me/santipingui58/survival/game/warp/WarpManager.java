package me.santipingui58.survival.game.warp;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.utils.Utils;

public class WarpManager {

	
	private HikaSurvival hikaSurvival;
	private boolean loadedCorrectly = false;
	public WarpManager(HikaSurvival hikaSurvival) {
		this.hikaSurvival = hikaSurvival;
		loadWarps();
	}
	
	
	private Set<HikaWarp> warps = new HashSet<HikaWarp>();
	
	
	public Set<HikaWarp> getWarps() {
		return this.warps;
	}
	
	
	public HikaWarp getWarp(String name) {
		for (HikaWarp warp : this.warps) {
			if (warp.getName().equalsIgnoreCase(name)) {
				return warp;
			}
		}
		return null;
	}
	
	
	public void createWarp(String name,Location loc, Material item) {
		HikaWarp warp = new HikaWarp(name, loc, item);
		this.warps.add(warp);
		saveWarps();
	}
	
	
	
	
	public void removeWarp(String name) {
		HikaWarp warp = getWarp(name);
		this.warps.remove(warp);
		this.hikaSurvival.data.getConfig().set("warps."+warp.getName(), null);
		this.hikaSurvival.data.saveConfig();
	}
	
	public void saveWarps() {
		if (!loadedCorrectly) return;
		for (HikaWarp warp : this.warps) {
			this.hikaSurvival.data.getConfig().set("warps."+warp.getName()+".location", Utils.setLoc(warp.getLocation(), true));
			this.hikaSurvival.data.getConfig().set("warps."+warp.getName()+".item", warp.getItem().toString());
		}
		this.hikaSurvival.data.saveConfig();
	}
	
	
	public void loadWarps() {
		FileConfiguration config = this.hikaSurvival.data.getConfig();
		loadedCorrectly = true;
		
		if (config.contains("warps")) {
			try {
		for (String s : config.getConfigurationSection("warps").getKeys(false)) {
			Location loc = Utils.getLoc(config.getString("warps."+s+".location"), true);
			Material item = Material.valueOf(config.getString("warps."+s+".item"));
			HikaWarp warp = new HikaWarp(s,loc,item);
			this.warps.add(warp);
		}
			} catch(Exception ex) {
				ex.printStackTrace();
				loadedCorrectly = false;
			}
		}
	}


	public int getWarpPrice(Player p, HikaWarp warp) {
		double price = 0;
		int w = worlds(p.getLocation(),warp.getLocation());
		if (w==0) {
			double d = p.getLocation().distanceSquared(warp.getLocation());
			price =   (1.0/300000.0*d);
		} else if (w==2) {
				Location espejo = new Location(warp.getLocation().getWorld(),p.getLocation().getX()/64,p.getLocation().getY()/64,
						p.getLocation().getZ()/64);
				int d = (int)warp.getLocation().distanceSquared(espejo);
				price =   (1.0/300000.0*d);
				price = price*4;
				
				/*
				Location espejo = new Location(location.getWorld(),p.getLocation().getX()/64,p.getLocation().getY()/64,
						p.getLocation().getZ()/64);
				int d = (int) location.distanceSquared(espejo);
				price =   (1.0/200000.0*d);
				price = price*4;
				*/
		} else if (w==1) {
			Location espejo = new Location(warp.getLocation().getWorld(),p.getLocation().getX()/8,p.getLocation().getY()/8,
					p.getLocation().getZ()/8);
			int d = (int)warp.getLocation().distanceSquared(espejo);
			price =   (1.0/300000.0*d);
			price = price*2;
			
			/*
			Location espejo = new Location(location.getWorld(),p.getLocation().getX()/8,p.getLocation().getY()/8,
					p.getLocation().getZ()/8);
			int d = (int) location.distanceSquared(espejo);
			price =   (1.0/200000.0*d);
			price = price*2;
			*/
		}
		
		if (price<1) price=1;
		return (int) price;
	}
	
	
	private int worlds(Location l1, Location l2) {
		String world1 = l1.getWorld().getName();
		String world2 = l2.getWorld().getName();
		if (world1.equalsIgnoreCase("world") && world2.equalsIgnoreCase("world_the_end") 
				||
				world1.equalsIgnoreCase("world_the_end") && world2.equalsIgnoreCase("world") ) {
			return 2;
		} else if (world1.equalsIgnoreCase(world2)) {
			return 0;
		} else {
			return 1;
		}
		
	}


	
	
}
