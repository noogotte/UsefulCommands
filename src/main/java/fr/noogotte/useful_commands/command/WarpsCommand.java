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
import fr.noogotte.useful_commands.component.WarpsComponent;
import fr.noogotte.useful_commands.component.WarpsComponent.Warp;

@NestedCommands(name = "useful")
public class WarpsCommand extends UsefulCommands {

    private WarpsComponent warpscomponent;

    public WarpsCommand(WarpsComponent warpscomponent) {
        this.warpscomponent = warpscomponent;
    }

    @Command(name = "setwarp", min = 1, max = 1)
    public void createWarp(Player player, CommandArgs args) {
        if(warpscomponent.isWarp(args.get(0))) {
            throw new CommandError("Le warp " + args.get(0) + " existe déjà.");
        } else {
            Location location = player.getLocation();
            warpscomponent.addWarp(args.get(0), location);
            player.sendMessage(ChatColor.GREEN + "Vous avez créé un warp : "
                    + ChatColor.AQUA + args.get(0));
        }
    }

    @Command(name = "warp", min = 1, max = 2)
    public void teleportToWarp(CommandSender sender, CommandArgs args) {
        if(!warpscomponent.isWarp(args.get(0))) {
            throw new CommandError("Le warp " + args.get(0) + " n'existe pas.");
        } else {
            String warpName = args.get(0);
            List<Player> targets = args.getPlayers(1).match(sender);

            for (Player target : targets) {
                Warp warp = warpscomponent.getWarp(warpName);
                target.teleport(warp.toLocation());
                target.sendMessage(ChatColor.GREEN + "Poof !");

                if (!sender.equals(target)) {
                    sender.sendMessage( target.getName() + "a été téléporté au warp : " + warpName);
                }
            }
        }
    }

    @Command(name = "deletewarp", min = 1, max = 1)
    public void deleteWarp(CommandSender sender, CommandArgs args) {
        if(!warpscomponent.isWarp(args.get(0))) {
            String arg = args.get(0);
            throw new CommandError("Le warp " + arg + " n'existe pas.");
        } else {
            warpscomponent.deleteWarp(args.get(0));
            sender.sendMessage(ChatColor.RED + "Vous avez supprimé le warp : " + args.get(0));
        }
    }

    @Command(name = "warps")
    public void warps(CommandSender sender, CommandArgs args) {
        sender.sendMessage(ChatColor.GOLD + "Warps : ");
        for (Entry<String, Warp> warpEntry : warpscomponent.warps()) {
            sender.sendMessage(ChatColor.AQUA + "  -" + warpEntry.getKey());
        }
    }
}
