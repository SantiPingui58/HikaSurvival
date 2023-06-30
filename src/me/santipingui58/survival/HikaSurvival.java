package me.santipingui58.survival;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;

import me.santipingui58.survival.commands.BankCommand;
import me.santipingui58.survival.commands.BankTopCommand;
import me.santipingui58.survival.commands.ByPassCommand;
import me.santipingui58.survival.commands.DelHomeCommand;
import me.santipingui58.survival.commands.HomeCommand;
import me.santipingui58.survival.commands.HoverCommand;
import me.santipingui58.survival.commands.MsgCommand;
import me.santipingui58.survival.commands.ReplyCommand;
import me.santipingui58.survival.commands.SetHomeCommand;
import me.santipingui58.survival.commands.StreamCommand;
import me.santipingui58.survival.commands.StructureCommand;
import me.santipingui58.survival.commands.WarpCommand;
import me.santipingui58.survival.config.Configuration;
import me.santipingui58.survival.discord.DiscordManager;
import me.santipingui58.survival.game.bank.BankManager;
import me.santipingui58.survival.game.boat.BoatManager;
import me.santipingui58.survival.game.structures.StructureManager;
import me.santipingui58.survival.game.warp.WarpManager;
import me.santipingui58.survival.io.database.SQLManager;
import me.santipingui58.survival.io.redis.RedisManager;
import me.santipingui58.survival.listener.ChatListener;
import me.santipingui58.survival.listener.NPCListener;
import me.santipingui58.survival.listener.PlayerConnectListener;
import me.santipingui58.survival.listener.PlayerListener;
import me.santipingui58.survival.listener.ServerListener;
import me.santipingui58.survival.listener.protocol.ClientSteerVehiclePacket;
import me.santipingui58.survival.player.SurviPlayer;
import me.santipingui58.survival.task.DiscordRankTask;
import me.santipingui58.survival.task.RedisTask;
import me.santipingui58.survival.task.SavePlayersTask;
import me.santipingui58.survival.task.TimeTask;
import me.santipingui58.survival.utils.Utils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.milkbowl.vault.economy.Economy;

/**
 * Simple Survival Core for hikarilof's Stream series
 * @author SantiPingui58
 *
 */

public class HikaSurvival extends JavaPlugin {
	
	private  LuckPerms luckPerms;
	public static Configuration config;
	public Configuration data;
	private  Economy econ = null;
	private Essentials essentials;
	
	private Set<SurviPlayer> players = new HashSet<SurviPlayer>();
	
	private StructureManager structureManager;
	private SQLManager sqlManager;
	private DiscordManager discordManager;
	private RedisManager redisManager;
	private WarpManager warpManager;
	private BoatManager boatManager;
	private BankManager bankManager;
	
    @Override
    public void onEnable() {
    	initDependencies();
    	  initConfig();
    	  
    	  
    	  registerListeners();
    	  registerCommands();
    	  registerManagers();
    	  
    	  initTasks();
    	  
    	 
    }
    
    @Override
    public void onDisable() {
    	getStructureManager().saveStructures();
    	this.sqlManager.getPool().closePool();
    	
    }
    
    public void initConfig() {
    	config = new Configuration("config.yml", this);
  	  data = new Configuration("data.yml", this);
  	 
    }
    
    public void initDependencies() {
    	luckPerms = LuckPermsProvider.get();
    	
    	 RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
   	  econ = rsp.getProvider();
   	  
  	this.essentials = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");	
    }
    
    private void initTasks() {
    	new DiscordRankTask(this);
    	new SavePlayersTask(this);
    	new TimeTask(this);
    	new RedisTask(this);
    }
    
    
    private void registerListeners() {
    	
    	 getServer().getPluginManager().registerEvents(new ServerListener(this),this);
    	 getServer().getPluginManager().registerEvents(new PlayerConnectListener(this),this);
    	 getServer().getPluginManager().registerEvents(new NPCListener(this),this);
    	 getServer().getPluginManager().registerEvents(new ChatListener(this),this);
    	 getServer().getPluginManager().registerEvents(new PlayerListener(this),this);
    	 
    	 new ClientSteerVehiclePacket(this);
    }
    
    
    
    private void registerCommands() {
    	getCommand("structure").setExecutor(new StructureCommand(this));
    	getCommand("msg").setExecutor(new MsgCommand(this));
    	getCommand("reply").setExecutor(new ReplyCommand(this));
    	getCommand("hover").setExecutor(new HoverCommand(this));
    	getCommand("warp").setExecutor(new WarpCommand(this));
    	getCommand("sethome").setExecutor(new SetHomeCommand(this));
    	getCommand("delhome").setExecutor(new DelHomeCommand(this));
    	getCommand("home").setExecutor(new HomeCommand(this));
    	getCommand("bank").setExecutor(new BankCommand(this));
    	getCommand("banktop").setExecutor(new BankTopCommand(this));
    	getCommand("bypass").setExecutor(new ByPassCommand(this));
    	getCommand("stream").setExecutor(new StreamCommand(this));
    	
    }
    
    private void registerManagers() {
    	this.structureManager = new StructureManager(this);
    	this.sqlManager = new SQLManager(this);
    	this.discordManager = new DiscordManager(this);
    	this.redisManager = new RedisManager(this);
    	this.warpManager = new WarpManager(this);
    	this.boatManager = new BoatManager(this);
    	this.bankManager = new BankManager(this);
    }
    
    

    public StructureManager getStructureManager() {
    	return this.structureManager;
    }
    
    public SQLManager getSQLManager() {
    	return this.sqlManager;
    }
    
    public DiscordManager getDiscordManager() {
    	return this.discordManager;
    }
    
    public RedisManager getRedisManager() {
    	return this.redisManager;
    }
    
    public WarpManager getWarpManager() {
    	return this.warpManager;
    }
    
    public BankManager getBankManager() {
    	return this.bankManager;
    }

public BoatManager getBoatManager() {
	return this.boatManager;
}

    
    public LuckPerms getLuckPerms() {
    	return this.luckPerms;
    }
    
    
public Economy getEconomy() {
	return this.econ;
}



public void payToServer(Player p, int price) {
	this.econ.withdrawPlayer(p, price);
	bankManager.addMoney(price);
}


public Essentials getEssentials() {
	return this.essentials;
}

public Set<SurviPlayer> getPlayers() {
	return this.players;
}
    


public SurviPlayer getPlayer(OfflinePlayer player) {
	SurviPlayer toReturn = null;
	Set<SurviPlayer> toRemove = new HashSet<SurviPlayer>();
	for (SurviPlayer sp : this.players) {
		if (sp==null) {
			toRemove.add(sp);
			continue;
		}
		if (sp.getUuid()==null) {
			toRemove.add(sp);
			Utils.debug("SurviPlayer Uuid null",this);
			try {
				Utils.debug(sp.getTwitchId(),this);
				
			}catch(Exception ex) {}
			
			try {
				Utils.debug(sp.getOfflinePlayer().getUniqueId().toString(),this);
			}catch(Exception ex) {}
			
		continue;
		}
		if (sp.getUuid().equals(player.getUniqueId())) {
			toReturn = sp;
			break;
		}
	}
	
	this.players.removeAll(toRemove);
	return toReturn;
}



public SurviPlayer getPlayerByTwitchId(String twitchId) {
	for (SurviPlayer sp : this.players) {
		if (sp.getTwitchId().equalsIgnoreCase(twitchId)) {
			return sp;
		}
	}
	return null;
}



    

}