package me.devnatan.events4m.quiz.command;

import lombok.Getter;
import me.devnatan.events4m.quiz.Quiz;
import me.devnatan.events4m.quiz.argument.Argument;
import me.devnatan.events4m.quiz.util.Executable;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public abstract class Command implements CommandExecutor, Executable {

    @Getter private boolean registered = false;
    @Getter private Argument[] arguments;

    Command(Argument... arguments) {
        this.arguments = arguments;
    }

    public void register(String name) {
        if(!registered) {
            Quiz.getInstance().getCommand(name).setExecutor(this);
            registered = true;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        if(args.length == 0) {
            execute(sender, args);
            return true;
        }

        for(Argument argument : arguments) {
            if(argument != null) {
                if(!(sender instanceof Player) && !argument.isConsoleExecutable()) {
                    sender.sendMessage("Somente jogadores IN-GAME podem fazer isto.");
                    return true;
                }

                if(args.length >= argument.getTarget() && args[argument.getTarget()].equalsIgnoreCase(argument.getName()))
                    argument.execute(sender, Arrays.copyOfRange(args, argument.getTarget(), args.length));
                return true;
            }
        }

        sender.sendMessage(ChatColor.RED + "Comando inválido, use /" + command.getName() + " para saber mais.");
        return true;
    }
}
