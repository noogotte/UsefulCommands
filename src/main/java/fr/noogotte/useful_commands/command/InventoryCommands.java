package fr.noogotte.useful_commands.command;

import java.util.List;

import org.bukkit.ChatColor;
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
    public void clear(Player player, CommandArgs args) {
        List<Player> targets = args.getPlayers(0, player);
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
            target.sendMessage(ChatColor.YELLOW
                    + "Inventaire vidé !");

            if (!player.equals(target)) {
                player.sendMessage(ChatColor.GREEN
                        + "Vous avez vidé l'inventaire de "
                        + ChatColor.BLUE + target.getName());
            }
        }
    }

    @Command(name = "give", flags = "e", min = 1, max = 3)
    public void give(Player player, CommandArgs args) {
        ItemType itemType = args.getItemType(0);
        List<Player> targets = args.getPlayers(2, player);

        ItemStack item;
        if(args.length() == 1) {
            item = itemType.toMaxItemStack();
        } else {
            item = itemType.toItemStack(args.getInteger(1));
        }

        if (args.hasFlag('e')) {
            for (Enchantment enchantment : Enchantment.values()) {
                if (enchantment.canEnchantItem(item)) {
                    item.addEnchantment(
                            enchantment, enchantment.getMaxLevel());
                }
            }
        }

        for (Player target : targets) {
            target.getInventory().addItem(item);
            target.sendMessage(ChatColor.GREEN
                    + "Vous avez reçu "
                    + ChatColor.AQUA + item.getAmount()
                    + ChatColor.GREEN + " de "
                    + ChatColor.AQUA + itemType.getMaterial());
            if (!player.equals(target)) {
                player.sendMessage(ChatColor.GREEN
                        + "Vous avez donné à " + target.getName()
                        + ChatColor.GREEN + ": "
                        + ChatColor.AQUA + item.getAmount()
                        + ChatColor.GREEN + " de "
                        + ChatColor.AQUA + itemType.getMaterial());
            }
        }
    }

    @Command(name = "open", min = 0, max = 1)
    public void openInv(Player player, CommandArgs args) {
        Player target = args.getPlayer(0, player);
        if (target.isOp()) {
            target.sendMessage(ChatColor.RED
                    + "Votre inventaire a été ouvert par "
                    + ChatColor.GRAY + player.getName());
        }
        Inventory inventory = target.getInventory();
        player.openInventory(inventory);
    }

    @Command(name = "id", min = 0, max = 0)
    public void id(Player player, CommandArgs args) {
        player.sendMessage(ChatColor.GREEN + "Vous tenez : "
                + ChatColor.AQUA + player.getItemInHand().getData());
        player.sendMessage(ChatColor.GREEN + "Son id est : "
                + ChatColor.AQUA + player.getItemInHand().getTypeId());
    }

    @Command(name = "enchantment", min = 1, max = 2)
    public void enchantment(Player player, CommandArgs args) {
        Enchantment enchantment = args.getEnchantment(0);
        Integer level = args.getInteger(1, 1);
        if(!enchantment.canEnchantItem(player.getItemInHand())) {
            player.sendMessage(ChatColor.GREEN + "L'enchantement " 
                    + ChatColor.GOLD + enchantment.getName() 
                    + ChatColor.GREEN + " ne peux pas être apliqué à " 
                    + ChatColor.GOLD + player.getItemInHand().getType());
        } else if (level > enchantment.getMaxLevel()) {
            player.sendMessage(ChatColor.GREEN
                    + "Le niveau d'enchantement doit être inférieur ou égal à "
                    + ChatColor.RED + enchantment.getMaxLevel());
        } else {
            player.getItemInHand().addEnchantment(enchantment, level);
            player.sendMessage(ChatColor.GREEN + "Vous avez ajouté " +
                    ChatColor.GOLD + enchantment.getName() 
                    + ChatColor.GOLD + " : " 
                    + ChatColor.GREEN
                    + player.getItemInHand().getEnchantmentLevel(enchantment));
        }
    }
}
