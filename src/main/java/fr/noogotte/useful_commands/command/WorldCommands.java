package fr.noogotte.useful_commands.command;

import org.bukkit.Bukkit;
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
			player.sendMessage("Vous avez mis le jours dans" + player.getWorld().getName());
		} else if(args.get(0).equalsIgnoreCase("night")) {
			player.getWorld().setTime(24000);
			Bukkit.broadcastMessage(player.getName() + "à mis la nuit à");
			
		} else {
			throw new CommandUsageError("Argument " + args.get(0) + " inconnu.");
		}
	}
	
	@Command(name = "weather", min = 1, max = 1)
	public void weather(Player player, CommandArgs args) {
		
		if(args.get(0).equalsIgnoreCase("sun")) {
			player.getWorld().setStorm(false);
		} else if(args.get(0).equalsIgnoreCase("storm")) {
			player.getWorld().setStorm(true);
			
		} else {
			throw new CommandUsageError("Argument " + args.get(0) + " inconnu.");
		}
	}
}
