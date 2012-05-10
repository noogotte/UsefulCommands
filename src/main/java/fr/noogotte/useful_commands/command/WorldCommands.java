package fr.noogotte.useful_commands.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.CommandArgs;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.exception.CommandUsageError;

@NestedCommands(name = "useful")
public class WorldCommands extends UsefulCommands {
	
	@Command(name = "time", min = 1, max = 1)
	public void time(Player player, CommandArgs args) {
		
		if(args.get(0).equalsIgnoreCase("day")) {
			player.getWorld().setTime(20*60);
			player.sendMessage(ChatColor.GOLD + "Vous avez mis le jour dans " + ChatColor.AQUA + player.getWorld().getName());
		} else if(args.get(0).equalsIgnoreCase("night")) {
			player.getWorld().setTime(20*60*11);
			player.sendMessage(ChatColor.GOLD + "Vous avez mis la nuit dans " + ChatColor.AQUA + player.getWorld().getName());
		} else {
			throw new CommandUsageError("Argument " + args.get(0) + " inconnu.");
		}
	}
	
	@Command(name = "weather", min = 1, max = 1)
	public void weather(Player player, CommandArgs args) {
		
		if(args.get(0).equalsIgnoreCase("sun")) {
			player.getWorld().setStorm(false);
			player.sendMessage(ChatColor.GOLD + "Vous avez mis le soleil dans " + ChatColor.AQUA + player.getWorld().getName());
		} else if(args.get(0).equalsIgnoreCase("storm")) {
			player.getWorld().setStorm(true);
			player.sendMessage(ChatColor.GOLD + "Vous avez mis la pluit dans " + ChatColor.AQUA + player.getWorld().getName());
		} else {
			throw new CommandUsageError("Argument " + args.get(0) + " inconnu.");
		}
	}
}
