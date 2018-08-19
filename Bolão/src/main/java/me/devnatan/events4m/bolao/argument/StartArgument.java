package me.devnatan.events4m.bolao.argument;

import org.bukkit.command.CommandSender;

public class StartArgument extends Argument {

    public StartArgument() {
        super("iniciar", 0);
        consoleExecutable = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

    }
}
