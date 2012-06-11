package fr.noogotte.useful_commands.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.util.Util;

@NestedCommands(name = "useful")
public class PlayerCommands extends UsefulCommands {

    @Command(name = "gamemode", flags = "cs", min = 0, max = 1)
    public void gamemode(CommandSender sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(0).match(sender);

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

            target.sendMessage(ChatColor.GREEN
                    + "Vous êtes maintenant en "
                    + ChatColor.AQUA + target.getGameMode());
            if (!sender.equals(target)) {
                sender.sendMessage(ChatColor.GREEN
                        + "Vous avez mis "
                        + ChatColor.AQUA + target.getName()
                        + ChatColor.GREEN + " en "
                        + ChatColor.AQUA +  target.getGameMode());
            }
        }
    }

    @Command(name = "heal", flags = "hf", min = 0, max = 1)
    public void heal(CommandSender sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(0).match(sender);
        boolean health = !args.hasFlags() || args.hasFlag('h');
        boolean food = !args.hasFlags() || args.hasFlag('f');

        for (Player target : targets) {
            if (health) {
                target.setHealth(20);
            }
            if (food) {
                target.setFoodLevel(20);
            }

            target.sendMessage(ChatColor.YELLOW
                    + "Vous voilà soigné et nourri");
            if (!sender.equals(target)) {
                sender.sendMessage(ChatColor.GREEN
                        + "Vous avez soigné et nourri "
                        + ChatColor.BLUE + target.getName());
            }
        }
    }

    @Command(name = "kick", flags = "o", min = 1, max = 2)
    public void kick(CommandSender sender, CommandArgs args) {
        String reason = args.get(1, "Kicked !");
        boolean dontKickOps = !args.hasFlag('o');

        for (Player target : args.getPlayers(0).match()) {
            if (dontKickOps && target.isOp()) {
                sender.sendMessage(ChatColor.RED + target.getName()
                        + " est OP vous ne pouvez pas le kicker "
                        + "(utilisez \"-o\" pour forcer le kick).");
            } else {
                target.kickPlayer(reason);
                Util.broadcast(ChatColor.AQUA + target.getName()
                        + ChatColor.GREEN + " a été kické par "
                        + ChatColor.AQUA + sender.getName());
            }
        }
    }

    @Command(name = "tell", min = 2, max = 2)
    public void tell(CommandSender sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(0).value();
        String message = args.get(1);

        String senderName;
        if (sender instanceof Player) {
            senderName = ((Player) sender).getDisplayName();
        } else if (sender instanceof ConsoleCommandSender) {
            senderName = "*Console*";
        } else {
            senderName = "*Inconnu*";
        }

        StringBuilder receivers = new StringBuilder();
        for (Player target : targets) {
            target.sendMessage(ChatColor.ITALIC.toString()
                    + ChatColor.AQUA + "De "
                    + senderName
                    + ChatColor.AQUA + " : "
                    + ChatColor.WHITE +  " " + message);

            receivers.append(target.getDisplayName());
            receivers.append(" ");

            if (!(sender instanceof ConsoleCommandSender)) {
                Bukkit.getConsoleSender().sendMessage(
                        "[MSG] de " + sender.getName()
                        +  " à " + target.getName()
                        + ": " + message);
            }
        }

       sender.sendMessage(ChatColor.ITALIC.toString()
                + ChatColor.AQUA + "A " + receivers + ":");
        sender.sendMessage("  " + message);
    }

    @Command(name = "fly", min = 0, max = 1)
    public void fly(CommandSender sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(0).match(sender);
        for (Player target : targets) {
            if (target.isFlying()) {
                target.setAllowFlight(false);
                target.setFlying(false);
            } else {
                target.setAllowFlight(true);
                target.setFlying(true);
            }
            if (target.isFlying()) {
                sender.sendMessage(ChatColor.GREEN
                        + " Vous pouvez désormais voler !");
            } else {
                sender.sendMessage(ChatColor.GREEN
                        + " Vous ne pouvez plus voler !");
            }

            if (!sender.equals(target)) {
                if (target.isFlying()) {
                    sender.sendMessage(ChatColor.GOLD + target.getName()
                            + ChatColor.GREEN + " peut maintenant voler !");
                } else {
                    sender.sendMessage(ChatColor.GOLD + target.getName()
                            + ChatColor.GREEN + " ne peut plus voler !");
                }
            }
        }
    }

    @Command(name = "kill", min = 0, max = 1)
    public void kill(CommandSender sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(0).match(sender);
        for (Player target : targets) {
            target.setHealth(0);
            sender.sendMessage(ChatColor.GREEN + "Vous vous êtes suicidés !");

            if (!sender.equals(target)) {
                sender.sendMessage(ChatColor.GREEN + "Vous avez tué "
                        + ChatColor.WHITE + target.getName());
            }
        }
    }

    @Command(name = "effect", min = 1, max = 3)
    public void effect(CommandSender sender, CommandArgs args) {
        PotionEffectType effect = args.getPotionEffectType(0).value();
        Integer duration = args.getInteger(1).value(60);
        PotionEffect newEffect = new PotionEffect(
                effect, duration * 20, 1);

        List<Player> targets = args.getPlayers(2).value(sender);

        for (Player target : targets) {
            target.addPotionEffect(newEffect, true);
            sender.sendMessage(ChatColor.GREEN +"Vous êtes sous influence de " +
                    ChatColor.GOLD + effect.getName());

            if (!sender.equals(target)) {
                sender.sendMessage(ChatColor.GOLD + target.getName() + 
                        ChatColor.GREEN + " est désormais sous l'effet de " + 
                        ChatColor.GOLD + effect.getName() + 
                        ChatColor.GREEN + " pour " + effect.getDurationModifier());
            }
        }
    }

    @Command(name = "rename", min = 1, max = 2)
    public void rename(CommandSender sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(1).value(sender);
        boolean reset = args.get(0).equals("reset");

        for (Player target : targets) {
            if (reset) {
                String name = target.getName();
                target.setDisplayName(name);
                target.setPlayerListName(name);
                target.sendMessage(ChatColor.GREEN
                        + "Vous êtes de nouveau connus sous le nom "
                        + ChatColor.GOLD + name);

                if (!sender.equals(target)) {
                    sender.sendMessage(ChatColor.GREEN + "Vous avez mis "
                            + ChatColor.GOLD + name
                            + ChatColor.GREEN + " avec son nom d'origine");
                }
            } else {
                target.setDisplayName(args.get(0));
                target.setPlayerListName(args.get(0));
                target.sendMessage(ChatColor.GREEN + "Vous voilà renommez en "
                        + ChatColor.GOLD + args.get(0));

                if (!sender.equals(target)) {
                    sender.sendMessage(ChatColor.GREEN + "Vous avez renommez "
                            + ChatColor.GOLD + target.getName()
                            + ChatColor.GREEN + " en "
                            + ChatColor.GOLD + args.get(0));
                }
            }
        }
    }
}