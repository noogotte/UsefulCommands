package fr.noogotte.useful_commands.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.util.Util;
import fr.noogotte.useful_commands.component.SpyComponent;

@NestedCommands(name = "useful")
public class SpyCommand extends UsefulCommands {
	
	private SpyComponent spyComponent;

	public SpyCommand(SpyComponent spyComponent) {
		this.spyComponent = spyComponent;
	}
	
	@Command(name = "spy", min = 0, max = 1)
	public void spy(CommandSender sender, CommandArgs args) {
		List<Player> targets = args.getPlayers(0).match(sender, "useful.spy.command.other");
		
		for(Player target : targets) {
			if(!spyComponent.isSpy(target)) {
				spyComponent.addPlayer(target);
				for(Player allPlayer : Bukkit.getOnlinePlayers()) {
					allPlayer.hidePlayer(target);
				}
				Util.broadcast("useful.spy.command.broadcast", target.getName() + " est passé(e) en Spy Mode.");
				Util.broadcast(ChatColor.YELLOW + target.getName() + " left the game");
			} else {
				spyComponent.removePlayer(target);
				for(Player allPlayer : Bukkit.getOnlinePlayers()) {
					allPlayer.showPlayer(target);
				}
				Util.broadcast("useful.spy.command.broadcast", target.getName() + " n'est plus en Spy Mode.");
				Util.broadcast(ChatColor.YELLOW + target.getName() + " joined the game");
			}
		}
	}
	
	@Command(name = "spy-list")
    public void spmls(CommandSender sender, CommandArgs args) {
        sender.sendMessage(ChatColor.GREEN + "Espion : ");
        for (Player player : spyComponent.getSpy()) {
            sender.sendMessage(ChatColor.YELLOW + " - " + player.getDisplayName());
        }
    }
}
