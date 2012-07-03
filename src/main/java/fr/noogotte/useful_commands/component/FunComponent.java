package fr.noogotte.useful_commands.component;

import java.util.Collections;
import java.util.List;

import fr.aumgn.bukkitutils.command.Commands;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;
import fr.noogotte.useful_commands.command.FunCommands;

public class FunComponent extends Component {

    public FunComponent(UsefulCommandsPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "fun";
    }

    @Override
    public List<Commands> getCommands() {
        return Collections.<Commands>singletonList(new FunCommands(plugin));
    }
}
