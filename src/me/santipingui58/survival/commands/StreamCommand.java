package me.santipingui58.survival.commands;

import java.net.URI;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.utils.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class StreamCommand implements CommandExecutor {

	
	private HikaSurvival hikaSurvival;
	public StreamCommand(HikaSurvival hikaSurvival) {
		this.hikaSurvival = hikaSurvival;
	}
	
	
	
	private HashMap<UUID,Integer> delay = new HashMap<UUID,Integer>();
	
	
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
		if(!(sender instanceof Player)) {
			
			sender.sendMessage("Solo los jugadores pueden hacer esto!");
			return true;
			
		} else {
			Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("stream")) {
			if (p.hasPermission("splindux.stream")) {
			if (args.length==0) {
				sender.sendMessage("§aUso del comando: /stream <link>");
			} else {			
				try {
					URI uri = new URI(args[0]);
					String domain = uri.getHost();
				    String pag = domain.startsWith("www.") ? domain.substring(4) : domain;
				    if (Utils.containsIgnoreCase(pag, "youtube") || Utils.containsIgnoreCase(pag, "twitch")) {
				    	if (!delay.containsKey(p.getUniqueId())) {
							Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "discord broadcast #꒰🎥┊streamers-spam "+"**"+ p.getName() + "** está en Stream! "
									+ "" + args[0]);
					
							TextComponent msg1 = new TextComponent("§a§l" + p.getName().toUpperCase() + " §c§lESTA EN STREAM!");
							msg1.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, args[0]));
							msg1.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aAbrir Stream").create()));
								
							TextComponent msg2 = new TextComponent("§7[Click para ir al Stream]");
							msg2.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, args[0]));
							msg2.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aAbrir Stream").create()));
							
							
							for (Player pl : Bukkit.getOnlinePlayers()) {
								pl.sendMessage("");
								pl.sendMessage("");
								pl.sendMessage("");
								pl.sendMessage("§f§l-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
								pl.getPlayer().spigot().sendMessage(msg1);
								pl.getPlayer().spigot().sendMessage(msg2);
								pl.sendMessage("§f§l-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
								pl.sendMessage("");
							}
							
							delay.put(p.getUniqueId(), 30);
							new BukkitRunnable() {
								public void run() {
									if (delay.containsKey(p.getUniqueId())) {
										int i = delay.get(p.getUniqueId());
										i--;
										if (i<=0) {
											cancel();
										} else {
											delay.put(p.getUniqueId(), i);
										}
									} else {
										cancel();
									}
								}
							}.runTaskTimerAsynchronously(hikaSurvival, 0L, 20L*60);

							
							
						} else {
							p.sendMessage("§cDebes esperar §a" + delay.get(p.getUniqueId()) + " §c minutos para volver a usar este comando");
							return false;
						}
				    } else {
				    	p.sendMessage("§cSolo puedes colocar links de §fyoutube.com §co §5twitch.tv");
						return false;
				    }
				} catch (Exception e) {
					p.sendMessage("§a" + args[0] + "§c no es un link valido. Ejemplo: (https://twitch.tv/hikarilof");
					return false;
				}
				
			
				
				
			}
			} 
			}
			

}
		
		
		return false;
	}
	

	
}