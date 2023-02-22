package com.cl3t4p.commandapi;

import com.cl3t4p.commandapi.CommandManager;
import com.cl3t4p.commandapi.annotation.CommandInfo;
import com.cl3t4p.commandapi.annotation.CommandPermission;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Getter
public class CommandWrapper {

    final String permission;
    final int required;
    final Method method;
    final Command command;
    final Object instance;
    final CommandManager manager;
    final Class<?>[] argumentsType;

    public CommandWrapper(Method method, Object object, CommandManager manager) throws Exception {
        CommandInfo info = method.getDeclaredAnnotation(CommandInfo.class);
        if (info == null) {
            throw new Exception("Method need to contain @CommandInfo");
        }
        if (!CommandSender.class.isAssignableFrom(method.getParameterTypes()[0])) {
            throw new Exception("Method first arguments does not impements CommandSender");
        }
        this.argumentsType = method.getParameterTypes();
        this.method = method;
        this.required = info.required();
        this.instance = object;
        this.manager = manager;

        String name = info.name().isEmpty() ? method.getName() : info.name();

        command = new Command(name) {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
                onCommand(sender, args);
                return true;
            }
        };

        CommandPermission perm = method.getDeclaredAnnotation(CommandPermission.class);
        if (perm != null) {
            permission = perm.value();
            command.setPermission(permission);
        } else
            permission = "";

        if (info.alias().length != 0) {
            command.setAliases(Arrays.asList(info.alias()));
        }
    }

    public void onCommand(CommandSender sender, String[] args) {
        if (!argumentsType[0].isInstance(sender)) {
            sender.sendMessage(
                    color("&c> Only " + argumentsType[0].getTypeName() + " are allowed to do this command!"));
            return;
        }
        if (args.length < required) {
            sender.sendMessage(color("&c> Not enough arguments!"));
            return;
        }

        Object[] arguments = new Object[method.getParameterCount()];

        arguments[0] = sender;

        for (int i = 0; i < method.getParameterCount() - 1; i++) {
            if (args.length == i)
                break;
            try {
                arguments[i + 1] = manager.parse(argumentsType[i + 1], args[i]);
            } catch (IllegalArgumentException e) {
                sender.sendMessage(
                        color(String.format("&c> Wrong argument at position %d error : %s", i + 1, e.getMessage())));
                return;
            }
        }
        try {
            method.invoke(instance, arguments);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected String color(@NotNull String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
