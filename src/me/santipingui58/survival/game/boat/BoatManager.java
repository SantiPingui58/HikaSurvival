package me.santipingui58.survival.game.boat;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Boat;
import org.bukkit.scheduler.BukkitRunnable;

import me.santipingui58.survival.HikaSurvival;


public class BoatManager {

	
	public static int MAX_SPEED_MULTIPLIER = 2;
	 public Set<FastBoat> fastboats = new HashSet<>();
	
	 private HikaSurvival hikaSurvival;
	 public BoatManager(HikaSurvival hikaSurvival) {
		 this.hikaSurvival = hikaSurvival;
		 tick();
	 }


	    public Set<FastBoat> getFastBoats() {
	    	return this.fastboats;
	    }
	    
	    
	    public void createFastBoat(UUID uuid,Boat boat) {
	    	FastBoat fastBoat = new FastBoat(uuid,boat);
	    	this.fastboats.add(fastBoat);
	    }
	    
	    
	    public void loadFastBoat(Boat boat) {
	    	if (boat.hasMetadata("fastboat")) {
	    		UUID uuid = UUID.fromString(boat.getMetadata("fastboat").get(0).asString());
	    	createFastBoat(uuid, boat);
	    	}
	    }
	    
	    
	    private void tick() {
	    	new BukkitRunnable() {
	    		public void run() {
	    			for (FastBoat fb : fastboats) fb.tick();
	    		}
	    		
	    	}.runTaskTimer(hikaSurvival, 0L, 1L);
	    }


		public FastBoat getFastBoat(Boat boat) {
			for (FastBoat fastBoat : this.fastboats) {
				if (fastBoat.getBoat().equals(boat)) {
					return fastBoat;
				}
			}
			return null;
		}
}
