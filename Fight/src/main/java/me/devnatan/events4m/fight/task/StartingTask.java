package me.devnatan.events4m.fight.task;

import me.devnatan.events4m.fight.FightPlugin;
import me.devnatan.events4m.fight.event.Event;
import org.bukkit.Bukkit;

public class StartingTask extends AbstractTask {

    private final int DURATION = 10;

    public StartingTask() {
        super(20L, false);
    }

    @Override
    void schedule() {
        FightPlugin plugin = FightPlugin.getInstance();
        Event event = plugin.getEvent();
        if(elapsed == DURATION) {
            if(event.getFighters().size() >= 10) {
                // debug
                Bukkit.broadcastMessage("Distribuindo jogadores.");
                event.subdivide(remaining -> {
                    // debug
                    Bukkit.broadcastMessage("Jogadores sub-divividos");
                    if(remaining != null) {
                        // debug
                        Bukkit.broadcastMessage("O resto: " + remaining.getPlayer().getName());
                    }

                    plugin.getTaskMap().get("fighting").start(plugin);
                });
            } else {
                event.forceStop();
            }

            stop();
            return;
        }

        // debug
        Bukkit.broadcastMessage("Iniciando sá praga em " + (DURATION - elapsed) + "...");
    }

}
