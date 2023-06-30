package me.santipingui58.survival.game.home;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;


public class HikaHome {

	
	private UUID owner;
	private Location location;
	private String name;
	private int id;
	
	public HikaHome(UUID owner, String name,Location location) {
		this.owner = owner;
		this.name = name;
		this.location = location;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public UUID getOwner() {
		return this.owner;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public String getName() {
		return this.name;
	}
	
	
	public int getPrice(Player p) {
			double price = 0;
			int w = worlds(p.getLocation(),location);
			if (w==0) {
				int d = (int) p.getLocation().distanceSquared(location);
				price =   (1.0/200000.0*d);
			} else if (w==2) {
					Location espejo = new Location(location.getWorld(),p.getLocation().getX()/64,p.getLocation().getY()/64,
							p.getLocation().getZ()/64);
					int d = (int) location.distanceSquared(espejo);
					price =   (1.0/200000.0*d);
					price = price*4;
					
			} else if (w==1) {
				Location espejo = new Location(location.getWorld(),p.getLocation().getX()/8,p.getLocation().getY()/8,
						p.getLocation().getZ()/8);
				int d = (int) location.distanceSquared(espejo);
				price =   (1.0/200000.0*d);
				price = price*2;
			}
			
			if (price<1) price=1;
			return (int)price;
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
