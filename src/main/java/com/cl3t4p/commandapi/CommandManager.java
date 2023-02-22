package com.cl3t4p.commandapi;

import com.cl3t4p.commandapi.annotation.CommandInfo;
import com.cl3t4p.commandapi.parser.Parser;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Manager for controlling Command classes
 */
public class CommandManager {
    @Getter
    final CommandMapWrapper manager;

    final HashMap<Class<?>, Parser<?>> parsers = Parser.newMap();
    @Getter
    final Set<CommandWrapper> commands = new HashSet<>();

    public CommandManager(Plugin plugin) {
        this.manager = new CommandMapWrapper(plugin);
    }

    protected Object parse(Class<?> type, String arg) throws IllegalArgumentException {
        return parsers.get(type).parse(arg);
    }

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

    public void unregister() {
        manager.unregister();
    }

    public <T> void addParser(Class<T> clazz, Parser<T> parser) {
        parsers.put(clazz, parser);
    }
}
