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
            if(event.getFighters().len() >= 10) {
                Bukkit.broadcastMessage("Distribuindo jogadores.");
                event.subdivide(remaining -> {
                    Bukkit.broadcastMessage("Jogadores sub-divividos");
                    if(remaining != null) {
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

        Bukkit.broadcastMessage("Iniciando sá praga em " + (DURATION - elapsed) + "...");
    }

}
