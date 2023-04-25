# Command API

This is a simple command api for bukkit/spigot plugins.

## How to use

### Add the dependency

Install to local maven

```
git clone git@github.com:cl3t4p/CommandAPI.git
cd CommandAPI
mvn source:jar install
```

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```xml  
<dependency>
    <groupId>com.github.cl3t4p</groupId>
    <artifactId>CommandAPI</artifactId>
    <version>0.7</version>
</dependency>
```
[![](https://jitpack.io/v/cl3t4p/CommandAPI.svg)](https://jitpack.io/#cl3t4p/CommandAPI)

### Annotation

`@CommandInfo`
This annotation can be used without any arguments This annotation is used to check if a method is a command
- `name`: the name of the command. If not specified, the method name is used.
- `required`: the minimum number of arguments required for the command.
- `alias`: an array of alternative names for the command.

```java

import com.cl3t4p.commandapi.Commands;
import com.cl3t4p.commandapi.annotation.CommandInfo;
import com.cl3t4p.commandapi.annotation.CommandPermission;
import com.cl3t4p.commandapi.arguments.Argument;
import org.bukkit.entity.Player;


public class ExampleCommand implements Commands {


    // Specifies that this method is a command handler for the "/teleport" command, 
    // with aliases "/tp" and "/tpt".
    // Requires the sender to provide 1 argument (the target's name).
    // Requires the sender to have the "example.teleport" permission to use this command.
    // The library will check if the sender is a player and if the target is online.
    @CommandInfo(name = "teleport", required = 1, alias = {"tp", "tpt"})
    @CommandPermission("example.teleport")
    public teleport(Player player, Player target) {
        player.teleport(target);
    }

}
```

---

### Register the command

```java

import com.cl3t4p.commandapi.CommandManager;
import com.cl3t4p.commandapi.CommandMapWrapper;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {
    
    CommandManager cmdManager;

    @Override
    public void onEnable() {
        cmdManager = new CommandManager(this);

        // Register the command
        cmdManager.add(new ExampleCommand());
    }

    @Override
    public void onDisable() {
        
        // Unregister the commands
        cmdManager.unregister();
    }
}
```

