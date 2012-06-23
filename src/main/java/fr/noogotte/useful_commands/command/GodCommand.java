package fr.noogotte.useful_commands.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.command.exception.CommandError;
import fr.noogotte.useful_commands.component.GodComponent;

@NestedCommands(name = "useful")
public class GodCommand extends UsefulCommands {

    private GodComponent godComponent;

    public GodCommand(GodComponent godComponent) {
        this.godComponent = godComponent;
    }

    @Command(name = "god", min = 0, max = 1)
    public void god(CommandSender sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(0)
                .match(sender, "useful.god.other");

        for (Player target : targets) {
            if (godComponent.isGod(target)) {
                if (!sender.equals(target)) {
                    throw new CommandError(target.getName()
                            + " est déja en mode dieu.");
                }
                throw new CommandError("Vous êtes déja en mode dieu.");
            } else {
                godComponent.setGod(target);
                target.sendMessage(ChatColor.BLUE + "Vous êtes en mode dieu.");
                if (!sender.equals(target)) {
                    sender.sendMessage(ChatColor.GREEN + target.getName()
                            + ChatColor.BLUE + " est en mode dieu.");
                }
            }
        }
    }

    @Command(name = "ungod", min = 0, max = 1)
    public void ungod(CommandSender sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(0)
                .match(sender, "useful.ungod.other");

        for (Player target : targets) {
            if (!godComponent.isGod(target)) {
                if (!sender.equals(target)) {
                    throw new CommandError(target.getName()
                            + " n'est pas en mode dieu.");
                }
                throw new CommandError("Vous n'êtes pas en mode dieu.");
            } else {
                godComponent.removeGod(target);
                target.sendMessage(ChatColor.BLUE
                        + "Vous n'êtes plus en mode dieu.");
                if (!sender.equals(target)) {
                    sender.sendMessage(ChatColor.GREEN + target.getName()
                            + ChatColor.BLUE + " n'est plus en mode dieu.");
                }
            }
        }
    }
}
