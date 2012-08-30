package fr.noogotte.useful_commands.command;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.itemtype.ItemType;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;

@NestedCommands("useful")
public class InventoryCommands extends UsefulCommands {

    public InventoryCommands(UsefulCommandsPlugin plugin) {
        super(plugin);
    }

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
            target.sendMessage(msg("clear.target"));

            if (!sender.equals(target)) {
                sender.sendMessage(msg("clear.sender", target.getDisplayName()));
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
            target.sendMessage(msg("give.target", item.getAmount(), itemType.getMaterial()));
            if (!sender.equals(target)) {
                sender.sendMessage(msg("give.sender", target.getName(), item.getAmount(), itemType.getMaterial()));
            }
        }
    }

    @Command(name = "openinv", min = 0, max = 1)
    public void openInv(Player sender, CommandArgs args) {
        Player target = args.getPlayer(0).valueOr(sender);

        if (target.hasPermission("useful.inventory.openinv.notify")) {
            target.sendMessage(msg("openinv.notify", sender.getDisplayName()));
        }
        Inventory inventory = target.getInventory();
        sender.openInventory(inventory);
    }

    @Command(name = "id", min = 0, max = 0)
    public void id(Player sender, CommandArgs args) {
        sender.sendMessage(msg("id.name", sender.getItemInHand().getData()));
        sender.sendMessage(msg("id.id", sender.getItemInHand().getTypeId()));
    }

    @Command(name = "enchantment", min = 1, max = 2)
    public void enchantment(Player sender, CommandArgs args) {
        Enchantment enchantment = args.getEnchantment(0).value();
        Integer level = args.getInteger(1).valueOr(1);

        if (!enchantment.canEnchantItem(sender.getItemInHand())) {
            sender.sendMessage(msg("enchant.error.canNotBeApplied", enchantment.getName(), sender.getItemInHand().getType()));
        } else if (level > enchantment.getMaxLevel()) {
            sender.sendMessage(msg("enchant.error.levelIsTooHight", enchantment.getMaxLevel()));
        } else {
            sender.getItemInHand().addEnchantment(enchantment, level);
            sender.sendMessage(msg("enchant.add", enchantment.getName(), sender.getItemInHand().getEnchantmentLevel(enchantment)));
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
        sender.sendMessage(msg("remove_enchant"));
    }

    @Command(name = "openender", min = 0, max = 1)
    public void openEnder(Player sender, CommandArgs args) {
        Player target = args.getPlayer(0).valueOr(sender);

        if (target.hasPermission("useful.inventory.openender.notify")) {
            target.sendMessage(msg("openender.notify", sender.getDisplayName()));
        }
        Inventory inventory = target.getEnderChest();
        sender.openInventory(inventory);
    }
}
