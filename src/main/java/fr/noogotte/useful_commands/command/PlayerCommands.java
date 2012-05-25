package fr.noogotte.useful_commands.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

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
	
	@Command(name = "seed", min = 0, max = 0)
	public void seed(Player player, CommandArgs args) {
		player.sendMessage(ChatColor.GREEN + "Seed : " + ChatColor.BLUE + player.getWorld().getSeed());
	}
	
	@Command(name = "spawn", min = 0, max = 1)
	public void spawn(Player player, CommandArgs args) {
		if (args.length() == 0) {
			this.teleporteSpawn(player);
			player.sendMessage(ChatColor.GREEN + "Vous êtes au spawn !");
		} else {
			for (Player target : args.getPlayers(0)) {
				this.teleporteSpawn(target);
				target.sendMessage(ChatColor.GREEN + "Vous avez été téléporté au spawn !");
				player.sendMessage(ChatColor.GREEN + "Vous avez été téléporté au spawn : " + ChatColor.BLUE + target.getName());
			}
		}
	}
	
	private void teleporteSpawn(Player target) {
		target.teleport(target.getWorld().getSpawnLocation());
	}
	
	@Command(name = "setspawn", min = 0, max = 0)
	public void setSpawn(Player player, CommandArgs args) {
		Location playerloc = player.getLocation();
		player.getWorld().setSpawnLocation(playerloc.getBlockX(), playerloc.getBlockY(), playerloc.getBlockZ());
		player.sendMessage(ChatColor.GREEN + "Vous avez mis le spawn !");
	}
	
	@Command(name = "give", min = 1, max = 3)
	public void give(Player player, CommandArgs args) {
		Material item = args.getMaterial(0);
		if(args.length() == 1) {
			player.getInventory().addItem(new ItemStack(item, 64));
			player.sendMessage(ChatColor.GREEN + "Vous vous êtes donnés un stack de " + ChatColor.AQUA + item);
		} else if (args.length() == 2) {
			Integer amount = args.getInteger(1);
			player.getInventory().addItem(new ItemStack(item, amount));
			player.sendMessage(ChatColor.GREEN + "Vous vous êtes donnés "  + ChatColor.AQUA 
					+ amount + ChatColor.GREEN + " de " + ChatColor.AQUA + item);
		} else if (args.length() == 3) {
			Integer amount = args.getInteger(1);
			for (Player target : args.getPlayers(2)) {
				target.getInventory().addItem(new ItemStack(item, amount));
				player.sendMessage(ChatColor.GREEN + "Vous avez donnez à " + target.getName() 
						+ ChatColor.GREEN + ": " + ChatColor.AQUA + amount + ChatColor.GREEN + " de " + ChatColor.AQUA + item);
			}
		}
	}
	
	@Command(name = "open", min = 1, max = 1)
	public void openInv(Player player, CommandArgs args) {
		Player target = Bukkit.getPlayer(args.get(0));
		if(target == null) {
			player.sendMessage(ChatColor.RED + "Le joueur ("+ args.get(0) +  ") n'existe pas.");
		} else {
			Inventory inventory = target.getInventory();
			player.openInventory(inventory);
		}
	}
}