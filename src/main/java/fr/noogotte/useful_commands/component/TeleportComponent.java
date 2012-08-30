package fr.noogotte.useful_commands.component;

import fr.aumgn.bukkitutils.command.Commands;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;
import fr.noogotte.useful_commands.command.TeleportCommands;

public class TeleportComponent extends Component {

    public TeleportComponent(UsefulCommandsPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "teleport";
    }

    @Override
    public Commands[] getCommands() {
        return new Commands[] { new TeleportCommands(plugin) };
    }
}
