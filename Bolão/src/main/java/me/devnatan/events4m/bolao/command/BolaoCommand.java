package me.devnatan.events4m.bolao.command;

import me.devnatan.events4m.bolao.Bolao;
import me.devnatan.events4m.bolao.Event;
import me.devnatan.events4m.bolao.argument.Argument;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BolaoCommand extends Command {

    public BolaoCommand(Argument[] arguments) {
        super(arguments);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("Somente jogadores IN-GAME podem fazer isto.");
            return;
        }

        Player p = (Player) sender;
        Bolao b = Bolao.getInstance();
        Event e = b.getEvent();
        if(!e.isStarted()) {
            p.sendMessage(ChatColor.RED + "O evento Bolão ainda não começou.");
            return;
        }

        if(e.getPlayers().contains(p)) {
            p.sendMessage(ChatColor.RED + "Você já está participando do evento.");
            p.sendMessage(ChatColor.RED + "Total acumulado: " + ChatColor.RESET + "$" + String.format("%.2f", b.getAmount()));
            return;
        }

        if(!b.getEconomy().has(p, b.getAmount())) {
            p.sendMessage(ChatColor.RED + "Você precisa de $" + String.format("%.2f", b.getAmount()) + " para apostar no bolão.");
            return;
        }

        e.getPlayers().add(p);
        b.getEconomy().withdrawPlayer(p, b.getAmount());
        p.sendMessage(" ");
        p.sendMessage(" " + ChatColor.GREEN + "Você está participando do evento " + ChatColor.BOLD + "BOLãO" + ChatColor.GREEN + ".");
        p.sendMessage(" " + ChatColor.GREEN + "Nós removemos " + ChatColor.RESET + "$" + String.format("%.2f", b.getAmount()) + ChatColor.GREEN + " da sua conta.");
        p.sendMessage(" " + ChatColor.GREEN + "Saia do evento usando " + ChatColor.RESET + "/bolao sair" + ChatColor.GREEN + " e seu dinheiro será devolvido.");
        p.sendMessage(" " + ChatColor.GREEN + "Caso você saia do jogo seu dinheiro também será devolvido.");
        p.sendMessage(" ");
    }
}
