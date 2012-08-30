package fr.noogotte.useful_commands.component;

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
    public Commands[] getCommands() {
        return new Commands[] { new InventoryCommands(plugin) };
    }
}
