package com.cl3t4p.commandapi.tab;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
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
public class StringCompleter implements TabCompleter {

    List<List<String>> completitionList = new ArrayList<>();

    public StringCompleter(String string) {
        for (String s : string.split(",")) {
            completitionList.add(List.of(s.split("\\|")));
        }
    }

    @Override
    public List<String> onComplete(CommandSender sender, String[] args) {
        if (completitionList.size() < args.length) {
            return List.of("");
        }
        return completitionList.get(args.length - 1);
    }
}
