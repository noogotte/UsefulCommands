package fr.noogotte.useful_commands;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.geom.Direction;
import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.geom.Vector2D;
import fr.aumgn.bukkitutils.geom.direction.HorizontalDirection;

public final class LocationUtil {

    public static Vector getTargetBlockLocation(Player player, int maxDistance) {
        Block block = player.getTargetBlock(null, maxDistance);
        Vector2D pos = new Vector(block.getLocation()).to2D();
        return pos.toHighest(player.getWorld());
    }

    public static Vector getDistantLocation(Player player, int distance) {
        Vector pos = new Vector(player.getLocation());
        Direction dir = new HorizontalDirection(player.getLocation().getYaw());
        Vector dirVector = dir.getVector().multiply(distance);

        return pos.add(dirVector).to2D().toHighest(player.getWorld());
    }

    private LocationUtil() {
    }
}
