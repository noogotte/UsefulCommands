package fr.noogotte.useful_commands.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.util.Util;
import fr.noogotte.useful_commands.AfkComponent;
import fr.noogotte.useful_commands.GodComponent;

@NestedCommands(name = "useful")
public class AfkCommand extends UsefulCommands {
	
	private AfkComponent afkcomponent;

    public AfkCommand(AfkComponent afkcomponent) {
        this.afkcomponent = afkcomponent;
    }
    
    @Command(name = "afk", min = 0, max = 1)
    public void toggleAfk(Player player, CommandArgs args) {
    	List<Player> targets = args.getPlayers(0, player);
    	
    	for (Player target : targets) {
    		if(!afkcomponent.isAfk(target)) {
    			afkcomponent.addPlayer(target);
    			target.setPlayerListName(ChatColor.ITALIC + target.getName());
    			Util.broadcast(ChatColor.GOLD + target.getName() + 
    					ChatColor.GREEN + " est maintenant en AFK.");
    			target.sendMessage(ChatColor.GREEN + "Vous êtes AFK tapez" + 
    					ChatColor.GOLD + " /afk " + 
    					ChatColor.GREEN + "pour en resortir");
    		} else if (afkcomponent.isAfk(target)){
    			target.setPlayerListName(target.getName());
    			afkcomponent.removePlayer(target);
    			Util.broadcast(ChatColor.GOLD + target.getName() + 
    					ChatColor.GREEN + " n'est plus en AFK.");
    			target.sendMessage(ChatColor.GREEN + "Vous n'êtes plus en AFK.");
    		}
    		
    		if(!player.equals(target)) {
    			player.sendMessage(ChatColor.GREEN + "Vous avez mis " + 
    					ChatColor.GOLD + target.getName() + 
    					ChatColor.GREEN + " en Afk");
    		}
    	}
    }
}