package fr.noogotte.useful_commands.command;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.command.exception.CommandError;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;
import fr.noogotte.useful_commands.component.GodComponent;

@NestedCommands("useful")
public class GodCommand extends UsefulCommands {

    private GodComponent godComponent;

    public GodCommand(GodComponent godComponent, UsefulCommandsPlugin plugin) {
    	super(plugin);
        this.godComponent = godComponent;
    }

    @Command(name = "god", min = 0, max = 1)
    public void god(CommandSender sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(0)
                .matchWithPermOr("useful.god.other", sender);

        for (Player target : targets) {
            if (godComponent.isGod(target)) {
                if (!sender.equals(target)) {
                    throw new CommandError(msg("god.isAlreadyInGodMode.sender_€", target.getDisplayName()));
                } else {
                	throw new CommandError(msg("god.isAlreadyInGodMode.target_€"));
                }
            } else {
                godComponent.setGod(target);
                target.sendMessage(msg("god.target.isGod"));
                if (!sender.equals(target)) {
                    sender.sendMessage(msg("god.sender.isGod", target.getDisplayName()));
                }
            }
        }
    }

    @Command(name = "ungod", min = 0, max = 1)
    public void ungod(CommandSender sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(0)
                .matchWithPermOr("useful.ungod.other", sender);

        for (Player target : targets) {
            if (!godComponent.isGod(target)) {
                if (!sender.equals(target)) {
                    throw new CommandError(msg("god.isNotInGodMode.sender_€", target.getDisplayName()));
                } else {
                	throw new CommandError(msg("god.isNotInGodMode.target_€"));
                }
            } else {
                godComponent.removeGod(target);
                target.sendMessage(msg("god.target.isnotGod"));
                if (!sender.equals(target)) {
                    sender.sendMessage(msg("god.sender.isnotGod", target.getDisplayName()));
                }
            }
        }
    }
}
