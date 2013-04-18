package fr.noogotte.useful_commands.command;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.command.exception.CommandError;
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

    @Command(name = "give", flags = "e", argsFlags = "n", min = 1, max = 3)
    public void give(CommandSender sender, CommandArgs args) {
        ItemType itemType = args.getItemType(0).value();
        List<Player> targets = args.getPlayers(2)
                .matchWithPermOr("useful.inventory.give.other", sender);

        int amount = args.getInteger(1).valueOr(itemType.getMaxStackSize());
        ItemStack item = itemType.toItemStack(amount);
        String name = args.get('n');

        if (args.hasFlag('e') && !itemType.getMaterial().isBlock()) {
            for (Enchantment enchantment : Enchantment.values()) {
                if (enchantment.canEnchantItem(item)) {
                    item.addEnchantment(enchantment, enchantment.getMaxLevel());
                }
            }
        }

        if (args.hasArgFlag('n')) {
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(name);
            item.setItemMeta(meta);
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
            enchantments = args.getEnchantments(0).value();
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

    @Command(name = "durability")
    public void durability(Player sender, CommandArgs args) {
        Short durability = (short) (sender.getItemInHand().getDurability() + 1);
        Short maxDurability = (short) (sender.getItemInHand().getType().getMaxDurability() + 1);

        if (sender.getItemInHand().getType().isBlock()) {
            throw new CommandError(msg("durability.isNotAnItem_€", sender.getItemInHand().getType()));
        }

        if (sender.getItemInHand().getType().getMaxDurability() == 0) {
            throw new CommandError(msg("durability.isNotAnItemWithDurability_€"));
        }

        if (sender.getItemInHand().getDurability() == 0) {
            throw new CommandError(msg("durability", maxDurability));
        }

        sender.sendMessage(msg("durability", maxDurability - durability + 1));
    }

    @Command(name = "setdurability", min = 1, max = 1)
    public void setDurability(Player sender, CommandArgs args) {
        Short maxDurability = (short) (sender.getItemInHand().getType().getMaxDurability() + 1);
        Short durability = args.getShort(0).value();

        if (sender.getItemInHand().getType().isBlock()) {
            throw new CommandError(msg("durability.isNotAnItem_€", sender.getItemInHand().getType()));
        }

        if (sender.getItemInHand().getType().getMaxDurability() == 0) {
            throw new CommandError(msg("durability.isNotAnItemWithDurability_€"));
        }

        if (sender.getItemInHand().getDurability() == 0) {
            throw new CommandError(msg("durability", maxDurability));
        }

        sender.getItemInHand().setDurability((short) (maxDurability - durability));
        sender.sendMessage(msg("setdurability", durability));
    }

    @Command(name = "renameitem", min = 1, max = 1)
    public void renameItem(Player sender, CommandArgs args) {
        ItemStack stack = sender.getItemInHand();

        if (stack.getTypeId() == 0) {
            throw new CommandError("Item can not be null or empty");
        }

        if (stack.getAmount() != 1) {
            throw new CommandError("Item amount can not be more than 1");
        }

        ItemMeta meta = (ItemMeta) stack.getItemMeta();
        String newItemName = args.get(0);
        meta.setDisplayName(newItemName);
        stack.setItemMeta(meta);
        sender.sendMessage(msg("renameitem", stack.getType(), newItemName));
    }

    @Command(name = "setlore", min = 1, max = -1)
    public void setLore(Player sender, CommandArgs args) {
        ItemStack stack = sender.getItemInHand();

        if (stack.getTypeId() == 0) {
            throw new CommandError("Item can not be null or empty");
        }

        if (stack.getAmount() != 1) {
            throw new CommandError("Item amount can not be more than 1");
        }

        ItemMeta meta = (ItemMeta) stack.getItemMeta();
        List<String> lore = args.asList();
        meta.setLore(lore);
        stack.setItemMeta(meta);
        sender.sendMessage(msg("setlore"));
    }

    @Command(name="setskull", min = 1, max = 1)
    public void setSkullOwner(Player sender, CommandArgs args) {
        ItemStack stack = sender.getItemInHand();

        if (stack.getType() != Material.SKULL_ITEM) {
            throw new CommandError("Item must be an Human's skull");
        }

        if (stack.getData().getData() != 3) {
            throw new CommandError("Only Human's skull is accepted");
        }

        SkullMeta meta = (SkullMeta) stack.getItemMeta();
        String ownerName = args.get(0);
        meta.setOwner(ownerName);
        stack.setItemMeta(meta);
        sender.sendMessage(msg("setskull", ownerName));
    }

    @Command(name="setarmorcolor", min = 3, max = 3)
    public void setArmorColor(Player sender, CommandArgs args) {
        ItemStack stack = sender.getItemInHand();

        if (!isLeatherArmor(stack.getType())) {
            throw new CommandError("Item must be an leather armor");
        }

        LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();

        int red = args.getInteger(0).value();
        int green = args.getInteger(1).value();
        int blue = args.getInteger(2).value();
        Color color = Color.fromRGB(red, green, blue);

        meta.setColor(color);
        stack.setItemMeta(meta);
        sender.sendMessage(msg("setarmorcolor"));
    }

    private boolean isLeatherArmor(Material mat) {
        return mat == Material.LEATHER_BOOTS
                || mat == Material.LEATHER_CHESTPLATE
                || mat == Material.LEATHER_HELMET
                || mat == Material.LEATHER_LEGGINGS;
    }

    @Command(name="setauthor", min = 1, max = 1)
    public void setAuthor(Player sender, CommandArgs args) {
        ItemStack stack = sender.getItemInHand();

        if (stack.getType() != Material.WRITTEN_BOOK) {
            throw new CommandError("Item must be a Written Book");
        }

        BookMeta meta = (BookMeta) stack.getItemMeta();

        String author = args.get(0);
        meta.setAuthor(author);
        stack.setItemMeta(meta);
        sender.sendMessage(msg("setauthor", author));
    }

    @Command(name="setpages", min = 1, max = -1)
    public void setPages(Player sender, CommandArgs args) {
        ItemStack stack = sender.getItemInHand();

        if (stack.getType() != Material.WRITTEN_BOOK) {
            throw new CommandError("Item must be a Written Book");
        }

        BookMeta meta = (BookMeta) stack.getItemMeta();

        List<String> pages = args.asList();
        meta.setPages(pages);
        stack.setItemMeta(meta);
        sender.sendMessage(msg("setpages"));
    }

    @Command(name="settitle", min = 1, max = 1)
    public void setTitle(Player sender, CommandArgs args) {
        ItemStack stack = sender.getItemInHand();

        if (stack.getType() != Material.WRITTEN_BOOK) {
            throw new CommandError("Item must be a Written Book");
        }

        BookMeta meta = (BookMeta) stack.getItemMeta();

        String title = args.get(0);
        meta.setTitle(title);
        stack.setItemMeta(meta);
        sender.sendMessage(msg("settitle"));
    }
}