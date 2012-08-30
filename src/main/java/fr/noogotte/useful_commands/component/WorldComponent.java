package fr.noogotte.useful_commands.component;

import fr.aumgn.bukkitutils.command.Commands;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;
import fr.noogotte.useful_commands.command.WorldCommands;

public class WorldComponent extends Component {

    public WorldComponent(UsefulCommandsPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "world";
    }

    @Override
    public Commands[] getCommands() {
        return new Commands[] { new WorldCommands(plugin) };
    }
}
