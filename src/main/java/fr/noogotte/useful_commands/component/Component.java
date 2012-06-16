package fr.noogotte.useful_commands.component;

import java.util.Collections;
import java.util.List;

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

    public abstract String getName();

    public List<Commands> getCommands() {
        return Collections.<Commands>emptyList();
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
