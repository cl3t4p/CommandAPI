package com.cl3t4p.commandapi;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is used to create a main command.
 *
 * @author cl3t4p
 *
 * @version 0.3
 *
 * @since 0.2
 **/
public class MainCommand extends Command {

    private final HashMap<String, Command> commands = new HashMap<>();
    private final String message;

    public MainCommand(@NotNull String name, String permission, CommandManager manager) {
        super(name);
        if (!permission.isEmpty())
            setPermission(permission);
        this.message = color(manager.getMain_command());
    }

    public void addCommand(Command command) {
        commands.put(command.getName().toLowerCase(), command);
    }

    public void addCommand(CommandWrapper command) {
        addCommand(command.getCommand());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(message);
            return true;
        }
        Command command = commands.get(args[0].toLowerCase());
        if (command == null) {
            sender.sendMessage(message);
        } else {
            command.execute(sender, alias, removeFirstArray(args));
        }
        return true;
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args)
            throws IllegalArgumentException {
        if (args.length <= 1) {
            return new ArrayList<>(commands.keySet());
        } else {
            Command command = commands.get(args[0].toLowerCase());
            if (command != null) {
                command.tabComplete(sender, alias, removeFirstArray(args));
            }
        }
        return super.tabComplete(sender, alias, args);
    }

    private String[] removeFirstArray(String[] array) {
        String[] returnArray = new String[array.length - 1];
        System.arraycopy(array, 1, returnArray, 0, array.length - 1);
        return returnArray;
    }

    protected String color(@NotNull String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
