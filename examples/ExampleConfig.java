package me.xorrad.lib.configs;

import me.xorrad.lib.CubikCivilization;
import me.xorrad.lib.configs.Config;

import java.util.logging.Level;

public class ExampleConfig extends Config {

    public ExampleConfig() {
        super("example.yml");
    }

    @Override
    public void initDefault() {
        set("test", 1);

        CubikCivilization.log(Level.INFO, "Created default config file (%s)", this.getName());
    }

}
