package fr.noogotte.useful_commands.command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.noogotte.useful_commands.AfkComponent;
import fr.noogotte.useful_commands.GodComponent;

@NestedCommands(name = "useful")
public class PlayerInfoCommand extends UsefulCommands {
	
	private GodComponent godComponent;
	private AfkComponent afkComponent;

    public PlayerInfoCommand(GodComponent godComponent, AfkComponent afkComponent) {
        this.godComponent = godComponent;
        this.afkComponent = afkComponent;
    
    }
    
	@Command(name = "playerinfo", min = 1, max = 1)
    public void playerInfo(Player player, CommandArgs args) {
        Player target = args.getPlayer(0);

        player.sendMessage(ChatColor.GREEN + ""
                + ChatColor.UNDERLINE + "Info de "
                +  target.getName());
        player.sendMessage(ChatColor.GREEN +"Vie : "
                + ChatColor.AQUA + target.getHealth());
        player.sendMessage(ChatColor.GREEN +"Faim : "
                + ChatColor.AQUA + target.getFoodLevel());
        player.sendMessage(ChatColor.GREEN +"IP : "
                + ChatColor.AQUA + target.getAddress());
        Location loc = target.getLocation();
        player.sendMessage(ChatColor.GREEN + "Coordonnées : "
                + ChatColor.AQUA + loc.getBlockX()
                + ChatColor.GREEN + "," + ChatColor.AQUA + loc.getBlockY()
                + ChatColor.GREEN + "," + ChatColor.AQUA + loc.getBlockZ()
                + ChatColor.GREEN + " Monde : "
                + ChatColor.AQUA + target.getWorld().getName());
        player.sendMessage(ChatColor.GREEN +"Gamemode : "
                + ChatColor.AQUA + target.getGameMode());
        player.sendMessage(ChatColor.GREEN +"Expérience : "
                + ChatColor.AQUA + target.getLevel());
        
        if(godComponent.isGod(target)) {
        	player.sendMessage(ChatColor.GREEN + "Mode dieux : " + 
        			ChatColor.AQUA + "Oui");
        } else {
        	player.sendMessage(ChatColor.GREEN + "Mode dieux : " + 
        			ChatColor.AQUA + "Non");
        }
        
        if(afkComponent.isAfk(target)) {
        	player.sendMessage(ChatColor.GREEN + "AFK : " + 
        			ChatColor.AQUA + "Oui");
        } else {
        	player.sendMessage(ChatColor.GREEN + "AFK : " + 
        			ChatColor.AQUA + "Non");
        }
    }

}
