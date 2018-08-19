package me.devnatan.events4m.bolao.argument;

import me.devnatan.events4m.bolao.Bolao;
import me.devnatan.events4m.bolao.Event;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveArgument extends Argument {

    public LeaveArgument() {
        super("sair", 0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        Bolao b = Bolao.getInstance();
        Event e = b.getEvent();
        if(!e.getPlayers().contains(p)) {
            p.sendMessage(ChatColor.RED + "Você não está participando do evento Bolão.");
            return;
        }

        e.getPlayers().remove(p);
        b.getEconomy().depositPlayer(p, b.getAmount());
        p.sendMessage(" ");
        p.sendMessage(" " + ChatColor.RED + "Você saiu do evento " + ChatColor.BOLD + "BOLãO" + ChatColor.RED + ".");
        p.sendMessage(" " + ChatColor.RED + "Devolvemos seus " + ChatColor.RESET + "$" + String.format("%.2f", b.getAmount()) + ChatColor.RED + " para sua conta.");
        p.sendMessage(" " + ChatColor.RED + "Participe novamente do evento usando " + ChatColor.RESET + "/bolao" + ChatColor.RED + ".");
        p.sendMessage(" ");
    }
}
