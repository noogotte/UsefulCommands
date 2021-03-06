package fr.noogotte.useful_commands.component;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.aumgn.bukkitutils.command.Commands;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;
import fr.noogotte.useful_commands.command.VanishCommand;

public class VanishComponent extends Component implements Listener {

    private final Set<Player> vanish;

    public VanishComponent(UsefulCommandsPlugin plugin) {
        super(plugin);
        vanish = new HashSet<Player>();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public String getName() {
        return "vanish";
    }

    @Override
    public Commands[] getCommands() {
        return new Commands[] { new VanishCommand(this, plugin) };
    }

    public void addPlayer(Player player) {
        vanish.add(player);
    }

    public void removePlayer(Player player) {
        vanish.remove(player);
    }

    public boolean isVanish(Player player) {
        return vanish.contains(player);
    }

    public HashSet<Player> getVanishPLayer() {
        return new HashSet<Player>(vanish);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        for (Player vanishPlayer : vanish) {
            event.getPlayer().hidePlayer(vanishPlayer);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (isVanish(event.getPlayer())) {
            vanish.remove(event.getPlayer());
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.showPlayer(event.getPlayer());
            }
        }
    }
}
