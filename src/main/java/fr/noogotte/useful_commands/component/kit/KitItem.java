package fr.noogotte.useful_commands.component.kit;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class KitItem {

    private static class KitEnchantment {
        private final String type;
        private final int level;

        public KitEnchantment(Entry<Enchantment, Integer> entry) {
            this.type = entry.getKey().getName();
            this.level = entry.getValue();
        }

        public void addTo(ItemStack stack) {
            Enchantment enchantment = Enchantment.getByName(type);
            stack.addEnchantment(enchantment, level);
        }
    }

    private final Material material;
    private final int amount;
    private final short durability;
    private final KitEnchantment[] enchantments;

    public KitItem() {
        this.material = Material.AIR;
        this.amount = 0;
        this.durability = 0;
        this.enchantments = new KitEnchantment[0];
    }

    public KitItem(ItemStack stack) {
        this.material = stack.getType();
        this.amount = stack.getAmount();
        this.durability = stack.getDurability();

        Map<Enchantment, Integer> stackEnchantments =
                stack.getEnchantments();
        int size = stackEnchantments.keySet().size();
        this.enchantments = new KitEnchantment[size];
        int i = 0;
        for (Entry<Enchantment, Integer> entry
                : stackEnchantments.entrySet()) {
            enchantments[i] = new KitEnchantment(entry);
            i++;
        }
    }

    public ItemStack toItemStack() {
        ItemStack stack = new ItemStack(material, amount, durability);
        for (KitEnchantment enchantment : enchantments) {
            enchantment.addTo(stack);
        }

        return stack;
    }
}
