package me.xorrad.cubikcivilization.commands;

import me.xorrad.cubikcivilization.core.commands.CommandGroup;
import me.xorrad.cubikcivilization.core.commands.CommandParameter;

public class ExampleCommand extends CommandGroup {

    public ExampleCommand() {
        super("example");

        newDefaultCommand().execute((sender, params) -> {
            sender.sendMessage("The default command for " + this.getName() + " has been executed.");
        });

        newSubCommand("test")
                .playerOnly(true)
                .param(new CommandParameter("name", CommandParameter.Type.STRING))
                .param(new CommandParameter("amount", CommandParameter.Type.INT))
                .execute((sender, params) -> {
                    sender.sendMessage("§aname: §f" + ((String) params[0]));
                    sender.sendMessage("§aamount: §f" + ((int) params[1]));
                });
    }
}
