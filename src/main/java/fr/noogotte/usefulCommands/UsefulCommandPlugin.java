package fr.noogotte.usefulCommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.aumgn.bukkitutils.command.CommandsRegistration;
import fr.aumgn.bukkitutils.command.messages.FrenchMessages;
import fr.noogotte.usefulCommands.command.WorldCommands;

public class UsefulCommandPlugin extends JavaPlugin {
	
	@Override
	public void onEnable() {
		CommandsRegistration registration = new CommandsRegistration(
                this, new FrenchMessages());
		registration.register(new WorldCommands());
	}
}