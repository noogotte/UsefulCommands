package fr.noogotte.useful_commands.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.CommandArgs;
import fr.aumgn.bukkitutils.command.NestedCommands;

@NestedCommands(name = "useful")
public class TeleportCommands extends UsefulCommands {
	
	@Command(name = "teleportation", min = 1, max = 3)
    public void gamemode(Player player, CommandArgs args) {
		if(args.length() == 1) {
			Player target = args.getPlayer(0);
			player.teleport(target);
			player.sendMessage(ChatColor.GREEN + "Poof !");
		} else if (args.length() == 2) {
			Player target1 = args.getPlayer(0);
			Player target2 = args.getPlayer(1);
			target1.teleport(target2);
			player.sendMessage(ChatColor.AQUA + "Vous avez téléportés " + ChatColor.GREEN + target1.getName() + ChatColor.AQUA + " à " + ChatColor.GREEN + target2.getName());
		}
    }
	
	@Command(name = "teleportationall", min = 0, max = 0)
    public void tpall(Player player, CommandArgs args) {
		tpall(player);
	}
	
	public void tpall(Player player) {
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			onlinePlayer.teleport(player);
			player.sendMessage(ChatColor.GREEN + "Vous avez téléportés tous les joueures à vous.");
		}
	}
}
