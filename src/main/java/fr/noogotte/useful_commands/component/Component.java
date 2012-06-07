package fr.noogotte.useful_commands.component;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import fr.noogotte.useful_commands.UsefulCommandPlugin;

public abstract class Component {

    protected final UsefulCommandPlugin plugin;

    public Component(UsefulCommandPlugin plugin) {
        this.plugin = plugin;
    }

    public void onDisable() {
    }

    public void registerEvents(Listener... listeners) {
        PluginManager pm = Bukkit.getPluginManager();
        for (Listener listener : listeners) {
            pm.registerEvents(listener, plugin);
        }
    }

    public void unregisterEvents(Listener... listeners) {
        for (Listener listener : listeners) {
            HandlerList.unregisterAll(listener);
        }
    }
}
