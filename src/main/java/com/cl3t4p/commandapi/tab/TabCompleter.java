package com.cl3t4p.commandapi.tab;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface TabCompleter {
    List<String> onComplete(CommandSender sender,String[] args);
}
