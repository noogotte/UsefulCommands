package fr.noogotte.useful_commands;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.plugin.Plugin;

public class GodComponent implements Listener {

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
