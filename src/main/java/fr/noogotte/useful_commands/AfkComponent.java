package fr.noogotte.useful_commands;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class AfkComponent implements Listener {

    private Set<Player> afks = new HashSet<Player>();

    public AfkComponent(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void addPlayer(Player player) {
        afks.add(player);
    }

    public void removePlayer(Player player) {
        afks.remove(player);
    }

    public boolean isAfk(Player player) {
        return afks.contains(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (isAfk(player)) {
            removePlayer(player);
        }
    }

    @EventHandler()
    public void onMove(PlayerMoveEvent event) {
        if (isAfk(event.getPlayer())) {
            Location location = event.getPlayer().getLocation();
            Location from = event.getFrom();  
            Location to = event.getTo();
            if (from.getBlockX() != to.getBlockX()
                    || from.getBlockY() != to.getBlockY()
                    || from.getBlockZ() != to.getBlockZ()
                    || !from.getWorld().equals(to.getWorld())) {
                event.setTo(location);
            }
        }
    }

    @EventHandler()
    public void onDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (isAfk(player)) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED
                    + "Vous êtes AFK, vous ne pouvez pas dropper d'objet !");
        }
    }

    @EventHandler()
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (isAfk(player)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED
                    + "Vous êtes AFK, vous ne pouvez pas interagir !");
        }
    }

    @EventHandler()
    public void onInteractEntity(PlayerInteractEntityEvent event) throws EventException {
        Player player = event.getPlayer();
        if (isAfk(player)) {
            event.setCancelled(true);  
            player.sendMessage(ChatColor.RED
                    + "Vous êtes AFK, vous ne pouvez pas interagir !");
        }
    }

    @EventHandler()
    public void onDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player && isAfk((Player) entity)) {
            event.setCancelled(true);
        }
    }


    @EventHandler()
    public void onRegainHealth(EntityRegainHealthEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player && isAfk((Player) entity)) {
            event.setCancelled(true);
        }
    }


    @EventHandler()
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player && isAfk((Player) entity)) {	
            event.setCancelled(true);
        }
    }

    @EventHandler() 
    public void onTarget(EntityTargetEvent event) {
        Entity entity = event.getTarget();
        if (entity instanceof Player && isAfk((Player) entity)) {
            event.setCancelled(true); 
        }
    }

    @EventHandler()
    public void onExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Creature) {
            LivingEntity target = ((Creature) entity).getTarget();
            if (target instanceof Player && isAfk((Player) target)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler()
    public void onPickupItem(PlayerPickupItemEvent event) {
        if(isAfk(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}

