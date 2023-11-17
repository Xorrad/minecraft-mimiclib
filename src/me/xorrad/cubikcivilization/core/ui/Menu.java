package me.xorrad.cubikcivilization.core.ui;

import me.xorrad.cubikcivilization.CubikCivilization;
import me.xorrad.cubikcivilization.core.util.TriFunction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class Menu implements Listener {

    private int size;
    private String title;
    private HashMap<Integer, Item> items;
    private Consumer<Player> closeCallback;
    private TriFunction<Player, Item, InventoryClickEvent, Boolean> clickCallback;

    private Inventory inventory;

    public Menu() {
        this.size = 9*5;
        this.title = "";
        this.items = new HashMap<>();
        this.closeCallback = (player) -> {};
        this.clickCallback = (player, item, event) -> false;
    }

    public Menu size(int rows) {
        this.size = rows * 9;
        return this;
    }

    public Menu title(String title) {
        this.title = title;
        return this;
    }

    public Menu set(int index, Item item) {
        this.items.put(index, item);
        if(this.inventory != null)
            this.inventory.setItem(index, item);
        return this;
    }

    public Menu add(Item item) {
        for(int i = 0; i < this.size; i++) {
            if(this.items.containsKey(i)) {
                if(this.items.get(i).equals(item)) {
                    this.stack(i, item);
                    break;
                }
            } else {
                this.set(i, item);
                break;
            }
        }
        return this;
    }

    public Menu onClose(Consumer<Player> function) {
        this.closeCallback = function;
        return this;
    }

    public Menu onClick(TriFunction<Player, Item, InventoryClickEvent, Boolean> function) {
        this.clickCallback = function;
        return this;
    }

    public Menu create() {
        if(this.inventory == null)
            Bukkit.getPluginManager().registerEvents(this, CubikCivilization.getInstance());
        this.inventory = Bukkit.createInventory(null, this.size, this.title);
        this.items.forEach((index, item) -> this.inventory.setItem(index, item));
        return this;
    }

    public void open(Player player) {
        if(this.inventory == null) this.create();
        player.openInventory(this.inventory);
    }

    public void stack(int index, Item item) {
        this.items.get(index).setAmount(this.items.get(index).getAmount() + item.getAmount());
        if(this.inventory != null)
            this.inventory.setItem(index, this.items.get(index));
    }

    @EventHandler
    private void onInventoryClose(InventoryCloseEvent event) {
        if(!event.getInventory().equals(this.inventory))
            return;

        this.closeCallback.accept((Player) event.getPlayer());
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        if(!event.getInventory().equals(this.inventory))
            return;

        Player player = (Player) event.getWhoClicked();
        Item item = null;
        if(event.getCurrentItem() == null) item = new Item();
        else if(this.items.containsKey(event.getSlot())) item = this.items.get(event.getSlot());
        else item = Item.fromItemStack(event.getCurrentItem());

        boolean cancelled = this.clickCallback.apply(player, item, event);

        if(cancelled) {
            event.setCancelled(true);
            return;
        }

        if(item.isClickable()) {
            ItemClickResult result = ((ClickableItem) item).click(player, this);
        }
    }
}
