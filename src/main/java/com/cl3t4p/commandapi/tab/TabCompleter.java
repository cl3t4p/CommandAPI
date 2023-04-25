package com.cl3t4p.commandapi.tab;

import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * This class is used to parse a string to a specific type.
 *
 * @author cl3t4p
 *
 * @version 0.7
 *
 * @since 0.7
 */
public interface TabCompleter {
    List<String> onComplete(CommandSender sender, String[] args);
}
