package com.cl3t4p.commandapi;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class is used to create a main command.
 *
 * @author cl3t4p
 *
 * @version 0.7
 *
 * @since 0.2
 **/
public class MainCommand extends Command {

    private final CommandManager manager;
    private final HashMap<String, Command> commands = new HashMap<>();

    public MainCommand(@NotNull String name, String permission, CommandManager manager) {
        super(name);
        this.manager = manager;

        if (!permission.isEmpty())
            setPermission(permission);
    }

    public void setPermission(String permission) {
        super.setPermission(permission);
    }

    public void addCommand(Command command) {
        Set<String> names = new HashSet<>(command.getAliases());
        names.add(command.getName());
        names.forEach(c -> commands.put(c.toLowerCase(), command));
        manager.getManager().addPermission(command.getPermission());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        // TODO Custom help message
        if (args.length == 0) {
            manager.messenger.sendRaw("cmdapi_main_not_enough_arg", sender);
            return true;
        }
        Command command = commands.get(args[0].toLowerCase());
        if (command == null) {
            manager.messenger.sendRaw("cmdapi_main_wrong_subcommand", sender);
        } else {
            if (command.testPermission(sender))
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

}
