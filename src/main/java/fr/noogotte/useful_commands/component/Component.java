package fr.noogotte.useful_commands.component;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import fr.aumgn.bukkitutils.command.Commands;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;

public abstract class Component {

    protected final UsefulCommandsPlugin plugin;

    public Component(UsefulCommandsPlugin plugin) {
        this.plugin = plugin;
    }

    public UsefulCommandsPlugin getPlugin() {
        return plugin;
    }

    public abstract String getName();

    public Commands[] getCommands() {
        return new Commands[0];
    }

    public void onEnable() {
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
