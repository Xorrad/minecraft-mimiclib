package me.xorrad.lib.ui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class Item extends ItemStack {

    private BiFunction<Player, Menu, ItemClickResult> clickCallback;

    public Item() {
        super(Material.AIR);
        this.clickCallback = (player, menu) -> { return ItemClickResult.NO_RESULT; };
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

    public Item head(String playerName) {
        this.setType(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) this.getItemMeta();
        meta.setOwner(playerName);
        this.setItemMeta(meta);
        return this;
    }

    public Item head(PlayerProfile profile) {
        this.setType(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) this.getItemMeta();
        meta.setOwnerProfile(profile);
        this.setItemMeta(meta);
        return this;
    }

    public Item hideAttributes() {
        ItemMeta meta = this.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        this.setItemMeta(meta);
        return this;
    }

    public Item onClick(BiFunction<Player, Menu, ItemClickResult> function) {
        this.clickCallback = function;
        return this;
    }

    public ItemClickResult click(Player player, Menu menu) {
        return this.clickCallback.apply(player, menu);
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
