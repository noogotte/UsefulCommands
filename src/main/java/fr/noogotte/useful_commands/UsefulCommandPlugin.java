package fr.noogotte.useful_commands;

import org.bukkit.plugin.java.JavaPlugin;

import fr.aumgn.bukkitutils.command.CommandsRegistration;
import fr.aumgn.bukkitutils.command.messages.FrenchMessages;
import fr.noogotte.useful_commands.command.GodCommand;
import fr.noogotte.useful_commands.command.PlayerCommands;
import fr.noogotte.useful_commands.command.WorldCommands;
import fr.noogotte.useful_commands.listener.GodComponent;

public class UsefulCommandPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        GodComponent godComponent = new GodComponent(this);

        CommandsRegistration registration = new CommandsRegistration(
                this, new FrenchMessages());
        registration.register(new WorldCommands());
        registration.register(new PlayerCommands());
        registration.register(new GodCommand(godComponent));
    }
}