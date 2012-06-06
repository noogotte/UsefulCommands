package fr.noogotte.useful_commands;

import org.bukkit.plugin.java.JavaPlugin;

import fr.aumgn.bukkitutils.command.CommandsRegistration;
import fr.aumgn.bukkitutils.command.messages.FrenchMessages;
import fr.noogotte.useful_commands.command.AfkCommand;
import fr.noogotte.useful_commands.command.GodCommand;
import fr.noogotte.useful_commands.command.PlayerCommands;
import fr.noogotte.useful_commands.command.TeleportCommands;
import fr.noogotte.useful_commands.command.WarpsCommand;
import fr.noogotte.useful_commands.command.WorldCommands;

public class UsefulCommandPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        GodComponent godComponent = new GodComponent(this);
        WarpsComponent warpcomponent = new WarpsComponent();
        AfkComponent afkccomponent = new AfkComponent(this);

        CommandsRegistration registration = new CommandsRegistration(
                this, new FrenchMessages());
        registration.register(new WorldCommands());
        registration.register(new PlayerCommands());
        registration.register(new GodCommand(godComponent));
        registration.register(new TeleportCommands());
        registration.register(new WarpsCommand(warpcomponent));
        registration.register(new AfkCommand(afkccomponent));
    }
}