package fr.noogotte.useful_commands.component;

import fr.aumgn.bukkitutils.command.Commands;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;
import fr.noogotte.useful_commands.command.InfoServerCommands;
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
    public Commands[] getCommands() {
        return new Commands[] { 
            new PlayerCommands(plugin),
            new PlayerInfoCommand(plugin),
            new InfoServerCommands(plugin)
        };
    }
}
