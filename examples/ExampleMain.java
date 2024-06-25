package me.xorrad.lib;

import me.xorrad.lib.commands.ExampleCommand;
import me.xorrad.lib.configs.ExampleConfig;
import me.xorrad.lib.configs.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.logging.Level;

public class CubikCivilization extends JavaPlugin  {

    static CubikCivilization instance;
    private HashMap<String, Config> configs;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        this.registerCommands();
        this.registerEvents();
        this.initConfigurations();
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

    private void initConfigurations() {
        this.configs = new HashMap<String, Config>();
        this.loadConfig("example", new ExampleConfig());
    }

    public void loadConfig(String name, Config config) {
        this.configs.put(name, config);
        config.load();
    }

    public static void log(String format, Object... args) {
        log(Level.INFO, format, args);
    }

    public static void log(Level level, String format, Object... args) {
        instance.getLogger().log(level, String.format(format, args));
    }

    public static CubikCivilization getInstance() {
        return instance;
    }
}
