package fr.noogotte.useful_commands.component;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import fr.noogotte.useful_commands.UsefulCommandPlugin;

public class GodComponent extends Component implements Listener {

    private final Set<Player> gods;

    public GodComponent(UsefulCommandPlugin plugin) {
        super(plugin);
        gods = new HashSet<Player>();
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
    public void healthChange(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if(entity instanceof Player
                && gods.contains((Player) entity)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void foodLevelChange(FoodLevelChangeEvent event) {
        if(gods.contains(event.getEntity().getName())) {
            event.setCancelled(true);
        }
    }
}
