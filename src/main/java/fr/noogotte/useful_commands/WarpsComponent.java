package fr.noogotte.useful_commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Commands;
import fr.aumgn.bukkitutils.geom.Direction;
import fr.aumgn.bukkitutils.geom.Directions;
import fr.aumgn.bukkitutils.geom.Vector;

public class WarpsComponent {
    
    public static class Warp {
        private final String worldName;
        private final Vector position;
        private final Direction direction;

        public Warp(Location location) {
            this.worldName = location.getWorld().getName();
            this.position = new Vector(location);
            this.direction = Directions.fromLocation(location);
        }

        public Location toLocation() {
            World world = Bukkit.getWorld(worldName);
            return position.toLocation(world, direction);
        }
    }
    
    private final Map<String, Warp> warps;

    public WarpsComponent() {
        this.warps = new HashMap<String, Warp>();
    }
    
    public boolean isWarp(String name) {
    	return warps.containsKey(name);
    }
    public void addWarp(String name, Location location) {
        warps.put(name, new Warp(location));
    }
    
    public void deleteWarp(String name) {
    	warps.remove(name);
    }
}
