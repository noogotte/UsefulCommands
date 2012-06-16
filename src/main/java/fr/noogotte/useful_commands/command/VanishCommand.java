package fr.noogotte.useful_commands.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.noogotte.useful_commands.component.VanishComponent;

@NestedCommands(name = "useful")
public class VanishCommand extends UsefulCommands {

	private VanishComponent vanishComponent;
	
	public VanishCommand(VanishComponent vanishComponent) {
		this.vanishComponent = vanishComponent;
	}
	
	@Command(name = "vanish", min = 0, max = 1)
	public void vanish(CommandSender sender, CommandArgs args) {
		List<Player> targets = args.getPlayers(0).match(sender, "useful.vanish.command.other");

		for (Player target : targets) {
			if(!vanishComponent.isVanish(target)) {
				vanishComponent.addPlayer(target);
				for(Player allPlayer : Bukkit.getOnlinePlayers()) {
					allPlayer.hidePlayer(target);
				}
				target.sendMessage(ChatColor.GREEN + "Vous êtes invisible !");			
				if(!sender.equals(target)) {
					sender.sendMessage(ChatColor.GREEN + "Vous avez caché " +
							ChatColor.GOLD + target.getName());
				}
			} else {
				vanishComponent.removePlayer(target);
				for(Player allPlayer : Bukkit.getOnlinePlayers()) {
					allPlayer.showPlayer(target);
				}
				target.sendMessage(ChatColor.GREEN + "Vous êtes visible !");
				
				if(!sender.equals(target)) {
					sender.sendMessage(ChatColor.GREEN + "Vous avez montrer " +
							ChatColor.GOLD + target.getName());
				}
			}
		}
	}
}
