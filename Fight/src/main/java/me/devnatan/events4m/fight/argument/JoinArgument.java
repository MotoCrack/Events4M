package me.devnatan.events4m.fight.argument;

import me.devnatan.events4m.fight.FightPlugin;
import me.devnatan.events4m.fight.event.Event;
import me.devnatan.events4m.fight.event.EventPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinArgument extends Argument {

    public JoinArgument() {
        super("entrar", 0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        FightPlugin plugin = FightPlugin.getInstance();
        if(!plugin.getLocationMap().containsKey("lobby")) {
            player.sendMessage(ChatColor.RED + "Não foi possível definir o local de teletransporte.");
            return;
        }

        Event event = plugin.getEvent();
        if(event.isStarted()) {
            player.sendMessage(" ");
            player.sendMessage(" " + ChatColor.AQUA + "O evento " + ChatColor.BOLD + "FIGHT" + ChatColor.AQUA + " já começou.");
            player.sendMessage(" " + ChatColor.AQUA + "Você foi teleportado para o camarote.");
            player.sendMessage(" ");
            // TODO: Teleportar para o camarote
            return;
        }

        EventPlayer eventPlayer = event.getFighters().stream().filter(it -> it.getPlayer().equals(player)).findFirst().orElse(null);
        if(eventPlayer != null) {
            player.sendMessage(ChatColor.RED + "Você já está participando do evento Fight.");
            return;
        }

        eventPlayer = new EventPlayer(player);
        eventPlayer.setStoredInventoryContent(player.getInventory().getContents());
        eventPlayer.setPlaying(false);
        event.getFighters().append(eventPlayer);
        player.getInventory().clear();
        // TODO: Teleportar para a entrada.
        // TODO: Fazer uma mensagem de entrada empolgante.
    }
}
