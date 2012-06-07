package fr.noogotte.useful_commands.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
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
        if(args.length() == 1) {
            Player target = args.getPlayer(0);
            sender.teleport(target);
            sender.sendMessage(ChatColor.GREEN + "Poof !");
        } else {
            Player target = args.getPlayer(1);
            for (Player player : args.getPlayers(0)) {
                player.teleport(target);
                sender.sendMessage(ChatColor.AQUA +
                        "Vous avez téléporté "
                        + ChatColor.GREEN + player.getName() 
                        + ChatColor.AQUA + " à "
                        + ChatColor.GREEN + target.getName());
            }
        }
    }

    @Command(name = "summon", flags = "dt", min = 1, max = -1)
    public void summon(Player sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(0);
        int to = args.length() - (args.hasFlag('d') ? 1 : 0);
        for (int i = 1; i < to; i++) {
            targets.addAll(args.getPlayers(i));
        }

        Location location;
        if (args.hasFlag('d')) {
            int distance = args.getInteger(args.length() - 1);
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
                sender.sendMessage(ChatColor.AQUA
                        + "Vous avez téléporté "
                        + ChatColor.GREEN + target.getName());
        }
    }

    @Command(name = "teleport-to", min = 1, max = 3)
    public void teleportTo(Player player, CommandArgs args) {
        Vector teleportPos = args.getVector(0);
        World world = args.getWorld(1, player.getWorld());
        List<Player> targets = args.getPlayers(2, player);

        for (Player target : targets) {
            Vector currentPos = new Vector(target.getLocation());
            Direction dir = teleportPos.towards(currentPos);
            Location location = teleportPos.toLocation(world, dir);
            target.teleport(location);

            player.sendMessage(ChatColor.GREEN + "Poof !");
            if (!player.equals(target)) {
                player.sendMessage(ChatColor.GREEN
                        + "Vous avez téléporté "
                        + ChatColor.BLUE + target.getName());
            }
        }
    }

    @Command(name = "spawn", min = 0, max = -1)
    public void spawn(Player player, CommandArgs args) {
        List<Player> targets = args.getPlayers(0, player);
        for (int i = 1; i < args.length(); i++) {
            targets.addAll(args.getPlayers(i));
        }

        for (Player target : targets) {
            target.teleport(
                    target.getWorld().getSpawnLocation());

            target.sendMessage(ChatColor.GREEN + "Vous êtes au spawn !");
            if (!player.equals(target)) {
                player.sendMessage(ChatColor.GREEN
                        + "Vous avez téléporté "
                        + ChatColor.BLUE + target.getName()
                        + ChatColor.GREEN + " au spawn.");
            }
        }
    }
}