package fr.noogotte.useful_commands.component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.aumgn.bukkitutils.command.Commands;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;
import fr.noogotte.useful_commands.command.GodCommand;

public class GodComponent extends Component implements Listener {

    private final Set<Player> gods;

    public GodComponent(UsefulCommandsPlugin plugin) {
        super(plugin);
        gods = new HashSet<Player>();
    }

    @Override
    public String getName() {
        return "god";
    }

    @Override
    public List<Commands> getCommands() {
        return Collections.<Commands>singletonList(new GodCommand(this));
    }

    public boolean isGod(Player player) {
        return gods.contains(player);
    }

    public void setGod(Player player) {
        gods.add(player);
        if (gods.size() == 1) {
            registerEvents(this);
        }
    }

    public void removeGod(Player player) {
        gods.remove(player);
        if (gods.size() == 0) {
            unregisterEvents(this);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (isGod(player)) {
            removeGod(player);
        }
    }

    @EventHandler
    public void onHealthChange(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player && isGod((Player) entity)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        HumanEntity entity = event.getEntity();
        if (entity instanceof Player && isGod((Player) entity)) {
            event.setCancelled(true);
        }
    }
}
