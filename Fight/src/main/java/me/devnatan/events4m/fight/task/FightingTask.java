package me.devnatan.events4m.fight.task;

import me.devnatan.events4m.fight.FightPlugin;
import me.devnatan.events4m.fight.event.Event;
import org.bukkit.Bukkit;

import java.util.concurrent.TimeUnit;

public class FightingTask extends AbstractTask {

    private final long MAX_DURATION = TimeUnit.MINUTES.toSeconds(1);

    public FightingTask() {
        super(20L, false);
    }

    @Override
    void schedule() {
        /* if(elapsed == MAX_DURATION) {

        } */

        Event event = FightPlugin.getInstance().getEvent();
        event.next((index, next) -> {
            // debug
            Bukkit.broadcastMessage(index + "º: " + next[0].getPlayer().getName() + " vs. " + next[1].getPlayer().getName());
        }, winner -> {
            // debug
            Bukkit.broadcastMessage("Invoke winner: " + winner.getPlayer().getName());
            stop();
            event.stop(winner);
        });
    }

}
