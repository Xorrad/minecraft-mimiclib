package me.xorrad.cubikcivilization;

import me.xorrad.cubikcivilization.commands.ExampleCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CubikCivilization extends JavaPlugin {

    static CubikCivilization instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {

    }

    private void registerCommands() {
        new ExampleCommand().register();
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();

    }

    public static CubikCivilization getInstance() {
        return instance;
    }
}
