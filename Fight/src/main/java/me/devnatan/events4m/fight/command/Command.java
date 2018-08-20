package me.devnatan.events4m.fight.command;

import lombok.Getter;
import me.devnatan.events4m.fight.argument.Argument;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public abstract class Command implements CommandExecutor, Executable {

    @Getter private boolean registered = false;
    @Getter private Argument[] arguments;

    public Command(Argument... arguments) {
        this.arguments = arguments;
    }

    public void register(JavaPlugin plugin, String name) {
        if(!registered) {
            plugin.getCommand(name).setExecutor(this);
            registered = true;
        }
    }

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        for(Argument argument : arguments) {
            if(argument != null) {
                int len = argument.getTarget();
                if(args.length > len && args[len] != null && args[len].equalsIgnoreCase(argument.getName())) {
                    if(!(sender instanceof Player) && !argument.isConsoleExecutable()) {
                        sender.sendMessage("Somente jogadores IN-GAME podem fazer isto.");
                        return true;
                    }

                    argument.execute(sender, Arrays.copyOfRange(args, len + 1, args.length));
                    return true;
                }
            }
        }

        execute(sender, args);
        return true;
    }
}
