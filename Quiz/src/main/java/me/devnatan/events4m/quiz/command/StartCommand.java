package me.devnatan.events4m.quiz.command;

import me.devnatan.events4m.quiz.util.CommandUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            return CommandUtil.canContinue((Player) sender, "events4m.quiz." + command.getName().toLowerCase());
        }

        // TODO: continuar =)
        return false;
    }
}
