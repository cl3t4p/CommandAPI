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
<dependency>
    <groupId>com.cl3t4p</groupId>
    <artifactId>command-api</artifactId>
    <version>0.2</version>
</dependency>
```

### Annotation

`@CommandInfo`
This annotation can be used witouth any arguments This annotation is used to check if a method is a command
- `name`: the name of the command. If not specified, the method name is used.
- `required`: the minimum number of arguments required for the command.
- `alias`: an array of alternative names for the command.

```java

import com.cl3t4p.commandapi.Command;
import com.cl3t4p.commandapi.annotation.CommandInfo;
import com.cl3t4p.commandapi.annotation.CommandPermission;
import com.cl3t4p.commandapi.arguments.Argument;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


// Check if the args is a player name and if the player is online
public class ExampleCommand implements Command {

    
    // Specifies that this method is a command handler for the "/teleport" command, 
    // with aliases "/tp" and "/tpt".
    // Requires the sender to provide 1 arguments (the target's name).
    // Requires the sender to have the "example.teleport" permission to use this command.
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

    @Override
    public void onEnable() {
        CommandManager cmdManager = new CommandManager(this);

        // Register the command
        cmdManager.add(new ExampleCommand());
    }
}
```

