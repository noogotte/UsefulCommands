package fr.noogotte.useful_commands.component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.google.gson.reflect.TypeToken;

import fr.aumgn.bukkitutils.command.Commands;
import fr.aumgn.bukkitutils.gconf.GConfLoadException;
import fr.aumgn.bukkitutils.gconf.GConfLoader;
import fr.aumgn.bukkitutils.geom.Direction;
import fr.aumgn.bukkitutils.geom.Directions;
import fr.aumgn.bukkitutils.geom.Vector;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;
import fr.noogotte.useful_commands.command.HomeCommands;

public class HomeComponent extends Component {

	public static class Home {
		private final String worldName;
		private final Vector position;
		private final Direction direction;

		public Home(Location location) {
			this.worldName = location.getWorld().getName();
			this.position = new Vector(location);
			this.direction = Directions.fromLocation(location);
		}

		public Location toLocation() {
			World world = Bukkit.getWorld(worldName);
			return position.toLocation(world, direction);
		}
	}

	private final Map<String, Home> homes;
	public HomeComponent(UsefulCommandsPlugin plugin) {
		super(plugin);
		GConfLoader loader = plugin.getGConfLoader();
		homes = new HashMap<String, Home>();
		try {
			TypeToken<HashMap<String, Home>> typeToken =
					new TypeToken<HashMap<String, Home>>() {};
					homes.putAll(loader.loadOrCreate("homes.json", typeToken));
		} catch (GConfLoadException exc) {
			plugin.getLogger().severe("Unable to load homes.json.");
		}
	}

	@Override
	public String getName() {
		return "home";
	}

	@Override
	public List<Commands> getCommands() {
		return Collections.<Commands>singletonList(new HomeCommands(this));
	}

	public boolean haveHome(String player) {
		return homes.containsKey(player);
	}

	public Home getHome(String player) {
		return homes.get(player);
	}

	public void addHome(Player player, String player_) {
		homes.put(player_, new Home(player.getLocation()));
	}

	public void deleteHome(String player) {
		homes.remove(player);
		save();
	}

	public void save() {
		GConfLoader loader = plugin.getGConfLoader();
		try {
			loader.write("homes.json", homes);
		} catch (GConfLoadException exc) {
			plugin.getLogger().severe("Unable to save homes.json.");
		}
	}

	public Iterable<Entry<String, Home>> homes() {
		return homes.entrySet();
	}

	public boolean isEmpty() {
		return homes.isEmpty();
	}

	public void clearHomes() {
		homes.clear();
	}

	public int getNbHome() {
		return homes.size();
	}

	@Override
	public void onDisable() {
		save();
	}
}