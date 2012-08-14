package fr.noogotte.useful_commands.component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.aumgn.bukkitutils.command.Commands;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;
import fr.noogotte.useful_commands.command.AfkCommand;

public class AfkComponent extends Component implements Listener {

    private final Set<Player> afks;

    public AfkComponent(UsefulCommandsPlugin plugin) {
        super(plugin);
        afks = new HashSet<Player>();
    }

    @Override
    public String getName() {
        return "afk";
    }

    @Override
    public List<Commands> getCommands() {
        return Collections.<Commands>singletonList(new AfkCommand(this));
    }

    public void addPlayer(Player player) {
        afks.add(player);
        if (afks.size() == 1) {
            registerEvents(this);
        }
    }

    public void removePlayer(Player player) {
        afks.remove(player);
        if (afks.size() == 0) {
            unregisterEvents(this);
        }
    }

    public boolean isAfk(Entity entity) {
        return entity instanceof Player
                && isAfk((Player) entity);
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

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (isAfk(event.getPlayer())) {
            Location from = event.getFrom();
            Location to = event.getTo();
            if (from.getBlockX() != to.getBlockX()
                    || from.getBlockY() != to.getBlockY()
                    || from.getBlockZ() != to.getBlockZ()
                    || !from.getWorld().equals(to.getWorld())) {
                event.setTo(from);
            }
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (isAfk(player)) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED
                    + "Vous êtes AFK, vous ne pouvez pas dropper d'objet !");
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (isAfk(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (isAfk(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent event) {
        Entity entity = event.getEntity();
        if (isAfk(entity)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        Entity entity = event.getEntity();
        if (isAfk(entity)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onTarget(EntityTargetEvent event) {
        Entity entity = event.getTarget();
        if (isAfk(entity)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Creature) {
            LivingEntity target = ((Creature) entity).getTarget();
            if (target instanceof Player && isAfk((Player) target)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent event) {
        if (isAfk(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void entityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damagerEntity = event.getDamager();
        if (isAfk(damagerEntity)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void entityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (isAfk(entity)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (isAfk(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(
                    ChatColor.RED + "Vous ne pouvez pas parler, vous êtes AFK");
        }
    }

    @EventHandler
    public void onClicked(InventoryClickEvent event) {
        HumanEntity entity = event.getWhoClicked();
        if (!(entity instanceof Player)) {
            return;
        }

        Player player = (Player) entity;
        if (event.getView().getType() == InventoryType.PLAYER
                && player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if (isAfk(player)) {
            event.setCancelled(true);
        }
    }
}
