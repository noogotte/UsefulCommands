package fr.noogotte.useful_commands.component.kit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Kit {

    private final KitItem[] items;

    public Kit() {
        this.items = new KitItem[0];
    }

    public Kit(ItemStack[] stacks) {
        List<KitItem> list = new ArrayList<KitItem>(stacks.length);
        for (ItemStack stack : stacks) {
            if (stack == null
                    || stack.getType() == Material.AIR) {
                continue;
            }

            list.add(new KitItem(stack));
        }

        this.items = list.toArray(new KitItem[list.size()]);
    }

    public ItemStack[] toItemStacks() {
        ItemStack[] stacks = new ItemStack[items.length];
        for (int i = 0; i < items.length; i++) {
            stacks[i] = items[i].toItemStack();
        }

        return stacks;
    }
}
