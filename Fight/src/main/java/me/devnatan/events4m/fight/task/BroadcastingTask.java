package me.devnatan.events4m.fight.task;

import me.devnatan.events4m.fight.FightPlugin;
import me.devnatan.events4m.fight.event.Event;
import org.bukkit.Bukkit;

public class BroadcastingTask extends AbstractTask {

    private int broadcasts = 5;

    public BroadcastingTask() {
        super(60L, true);
    }

    @Override
    void schedule() {
        broadcasts--;
        if(broadcasts == 0) {
            FightPlugin plugin = FightPlugin.getInstance();
            Event event = plugin.getEvent();
            if(event.getFighters().size() >= 10) {
                event.setStarting(false);
                event.setStarted(true);
                plugin.getTaskMap().get("starting").start(plugin);
                stop();
            } else event.forceStop();

            return;
        }

        // debug
        Bukkit.broadcastMessage("Iniciando, anúncio numero " + broadcasts);
    }

}
