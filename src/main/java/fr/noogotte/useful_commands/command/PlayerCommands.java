package fr.noogotte.useful_commands.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.util.Util;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;
import fr.noogotte.useful_commands.event.DisplayNameLookupEvent;

@NestedCommands(name = "useful")
public class PlayerCommands extends UsefulCommands {

    public PlayerCommands(UsefulCommandsPlugin plugin) {
		super(plugin);
	}

	@Command(name = "gamemode", flags = "cs", min = 0, max = 1)
    public void gamemode(CommandSender sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(0)
                .matchWithPermOr("useful.player.gamemode.other", sender);

        boolean force = args.hasFlags();
        GameMode gameMode = null;
        if (args.hasFlag('c')) {
            gameMode = GameMode.CREATIVE;
        } else {
            gameMode = GameMode.SURVIVAL;
        }

        for (Player target : targets) {
            if (force && target.getGameMode() == gameMode) {
                continue;
            }

            if (target.getGameMode() == GameMode.CREATIVE) {
                target.setGameMode(GameMode.SURVIVAL);
            } else {
                target.setGameMode(GameMode.CREATIVE);
            }

            target.sendMessage(msg("gamemode.target", target.getGameMode()));
            if (!sender.equals(target)) {
                sender.sendMessage(msg("gamemode.sender", target.getDisplayName(), target.getGameMode()));
            }
        }
    }

    @Command(name = "heal", flags = "hf", min = 0, max = 1)
    public void heal(CommandSender sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(0)
                .matchWithPermOr("useful.player.heal.other", sender);
        boolean health = !args.hasFlags() || args.hasFlag('h');
        boolean food = !args.hasFlags() || args.hasFlag('f');

        for (Player target : targets) {
            if (health) {
                target.setHealth(20);
            }
            if (food) {
                target.setFoodLevel(20);
            }

            target.sendMessage(msg("heal.target"));
            if (!sender.equals(target)) {
                sender.sendMessage(msg("heal.sender", target.getDisplayName()));
            }
        }
    }

    @Command(name = "kick", flags = "o", min = 1, max = 2)
    public void kick(CommandSender sender, CommandArgs args) {
        String reason = args.get(1, "Kicked !");
        boolean dontKickOps = !args.hasFlag('o');

        for (Player target : args.getPlayers(0).match()) {
            if (dontKickOps && target.isOp()) {
                sender.sendMessage(msg("kick.dontKickOp", target.getDisplayName()));
            } else {
                target.kickPlayer(reason);
                Util.broadcast(msg("kick.broadcast", target.getName(), sender.getName(), reason));
            }
        }
    }

    @Command(name = "fly", min = 0, max = 1)
    public void fly(CommandSender sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(0)
                .matchWithPermOr("useful.player.fly.other", sender);
        for (Player target : targets) {
            if (target.isFlying()) {
                target.setAllowFlight(false);
                target.setFlying(false);
            } else {
                target.setAllowFlight(true);
                target.setFlying(true);
            }
            if (target.isFlying()) {
                sender.sendMessage(msg("fly.target.isFlying"));
            } else {
                sender.sendMessage(msg("fly.target.isNotFlying"));
            }

            if (!sender.equals(target)) {
                if (target.isFlying()) {
                    sender.sendMessage(msg("fly.sender.isFlying", target.getDisplayName()));
                } else {
                    sender.sendMessage(msg("fly.target.isNotFlying", target.getDisplayName()));
                }
            }
        }
    }

    @Command(name = "kill", min = 0, max = 1)
    public void kill(CommandSender sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(0)
                .matchWithPermOr("useful.player.kill.other", sender);
        for (Player target : targets) {
            target.setHealth(0);

            if (!sender.equals(target)) {
                sender.sendMessage(msg("kill.sender", target.getDisplayName()));
            }
        }
    }

    @Command(name = "effect", min = 1, max = 3)
    public void effect(CommandSender sender, CommandArgs args) {
        PotionEffectType effect = args.getPotionEffectType(0).value();
        Integer duration = args.getInteger(1).valueOr(60);
        PotionEffect newEffect = new PotionEffect(effect, duration * 20, 1);

        List<Player> targets = args.getPlayers(2)
                .matchWithPermOr("useful.player.effect.other", sender);

        for (Player target : targets) {
            target.addPotionEffect(newEffect, true);
            sender.sendMessage(msg("effect.target", effect.getName().toLowerCase()));

            if (!sender.equals(target)) {
                sender.sendMessage(msg("effect.sender", target.getDisplayName(), effect.getName().toLowerCase(), effect.getDurationModifier()));
            }
        }
    }

    @Command(name = "rename", min = 1, max = 2)
    public void rename(CommandSender sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(1)
                .matchWithPermOr("useful.player.rename.other", sender);
        boolean reset = args.get(0).equals("reset");

        for (Player target : targets) {
            if (reset) {
                DisplayNameLookupEvent event =
                        new DisplayNameLookupEvent(target);
                Bukkit.getPluginManager().callEvent(event);
                String name = event.getDisplayName();

                target.setDisplayName(name);
                target.setPlayerListName(name);
                target.sendMessage(msg("rename.reset.target", name));

                if (!sender.equals(target)) {
                    sender.sendMessage(ChatColor.GREEN + "Vous avez mis "
                            + ChatColor.GOLD + name + ChatColor.GREEN
                            + " avec son nom d'origine");
                }
            } else {
                target.setDisplayName(args.get(0));
                target.setPlayerListName(args.get(0));
                target.sendMessage(msg("rename.target", args.get(0)));

                if (!sender.equals(target)) {
                    sender.sendMessage(msg("rename.sender", target.getDisplayName(), args.get(0)));
                }
            }
        }
    }

    @Command(name = "burn", min = 1, max = 2)
    public void burn(CommandSender sender, CommandArgs args) {
        int duration = args.getInteger(1).valueOr(10);
        List<Player> targets = args.getPlayers(0)
                .matchWithPermOr("useful.player.burn.other", sender);

        for (Player target : targets) {
            target.setFireTicks(duration * 20);
            target.sendMessage(msg("burn.target", duration));

            if (!sender.equals(target)) {
                sender.sendMessage(msg("burn.sender", target.getDisplayName(), duration));
            }
        }
    }
}
