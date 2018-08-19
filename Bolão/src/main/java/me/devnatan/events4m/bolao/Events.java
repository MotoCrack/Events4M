package me.devnatan.events4m.bolao;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Iterator;

public class Events implements Listener {

    @EventHandler
    public void on(PlayerQuitEvent e) {
        Bolao bolao = Bolao.getInstance();
        Event event = bolao.getEvent();
        if(event.isStarted()) {
            Player player = e.getPlayer();
            Iterator<Player> iterator = event.getPlayers().iterator();
            while(iterator.hasNext()) {
                Player next = iterator.next();
                if(next.equals(player)) {
                    bolao.getEconomy().depositPlayer(player, bolao.getAmount());
                    iterator.remove();
                    break;
                }
            }
        }
    }

}
