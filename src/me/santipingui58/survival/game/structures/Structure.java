package me.santipingui58.survival.game.structures;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.gui.GUIBuilder;
import me.santipingui58.survival.player.SurviPlayer;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Structure {

	private UUID uuid;
	private NPC npc;
	private StructureType type;
	private HikaSurvival hikaSurvival;
	private String name;
	private Location location;
	
	
	public Structure(UUID uuid,String name,Location location,StructureType type,HikaSurvival hikaSurvival) { 
		this.uuid = uuid;
		this.hikaSurvival = hikaSurvival;
		this.name = name;
		this.location = location;
		getNPC(location);
		this.type = type;
	}
	
	private void getNPC(Location l) {
		 CitizensAPI.getNPCRegistry().forEach(npc2 -> {
			 if (npc2.getStoredLocation().distanceSquared(l)<=1) {
				 npc = npc2;
				 return;
			 }
		 } );
			
		 if (npc==null) {
			npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.VILLAGER, name); 
			npc.spawn(location);
		 }
		
	}
	
	public GUIBuilder getMenu(Player p) {
		return this.type.getGUI(hikaSurvival, p);
	}
	
	
	public String getName() {
		return this.name;
	}
	
	public UUID getUuid() {
		return this.uuid;
	}
	
	
	public StructureType getType() {
		return this.type;
	}
	
	
	public Location getLocation() {
		return this.location;
	}
	
	public NPC getNPC() {
		return this.npc;
	}

	@SuppressWarnings("deprecation")
	public void sendWelcomeMessage(SurviPlayer sp) {
		
		
		Player p = sp.getPlayer();
		
		Structure structure = this;
		new BukkitRunnable() {
			int i = 0;
			public void run() {	
				
				p.sendMessage("");
				p.sendMessage("");
				p.sendMessage("");
				p.sendMessage("");
				p.sendMessage("");
				p.sendMessage("");
				p.sendMessage("");
				if (i<type.getWelcomeMessages().length) {
					String msg = type.getWelcomeMessages()[i];
					p.sendMessage(npc.getName()+"§8:" + msg);
					i++;
				} else {
					p.sendMessage("");
					TextComponent msg1 = new TextComponent("[PAGAR ACCESO ($"+type.getPrice()+")]");
					msg1.setColor(net.md_5.bungee.api.ChatColor.GREEN );
					msg1.setBold(true);
					msg1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/hover " + type.toString()));		
					msg1.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aComprar el acceso a " + type.getName()).create()));		
					p.spigot().sendMessage(msg1);
					p.sendMessage("§8§oSi eres de Bedrock, pon el comando /hover " + type.toString().toLowerCase());
					sp.getSendingWelcomeMsg().remove(structure);
					cancel();
				}
			}
		}.runTaskTimer(hikaSurvival, 0L, 20*5);
		
	
		
	}
	
	
}

