package fr.noogotte.useful_commands.command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.noogotte.useful_commands.component.AfkComponent;
import fr.noogotte.useful_commands.component.GodComponent;

@NestedCommands(name = "useful")
public class PlayerInfoCommand extends UsefulCommands {

    private final GodComponent godComponent;
    private final AfkComponent afkComponent;

    public PlayerInfoCommand(GodComponent godComponent, AfkComponent afkComponent) {
        this.godComponent = godComponent;
        this.afkComponent = afkComponent;
    }

    @Command(name = "playerinfo", min = 1, max = 1)
    public void playerInfo(CommandSender sender, CommandArgs args) {
        Player target = args.getPlayer(0).value(sender);

        sender.sendMessage(ChatColor.GREEN + ""
                + ChatColor.UNDERLINE + "Info de "
                +  target.getName());
        sender.sendMessage(ChatColor.GREEN +"Vie : "
                + ChatColor.AQUA + target.getHealth());
        sender.sendMessage(ChatColor.GREEN +"Faim : "
                + ChatColor.AQUA + target.getFoodLevel());
        sender.sendMessage(ChatColor.GREEN +"IP : "
                + ChatColor.AQUA + target.getAddress());
        Location loc = target.getLocation();
        sender.sendMessage(ChatColor.GREEN + "Coordonnées : "
                + ChatColor.AQUA + loc.getBlockX()
                + ChatColor.GREEN + "," + ChatColor.AQUA + loc.getBlockY()
                + ChatColor.GREEN + "," + ChatColor.AQUA + loc.getBlockZ()
                + ChatColor.GREEN + " Monde : "
                + ChatColor.AQUA + target.getWorld().getName());
        sender.sendMessage(ChatColor.GREEN +"Gamemode : "
                + ChatColor.AQUA + target.getGameMode());
        sender.sendMessage(ChatColor.GREEN +"Expérience : "
                + ChatColor.AQUA + target.getLevel());

        if (godComponent != null) {
            if (godComponent.isGod(target)) {
                sender.sendMessage(ChatColor.GREEN + "Mode dieux : "
                        + ChatColor.AQUA + "Oui");
            } else {
                sender.sendMessage(ChatColor.GREEN + "Mode dieux : "
                        + ChatColor.AQUA + "Non");
            }
        }

        if (afkComponent != null) {
            if (afkComponent.isAfk(target)) {
                sender.sendMessage(ChatColor.GREEN + "AFK : "
                        + ChatColor.AQUA + "Oui");
            } else {
                sender.sendMessage(ChatColor.GREEN + "AFK : "
                        + ChatColor.AQUA + "Non");
            }
        }
    }
}
