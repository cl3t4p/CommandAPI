package com.cl3t4p.commandapi;


import com.cl3t4p.commandapi.annotation.Msg;
import com.cl3t4p.commandapi.annotation.CommandInfo;
import com.cl3t4p.commandapi.annotation.CommandPermission;
import com.cl3t4p.commandapi.exception.CommandException;
import com.cl3t4p.commandapi.parser.Parser;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;


/**
 * This class is used to wrap a method as a command.
 * @author cl3t4p
 * @version 0.2
 * @since 0.2
 */
@Getter
public class CommandWrapper {

    final String permission;
    final int required;
    final Method method;
    final Command command;
    final Object instance;
    final CommandManager manager;
    final Class<?>[] argumentsType;

    /**
     * This method is used to get the {@link CommandInfo} annotation of a method.
     * @param method The method to get the annotation from.
     * @param object The object that contains the method.
     * @param manager The {@link CommandManager} that will manage the command.
     * @throws CommandException If the method is not valid to be a command.
     */
    public CommandWrapper(Method method, Object object, CommandManager manager) throws CommandException {
        CommandInfo info = getInfo(method);
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

        permission = getPermission(method);

        if (info.alias().length != 0) {
            command.setAliases(Arrays.asList(info.alias()));
        }
    }

    /**
     * This method is used to get the {@link CommandPermission} annotation of a method.
     * @param method The method to get the annotation from.
     * @return The permission of the command.
     */
    private String getPermission(Method method) {
        CommandPermission perm = method.getDeclaredAnnotation(CommandPermission.class);
        if (perm != null) {
            command.setPermission(permission);
            return perm.value();
        }
        return "";
    }

    /**
     * This method is used to get the {@link CommandInfo} annotation of a method.
     * @param method The method to get the annotation from.
     * @return The {@link CommandInfo} annotation of the method.
     * @throws CommandException If the method is not valid to be a command.
     */
    private CommandInfo getInfo(Method method) throws CommandException {
        CommandInfo info = method.getDeclaredAnnotation(CommandInfo.class);
        if (info == null) {
            throw new CommandException("Method need to contain @CommandInfo");
        }
        if (!CommandSender.class.isAssignableFrom(method.getParameterTypes()[0])) {
            throw new CommandException("The first arguments does not implements CommandSender");
        }
        for (Class<?> parameterType : method.getParameterTypes()) {
            if(!manager.parsers.containsKey(parameterType))
                throw new CommandException("The parser does not have a parser for " + parameterType.getTypeName());
        }
        return info;
    }

    /**
     * This method is used to execute the command.
     * @param sender The sender of the command.
     * @param args The arguments of the command.
     */
    public void onCommand(CommandSender sender, String[] args) {
        if (!argumentsType[0].isInstance(sender)) {
            sender.sendMessage(color(String.format(manager.getWrong_type(), argumentsType[0].getTypeName())));
            return;
        }
        if (args.length < required) {
            sender.sendMessage(color(manager.getNot_enough()));
            return;
        }

        Object[] arguments = new Object[method.getParameterCount()];

        arguments[0] = sender;

        for (int i = 0; i < method.getParameterCount() - 1; i++) {
            if (args.length <= i)
                break;

            try {
                Parser.Response<?> response = manager.parse(argumentsType[i+1],args,i);
                i = response.getIndex();
                arguments[i + 1] = response.getObject();
            } catch (IllegalArgumentException e) {
                Msg msg = method.getParameters()[i + 1].getDeclaredAnnotation(Msg.class);
                if (msg == null)
                    sender.sendMessage(color(String.format(e.getMessage(), i + 1, e.getMessage())));
                else
                    sender.sendMessage(color(msg.value()));
                return;
            }
        }

        try {
            method.invoke(instance, arguments);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to translate color codes.
     */
    protected String color(@NotNull String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
