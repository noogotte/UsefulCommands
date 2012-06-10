package fr.noogotte.useful_commands.component.kit;

import org.bukkit.inventory.ItemStack;

public class Kit {

    private final KitItem[] items;

    public Kit() {
        this.items = new KitItem[0];
    }

    public Kit(ItemStack[] stacks) {
        this.items = new KitItem[stacks.length];
        for (int i = 0; i < stacks.length; i++) {
            items[i] = new KitItem(stacks[i]);
        }
    }

    public ItemStack[] toItemStacks() {
        ItemStack[] stacks = new ItemStack[items.length];
        for (int i = 0; i < items.length; i++) {
            stacks[i] = items[i].toItemStack();
        }

        return stacks;
    }
}
