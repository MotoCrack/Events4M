package me.devnatan.events4m.fight.task;

import org.bukkit.Bukkit;

public class FightingTask extends AbstractTask {

    public FightingTask() {
        super(20L, false);
    }

    @Override
    void schedule() {
        Bukkit.broadcastMessage("BOOOOORA HORA DO SHOW PORRA!");
    }

}
