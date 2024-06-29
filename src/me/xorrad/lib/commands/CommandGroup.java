package me.xorrad.lib.commands;

import me.xorrad.lib.LibMain;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

import java.util.*;
import java.util.stream.Collectors;

public class CommandGroup {

    static HashMap<String, CommandGroup> groups = new HashMap<>();

    private String name;
    private HashMap<String, Command> commands;

    public CommandGroup(String name) {
        this.name = name;
        this.commands = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public String getUsage() {
        String commands = this.commands.values().stream()
                .map(cmd -> cmd.getName())
                .filter(cmd -> !cmd.isEmpty())
                .collect(Collectors.joining(","));
        return "/" + this.name + " <" + commands + ">";
    }

    public void addCommand(Command command) {
        commands.put(command.getName(), command);
    }

    public Command newSubCommand(String name) {
        Command subCommand = new Command(this, name);
        commands.put(name, subCommand);
        return subCommand;
    }

    public Command newDefaultCommand() {
        Command command = newSubCommand("");
        command.complete((sender, args) -> {
            return this.getCommandNames();
        });
        return command;
    }

    public boolean hasCommand(String command) {
        return commands.containsKey(command);
    }

    public Command getCommand(String command) {
        return commands.get(command);
    }

    public boolean hasDefaultCommand() {
        return hasCommand("");
    }

    public Command getDefaultCommand() {
        return getCommand("");
    }

    public Command getTargetCommand(String[] strings) {
        if(strings.length == 0) {
            if(!hasDefaultCommand())
                return null;
            return getDefaultCommand();
        }
        if(!hasCommand(strings[0]) && !hasDefaultCommand())
            return null;
        return (hasCommand(strings[0])) ? getCommand(strings[0]) : getDefaultCommand();
    }

    public String[] getTargetCommandParameters(String[] strings) {
        if(strings.length > 0 && hasCommand(strings[0])) // Skip first string in parameters when a subcommand is specified.
            return Arrays.stream(strings).skip(1).toArray(String[]::new);
        return strings;
    }

    public List<String> getCommandNames() {
        return this.commands.values().stream()
                .map(cmd -> cmd.getName())
                .filter(cmd -> !cmd.isEmpty())
                .toList();
    }

    public String[] parseStrings(String[] strings) {
        ArrayList<String> parameters = new ArrayList<>();
        boolean inQuote = false;
        String quote = "";
        for(int i = 0; i < strings.length; i++) {
            if(strings[i].equalsIgnoreCase("\"")) { // Check for beginning/end of quote.
                if(inQuote)
                    parameters.add(quote);
                inQuote = !inQuote;
                quote = "";
                continue;
            }
            if(inQuote) {
                quote += strings[i];
                continue;
            }
            parameters.add(strings[i]);
        }
        return parameters.toArray(String[]::new);
    }

    public void register() {
        PluginCommand command = LibMain.getInstance().getCommand(name);
        command.setExecutor(new Executor());
        command.setTabCompleter(new Completer());
        groups.put(this.name, this);
    }

    private class Executor implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String alias, String[] strings) {
            strings = parseStrings(strings); // Merge words between quotes as a single string.
            Command command = getTargetCommand(strings);
            if(command == null) {
                sender.sendMessage("§cInvalid syntax! " + getUsage());
                return false;
            }
            String[] parameters = getTargetCommandParameters(strings);
            return command.execute(sender, parameters);
        }
    }

    private class Completer implements TabCompleter {
        @Override
        public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String alias, String[] strings) {
            strings = parseStrings(strings); // Merge words between quotes as a single string.
            Command command = getTargetCommand(strings);
            if(command == null) {
                return Collections.<String>emptyList();
            }
            strings = getTargetCommandParameters(strings);
            return command.complete(sender, strings);
        }
    }
}
