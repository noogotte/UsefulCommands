package fr.noogotte.useful_commands.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.Commands;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.itemtype.ItemType;

@NestedCommands(name = "useful")
public class InventoryCommands implements Commands {

    @Command(name = "clear", flags = "qra", min = 0, max = 1)
    public void clear(CommandSender sender, CommandArgs args) {
        List<Player> targets = args.getPlayers(0)
                .matchWithPermOr("useful.inventory.clear.other", sender);
        int from = args.hasFlag('q') ? 9 : 0;
        int to = args.hasFlag('r') ? 8 : 35;
        boolean armor = !args.hasFlag('a');

        for (Player target : targets) {
            for (int j = from; j <= to; j++) {
                target.getInventory().setItem(j, null);
            }
            if (armor) {
                for (int j = 36; j <= 39; j++) {
                    target.getInventory().setItem(j, null);
                }
            }
            target.sendMessage(ChatColor.YELLOW + "Inventaire vidé !");

            if (!sender.equals(target)) {
                sender.sendMessage(ChatColor.GREEN
                        + "Vous avez vidé l'inventaire de " + ChatColor.BLUE
                        + target.getName());
            }
        }
    }

    @Command(name = "give", flags = "e", min = 1, max = 3)
    public void give(CommandSender sender, CommandArgs args) {
        ItemType itemType = args.getItemType(0).value();
        List<Player> targets = args.getPlayers(2)
                .matchWithPermOr("useful.inventory.give.other", sender);

        int amount = args.getInteger(1).valueOr(itemType.getMaxStackSize());
        ItemStack item = itemType.toItemStack(amount);

        if (args.hasFlag('e') && !itemType.getMaterial().isBlock()) {
            for (Enchantment enchantment : Enchantment.values()) {
                if (enchantment.canEnchantItem(item)) {
                    item.addEnchantment(enchantment, enchantment.getMaxLevel());
                }
            }
        }

        for (Player target : targets) {
            target.getInventory().addItem(item);
            target.sendMessage(ChatColor.GREEN + "Vous avez reçu "
                    + ChatColor.AQUA + item.getAmount() + ChatColor.GREEN
                    + " de " + ChatColor.AQUA + itemType.getMaterial());
            if (!sender.equals(target)) {
                sender.sendMessage(ChatColor.GREEN + "Vous avez donné à "
                        + target.getName() + ChatColor.GREEN + ": "
                        + ChatColor.AQUA + item.getAmount() + ChatColor.GREEN
                        + " de " + ChatColor.AQUA + itemType.getMaterial());
            }
        }
    }

    @Command(name = "open", min = 0, max = 1)
    public void openInv(Player sender, CommandArgs args) {
        Player target = args.getPlayer(0).valueOr(sender);

        if (target.hasPermission("useful.inventory.open.notify")) {
            target.sendMessage(ChatColor.RED
                    + "Votre inventaire a été ouvert par " + ChatColor.GRAY
                    + sender.getName());
        }
        Inventory inventory = target.getInventory();
        sender.openInventory(inventory);
    }

    @Command(name = "id", min = 0, max = 0)
    public void id(Player sender, CommandArgs args) {
        sender.sendMessage(ChatColor.GREEN + "Vous tenez : " + ChatColor.AQUA
                + sender.getItemInHand().getData());
        sender.sendMessage(ChatColor.GREEN + "Son id est : " + ChatColor.AQUA
                + sender.getItemInHand().getTypeId());
    }

    @Command(name = "enchantment", min = 1, max = 2)
    public void enchantment(Player sender, CommandArgs args) {
        Enchantment enchantment = args.getEnchantment(0).value();
        Integer level = args.getInteger(1).valueOr(1);

        if (!enchantment.canEnchantItem(sender.getItemInHand())) {
            sender.sendMessage(ChatColor.GREEN + "L'enchantement "
                    + ChatColor.GOLD + enchantment.getName() + ChatColor.GREEN
                    + " ne peux pas être apliqué à " + ChatColor.GOLD
                    + sender.getItemInHand().getType());
        } else if (level > enchantment.getMaxLevel()) {
            sender.sendMessage(ChatColor.GREEN
                    + "Le niveau d'enchantement doit être inférieur ou égal à "
                    + ChatColor.RED + enchantment.getMaxLevel());
        } else {
            sender.getItemInHand().addEnchantment(enchantment, level);
            sender.sendMessage(ChatColor.GREEN + "Vous avez ajouté "
                    + ChatColor.GOLD + enchantment.getName() + ChatColor.GOLD
                    + " : " + ChatColor.GREEN
                    + sender.getItemInHand().getEnchantmentLevel(enchantment));
        }
    }

    @Command(name = "remove-enchantment", min = 0, max = 1)
    public void removeEnchantment(Player sender, CommandArgs args) {
        ItemStack stack = sender.getItemInHand();
        Iterable<Enchantment> enchantments;
        if (args.length() == 0) {
            enchantments = stack.getEnchantments().keySet();
        } else {
            enchantments = args.getList(0, Enchantment.class).value();
        }

        for (Enchantment enchantment : enchantments) {
            stack.removeEnchantment(enchantment);
        }
    }
}
