package com.cl3t4p.commandapi.tab;

import lombok.SneakyThrows;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;
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
public class MethodCompleter implements TabCompleter {

    final Method method;
    final Object object;

    public MethodCompleter(Method method, Object object) {
        this.method = method;
        this.object = object;
    }

    @SneakyThrows
    @Override
    public List<String> onComplete(CommandSender sender, String[] args) {
        return (List<String>) method.invoke(object, sender, args);
    }
}
