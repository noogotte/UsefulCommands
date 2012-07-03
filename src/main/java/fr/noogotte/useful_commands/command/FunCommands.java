package fr.noogotte.useful_commands.command;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;

@NestedCommands(name = "useful")
public class FunCommands extends UsefulCommands {

    public FunCommands(UsefulCommandsPlugin plugin) {
		super(plugin);
	}

	@Command(name = "rocket", min = 0, max = 2)
    public void rocket(CommandSender sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(0)
                .matchWithPermOr("useful.fun.rocket.other", sender);

        for (Player target : targets) {
            target.setVelocity(new Vector(0, 50, 0));
            target.sendMessage(msg("fun.rocket.target"));

            if (!sender.equals(target)) {
                sender.sendMessage(msg("fun.rocket.sender", target.getDisplayName()));
            }
        }
    }

    @Command(name = "strike", min = 0, max = 1)
    public void strike(CommandSender sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(0)
                .matchWithPermOr("useful.fun.strike.other", sender);

        for (Player target : targets) {
            target.getWorld().strikeLightning(target.getLocation());
            target.sendMessage(msg("fun.strike.target"));

            if (!sender.equals(target)) {
                sender.sendMessage(msg("fun.strike.sender", target.getDisplayName()));
            }
        }
    }
}
