package me.devnatan.events4m.fight.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void on(PlayerQuitEvent e) {
        // TODO: Remover o jogador do evento.
    }

    @EventHandler
    public void on(PlayerKickEvent e) {
        // TODO: Remover o jogador do evento
    }

    @EventHandler
    public void on(PlayerTeleportEvent e) {
        // TODO: Remover o jogador do evento.
    }

    @EventHandler
    public void on(PlayerDeathEvent e) {
        // TODO: Verificar o jogador no evento ou remove-lo.
    }

}
