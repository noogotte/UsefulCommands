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
import fr.noogotte.useful_commands.UsefulCommandsPlugin;
import fr.noogotte.useful_commands.component.KitsComponent;
import fr.noogotte.useful_commands.component.kit.Kit;

@NestedCommands(name = "useful")
public class KitsCommands extends UsefulCommands {

    private final KitsComponent component;

    public KitsCommands(KitsComponent component, UsefulCommandsPlugin plugin) {
    	super(plugin);
        this.component = component;
    }

    @Command(name = "kit", flags = "c", min = 1, max = 2)
    public void kit(CommandSender sender, CommandArgs args) {
        String kitName = args.get(0);
        boolean clear = args.hasFlag('c');

        if (component.hasNoKit()) {
            throw new CommandError(msg("kit.hasNotAKit_€"));
        }

        if (!component.isKit(kitName)) {
            throw new CommandError(msg("kit.isNotAKit_€", kitName));
        }

        Kit kit = component.get(kitName);
        List<Player> targets = args.getPlayers(1)
                .matchWithPermOr("useful.kit.give.other", sender);
        ItemStack[] stacks = kit.toItemStacks();

        for (Player target : targets) {
            if (clear) {
                for (int j = 0; j <= 39; j++) {
                    target.getInventory().setItem(j, null);
                }
            }

            target.getInventory().addItem(stacks);
            target.sendMessage(msg("kit.give.target", kitName));

            if (!sender.equals(target)) {
                sender.sendMessage(msg("kit.give.sender", kitName, target.getName()));
            }
        }
    }

    @Command(name = "kits")
    public void kits(CommandSender sender) {
        if (component.hasNoKit()) {
            throw new CommandError(msg("kit.hasNotAKit_€"));
        }

        sender.sendMessage(ChatColor.GREEN + "Kits :");
        for (Entry<String, Kit> entry : component) {
            sender.sendMessage(ChatColor.GREEN + "  - "
                    + ChatColor.AQUA + entry.getKey());
        }
    }

    @Command(name = "addkit", flags = "af", min = 1, max = 1)
    public void addKit(Player sender, CommandArgs args) {
        String name = args.get(0);

        ItemStack[] stacks;
        if (args.hasFlag('a')) {
            stacks = sender.getInventory().getContents();
        } else {
            stacks = new ItemStack[9];
            System.arraycopy(sender.getInventory().getContents(), 0, stacks, 0,
                    9);
        }
        Kit kit = new Kit(stacks);

        if (!args.hasFlag('f') && component.isKit(name)) {
            throw new CommandError(msg("kit.kitAlreadyExist_€"));
        }

        if (component.addKit(name, kit)) {
            sender.sendMessage(msg("kit.add", name));
        } else {
            sender.sendMessage(ChatColor.RED + "Une erreur est survenu.");
        }
    }

    @Command(name = "deletekit", flags = "f", min = 1, max = 1)
    public void deleteKit(Player sender, CommandArgs args) {
        String name = args.get(0);

        if (component.hasNoKit()) {
            throw new CommandError(msg("kit.hasNotAKit_€"));
        }

        if (!component.isKit(name)) {
            throw new CommandError(msg("kit.isNotAKit_€", name));
        }

        if (component.removeKit(name)) {
            sender.sendMessage(msg("kit.remove", name));
        } else {
            sender.sendMessage(ChatColor.RED + "Une erreur est survenu.");
        }
    }
}
