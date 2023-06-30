package me.santipingui58.survival.listener;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.kingdoms.constants.land.location.SimpleChunkLocation;
import org.kingdoms.events.invasion.KingdomPreInvadeEvent;

import de.Keyle.MyPet.api.event.MyPetCreateEvent;
import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.game.boat.FastBoat;
import me.santipingui58.survival.game.structures.Structure;
import me.santipingui58.survival.game.structures.StructureType;
import me.santipingui58.survival.player.SurviAccess;
import me.santipingui58.survival.player.SurviPlayer;
import me.santipingui58.survival.utils.Utils;
import nl.rutgerkok.blocklocker.BlockLockerAPIv2;
import nl.rutgerkok.blocklocker.event.PlayerProtectionCreateEvent;

@SuppressWarnings("deprecation")
public class ServerListener implements Listener {

	
	private HikaSurvival hikaSurvival;
    public ServerListener(HikaSurvival hikaSurvival) {
		this.hikaSurvival = hikaSurvival;
	}

    
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
    	if (e.getEntityType().equals(EntityType.PLAYER)) {
    		Player p = (Player) e.getEntity();
    		if (hikaSurvival.data.getConfig().contains("delay."+p.getUniqueId().toString())) {
    			e.setCancelled(true);
        }
    	}
    }
    
    
    @EventHandler
    public void onVehicleEnter(VehicleEnterEvent event) {
        if (event.getEntered() instanceof Player && event.getVehicle() instanceof Boat) {
            Boat boat = (Boat) event.getVehicle();
            hikaSurvival.getBoatManager().loadFastBoat(boat);
        }
    }

    @EventHandler
    public void onVehicleExit(VehicleExitEvent event) {
        if (event.getExited() instanceof Player && event.getVehicle() instanceof Boat) {
            Boat boat = (Boat) event.getVehicle();
            FastBoat fastBoat =  hikaSurvival.getBoatManager().getFastBoat(boat);
            hikaSurvival.getBoatManager().getFastBoats().remove(fastBoat);
        }
    }
    
    @EventHandler
    public void onDispenser(BlockDispenseEvent e) {
    	if (e.getItem().getType().equals(Material.LAVA_BUCKET)) {
    		e.setCancelled(true);
    	}
    }
    
	@EventHandler 
    public void onExplosion(ExplosionPrimeEvent e) {
    	if (e.getEntityType().equals(EntityType.ENDER_CRYSTAL) 
    			|| e.getEntityType().equals(EntityType.MINECART_TNT) 
    			|| e.getEntityType().equals(EntityType.PRIMED_TNT)) 
  	  e.setCancelled(true);
    }
	
	
	@EventHandler
	public void onExplosion2 (EntityExplodeEvent e) {
		if (e.getEntityType().equals(EntityType.ENDER_CRYSTAL) 
    			|| e.getEntityType().equals(EntityType.MINECART_TNT) 
    			|| e.getEntityType().equals(EntityType.PRIMED_TNT)) 
  	  e.setCancelled(true);
		
	}
	
	@EventHandler
	public void onLava(PlayerBucketEmptyEvent  e) {
		if (e.getPlayer().hasPermission("hika.mod")) return;
		if (e.getBucket().equals(Material.LAVA_BUCKET)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onFlint(PlayerInteractEvent  e) {
		if (e.getPlayer().hasPermission("hika.mod")) return;
		if (e.getItem()==null) return;
	         if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getItem().getType() == Material.FLINT_AND_STEEL && !e.getClickedBlock().getType().equals(Material.OBSIDIAN)){
	        	 e.setCancelled(true);
	}
	}

	@EventHandler
	public void onIgnite(BlockIgniteEvent e) {
		if (e.getCause().equals(IgniteCause.FLINT_AND_STEEL) && e.getBlock().getType().equals(Material.OBSIDIAN)) {
			return;
		}  else {
			e.setCancelled(true);
		}
	}
	
	
	public static Set<SimpleChunkLocation> chunksInvaded = new HashSet<SimpleChunkLocation>();
	@EventHandler
	public void onInvasion(KingdomPreInvadeEvent e) {
		chunksInvaded.addAll(e.getInvasion().getAffectedLands());
		
	} 
	
	
	
	@EventHandler
	public void onAchivement(PlayerAchievementAwardedEvent e) {
		e.setCancelled(false);
	}
	
	@EventHandler
	public void onDimensionChange(PlayerTeleportEvent  e) {
		Player p = e.getPlayer();
		SurviPlayer sp = hikaSurvival.getPlayer(p);
		if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)) {
			if (!e.getPlayer().isOp())	{
				boolean end = hikaSurvival.getRedisManager().get("end") !=null ? hikaSurvival.getRedisManager().get("end").equalsIgnoreCase("true") ? true : false : false;
				if (end) {
				e.setCancelled(true);
				return;
				}
			}
		}
		
		if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL) && !sp.hasAccess(SurviAccess.END)) {
		Structure structure =	hikaSurvival.getStructureManager().getNearestStructure(p.getLocation(), StructureType.MAGE);
			p.sendMessage("§cNo tienes permiso para entrar a la dimensión del End.");
			
			if (structure!=null) {
				int x = structure.getLocation().getBlockX();
				int z = structure.getLocation().getBlockX();
				p.sendMessage("§bDebes hablar con un Mago para que puedas hacerlo! (El más cercano se encuentra en: §5X"+x +" Z"+z );
			} else {
				p.sendMessage("§cActualmente no hay Magos en el Servidor." );
			}
			e.setCancelled(true);
			return;
		}
		
		if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) && !sp.hasAccess(SurviAccess.NETHER)) {
			Structure structure =	hikaSurvival.getStructureManager().getNearestStructure(p.getLocation(), StructureType.MAGE);
				p.sendMessage("§cNo tienes permiso para entrar a la dimensión del Nether.");
			
				if (structure!=null) {
					int x = structure.getLocation().getBlockX();
					int z = structure.getLocation().getBlockX();
					p.sendMessage("§bDebes hablar con un Mago para que puedas hacerlo! (El más cercano se encuentra en: §5X"+x +" Z"+z );
				} else {
					p.sendMessage("§cActualmente no hay Magos en el Servidor." );
				}
				e.setCancelled(true);
				return;
			}
	}
	
	@EventHandler
	public void onSignDestroy(BlockBreakEvent e) {
		
		if (e.getBlock().getType().toString().toLowerCase().contains("sign")) {
			if (BlockLockerAPIv2.isProtected(e.getBlock()) && BlockLockerAPIv2.isOwner(e.getPlayer(), e.getBlock())) {
				SurviPlayer sp = hikaSurvival.getPlayer(e.getPlayer());
				sp.addSignsAmount();
			}
		}
		
	}
	
	
	
	Set<UUID> signDelay = new HashSet<UUID>();
	@EventHandler
	public void onSign(PlayerProtectionCreateEvent  e) {
		Player p = e.getPlayer();
		if (signDelay.contains(p.getUniqueId())) return;
		SurviPlayer sp = hikaSurvival.getPlayer(p);
		if (sp.getSignsAmount()<=0 || !sp.hasAccess(SurviAccess.CASA_SEGURIDAD)) {
		
			e.setCancelled(true);
			
			if (sp.getSignsAmount()<=0) {
				p.sendMessage("§cNo tienes mas carteles de protección!");
			} else {
				p.sendMessage("§cNo tienes permiso para hacer esto.");
			}
			
			Structure structure =	hikaSurvival.getStructureManager().getNearestStructure(p.getLocation(), StructureType.SECURITY );
		
			if (structure!=null) {
				int x = structure.getLocation().getBlockX();
				int z = structure.getLocation().getBlockX();
				p.sendMessage("§bDebes hablar con un Guardia de Seguridad para que puedas hacerlo! (El más cercano se encuentra en: §5X"+x +" Z"+z );
			} else {
				p.sendMessage("§cActualmente no hay Guardias de Seguridad en el Servidor." );
			}
			e.setCancelled(true);
			return;
		
		} else {
			Utils.debug( p.getName()+ " colocó una proteccion en" + Utils.setLoc(e.getSignBlock().getLocation(), false), hikaSurvival);
			sp.removeSignsAmount();
			signDelay.add(p.getUniqueId());
			new BukkitRunnable() {
				public void run() {
					signDelay.remove(p.getUniqueId());
				}
			}.runTaskLater(hikaSurvival, 5);
			}
		}

	
	private HashMap<UUID,Integer> buyingPet = new HashMap<UUID,Integer>();
	
	@EventHandler
	public void onBuyPet(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
		if (e.getCurrentItem().getType().toString().toLowerCase().contains("spawn_egg") || e.getCurrentItem().getType().equals(Material.PUMPKIN)) {
			ItemStack item = e.getCurrentItem();
			ItemMeta meta =  item.getItemMeta();
			if (meta!=null) {
				List<String> lore = meta.getLore();
				if  (lore!=null && lore.size()==2) {
					String line = lore.get(1).replaceAll("[^0-9]","");
					try {
					int price = Integer.parseInt(line);
					buyingPet.put(e.getWhoClicked().getUniqueId(), price);
					new BukkitRunnable() {
						public void run() {
							
						}
					}.runTaskLaterAsynchronously(hikaSurvival, 10L);
					
					} catch(Exception ex) {}
				}
			}
		}
		}
		
	}
	
	
	@EventHandler
	public void onPetSpawn(MyPetCreateEvent e) {
		if (buyingPet.containsKey(e.getPlayer().getUniqueId())) 
			hikaSurvival.getBankManager().addMoney(buyingPet.get(e.getPlayer().getUniqueId()));
		
	}
}
