package me.santipingui58.survival.game.structures;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.utils.Utils;

public class StructureManager {

	
	private HikaSurvival hikaSurvival;
	private boolean loadedCorrectly = false;
	
	Set<Structure> structures = new HashSet<Structure>();
	
	public StructureManager(HikaSurvival hikaSurvival) {
		this.hikaSurvival = hikaSurvival;
		
		new BukkitRunnable() {
			public void run() {
		loadStructures();
			}
		}.runTaskLater(hikaSurvival, 20L);
	}
	
	public Set<Structure> getStructures() {
		return this.structures;
	}
	
	
	public void createStructure(String name,Location location,StructureType type) {
		Structure structure = new Structure(UUID.randomUUID(),name, location,type, hikaSurvival);
		this.structures.add(structure);
		saveStructures();
	}
	
	
	public void loadStructures() {
		FileConfiguration config = hikaSurvival.data.getConfig();
		loadedCorrectly = true;
		if (config.contains("structures")) {
		try {
	
		for (String s : config.getConfigurationSection("structures").getKeys(false)) {
			UUID uuid = UUID.fromString(s);
			Location location = Utils.getLoc(config.getString("structures."+s+".location"),true);
			String name = config.getString("structures."+s+".name");
			StructureType type = StructureType.valueOf(config.getString("structures."+s+".type"));
			Structure structure = new Structure(uuid,name, location, type, hikaSurvival);
			this.structures.add(structure);
		}
		} 	catch(Exception ex) {
			ex.printStackTrace();
			loadedCorrectly = false;
		}
 	}
	}
	
	
	public void saveStructures() {
		
		
		
		if (loadedCorrectly) {
		FileConfiguration config = hikaSurvival.data.getConfig();
		config.set("structures", null);
		for (Structure s : this.structures) {
			s.getNPC().despawn();
			s.getNPC().destroy();
			String u = s.getUuid().toString();
			config.set("structures."+u+".location", Utils.setLoc(s.getLocation(),true));
			config.set("structures."+u+".name", s.getName());
			config.set("structures."+u+".type", s.getType().toString());
		}
		hikaSurvival.data.saveConfig();
		}
		
	}
	
	
	public Structure getNearestStructure(Location l,StructureType type) {
		Structure nearestStructure = null;
		
		for (Structure structure : this.structures) {
			if (structure.getNPC().getStoredLocation().getWorld().equals(l.getWorld())) {
			if (structure.getType().equals(type)) {
			if (nearestStructure==null) {
				nearestStructure = structure;
			} else {
				if (nearestStructure.getNPC().getStoredLocation().distanceSquared(l) < structure.getNPC().getStoredLocation().distanceSquared(l)) {
					nearestStructure = structure;
				}
			}	
			}
			}
		}
		
		return nearestStructure;
	}

	public void noPermission(Player p,StructureType type) {
		Structure structure =	getNearestStructure(p.getLocation(), type);
		p.sendMessage("§cNo tienes permiso para hacer esto!");
		if (structure!=null) {
			int x = structure.getLocation().getBlockX();
			int z = structure.getLocation().getBlockZ();
			p.sendMessage("§bDebes ir a "+type.getName()+" §bpara que puedas hacerlo! (Más cercano se encuentra en: §5X"+x +" Z"+z );
		} else {
			p.sendMessage("§cActualmente no hay "+type.getName()+" §cen el Servidor." );
		}
		
	}


}
