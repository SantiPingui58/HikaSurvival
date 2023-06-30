package me.santipingui58.survival.utils;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.bukkit.entity.Player;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedPermissionData;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.node.types.PrefixNode;
import net.luckperms.api.util.Tristate;
import net.md_5.bungee.api.ChatColor;

public class LuckPermsUtils {

	static LuckPerms api = LuckPermsProvider.get();
	
	public static void setPrefix(Player p,String prefix) {
		 User user = api.getUserManager().getUser(p.getUniqueId());
		 user.data().add(PrefixNode.builder(prefix, 0).build());
		 api.getUserManager().saveUser(user);
	} 
	
	public static String getPrefix(Player p ) {
		return ChatColor.translateAlternateColorCodes('&', 
				LuckPermsProvider.get().getUserManager().getUser(p.getUniqueId()).getCachedData().getMetaData().getPrefix());
	}
	
	
	
	
	public static void addToGroup(UUID uuid, String groupName) {

		Group group = api.getGroupManager().getGroup(groupName);
		
		CompletableFuture<User> userFuture = api.getUserManager().loadUser(uuid);

		userFuture.thenAcceptAsync(user -> {
			InheritanceNode node = InheritanceNode.builder(group).value(true).build();
			user.data().add(node);
			api.getUserManager().saveUser(user);
		});
	}

	public static void removeFromGroup(UUID uuid, String groupName) {
		Group group = api.getGroupManager().getGroup(groupName);
		
		CompletableFuture<User> userFuture = api.getUserManager().loadUser(uuid);

		userFuture.thenAcceptAsync(user -> {
			InheritanceNode node = InheritanceNode.builder(group).value(false).build();
			user.data().add(node);
			api.getUserManager().saveUser(user);
		});
		
	} 
	
	
	public static User getUser(UUID uuid) {
		UserManager userManager = api.getUserManager();
	    CompletableFuture<User> userFuture = userManager.loadUser(uuid);

	    return userFuture.join(); // ouch! (block until the User is loaded)
	}
	
	
	public static boolean hasPermission(UUID uuid,String permission) {
		User user = getUser(uuid);
		CachedPermissionData permissionData = user.getCachedData().getPermissionData();
		Tristate checkResult = permissionData.checkPermission(permission);
		return checkResult.asBoolean();

	}
	
	public static void addPermission(UUID uuid, String permission) {
		User user = api.getUserManager().getUser(uuid);
	    user.data().add(Node.builder(permission).build());
	    api.getUserManager().saveUser(user);
	}
	
	public static void removePermission(UUID uuid, String permission) {
		User user = api.getUserManager().getUser(uuid);
		Node node = Node.builder(permission).build();
		user.data().remove(node);
	    api.getUserManager().saveUser(user);
	}
}
