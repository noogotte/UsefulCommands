package fr.noogotte.useful_commands.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.noogotte.useful_commands.event.VisibleCheckEvent;

@NestedCommands(name = "useful")
public class InfoServerCommands extends UsefulCommands {

	@Command(name = "online")
	public void onlinePlayers(CommandSender sender, CommandArgs args) {
	    List<Player> onlinePlayers = new ArrayList<Player>();
	    for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
	        VisibleCheckEvent event = new VisibleCheckEvent(onlinePlayer);
	        Bukkit.getPluginManager().callEvent(event);
	        if(event.isVisible()) {
	            onlinePlayers.add(onlinePlayer);
	        }
	    }

	    sender.sendMessage(ChatColor.GREEN + "Joueur(s) connecté (" + onlinePlayers.size() +  ")" + ":");
	    for(Player onlinePlayer : onlinePlayers) {
	        sender.sendMessage(ChatColor.BLUE + "  - " + onlinePlayer.getDisplayName());
	    }
	}

    @Command(name = "operatorlist")
    public void opList(CommandSender sender, CommandArgs args) {
    	sender.sendMessage(ChatColor.GREEN + "Joueur(s) OP (" + Bukkit.getOperators().size() +  ")" + ":");
    	for(OfflinePlayer opPlayer : Bukkit.getOperators()) {
    		sender.sendMessage(ChatColor.BLUE + "  - " + opPlayer.getName());
    	}
    }
}