package fr.noogotte.useful_commands.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.CommandArgs;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.noogotte.useful_commands.GodComponent;

@NestedCommands(name = "useful")
public class GodCommand extends UsefulCommands {

    private GodComponent godComponent;

    public GodCommand(GodComponent godComponent) {
        this.godComponent = godComponent;
    }

    @Command(name = "god", min = 0, max = 1)
    public void god(Player player, CommandArgs args) {
        if (args.length() == 0) {
            if (godComponent.isGod(player)) {
                godComponent.removeGod(player);
                player.sendMessage(ChatColor.AQUA + "Mode dieux arreté.");
            } else {
                godComponent.setGod(player);
                player.sendMessage(ChatColor.GREEN + "Vous êtes en mode Dieux tapez " +
                        ChatColor.BLUE + " /god " + ChatColor.GREEN + " pour en resortir.");
            }
        } else {
            Player target = args.getPlayer(0);
            if (godComponent.isGod(target)) {
                godComponent.removeGod(target);
                player.sendMessage(ChatColor.AQUA + "Mode dieux arreté pour " +
                        ChatColor.BLUE + target.getName());
            } else {
                godComponent.setGod(target);
                player.sendMessage(ChatColor.AQUA + "Vous avez mis le mode Dieux pour " +
                        ChatColor.BLUE + target.getName());
            }
        }
    }
}