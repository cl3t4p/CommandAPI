package com.cl3t4p.commandapi;

import com.cl3t4p.lib.chatlib.Messenger;
import com.cl3t4p.commandapi.annotation.CommandInfo;
import com.cl3t4p.commandapi.parser.Parser;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.plugin.Plugin;

import java.util.*;

/**
 * This class is used to create and manage commands.
 *
 * @author cl3t4p
 *
 * @version 0.7
 *
 * @since 0.2
 */
public class CommandManager {

    public static final String MSG_PREFIX = "cmdapi_";

    @Getter
    final Messenger messenger;

    final static HashMap<Class<?>, Parser<?>> PARSER = Parser.newMap();

    @Getter
    final CommandMapWrapper manager;
    @Getter
    final Set<Command> commands = new HashSet<>();

    final HashMap<String, MainCommand> mainCommands = new HashMap<>();

    public CommandManager(Plugin plugin) {
        this(plugin, new Messenger());
    }

    public CommandManager(Plugin plugin, Messenger messenger) {
        this.messenger = messenger;
        addMessageIfNotPresent("not_enough_arg", "&c> Not enough arguments!");
        addMessageIfNotPresent("wrong_sender", "&c> Only %s are allowed to do this command!");
        addMessageIfNotPresent("main_not_enough_arg", "&c> This command need at least 1 argument");
        addMessageIfNotPresent("main_wrong_subcommand", "&c> This command does not exists!");
        Parser.populateMessenger(messenger);
        this.manager = new CommandMapWrapper(plugin);
    }

    private void addMessageIfNotPresent(String key, String value) {
        if (!messenger.containsKey(key))
            messenger.addMessage(MSG_PREFIX + key, value);
    }

    protected Parser.Response<?> parse(Class<?> type, String[] args, int index) throws IllegalArgumentException {
        return PARSER.get(type).parse(args, index);
    }

    /**
     * Register the method of a command object.
     *
     * @param command
     *            The command object.
     */
    public void add(Commands command) {
        Arrays.stream(command.getClass().getDeclaredMethods())
                .filter(method -> method.getDeclaredAnnotation(CommandInfo.class) != null).map(method -> {
                    try {
                        return new CommandWrapper(method, command, this);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }).filter(Objects::nonNull).filter(CommandWrapper::isSuperCommand).forEach(cmd -> {
                    manager.register(cmd.getCommand());
                    commands.add(cmd.getCommand());
                });
    }

    /**
     * Unregister the command from the server.
     */
    public void unregister() {
        manager.unregister();
    }

    /**
     * Add a parser to the manager.
     *
     * @param clazz
     *            The class of the object to parse.
     * @param parser
     *            The parser.
     * @param <T>
     *            The type of the object to parse.
     */
    public <T> void addParser(Class<T> clazz, Parser<T> parser) {
        PARSER.put(clazz, parser);
    }

    public void addMainCommand(String[] mainNames, Command command, CommandInfo info, String permission) {
        String name = mainNames[mainNames.length - 1].toLowerCase();
        MainCommand mainCommand;
        if (!mainCommands.containsKey(name)) {
            mainCommand = new MainCommand(name, "", this);
            mainCommands.put(name, mainCommand);
        } else {
            mainCommand = mainCommands.get(name);
        }
        if (permission != null) {
            mainCommand.setPermission(permission);
        }
        if (info != null) {
            mainCommand.setAliases(Arrays.asList(info.alias()));
        }

        mainCommand.addCommand(command);

        if (mainNames.length > 1) {
            String[] subNames = Arrays.copyOfRange(mainNames, 0, mainNames.length - 1);
            addMainCommand(subNames, mainCommand);
        } else {
            manager.register(mainCommand);
        }

    }

    public void addMainCommand(String[] mainNames, Command command) {
        String name = mainNames[mainNames.length - 1].toLowerCase();
        MainCommand mainCommand;
        if (!mainCommands.containsKey(name)) {
            mainCommand = new MainCommand(name, "", this);
            mainCommands.put(name, mainCommand);
        } else {
            mainCommand = mainCommands.get(name);
        }
        mainCommand.addCommand(command);

        if (mainNames.length > 1) {
            String[] subNames = Arrays.copyOfRange(mainNames, 0, mainNames.length - 1);
            addMainCommand(subNames, mainCommand);
        } else {
            manager.register(mainCommand);
        }
    }
}
