package com.cl3t4p.commandapi;

import com.cl3t4p.commandapi.annotation.CommandInfo;
import com.cl3t4p.commandapi.parser.Parser;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.Command;
import org.bukkit.plugin.Plugin;

import java.util.*;

/**
 * This class is used to create and manage commands.
 *
 * @author cl3t4p
 * @version 0.3
 * @since 0.2
 */
public class CommandManager {


    final static HashMap<Class<?>, Parser<?>> PARSER = Parser.newMap();

    @Getter
    final CommandMapWrapper manager;
    @Getter
    final Set<Command> commands = new HashSet<>();

    final HashMap<String,MainCommand> mainCommands = new HashMap<>();

    @Getter
    @Setter
    String not_enough = "&c> Not enough arguments!";
    @Getter
    @Setter
    String wrong_type = "&c> Only %s are allowed to do this command!";

    public CommandManager(Plugin plugin) {
        this.manager = new CommandMapWrapper(plugin);
    }

    protected Parser.Response<?> parse(Class<?> type, String[] args, int index) throws IllegalArgumentException {
        return PARSER.get(type).parse(args, index);
    }

    /**
     * Register the method of a command object.
     *
     * @param command The command object.
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
                })
                .filter(Objects::nonNull)
                .forEach(cmd -> {
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
     * @param clazz  The class of the object to parse.
     * @param parser The parser.
     * @param <T>    The type of the object to parse.
     */
    public <T> void addParser(Class<T> clazz, Parser<T> parser) {
        PARSER.put(clazz, parser);
    }

    public void addMainCommand(String[] mainNames,Command command) {
        String name = mainNames[mainNames.length-1].toLowerCase();
        MainCommand mainCommand;
        if(!mainCommands.containsKey(name)){
            //TODO figure out permissions
            mainCommand = new MainCommand(name,"");
            mainCommands.put(name,mainCommand);
        }else{
            mainCommand = mainCommands.get(name);
        }
        mainCommand.addCommand(command);

        if(mainNames.length>1) {
            String[] subNames = Arrays.copyOfRange(mainNames, 0, mainNames.length - 1);
            addMainCommand(subNames, mainCommand);
        }else{
            manager.register(mainCommand);
        }
    }
}
