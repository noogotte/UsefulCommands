package fr.noogotte.useful_commands.command;

import fr.aumgn.bukkitutils.command.Commands;
import fr.aumgn.bukkitutils.localization.PluginResourceBundles;
import fr.aumgn.bukkitutils.localization.bundle.PluginResourceBundle;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;

public class UsefulCommands implements Commands {

	private UsefulCommandsPlugin plugin;

	public UsefulCommands(UsefulCommandsPlugin plugin) {
		this.plugin = plugin;
	}

	protected String msg(String key) {
		return getBundle().get(key);
	}

	protected String msg(String key, Object... arguments) {
		return getBundle().get(key, arguments);
	}

	private PluginResourceBundle getBundle() {
		PluginResourceBundles bundles = new PluginResourceBundles(plugin);
		return bundles.get("messages");
	}
}