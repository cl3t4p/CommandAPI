package com.cl3t4p.commandapi;

import com.cl3t4p.commandapi.annotation.CommandInfo;
import com.cl3t4p.commandapi.exception.CommandException;
import com.cl3t4p.commandapi.parser.Parser;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class is used to create and manage commands.
 *
 * @author cl3t4p
 * @version 0.2
 * @since 0.2
 */
public class CommandManager {

    @Getter
    final CommandMapWrapper manager;

    @Getter
    @Setter
    String not_enough = "&c> Not enough arguments!";
    @Getter
    @Setter
    String wrong_type = "&c> Only %s are allowed to do this command!";

    @Getter
    final Set<CommandWrapper> commands = new HashSet<>();

    final HashMap<Class<?>, Parser<?>> parsers = Parser.newMap();

    public CommandManager(Plugin plugin) {
        this.manager = new CommandMapWrapper(plugin);
    }

    protected Object parse(Class<?> type, String[] args,int index) throws IllegalArgumentException {
        return parsers.get(type).parse(args,index);
    }

    /**
     * Register the method of a command object.
     * @param command The command object.
     */
    public void add(Command command) {
        Set<CommandWrapper> cmds = Arrays.stream(command.getClass().getDeclaredMethods())
                .filter(method -> method.getDeclaredAnnotation(CommandInfo.class) != null).map(method -> {
                    try {
                        return new CommandWrapper(method, command, this);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }).filter(Objects::nonNull).collect(Collectors.toSet());
        cmds.stream().map(CommandWrapper::getCommand).forEach(manager::register);
        commands.addAll(cmds);
    }

    /**
     * Unregister the command from the server.
     */
    public void unregister() {
        manager.unregister();
    }

    /**
     * Add a parser to the manager.
     * @param clazz The class of the object to parse.
     * @param parser The parser.
     * @param <T> The type of the object to parse.
     */
    public <T> void addParser(Class<T> clazz, Parser<T> parser) {
        parsers.put(clazz, parser);
    }



}
