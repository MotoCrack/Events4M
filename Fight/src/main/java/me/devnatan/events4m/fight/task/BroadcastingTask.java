package me.devnatan.events4m.fight.task;

import lombok.Getter;
import lombok.Setter;
import me.devnatan.events4m.fight.FightPlugin;
import me.devnatan.events4m.fight.event.Event;
import org.bukkit.Bukkit;

public class BroadcastingTask extends AbstractTask {

    @Getter @Setter private int broadcasts = 5;

    public BroadcastingTask() {
        super(60L, true);
    }

    @Override
    void schedule() {
        broadcasts--;
        if(broadcasts == 0) {
            FightPlugin plugin = FightPlugin.getInstance();
            Event event = plugin.getEvent();
            if(event.getFighters().size() >= 10)
                plugin.getTaskMap().get("starting").start(plugin);
            else {
                event.forceStop();
                return;
            }

            stop();
            return;
        }

        // debug
        Bukkit.broadcastMessage("Iniciando, anúncio numero " + broadcasts);
    }

}
