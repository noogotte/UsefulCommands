package fr.noogotte.useful_commands.command;

import static fr.noogotte.useful_commands.LocationUtil.getDistantLocation;
import static fr.noogotte.useful_commands.LocationUtil.getTargetBlockLocation;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
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
import fr.aumgn.bukkitutils.util.Util;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;

@NestedCommands("useful")
public class WorldCommands extends UsefulCommands {

    private UsefulCommandsPlugin plugin;

    public WorldCommands(UsefulCommandsPlugin plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Command(name = "seed", min = 0, max = 1)
    public void seed(CommandSender sender, CommandArgs args) {
        List<World> worlds = args.getWorlds(0).valueOr(sender);

        for (World world : worlds) {
            sender.sendMessage(msg("seed.message", world.getName(), world.getSeed()));
        }
    }

    @Command(name = "setspawn", min = 0, max = 2)
    public void setSpawn(CommandSender sender, CommandArgs args) {
        Vector position = args.getVector(0).valueOr(sender);
        World world = args.getWorld(0).valueOr(sender);
        world.setSpawnLocation(
                position.getBlockX(),
                position.getBlockY(),
                position.getBlockZ());
        sender.sendMessage(msg("setspawn.message"));
    }

    @Command(name = "time", min = 0, max = 2, flags = "i")
    public void time(CommandSender sender, CommandArgs args) {
        List<World> worlds = args.getWorlds(1).valueOr(sender);

        if (args.hasFlag('i') || args.length() == 0) {
            for (World world : worlds) {
                sender.sendMessage(msg("time.message.sendCurrentTime", world.getName(), worldTimeToString(world.getTime())));
            }
        }

        if (args.length() != 0) {
            int time = args.getTime(0).value();
            for (World world : worlds) {
                world.setTime(time);
                if (time == (24) * 1000) {
                    Util.broadcast("useful.world.time.broadcast",
                            msg("time.message.day", sender.getName(), world.getName()));
                }
                else if (time == (22 - 8 + 24) * 1000) {
                    Util.broadcast("useful.world.time.broadcast",
                            msg("time.message.night", sender.getName(), world.getName()));
                } else {
                    Util.broadcast("useful.world.time.broadcast",
                            msg("time.message.other", sender.getName(), world.getName()));
                }
            }
        }
    }

    public String worldTimeToString(long time) {
        int hours = (int) ((time / 1000 + 8) % 24);
        int minutes = (int) (60 * (time % 1000) / 1000);
        return String.format("%02d:%02d",
                hours, minutes, (hours % 12) == 0 ? 12 : hours % 12, minutes,
                        hours < 12 ? "am" : "pm");
    }

    @Command(name = "weather", min = 0, max = 2)
    public void weather(CommandSender sender, CommandArgs args) {
        List<World> worlds = args.getWorlds(1).valueOr(sender);    

        boolean toggle = args.length() == 0;
        boolean storm = false;
        if (!toggle) {
            String arg = args.get(0);
            if (arg.equalsIgnoreCase("sun")) {
                storm = false;
            } else if (arg.equalsIgnoreCase("storm")) {
                storm = true;
            } else {
                throw new CommandUsageError("Argument " + arg + " inconnu.");
            }
        }

        for (World world : worlds) {
            if (toggle) {
                storm = !world.hasStorm();
            }

            world.setStorm(storm);
            if (storm) {
                Util.broadcast("useful.weather.broadcast", msg("weather.storm", sender.getName(), world.getName()));
            } else {
                Util.broadcast("useful.weather.broadcast", msg("weather.sun", sender.getName(), world.getName()));
            }
        }
    }

    @Command(name = "spawnmob", flags = "tp", argsFlags = "d", min = 1, max = 3)
    public void spawnmob(Player sender, CommandArgs args) {
        EntityType entity = args.getEntityType(0).value();
        if (!entity.isSpawnable() && isNotAMob(entity)) {
            throw new CommandError(msg("entity.isNotAMob"));
        }

        int count = args.getInteger(1).valueOr(1);
        List<Location> locations = new ArrayList<Location>();
        if (args.hasFlag('t')) {
            for (Player target : args.getPlayers(2).valueOr(sender)) {
                Location location = getTargetBlockLocation(target, 180)
                        .toLocation(sender.getWorld());
                locations.add(location);
            }
        } else if (args.hasArgFlag('d')) {
            int distance = args.getInteger('d').value();
            for (Player target : args.getPlayers(2).valueOr(sender)) {
                Location location = getDistantLocation(target, distance)
                        .toLocation(sender.getWorld());
                locations.add(location);
            }
        } else if (args.hasFlag('p')) {
            Vector2D pos2D = args.getVector2D(2).value();
            Vector pos = pos2D.toHighest(sender.getWorld());
            locations.add(pos.toLocation(sender.getWorld()));
        } else {
            for (Player target : args.getPlayers(2).valueOr(sender)) {
                locations.add(target.getLocation());
            }
        }

        int totalCount = 0;
        for (int i = 0; i < count; i++) {
            for (Location location : locations) {
                location.getWorld().spawnEntity(location, entity);
                totalCount++;
            }
        }

        sender.sendMessage(msg("spawnmob.message", totalCount, entity.getName()));
    }

    @Command(name = "removemob", argsFlags = "wcp", min = 0, max = 2)
    public void removemob(CommandSender sender, CommandArgs args) {
        List<EntityType> types;
        if (args.length() == 0 || args.get(0).equals("*")) {
            types = null;
        } else {
            types = args.getList(0, EntityType.class).value();

            for (EntityType type : types) {
                if (isNotAMob(type)) {
                    throw new CommandError(msg("mob.isNotAMob", type.getName()));
                }
            }
        }

        boolean hasRadius = args.length() > 1;
        int radius = 0;
        Vector from = null;
        World world = null;

        if (hasRadius) {
            radius = args.getInteger(1).valueOr(1);
            radius *= radius;

            if (args.hasArgFlag('c')) {
                if (!args.hasArgFlag('w')) {
                    throw new CommandUsageError(msg("world.miss"));
                }
                from = args.getVector('c').value();
                world = args.getWorld('w').value();
            } else {
                Player target = args.getPlayer('p').valueOr(sender);
                from = new Vector(target);
                world = target.getWorld();
            }
        } else {
            if (args.hasArgFlag('p')) {
                world = args.getPlayer('p').valueOr(sender).getWorld();
            } else {
                world = args.getWorld('w').valueOr(sender);
            }
        }

        int count = 0;
        for (Entity entity : world.getEntities()) {
            EntityType entityType = entity.getType();
            if (isNotAMob(entityType)) {
                continue;
            }

            if (types != null && !types.contains(entityType)) {
                continue;
            }

            if (hasRadius && new Vector(entity).distanceSq(from) > radius) {
                continue;
            }

            count++;
            entity.remove();
        }

        sender.sendMessage(msg("removemob.message", count));
    }

    @Command(name = "position", min = 0, max = 1)
    public void getPos(CommandSender sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(0)
                .matchWithPermOr("useful.world.position.other", sender);

        for (Player target : targets) {
            Location location = target.getLocation();
            sender.sendMessage(ChatColor.UNDERLINE + "" + ChatColor.AQUA
                    + target.getName() + " :");
            sender.sendMessage(ChatColor.AQUA + "X: " + ChatColor.GREEN
                    + location.getX());
            sender.sendMessage(ChatColor.AQUA + "Y: " + ChatColor.GREEN
                    + location.getY());
            sender.sendMessage(ChatColor.AQUA + "Z: " + ChatColor.GREEN
                    + location.getZ());
            sender.sendMessage(ChatColor.AQUA + "Pitch: " + ChatColor.GREEN
                    + location.getPitch());
            sender.sendMessage(ChatColor.AQUA + "Yaw: " + ChatColor.GREEN
                    + location.getYaw());
        }
    }

    private boolean isNotAMob(EntityType type) {
        return type.equals(EntityType.ARROW) || type.equals(EntityType.BOAT)
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
