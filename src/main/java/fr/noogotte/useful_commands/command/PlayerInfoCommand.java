package fr.noogotte.useful_commands.command;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;
import fr.noogotte.useful_commands.component.AfkComponent;
import fr.noogotte.useful_commands.component.GodComponent;

@NestedCommands(name = "useful")
public class PlayerInfoCommand extends UsefulCommands {

    private final UsefulCommandsPlugin plugin;

    public PlayerInfoCommand(UsefulCommandsPlugin plugin) {
    	super(plugin);
        this.plugin = plugin;
    }

    @Command(name = "playerinfo", min = 1, max = 1)
    public void playerInfo(CommandSender sender, CommandArgs args) {
        Player target = args.getPlayer(0).valueOr(sender);
        String reponse;

        sender.sendMessage(msg("info.top", target.getDisplayName()));
        sender.sendMessage(msg("info.health", target.getHealth()));
        sender.sendMessage(msg("info.hunger", target.getFoodLevel()));
        sender.sendMessage(msg("info.ip", target.getAddress()));
        Location loc = target.getLocation();
        sender.sendMessage(msg("info.position", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), target.getWorld().getName()));
        sender.sendMessage(msg("info.gamemode", target.getGameMode()));
        sender.sendMessage(msg("info.experience", target.getLevel()));
        
        if (target.isOnline()) {
        	reponse = msg("info.reponse.yes");
        } else {
        	reponse = msg("info.reponse.no");
        }
        
        sender.sendMessage(msg("info.isOp", reponse));

        GodComponent godComponent = plugin.getComponent(GodComponent.class);
        if (godComponent != null) {
        	if(godComponent.isGod(target)) {
        		reponse = msg("info.reponse.yes");
        	} else {
        		reponse = msg("info.reponse.no");
        	}

        	sender.sendMessage(msg("info.isInGodMode", reponse));
        }

        AfkComponent afkComponent = plugin.getComponent(AfkComponent.class);
        if (afkComponent != null) {
        	if (afkComponent.isAfk(target)) {
        		reponse = msg("info.reponse.yes");
        	} else {
        		reponse = msg("info.reponse.no");
        	}

        	sender.sendMessage(msg("info.isAfk", reponse));
        }
    }
}
