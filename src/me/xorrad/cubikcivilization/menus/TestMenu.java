package me.xorrad.cubikcivilization.menus;

import me.xorrad.cubikcivilization.core.ui.Item;
import me.xorrad.cubikcivilization.core.ui.ItemClickResult;
import me.xorrad.cubikcivilization.core.ui.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TestMenu {

    public static void open(Player player) {
        new Menu()
                .title("Test")
                .size(2)
                .add(new Item().material(Material.IRON_SWORD))
                .add(new Item()
                        .material(Material.BOOK)
                        .amount(10)
                        .name("§aAccounts")
                        .lore("§eHello world")
                        .onClick((p, menu) -> {
                            p.sendMessage("hello world");
                            return ItemClickResult.NO_RESULT;
                        })
                )
                .onClick((p, item, event) -> {
                    return true;
                })
                .open(player);
    }

}
