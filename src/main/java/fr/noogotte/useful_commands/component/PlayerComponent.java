package fr.noogotte.useful_commands.component;

import java.util.ArrayList;
import java.util.List;

import fr.aumgn.bukkitutils.command.Commands;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;
import fr.noogotte.useful_commands.command.PlayerCommands;
import fr.noogotte.useful_commands.command.PlayerInfoCommand;

public class PlayerComponent extends Component {

    public PlayerComponent(UsefulCommandsPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "player";
    }

    @Override
    public List<Commands> getCommands() {
        List<Commands> commands = new ArrayList<Commands>();
        commands.add(new PlayerCommands());
        commands.add(new PlayerInfoCommand(plugin));

        return commands;
    }
}
