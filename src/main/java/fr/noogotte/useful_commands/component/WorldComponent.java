package fr.noogotte.useful_commands.component;

import java.util.Collections;
import java.util.List;

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
    public List<Commands> getCommands() {
        return Collections.<Commands>singletonList(new WorldCommands());
    }
}
