package me.santipingui58.survival.task;

import java.util.UUID;

import org.bukkit.scheduler.BukkitRunnable;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Member;
import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.discord.DiscordManager;
import me.santipingui58.survival.utils.LuckPermsUtils;

public class DiscordRankTask {

	public DiscordRankTask(HikaSurvival hikaSurvival) {
		DiscordManager dm = hikaSurvival.getDiscordManager();
		
		new BukkitRunnable() {
			public void run() {
				for (Member member : dm.guild.getMembers()) {	
					
					//check if linked
					if (DiscordSRV.getPlugin().getAccountLinkManager().getLinkedAccounts().containsKey(member.getId())) {
						UUID uuid = DiscordSRV.getPlugin().getAccountLinkManager().getLinkedAccounts().get(member.getId());
									
					if (member.getRoles().contains(dm.sub)) {
						LuckPermsUtils.addToGroup(uuid, "sub");
					} else if (member.getRoles().contains(dm.vip)) {
						LuckPermsUtils.addToGroup(uuid, "vip");
					}else if (member.getRoles().contains(dm.booster)) {
						LuckPermsUtils.addToGroup(uuid, "vip");
					}
					}
				}
				
				
				
				
				
				
			}
		}.runTaskTimerAsynchronously(hikaSurvival, 0L, 20L*60*5);
		
	}
	
	
}
