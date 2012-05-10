package fr.noogotte.usefulCommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.world.WorldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class UsefulCommandsMain extends JavaPlugin {

	
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player))
	    {
	      sender.sendMessage("Commande utilisable seulement en tant que joueur");
	      return true;
	    }
		
		if (args.length > 3)
	    {
	      return false;
	    }
		
		if (label.equalsIgnoreCase("gamemode")) {
			UsefulCommand.gamemode(sender);
		} else if (label.equalsIgnoreCase("sun")) {
			UsefulCommand.sun(sender);
		} else if (label.equalsIgnoreCase("storm")) {
			UsefulCommand.storm(sender);
		} else if (label.equalsIgnoreCase("day")) {
			UsefulCommand.day(sender);
		} else if (label.startsWith("id")){
			UsefulCommand.id(sender);
		} else if(label.equalsIgnoreCase("clear")) {
			UsefulCommand.clear(sender);
		} else if (label.equalsIgnoreCase("heal")) {
			UsefulCommand.heal(sender);
		} else if (label.equalsIgnoreCase("suicide")) {
			UsefulCommand.suicide(sender);
		} else if (label.equalsIgnoreCase("tphere")) {
			UsefulCommand.tphere(sender, args);
		} else if (label.equalsIgnoreCase("teleporte")) {
			UsefulCommand.tp(sender, args);
		} else if (label.equalsIgnoreCase("potion")) {
			UsefulCommand.potion(sender, args);
		} else if (label.equalsIgnoreCase("spawnmob")) {
			UsefulCommand.spawnmob(sender, args);
		}
		return true;
	}
}
