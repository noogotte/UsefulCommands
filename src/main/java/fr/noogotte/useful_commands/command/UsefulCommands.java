package fr.noogotte.useful_commands.command;

import fr.aumgn.bukkitutils.command.Commands;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;

public class UsefulCommands implements Commands {

    private UsefulCommandsPlugin plugin;

    public UsefulCommands(UsefulCommandsPlugin plugin) {
        this.plugin = plugin;
    }

    protected String msg(String key) {
        return plugin.getMessages().get(key);
    }

    protected String msg(String key, Object... arguments) {
        return plugin.getMessages().get(key, arguments);
    }
}