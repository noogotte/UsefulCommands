package fr.noogotte.useful_commands.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.CommandArgs;
import fr.aumgn.bukkitutils.command.NestedCommands;

@NestedCommands(name = "useful")
public class PlayerCommands extends UsefulCommands {

	@Command(name = "gamemode", min = 0, max = 1)
	public void gamemode(Player player, CommandArgs args) {
		if (args.length() == 0) {
			gamemode(player);
			player.sendMessage(ChatColor.GREEN + "Vous vous êtes mis en "
					+ ChatColor.AQUA + player.getGameMode());
		} else {
			for (Player target : args.getPlayers(0)) {
				this.gamemode(target);
				player.sendMessage(ChatColor.GREEN + "Vous  avez mis " +
						ChatColor.AQUA + target.getName() + ChatColor.GREEN + " en " + ChatColor.AQUA +  target.getGameMode());
			}
		}
	}

	private void gamemode(Player target) {
		if (target.getGameMode() == GameMode.CREATIVE) {
			target.setGameMode(GameMode.SURVIVAL);
		} else {
			target.setGameMode(GameMode.CREATIVE);
		}
	}

	@Command(name = "heal", min = 0, max = 1)
	public void heal(Player player, CommandArgs args) {
		if (args.length() == 0) {
			heal(player);
			player.sendMessage(ChatColor.YELLOW + "Vous voila soigné et nourris");
		} else {
			for (Player target : args.getPlayers(0)) {
				this.heal(target);
				player.sendMessage(ChatColor.GREEN + "Vous vous avez soignés et nourris " +
						ChatColor.BLUE + target.getName());
				target.sendMessage(ChatColor.YELLOW + "Vous êtes soignés et nourrit");
			}
		}
	}

	private void heal(Player target) {
		target.setHealth(20);
		target.setFoodLevel(20);
	}

	@Command(name = "clear", min = 0, max = 1)
	public void clear(Player player, CommandArgs args) {
		if (args.length() == 0) {
			clear(player);
			player.sendMessage(ChatColor.YELLOW + "Clear !");
		} else {
			for (Player target : args.getPlayers(0)) {
				this.clear(target);
				player.sendMessage(ChatColor.GREEN + "Vous avez vidés l'inventaire de " +
						ChatColor.BLUE + target.getName());
				target.sendMessage(ChatColor.YELLOW + "Inventaire vidé !");
			}
		}
	}

	private void clear(Player target) {
		for (int j = 0; j <= 39; j++) {
			target.getInventory().setItem(j, null);
		}
	}
}