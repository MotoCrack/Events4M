package me.devnatan.events4m.fight.argument;

import me.devnatan.events4m.fight.FightPlugin;
import me.devnatan.events4m.fight.event.Event;
import me.devnatan.events4m.fight.event.EventPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class JoinArgument extends Argument {

    public JoinArgument() {
        super("entrar", 0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        FightPlugin plugin = FightPlugin.getInstance();
        Map<String, Location> locationMap = plugin.getLocationMap();
        if(!locationMap.containsKey("lobby")) {
            player.sendMessage(ChatColor.RED + "Não foi possível definir o local de teletransporte.");
            return;
        }

        Event event = plugin.getEvent();
        if(event.isStarted()) {
            if(!locationMap.containsKey("camarote")) {
                player.sendMessage(ChatColor.RED + "Não foi possível definir o local de teletransporte.");
                return;
            }

            player.sendMessage(" ");
            player.sendMessage(" " + ChatColor.AQUA + "O evento " + ChatColor.BOLD + "FIGHT" + ChatColor.AQUA + " já começou.");
            player.sendMessage(" " + ChatColor.AQUA + "Você foi teleportado para o camarote.");
            player.sendMessage(" ");
            player.teleport(locationMap.get("camarote"));
            return;
        }

        if(!event.isStarting()) {
            player.sendMessage(ChatColor.RED + "O evento Fight não está acontecendo.");
            return;
        }

        if(event.getPlayer(player) != null) {
            player.sendMessage(ChatColor.RED + "Você já está participando do evento Fight.");
            return;
        }

        EventPlayer eventPlayer = new EventPlayer(player);
        eventPlayer.setArmorContent(player.getInventory().getArmorContents());
        eventPlayer.setInventoryContent(player.getInventory().getContents());
        eventPlayer.setPlaying(false);
        event.getFighters().add(eventPlayer);
        player.getInventory().clear();
        player.teleport(locationMap.get("lobby"));
        player.sendMessage(" ");
        player.sendMessage(" " + ChatColor.AQUA + "Você entrou no evento " + ChatColor.BOLD + "FIGHT" + ChatColor.AQUA + ".");
        player.sendMessage(" " + ChatColor.AQUA + "Aguarde-o iniciar para duelar!");
        player.sendMessage(" ");
        player.sendMessage(" " + ChatColor.AQUA + "Seus itens serão devolvidos ao fim do evento.");
        player.sendMessage(" ");
    }
}
