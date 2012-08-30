package fr.noogotte.useful_commands.component;

import fr.aumgn.bukkitutils.command.Commands;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;
import fr.noogotte.useful_commands.command.PluginCommands;

public class PluginComponent extends Component {

    public PluginComponent(UsefulCommandsPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "plugin";
    }

    @Override
    public Commands[] getCommands() {
        return new Commands[] { new PluginCommands(plugin) };
    }
}