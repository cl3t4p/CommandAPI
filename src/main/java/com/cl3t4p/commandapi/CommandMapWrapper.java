package com.cl3t4p.commandapi;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Used to get the {@link CommandMap} from the server.
 *
 * @author cl3t4p
 *
 * @version 1.0
 *
 * @since 1.0
 */
public class CommandMapWrapper {
    CommandMap commandMap;
    private final Plugin plugin;
    final Set<Permission> perms = new HashSet<>();
    final Set<Command> commands = new HashSet<>();

    public CommandMapWrapper(Plugin plugin) {
        this.plugin = plugin;
    }

    public CommandMap getCommandMap() {
        if (commandMap == null) {
            try {
                final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                bukkitCommandMap.setAccessible(true);
                commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            } catch (Exception e) {
                e.printStackTrace();
                commandMap = null;
            }
        }
        return commandMap;
    }

    /**
     * Register the command and the permission if it has one.
     *
     * @param command
     *            The command to register
     */
    public void register(Command command) {
        String permission = command.getPermission();
        if (permission != null && !permission.isEmpty()) {
            if (!perms.stream().map(Permission::getName).collect(Collectors.toSet())
                    .contains(command.getPermission())) {
                Permission perm = new Permission(permission);
                Bukkit.getServer().getPluginManager().addPermission(perm);
                perms.add(perm);
            }
        }
        getCommandMap().register(command.getName(), plugin.getName(), command);
        commands.add(command);
    }

    public void unregister() {
        perms.forEach(p -> Bukkit.getPluginManager().removePermission(p));
    }

}
