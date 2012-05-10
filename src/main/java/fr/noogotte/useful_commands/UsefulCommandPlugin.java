package fr.noogotte.useful_commands;

import org.bukkit.plugin.java.JavaPlugin;

import fr.aumgn.bukkitutils.command.CommandsRegistration;
import fr.aumgn.bukkitutils.command.messages.FrenchMessages;
import fr.noogotte.useful_commands.command.WorldCommands;

public class UsefulCommandPlugin extends JavaPlugin {
	
	@Override
	public void onEnable() {
		CommandsRegistration registration = new CommandsRegistration(
                this, new FrenchMessages());
		registration.register(new WorldCommands());
	}
}