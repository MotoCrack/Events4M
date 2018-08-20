package me.devnatan.events4m.fight.argument;

import me.devnatan.events4m.fight.FightPlugin;
import me.devnatan.events4m.fight.event.Event;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class StartArgument extends Argument {

    public StartArgument() {
        super("iniciar", 0);
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
        if(event.isStarted()) {
            player.sendMessage(ChatColor.RED + "O evento Fight já está acontecendo.");
            player.sendMessage(ChatColor.RED + "Use /fight parar: para interrompe-lo.");
            return;
        }

        Map<String, Location> locationMap = plugin.getLocationMap();
        if(!locationMap.containsKey("lobby")) {
            player.sendMessage(ChatColor.RED + "Não foi possível definir o local de lobby.");
            return;
        }

        if(!locationMap.containsKey("saida")) {
            player.sendMessage(ChatColor.RED + "Não foi possível definir o local de saida.");
            return;
        }

        if(!locationMap.containsKey("entrada1")) {
            player.sendMessage(ChatColor.RED + "Não foi possível definir o local de entrada 1.");
            return;
        }

        if(!locationMap.containsKey("entrada2")) {
            player.sendMessage(ChatColor.RED + "Não foi possível definir o local de entrada 2.");
            return;
        }

        player.sendMessage(ChatColor.YELLOW + "Iniciando evento Fight...");
        event.start();
    }
}
