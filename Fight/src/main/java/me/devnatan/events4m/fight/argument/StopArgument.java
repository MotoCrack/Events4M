package me.devnatan.events4m.fight.argument;

import me.devnatan.events4m.fight.FightPlugin;
import me.devnatan.events4m.fight.event.Event;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class StopArgument extends Argument {

    public StopArgument() {
        super("parar", 0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if(!player.hasPermission("events4m.fight." + getName().toLowerCase())) {
            player.sendMessage(ChatColor.RED + "Você não tem permissão para fazer isto.");
            return;
        }

        FightPlugin plugin = FightPlugin.getInstance();
        Event event = plugin.getEvent();
        if(!event.isStarted()) {
            player.sendMessage(ChatColor.RED + "O evento Fight não está acontecendo.");
            player.sendMessage(ChatColor.RED + "Use /fight iniciar: para ele começar.");
            return;
        }

        Map<String, Location> locationMap = plugin.getLocationMap();
        if(!locationMap.containsKey("saida")) {
            player.sendMessage(ChatColor.RED + "Não foi possível definir o local de saida.");
            return;
        }

        player.sendMessage(ChatColor.YELLOW + "Interrompendo evento Fight...");
        event.forceStop(true);
    }
}
