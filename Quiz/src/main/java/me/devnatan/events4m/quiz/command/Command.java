package me.devnatan.events4m.quiz.command;

import me.devnatan.events4m.quiz.Quiz;
import me.devnatan.events4m.quiz.argument.Argument;
import me.devnatan.events4m.quiz.util.Executable;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public abstract class Command implements CommandExecutor, Executable {

    private boolean registered = false;
    private Argument[] arguments;

    Command(Argument... arguments) {
        this.arguments = arguments;
    }

    public void register(String name) {
        if(!registered) {
            Quiz.getInstance().getCommand(name).setExecutor(this);
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
