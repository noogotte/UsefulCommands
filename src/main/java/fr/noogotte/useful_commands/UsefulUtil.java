package fr.noogotte.useful_commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UsefulUtil {
	
	public static void broadcastToOp(String message) {
    	for(Player player : Bukkit.getOnlinePlayers()) {
    		if(player.isOp()) {
    			player.sendMessage(message);
    		}
    	}
    }
}
