package com.cl3t4p.commandapi;


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

    private final HashMap<String,Command> commands = new HashMap<>();
    public MainCommand(@NotNull String name,String permission) {
        super(name);
        if(!permission.isEmpty())
            setPermission(permission);
    }

    public void addCommand(Command command){
        commands.put(command.getName().toLowerCase(),command);
    }
    public void addCommand(CommandWrapper command){
        addCommand(command.getCommand());
    }

    public String getHelpMessage(){
        return  "&c> Please select a option";
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        if(args.length == 0){
            sender.sendMessage(getHelpMessage());
            return true;
        }
        Command command = commands.get(args[0].toLowerCase());
        if(command == null){
            sender.sendMessage(getHelpMessage());
        }else{
            command.execute(sender,alias,removeFirstArray(args));
        }
        return true;
    }


    @NotNull
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if(args.length <= 1){
            return new ArrayList<>(commands.keySet());
        }else{
            Command command = commands.get(args[0].toLowerCase());
            if(command != null){
                command.tabComplete(sender,alias,removeFirstArray(args));
            }
        }
        return super.tabComplete(sender, alias, args);
    }

    private String[] removeFirstArray(String[] array){
        String[] returnArray = new String[array.length - 1];
        System.arraycopy(array, 1, returnArray, 0, array.length - 1);
        return returnArray;
    }
}
