package me.santipingui58.survival.gui;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.santipingui58.survival.HikaSurvival;


public abstract class GUIBuilder implements Listener {

    protected HikaSurvival hikaSurvival;
    
	Inventory _inv;
	HashMap<Integer,ItemStack> inventory = new HashMap<Integer,ItemStack>();
	
	public GUIBuilder(String name, int rows,HikaSurvival hikaSurvival){
		this.hikaSurvival = hikaSurvival;
		_inv = Bukkit.createInventory(null, 9 * rows, ChatColor.translateAlternateColorCodes('&', name));
		hikaSurvival.getServer().getPluginManager().registerEvents(this, hikaSurvival);

	}
	
	public  void a(ItemStack stack){
		_inv.addItem(stack);
	}
	public void s(int i , ItemStack stack){
		//_inv.setItem(i, stack);
		inventory.put(i, stack);
	}
	
	public void buildInventory() {
		_inv.clear();
		for (Entry<Integer, ItemStack> entry : inventory.entrySet()) {
		    int i = entry.getKey();
		    ItemStack stack = entry.getValue();
		    _inv.setItem(i, stack);
		}
		
	}
	public Inventory i(){
		return _inv;
	}
	


	public void o(Player p){ 
		p.openInventory(_inv);
	}

	  @EventHandler
	    public void onInventoryClick(InventoryClickEvent event) {
	        if (event.getInventory().equals(this.i())) {
	            if (event.getCurrentItem() != null && this.i().contains(event.getCurrentItem()) && event.getWhoClicked() instanceof Player) {
	            	Player p = (Player) event.getWhoClicked();
	                this.onClick(p, event.getCurrentItem(), event.getSlot());
	                event.setCancelled(true);
	            }
	        }
	    }
	  @EventHandler
	    public void onInventoryClose(InventoryCloseEvent event) {
	        if (event.getInventory().equals(this.i()) && event.getPlayer() instanceof Player) {
	            this.onClose((Player) event.getPlayer());
	        }
	    }
	  public void onClose(Player player) {}
	  public abstract void onClick(Player p, ItemStack stack, int slot);
}