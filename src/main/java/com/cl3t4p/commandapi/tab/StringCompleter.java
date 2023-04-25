package com.cl3t4p.commandapi.tab;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class StringCompleter implements TabCompleter {


    List<List<String>> completitionList = new ArrayList<>();
    

    public StringCompleter(String string) {
        for (String s : string.split(",")) {
            completitionList.add(List.of(s.split("\\|")));
        }
    }

    @Override
    public List<String> onComplete(CommandSender sender, String[] args) {
        if(completitionList.size() < args.length){
            return List.of("");
        }
        return completitionList.get(args.length-1);
    }
}
