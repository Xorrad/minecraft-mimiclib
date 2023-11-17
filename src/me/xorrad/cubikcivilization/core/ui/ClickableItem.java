package me.xorrad.cubikcivilization.core.ui;

import org.bukkit.entity.Player;

import java.util.function.BiFunction;

public class ClickableItem extends Item {

    private BiFunction<Player, Menu, ItemClickResult> clickCallback;

    public ClickableItem() {
        this.clickCallback = (player, menu) -> { return ItemClickResult.NO_RESULT; };
    }

    public ClickableItem onClick(BiFunction<Player, Menu, ItemClickResult> function) {
        this.clickCallback = function;
        return this;
    }

    public ItemClickResult click(Player player, Menu menu) {
        return this.clickCallback.apply(player, menu);
    }

    @Override
    public boolean isClickable() {
        return true;
    }
}
