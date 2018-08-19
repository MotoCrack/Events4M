package me.devnatan.events4m.bolao.argument;

import me.devnatan.events4m.bolao.Bolao;
import me.devnatan.events4m.bolao.Event;
import me.devnatan.events4m.bolao.util.AnyUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class StartArgument extends Argument {

    public StartArgument() {
        super("iniciar", 0);
        consoleExecutable = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        AnyUtil.ifPermissioned(sender, "events4m.bolao." + getName().toLowerCase(), () -> {
            Bolao b = Bolao.getInstance();
            Event e = b.getEvent();
            if(e.isStarted()) {
                sender.sendMessage(ChatColor.RED + "O evento Bolão já começou.");
                return;
            }

            sender.sendMessage(ChatColor.YELLOW + "Iniciando evento Bolão...");
            e.start();
        });
    }
}
