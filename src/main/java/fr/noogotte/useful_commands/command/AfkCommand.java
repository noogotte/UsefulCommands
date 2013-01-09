package fr.noogotte.useful_commands.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.util.Util;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;
import fr.noogotte.useful_commands.component.AfkComponent;

@NestedCommands("useful")
public class AfkCommand extends UsefulCommands {

    private AfkComponent afkcomponent;

    public AfkCommand(AfkComponent afkcomponent, UsefulCommandsPlugin plugin) {
    	super(plugin);
        this.afkcomponent = afkcomponent;
    }

    @Command(name = "afk", min = 0, max = 1)
    public void toggleAfk(CommandSender sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(0)
                .matchWithPermOr("useful.afk.other", sender);

        for (Player target : targets) {
            if (!afkcomponent.isAfk(target)) {
                afkcomponent.addPlayer(target);
                target.setPlayerListName(ChatColor.ITALIC + target.getName());
                Util.broadcast(msg("afk.broadcast.isAfk", target.getDisplayName()));
                target.sendMessage(msg("afk.target.isAfk"));
                target.setDisplayName("(AFK)" + target.getName());
                int cooldown = afkcomponent.getPlugin().getUsefulConfig().getCooldownBeforeKick();
                if (cooldown != 0) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(afkcomponent.getPlugin(),
                        kickAfterAfkCoolDown(target), cooldown);
                }
            } else if (afkcomponent.isAfk(target)) {
            	target.setDisplayName(target.getName());
                target.setPlayerListName(target.getName());
                afkcomponent.removePlayer(target);
                Util.broadcast(msg("afk.broadcast.isnotAfk", target.getDisplayName()));
                if (afkcomponent.isAfk(target)) {
                	target.sendMessage(msg("afk.target.isAfk"));
                }
            }

            if (!sender.equals(target)) {
                sender.sendMessage(msg("afk.sender.isAfk", target.getDisplayName()));
            }
        }
    }

    private Runnable kickAfterAfkCoolDown(final Player player) {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                if(afkcomponent.isAfk(player)) {
                    player.kickPlayer(msg("afk.kickAfterCoolDown"));
                    afkcomponent.getPlugin().getLogger().info(
                            msg("afk.kickAfterCooldown.console", player.getName()));
                }
            }
        };

        return run;
    }
}
