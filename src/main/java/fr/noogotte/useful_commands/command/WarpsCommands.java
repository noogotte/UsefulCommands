package fr.noogotte.useful_commands.command;

import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.command.exception.CommandError;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;
import fr.noogotte.useful_commands.component.WarpsComponent;
import fr.noogotte.useful_commands.component.WarpsComponent.Warp;

@NestedCommands(name = "useful")
public class WarpsCommands extends UsefulCommands {

    private WarpsComponent warpscomponent;

    public WarpsCommands(WarpsComponent warpscomponent, UsefulCommandsPlugin plugin) {
    	super(plugin);
        this.warpscomponent = warpscomponent;
    }

    @Command(name = "setwarp", min = 1, max = 1)
    public void createWarp(Player player, CommandArgs args) {
        if (warpscomponent.isWarp(args.get(0))) {
			throw new CommandError(msg("warp.alreadyExist_€", args.get(0)));
        } else {
            Location location = player.getLocation();
            warpscomponent.addWarp(args.get(0), location);
			player.sendMessage(msg("warp.created", args.get(0)));
        }
    }

    @Command(name = "warp", min = 1, max = 2)
    public void teleportToWarp(CommandSender sender, CommandArgs args) {
        if (!warpscomponent.isWarp(args.get(0))) {
			throw new CommandError(msg("warp.notExist_€", args.get(0)));
        }

        String warpName = args.get(0);
        List<Player> targets = args.getPlayers(1)
                .matchWithPermOr("useful.warp.tp.other", sender);

        for (Player target : targets) {
            Warp warp = warpscomponent.getWarp(warpName);
            target.teleport(warp.toLocation());
			target.sendMessage(msg("teleport.target"));

            if (!sender.equals(target)) {
				sender.sendMessage(msg("warp.sender", target.getDisplayName(), warpName));
            }
        }
    }

    @Command(name = "deletewarp", flags = "a", min = 0, max = 1)
    public void deleteWarp(CommandSender sender, CommandArgs args) {
        boolean all = args.hasFlag('a');

        if (all) {
            warpscomponent.clearWarp();
			sender.sendMessage(msg("warp.deleteAll"));
        } else {
            if (!warpscomponent.isWarp(args.get(0))) {
                String arg = args.get(0);
				throw new CommandError(msg("warp.notExist_€", arg));
            } else {
                warpscomponent.deleteWarp(args.get(0));
				sender.sendMessage(msg("warp.delete", args.get(0)));
            }
        }
    }

    @Command(name = "warps")
    public void warps(CommandSender sender) {
        if (warpscomponent.isEmpty()) {
			throw new CommandError(msg("warp.list.isEmpty_€"));
        }

        sender.sendMessage(ChatColor.GOLD + "Warps : ");
        for (Entry<String, Warp> warpEntry : warpscomponent.warps()) {
            sender.sendMessage(ChatColor.AQUA + "  -" + warpEntry.getKey());
        }
    }

    @Command(name = "warplocation", min = 1, max = 1)
    public void warpLocation(CommandSender sender, CommandArgs args) {
        if (warpscomponent.isEmpty()) {
			throw new CommandError(msg("warp.list.isEmpty_€"));
        }
        if (!warpscomponent.isWarp(args.get(0))) {
			throw new CommandError(msg("warp.notExist_€", args.get(0)));
        } else {
            String warpName = args.get(0);
            Warp warp = warpscomponent.getWarp(warpName);
            Location warpLocation = warp.toLocation();

            sender.sendMessage(ChatColor.GREEN + warpName + ChatColor.AQUA
                    + " :");
            sender.sendMessage(ChatColor.AQUA + "  - X: " + ChatColor.GREEN
                    + warpLocation.getX());
            sender.sendMessage(ChatColor.AQUA + "  - Y: " + ChatColor.GREEN
                    + warpLocation.getY());
            sender.sendMessage(ChatColor.AQUA + "  - Z: " + ChatColor.GREEN
                    + warpLocation.getZ());
            sender.sendMessage(ChatColor.AQUA + "  - Pitch: " + ChatColor.GREEN
                    + warpLocation.getPitch());
            sender.sendMessage(ChatColor.AQUA + "  - Yaw: " + ChatColor.GREEN
                    + warpLocation.getYaw());
        }
    }
}
