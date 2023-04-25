package com.cl3t4p.example;


import com.cl3t4p.commandapi.Commands;
import com.cl3t4p.commandapi.annotation.CommandInfo;
import com.cl3t4p.commandapi.annotation.CommandPermission;
import com.cl3t4p.commandapi.annotation.Msg;
import com.cl3t4p.commandapi.annotation.Tab;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Example implements Commands {

    @Tab(value = "startTab",isMethod =true)
    @CommandPermission("test.example")
    @CommandInfo(name = "test example")
    public void start(CommandSender sender,@Msg("in_cooldown") Integer number) {

    }




    public List<String> startTab(CommandSender sender,String[] args){
        if(args.length == 0){
            return List.of("test","test");
        }else{
            return List.of();
        }
    }

}
