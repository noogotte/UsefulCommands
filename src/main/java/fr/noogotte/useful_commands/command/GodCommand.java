package fr.noogotte.useful_commands.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.noogotte.useful_commands.component.GodComponent;

@NestedCommands(name = "useful")
public class GodCommand extends UsefulCommands {

    private GodComponent godComponent;

    public GodCommand(GodComponent godComponent) {
        this.godComponent = godComponent;
    }

    @Command(name = "god", min = 0, max = 1)
    public void god(CommandSender sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(0).match(sender, "useful.god.other");

        for (Player target : targets) {
            if (godComponent.isGod(target)) {
                godComponent.removeGod(target);
                target.sendMessage(ChatColor.AQUA
                        + "Mode dieu arreté.");
                if (!sender.equals(target)) {
                    sender.sendMessage(ChatColor.AQUA
                            + "Mode Dieu arreté pour "
                            + ChatColor.BLUE + target.getName());
                }
            } else {
                godComponent.setGod(target);
                target.sendMessage(ChatColor.GREEN
                        + "Vous êtes en mode Dieu, tapez "
                        + ChatColor.BLUE + " /god "
                        + ChatColor.GREEN + " pour en ressortir.");
                if (!sender.equals(target)) {
                    sender.sendMessage(ChatColor.AQUA
                            + "Vous avez activé le mode Dieu pour "
                            + ChatColor.BLUE + target.getName());
                }
            }
        }
    }
}