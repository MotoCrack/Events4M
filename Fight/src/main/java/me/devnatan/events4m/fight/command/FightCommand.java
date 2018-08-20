package me.devnatan.events4m.fight.command;

import me.devnatan.events4m.fight.argument.Argument;
import org.bukkit.command.CommandSender;

public class FightCommand extends Command {

    public FightCommand(Argument... arguments) {
        super(arguments);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("Eae men");
    }
}
