package fr.noogotte.useful_commands.command;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.command.exception.CommandError;
import fr.aumgn.bukkitutils.geom.Vector;
import fr.noogotte.useful_commands.GodComponent;
import fr.noogotte.useful_commands.WarpsComponent;

@NestedCommands(name = "useful")
public class WarpsCommand extends UsefulCommands {
	
	private WarpsComponent warpscomponent;

    public WarpsCommand(WarpsComponent warpscomponent) {
        this.warpscomponent = warpscomponent;
    }
    
	@Command(name = "setwarp", min = 1, max = 1)
	public void createWarp(Player player, CommandArgs args) {
		if(warpscomponent.isWarp(args.get(0))) {
			throw new CommandError("Le warp " + args.get(0) + " existe déjà.");
		} else {
			Location location = player.getLocation();
			warpscomponent.addWarp(args.get(0), location);		
			player.sendMessage("Vous avez créé un warp : " + args.get(0));
		}
	}
	
	@Command(name = "warp", min = 1, max = 1)
	public void teleporttoWarp(Player player, CommandArgs args) {
		
	}
	
	@Command(name = "deletewarp", min = 1, max = 1)
	public void deleteWarp(Player player, CommandArgs args) {
		if(!warpscomponent.isWarp(args.get(0))) {
			String arg = args.get(0);
			throw new CommandError("Le warp " + arg + " n'existe pas.");
		} else {
			warpscomponent.deleteWarp(args.get(0));
			player.sendMessage("Vous avez supprimé le warp : " + args.get(0));
		}	
	}
}
