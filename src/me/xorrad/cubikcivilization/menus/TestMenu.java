package me.xorrad.cubikcivilization.menus;

import me.xorrad.cubikcivilization.core.ui.Item;
import me.xorrad.cubikcivilization.core.ui.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class TestMenu {

    public static void open(Player player) {
        new Menu()
                .title("Test")
                .size(2)
                .add(new Item().material(Material.IRON_SWORD))
                .onClick((player1, item, event) -> {
                    return event.getRawSlot() < event.getInventory().getSize();
                })
                .open(player);
    }

}
