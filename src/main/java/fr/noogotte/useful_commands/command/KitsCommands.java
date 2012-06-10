package fr.noogotte.useful_commands.command;

import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.command.exception.CommandError;
import fr.noogotte.useful_commands.component.KitsComponent;
import fr.noogotte.useful_commands.component.kit.Kit;

@NestedCommands(name = "useful")
public class KitsCommands extends UsefulCommands {

    private final KitsComponent component;

    public KitsCommands(KitsComponent component) {
        this.component = component;
    }

    @Command(name = "kit", min = 1, max = 2)
    public void kit(CommandSender sender, CommandArgs args) {
        String kitName = args.get(0);
        if (!component.isKit(kitName)) {
            throw new CommandError(
                    "Aucun kit ne porte ce nom : " + kitName);
        }

        Kit kit = component.get(kitName);
        List<Player> targets = args.getPlayers(1).match(sender);
        ItemStack[] stacks = kit.toItemStacks();

        for (Player target : targets) {
            target.getInventory().addItem(stacks);
            target.sendMessage(ChatColor.GREEN + "Vous avez reçu le kit "
                    + ChatColor.AQUA + kitName
                    + ChatColor.GREEN +".");

            if (!sender.equals(target)) {
                sender.sendMessage(ChatColor.AQUA + target.getDisplayName()
                        + ChatColor.GREEN + " a recu le kit"
                        + ChatColor.AQUA + kitName
                        + ChatColor.GREEN + ".");
            }
        }
    }

    @Command(name = "kits")
    public void kits(CommandSender sender, CommandArgs args) {
        if (component.hasKits()) {
            sender.sendMessage(ChatColor.RED
                    + "Aucun kit n'est disponible.");
        } else {
            sender.sendMessage(ChatColor.GREEN + "Kits :");
            for (Entry<String, Kit> entry : component) {
                sender.sendMessage(ChatColor.GREEN + "  - "
                        + ChatColor.AQUA + entry.getKey());
            }
        }
    }

    @Command(name = "addkit", flags = "f", min = 1, max = 1)
    public void addKit(Player sender, CommandArgs args) {
        String name = args.get(0);
        Kit kit = new Kit(sender.getInventory().getContents());

        if (!args.hasFlag('f') && component.isKit(name)) {
            throw new CommandError("Un kit portant ce nom existe déjà."
                    + " Utilisez le flag -f pour le remplacer.");
        }

        if (component.addKit(name, kit)) {
            sender.sendMessage(ChatColor.GREEN + "Kit "
                    + ChatColor.AQUA + name
                    + ChatColor.GREEN + " enregistré.");
        } else {
            sender.sendMessage(ChatColor.RED
                    + "Une erreur est survenu.");
        }
    }

    @Command(name = "deletekit", flags = "f", min = 1, max = 1)
    public void deleteKit(Player sender, CommandArgs args) {
        String name = args.get(0);

        if (!component.isKit(name)) {
            throw new CommandError("Aucun kit ne porte ce nom.");
        }

        if (component.removeKit(name)) {
            sender.sendMessage(ChatColor.GREEN + "Kit "
                    + ChatColor.AQUA + name
                    + ChatColor.GREEN + " supprimé.");
        } else {
            sender.sendMessage(ChatColor.RED
                    + "Une erreur est survenu.");
        }
    }
}
