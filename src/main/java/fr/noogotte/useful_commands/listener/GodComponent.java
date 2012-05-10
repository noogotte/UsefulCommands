package fr.noogotte.useful_commands.listener;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import fr.noogotte.useful_commands.UsefulCommandPlugin;

public class GodComponent implements Listener {
	
	private UsefulCommandPlugin plugin;
	
	private Set<Player> gods = new HashSet<Player>();
	
	public GodComponent(Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	public void setGod(Player player) {
		gods.add(player);
	}
	
	public boolean isGod(Player player) {
		return gods.contains(player);
	}
	
	public void removeGod(Player player) {
		gods.remove(player);
	}
	
	public void setGodOther(Player target) {
		gods.add(target);
	}
	
	public boolean isGodOther(Player target) {
		return gods.contains(target);
	}
	
	public void removeGodOther(Player target) {
		gods.remove(target);
	}
	
	@EventHandler()
	public void healthChange(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		if(entity instanceof Player && gods.contains((Player) entity)) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler()
	public void foodLevelChange(FoodLevelChangeEvent event) {
		if(gods.contains(event.getEntity().getName())) {
			event.setCancelled(true);
		}
	}
}
