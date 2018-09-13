package me.devnatan.events4m.fight.task;

import me.devnatan.events4m.fight.FightPlugin;
import me.devnatan.events4m.fight.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class StartingTask extends AbstractTask {

    public StartingTask() {
        super(20L, false);
    }

    @Override
    void schedule() {
        FightPlugin plugin = FightPlugin.getInstance();
        Event event = plugin.getEvent();
        final int DURATION = 10;
        if(elapsed == DURATION) {
            if(event.getFighters().size() >= 2) {
                // debug
                Bukkit.broadcastMessage("Distribuindo jogadores.");
                event.subdivide(remaining -> {
                    // debug
                    Bukkit.broadcastMessage("Jogadores sub-divividos");
                    if(remaining != null) {
                        // debug
                        Bukkit.broadcastMessage("O resto: " + remaining.getPlayer().getName());
                    }

                    Bukkit.broadcastMessage(" ");
                    Bukkit.broadcastMessage(" " + ChatColor.AQUA + "Evento " + ChatColor.BOLD + "FIGHT" + ChatColor.AQUA + " começou!");
                    Bukkit.broadcastMessage(" " + ChatColor.AQUA + "Assista de camarote em " + ChatColor.RESET + "/fight camarote" + ChatColor.AQUA + ".");
                    Bukkit.broadcastMessage(" ");
                    Bukkit.broadcastMessage(" " + ChatColor.AQUA + "Estão participando " + ChatColor.RESET + event.getFighters().size() + " jogadores" + ChatColor.AQUA + ".");
                    Bukkit.broadcastMessage(" ");
                    plugin.getTaskMap().get("fighting").start(plugin);
                });
            } else event.forceStop(true);

            stop();
            return;
        }

        // debug
        Bukkit.broadcastMessage(ChatColor.AQUA + "Evento " + ChatColor.BOLD + "FIGHT" + ChatColor.RESET + " inicia em " + (DURATION - elapsed) + "...");
    }

}
