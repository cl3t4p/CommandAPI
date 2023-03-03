package com.cl3t4p.example;

import com.cl3t4p.commandapi.Commands;
import com.cl3t4p.commandapi.annotation.CommandInfo;
import com.cl3t4p.commandapi.annotation.CommandPermission;
import org.bukkit.command.CommandSender;

public class Example implements Commands {

    @CommandPermission("test.example")
    @CommandInfo(name = "test example")
    public void start(CommandSender sender,Integer number) {

    }

}
