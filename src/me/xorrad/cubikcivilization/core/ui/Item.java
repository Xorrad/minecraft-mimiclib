package me.xorrad.cubikcivilization.core.ui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class Item extends ItemStack {

    public Item() {
        super(Material.AIR);
    }

    public Item material(Material material) {
        this.setType(material);
        return this;
    }

    public Item amount(int amount) {
        this.setAmount(amount);
        return this;
    }

    public Item name(String name) {
        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(name);
        this.setItemMeta(meta);
        return this;
    }

    public Item lore(List<String> lines) {
        ItemMeta meta = this.getItemMeta();
        meta.setLore(lines);
        this.setItemMeta(meta);
        return this;
    }

    public Item lore(String... lines) {
        return this.lore(Arrays.stream(lines).toList());
    }

    public Item durability(int durability) {
        Damageable meta = (Damageable) this.getItemMeta();
        meta.setDamage(durability);
        this.setItemMeta(meta);
        return this;
    }

    public boolean isClickable() {
        return false;
    }

    public static Item fromItemStack(ItemStack itemStack) {
        Item item = new Item();
        item.setType(itemStack.getType());
        item.setAmount(itemStack.getAmount());
        item.setData(itemStack.getData());
        item.setItemMeta(itemStack.getItemMeta());
        return item;
    }

}
