package me.devnatan.events4m.fight.listener;

import me.devnatan.events4m.fight.FightPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void on(PlayerQuitEvent e) {
        FightPlugin.getInstance().getEvent().safeRemoveIfPresent(e.getPlayer());
    }

    @EventHandler
    public void on(PlayerKickEvent e) {
        FightPlugin.getInstance().getEvent().safeRemoveIfPresent(e.getPlayer());
    }

    @EventHandler
    public void on(PlayerTeleportEvent e) {
        FightPlugin.getInstance().getEvent().safeRemoveIfPresent(e.getPlayer());
    }

    @EventHandler
    public void on(PlayerDeathEvent e) {
        FightPlugin.getInstance().getEvent().safeRemoveIfPresent(e.getEntity());
    }

}
