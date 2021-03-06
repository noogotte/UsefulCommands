package fr.noogotte.useful_commands.component;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.google.gson.reflect.TypeToken;

import fr.aumgn.bukkitutils.command.Commands;
import fr.aumgn.bukkitutils.geom.Direction;
import fr.aumgn.bukkitutils.geom.Directions;
import fr.aumgn.bukkitutils.geom.Vector;
import fr.aumgn.bukkitutils.gson.GsonLoadException;
import fr.aumgn.bukkitutils.gson.GsonLoader;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;
import fr.noogotte.useful_commands.command.WarpsCommands;

public class WarpsComponent extends Component {

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

    public WarpsComponent(UsefulCommandsPlugin plugin) {
        super(plugin);
        GsonLoader loader = plugin.getGsonLoader();
        warps = new HashMap<String, Warp>();
        try {
            TypeToken<HashMap<String, Warp>> typeToken =
                    new TypeToken<HashMap<String, Warp>>() {};
                    warps.putAll(loader.loadOrCreate("warps.json", typeToken));
        } catch (GsonLoadException exc) {
            plugin.getLogger().severe("Unable to load warps.json.");
        }
    }

    @Override
    public String getName() {
        return "warps";
    }

    @Override
    public Commands[] getCommands() {
        return new Commands[] { new WarpsCommands(this, plugin) };
    }

    @Override
    public void onDisable() {
        save();
    }

    public boolean isWarp(String name) {
        return warps.containsKey(name);
    }

    public Warp getWarp(String name) {
        return warps.get(name);
    }

    public void addWarp(String name, Location location) {
        warps.put(name, new Warp(location));
        save();
    }

    public void deleteWarp(String name) {
        warps.remove(name);
        save();
    }

    public Iterable<Entry<String, Warp>> warps() {
        return warps.entrySet();
    }

    public void save() {
        GsonLoader loader = plugin.getGsonLoader();
        try {
            loader.write("warps.json", warps);
        } catch (GsonLoadException exc) {
            plugin.getLogger().severe("Unable to save warps.json.");
        }
    }

    public boolean isEmpty() {
        return warps.isEmpty();
    }

    public void clearWarp() {
        warps.clear();
    }
}
