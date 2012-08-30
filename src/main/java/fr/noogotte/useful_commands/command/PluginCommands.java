package fr.noogotte.useful_commands.command;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.command.exception.CommandError;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;

@NestedCommands("useful")
public class PluginCommands extends UsefulCommands {

    private UsefulCommandsPlugin plugin;

    public PluginCommands(UsefulCommandsPlugin plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Command(name = "disable", min = 1, max = 1)
    public void disablePlugin(CommandSender sender, CommandArgs args) {
        String pluginName = args.get(0);
        Plugin pluginToDisable = plugin.getServer().getPluginManager().getPlugin(pluginName);

        if (pluginToDisable == null) {
            throw new CommandError(msg("plugin.isNotAPlugin", pluginName));
        }

        if (!pluginToDisable.isEnabled()) {
            throw new CommandError(msg("disable_plugin.isAlreadyDisable_€", pluginToDisable));
        }

        plugin.getServer().getPluginManager().disablePlugin(pluginToDisable);
        sender.sendMessage(msg("disable_plugin.success", pluginToDisable));
    }

    @Command(name = "enable", min = 1, max = 1)
    public void enable(CommandSender sender, CommandArgs args) {
        String pluginName = args.get(0);
        Plugin pluginToEnable = plugin.getServer().getPluginManager().getPlugin(pluginName);

        if (pluginToEnable == null) {
            throw new CommandError(msg("plugin.isNotAPlugin", pluginName));
        }

        if (pluginToEnable.isEnabled()) {
            throw new CommandError(msg("enable_plugin.isAlreadyEnable_€", pluginToEnable));
        }

        plugin.getServer().getPluginManager().enablePlugin(pluginToEnable);
        sender.sendMessage(msg("enable_plugin.success", pluginToEnable));
    }

    @Command(name = "info-plugin", min = 1, max = 1)
    public void pluginInfo(CommandSender sender, CommandArgs args) {
        String pluginName = args.get(0);
        Plugin pluginToEnable = plugin.getServer().getPluginManager().getPlugin(pluginName);

        if (pluginToEnable == null) {
            throw new CommandError(msg("plugin.isNotAPlugin", pluginName));
        }

        sender.sendMessage(msg("info_plugin", pluginToEnable.getName(),
                pluginToEnable.isEnabled() ? "Enable" : "Disable"));
    }
}