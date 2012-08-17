package fr.noogotte.useful_commands.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.geom.Direction;
import fr.aumgn.bukkitutils.geom.Vector;

import static fr.noogotte.useful_commands.LocationUtil.*;

@NestedCommands(name = "useful")
public class TeleportCommands extends UsefulCommands {

    @Command(name = "teleportation", min = 1, max = 2)
    public void teleportation(Player sender, CommandArgs args) {
        Player to = args.getPlayer(0).value();
        List<Player> targets = args.getPlayers(1)
                .matchWithPermOr("useful.teleport.teleport.other", sender);

        for (Player target : targets) {
            target.teleport(to);
            target.sendMessage(ChatColor.GREEN + "Poof !");

            if (!target.equals(sender)) {
                sender.sendMessage(ChatColor.AQUA + "Vous avez téléporté "
                        + ChatColor.GREEN + target.getName() + ChatColor.AQUA
                        + " à " + ChatColor.GREEN + target.getName());
            }
        }
    }

    @Command(name = "summon", flags = "t", argsFlags = "d", min = 1, max = 1)
    public void summon(Player sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(0).match();

        Location location;
        if (args.hasArgFlag('d')) {
            int distance = args.getInteger('d').value();
            Vector pos = getDistantLocation(sender, distance);
            Direction dir = pos.towards(new Vector(sender));
            location = pos.toLocation(sender.getWorld(), dir);
        } else if (args.hasFlag('t')) {
            Vector pos = getTargetBlockLocation(sender, 180);
            Direction dir = pos.towards(new Vector(sender));
            location = pos.toLocation(sender.getWorld(), dir);
        } else {
            location = sender.getLocation();
        }

        for (Player target : targets) {
            if (sender.equals(target)) {
                continue;
            }

            target.teleport(location);
            target.sendMessage(ChatColor.GREEN + "Poof !");
            sender.sendMessage(ChatColor.AQUA + "Vous avez téléporté "
                    + ChatColor.GREEN + target.getName());
        }
    }

    @Command(name = "put", min = 0, max = 1)
    public void put(Player sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(0)
                .matchWithPermOr("useful.teleport.put.other", sender);

        Vector pos = getTargetBlockLocation(sender, 180);
        Direction dir = pos.towards(new Vector(sender));
        Location location = pos.toLocation(sender.getWorld(), dir);

        for (Player target : targets) {

            target.teleport(location);
            target.sendMessage(ChatColor.GREEN + "Poof !");
            if (!sender.equals(target)) {
                sender.sendMessage(ChatColor.AQUA + "Vous avez téléporté "
                        + ChatColor.GREEN + target.getName());
            }
        }
    }

    @Command(name = "teleport-to", min = 1, max = 3)
    public void teleportTo(CommandSender sender, CommandArgs args) {
        Vector teleportPos = args.getVector(0).value();
        World world = args.getWorld(1).valueOr(sender);
        List<Player> targets = args.getPlayers(2)
                .matchWithPermOr("useful.teleport.teleportto.other", sender);

        for (Player target : targets) {
            Vector currentPos = new Vector(target.getLocation());
            Direction dir = teleportPos.towards(currentPos);
            Location location = teleportPos.toLocation(world, dir);
            target.teleport(location);

            sender.sendMessage(ChatColor.GREEN + "Poof !");
            if (!sender.equals(target)) {
                sender.sendMessage(ChatColor.GREEN + "Vous avez téléporté "
                        + ChatColor.BLUE + target.getName());
            }
        }
    }

    @Command(name = "spawn", min = 0, max = 1)
    public void spawn(CommandSender sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(0)
                .matchWithPermOr("useful.teleport.spawn.other", sender);

        for (Player target : targets) {
            target.teleport(target.getWorld().getSpawnLocation());

            target.sendMessage(ChatColor.GREEN + "Vous êtes au spawn !");
            if (!sender.equals(target)) {
                sender.sendMessage(ChatColor.GREEN + "Vous avez téléporté "
                        + ChatColor.BLUE + target.getName() + ChatColor.GREEN
                        + " au spawn.");
            }
        }
    }
}
