package fr.noogotte.useful_commands.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.command.exception.CommandError;
import fr.aumgn.bukkitutils.command.exception.CommandUsageError;
import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.geom.Vector2D;

import static fr.noogotte.useful_commands.LocationUtil.*;

@NestedCommands(name = "useful")
public class WorldCommands extends UsefulCommands {

    @Command(name = "seed", min = 0, max = 1)
    public void seed(Player player, CommandArgs args) {
        World world = args.getWorld(0, player.getWorld());

        player.sendMessage(ChatColor.GREEN + "Seed : "
                + ChatColor.BLUE + world.getSeed());
    }

    @Command(name = "setspawn", min = 0, max = 0)
    public void setSpawn(Player player, CommandArgs args) {
        Location playerloc = player.getLocation();
        player.getWorld().setSpawnLocation(
                playerloc.getBlockX(),
                playerloc.getBlockY(),
                playerloc.getBlockZ());
        player.sendMessage(ChatColor.GREEN + "Vous avez défini le spawn !");
    }

    @Command(name = "time", min = 1, max = 2)
    public void time(Player player, CommandArgs args) {
        String arg = args.get(0);
        World world = args.getWorld(1, player.getWorld());

        if (arg.equalsIgnoreCase("day")) {
            world.setTime(20 * 60);
            player.sendMessage(ChatColor.GOLD
                    + "Vous avez mis le jour dans "
                    + ChatColor.AQUA + world.getName());
        } else if (arg.equalsIgnoreCase("night")) {
            world.setTime(20 * 60 * 11);
            player.sendMessage(ChatColor.GOLD
                    +"Vous avez mis la nuit dans "
                    + ChatColor.AQUA + world.getName());
        } else {
            throw new CommandUsageError(
                    "Argument " + arg + " inconnu.");
        }
    }

    @Command(name = "weather", min = 1, max = 2)
    public void weather(Player player, CommandArgs args) {
        String arg = args.get(0);
        World world = args.getWorld(1, player.getWorld());

        if (arg.equalsIgnoreCase("sun")) {
            world.setStorm(false);
            player.sendMessage(ChatColor.GOLD
                    + "Vous avez mis le soleil dans "
                    + ChatColor.AQUA + world.getName());
        } else if(arg.equalsIgnoreCase("storm")) {
            world.setStorm(true);
            player.sendMessage(ChatColor.GOLD
                    + "Vous avez mis la pluie dans "
                    + ChatColor.AQUA + world.getName());
        } else {
            throw new CommandUsageError(
                    "Argument " + arg + " inconnu.");
        }
    }

    @Command(name = "spawnmob", flags = "tdp", min = 1, max = 3)
    public void spawnmob(Player player, CommandArgs args) {
        EntityType entity = args.getEntityType(0);
        if (!entity.isSpawnable() && isNotAMob(entity)) {
            throw new CommandError("Vous ne pouvez pas spawner ce type d'entité");
        }

        int count = args.getInteger(1, 1);
        List<Location> locations = new ArrayList<Location>();
        if (args.hasFlag('t')) {
            for (Player target : args.getPlayers(2, player)) {
                Location location = getTargetBlockLocation(target, 180)
                        .toLocation(player.getWorld());
                locations.add(location);
            }
        } else if (args.hasFlag('d')) {
            int distance = args.getInteger(args.length() - 1);
            for (Player target : args.getPlayers(2, player)) {
                Location location = getDistantLocation(target, distance)
                        .toLocation(player.getWorld());
                locations.add(location);
            }
        } else if (args.hasFlag('p')) {
            Vector2D pos2D = args.getVector2D(2);
            Vector pos = pos2D.toHighest(player.getWorld());
            locations.add(pos.toLocation(player.getWorld()));
        } else {
            for (Player target : args.getPlayers(2, player)) {
                locations.add(target.getLocation());
            }
        }

        int totalCount = 0;
        for (int i = 0; i < count; i++) {
            for (Location location : locations) {
                location.getWorld().spawnCreature(location, entity);
                totalCount++;
            }
        }

        player.sendMessage(ChatColor.GREEN + "Vous avez spawn "
                + ChatColor.GOLD + totalCount
                + ChatColor.GREEN + " " + entity.getName());
    }

    @Command(name = "removemob", min = 0, max = 3)
    public void removemob(Player sender, CommandArgs args) {
        boolean all = args.length() == 0 || args.get(0).equals("*");

        EntityType type = null;
        if (!all) {
            type = args.getEntityType(0);

            if (isNotAMob(type)) {
                throw new CommandError(type.getName() + " n'est pas un mob.");
            }
        }

        boolean hasRadius = args.length() > 1;
        Vector from = new Vector(sender);
        int radius = args.getInteger(1, 0);
        radius *= radius;

        World world = args.getWorld(2, sender.getWorld());

        int count = 0;
        for (Entity entity : world.getEntities()) {
            EntityType entityType = entity.getType();
            if ((!all || isNotAMob(entityType))
                    && entityType != type) {
                continue;
            }

            if (hasRadius
                    && new Vector(entity).distanceSq(from) > radius) {
                continue;
            }

            count++;
            entity.remove();
        }

        sender.sendMessage(ChatColor.GREEN + "Vous avez supprimé "
                        + ChatColor.GOLD + count
                        + ChatColor.GREEN + " mobs");
    }

    private boolean isNotAMob(EntityType type) {
        return type.equals(EntityType.ARROW)
                || type.equals(EntityType.BOAT)
                || type.equals(EntityType.COMPLEX_PART)
                || type.equals(EntityType.DROPPED_ITEM)
                || type.equals(EntityType.EGG)
                || type.equals(EntityType.ENDER_CRYSTAL)
                || type.equals(EntityType.ENDER_PEARL)
                || type.equals(EntityType.EXPERIENCE_ORB)
                || type.equals(EntityType.FALLING_BLOCK)
                || type.equals(EntityType.FIREBALL)
                || type.equals(EntityType.FISHING_HOOK)
                || type.equals(EntityType.LIGHTNING)
                || type.equals(EntityType.MINECART)
                || type.equals(EntityType.PAINTING)
                || type.equals(EntityType.PLAYER)
                || type.equals(EntityType.PRIMED_TNT)
                || type.equals(EntityType.SMALL_FIREBALL)
                || type.equals(EntityType.SNOWBALL)
                || type.equals(EntityType.SPLASH_POTION)
                || type.equals(EntityType.THROWN_EXP_BOTTLE)
                || type.equals(EntityType.UNKNOWN)
                || type.equals(EntityType.WEATHER);
    }
}
