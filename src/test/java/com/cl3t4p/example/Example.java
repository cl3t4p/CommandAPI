package com.cl3t4p.example;

import com.cl3t4p.commandapi.Commands;
import com.cl3t4p.commandapi.annotation.CommandInfo;
import com.cl3t4p.commandapi.annotation.CommandPermission;
import com.cl3t4p.commandapi.annotation.Msg;
import org.bukkit.command.CommandSender;

public class Example implements Commands {

    @CommandPermission("test.perm")
    @CommandInfo(name = "village claim")
    public void start(CommandSender sender,Integer number) {

    }

}
