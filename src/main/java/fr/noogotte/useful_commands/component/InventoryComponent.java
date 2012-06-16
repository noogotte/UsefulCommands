package fr.noogotte.useful_commands.component;

import java.util.Collections;
import java.util.List;

import fr.aumgn.bukkitutils.command.Commands;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;
import fr.noogotte.useful_commands.command.InventoryCommands;

public class InventoryComponent extends Component {

    public InventoryComponent(UsefulCommandsPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "inventory";
    }

    @Override
    public List<Commands> getCommands() {
        return Collections.<Commands>singletonList(new InventoryCommands());
    }
}
