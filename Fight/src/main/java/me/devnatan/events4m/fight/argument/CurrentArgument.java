package me.devnatan.events4m.fight.argument;

import me.devnatan.events4m.fight.FightPlugin;
import me.devnatan.events4m.fight.event.Event;
import me.devnatan.events4m.fight.event.EventPlayer;
import me.devnatan.events4m.fight.task.AbstractTask;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class CurrentArgument extends Argument {

    public CurrentArgument() {
        super("atual", 0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        FightPlugin plugin = FightPlugin.getInstance();
        Event event = plugin.getEvent();
        if(!event.isStarted()) {
            player.sendMessage(ChatColor.RED + "O evento Fight não está acontecendo.");
            player.sendMessage(ChatColor.RED + "Use /fight iniciar: para ele começar.");
            return;
        }

        if(event.getCurrentArray() == null) {
            player.sendMessage(ChatColor.RED + "Não há nenhum jogador duelando.");
            return;
        }

        EventPlayer[] current = event.getCurrentArray();
        player.sendMessage(" " + ChatColor.AQUA + "Evento " + ChatColor.BOLD + "FIGHT" + ChatColor.RESET + " — " + event.getCurrentIndex() + "º duelo");
        player.sendMessage(" " + ChatColor.AQUA + current[0].getPlayer().getName() + " * " + current[1].getPlayer().getName());
        player.sendMessage(" ");

        // TODO: Formatar a duração <3
        Map<String, AbstractTask> taskMap = plugin.getTaskMap();
        if(taskMap.containsKey("fighting")) {
            player.sendMessage(" " + ChatColor.AQUA + "Duração: " + taskMap.get("fighting").getElapsed() + " segundos.");
        }
        try {
            EventPlayer[] next = event.getSubfighters().get(event.getCurrentIndex() + 1);
            player.sendMessage(" " + ChatColor.AQUA + "Próximo duelo: " + next[0].getPlayer().getName() + " * " + next[1].getPlayer().getName());
        } catch (IndexOutOfBoundsException ignored) { }
    }
}
