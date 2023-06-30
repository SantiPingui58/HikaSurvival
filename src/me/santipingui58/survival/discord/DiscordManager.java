package me.santipingui58.survival.discord;

import org.bukkit.entity.Player;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.JDA;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Guild;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Member;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Role;
import github.scarsz.discordsrv.objects.managers.AccountLinkManager;
import me.santipingui58.survival.HikaSurvival;
import me.santipingui58.survival.utils.LuckPermsUtils;
import me.santipingui58.survival.utils.Utils;

public class DiscordManager {

	public JDA jda;
	 public  Guild guild;
	  public Role sub;
	  public  Role booster;
	  public Role vip;
	
	private HikaSurvival hikaSurvival;
	public DiscordManager(HikaSurvival hikaSurvival) {
		this.hikaSurvival = hikaSurvival;
		jda =  DiscordSRV.getPlugin().getJda();
		guild = jda.getGuildById(hikaSurvival.getConfig().getString("discord.guild-id"));
		vip = guild.getRoleById("1092524952311312404");
		sub = guild.getRoleById("1083621784189927466");
		booster =  guild.getRoleById("852920303989948416");;
	}
	
	
	public void checkPlayerStillHasRank(Player p) {
		
		AccountLinkManager alm = DiscordSRV.getPlugin().getAccountLinkManager();
		if (p.hasPermission("hika.sub")) {
			if (alm.getLinkedAccounts().containsValue(p.getUniqueId())) {
				String id = Utils.getKeyByValue(alm.getLinkedAccounts(), p.getUniqueId());
				Member member = DiscordSRV.getPlugin().getJda().getGuildById(hikaSurvival.getConfig().getString("discord.guild-id")).getMemberById(id);
				if (!member.getRoles().contains(sub)) {
					LuckPermsUtils.removeFromGroup(p.getUniqueId(),"sub");
				}
			} else {
				LuckPermsUtils.removeFromGroup(p.getUniqueId(),"sub");
			}
		}
	}
	
	
	
	
}
