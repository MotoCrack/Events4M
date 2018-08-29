package me.devnatan.events4m.fight.task;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public abstract class AbstractTask {

    @Getter private boolean running;
    @Getter protected int elapsed;
    private final long step;
    private final boolean sync;
    private BukkitTask task;

    AbstractTask(long step, boolean sync) {
        this.step = step;
        this.sync = sync;
        running = false;
    }

    public void start(Plugin plugin) {
        if(running)
            throw new IllegalStateException("Task is already running.");

        Runnable runnable = () -> {
            if(running) {
                elapsed++;
                schedule();
            }
        };

        task = sync ?
                Bukkit.getScheduler().runTaskTimer(plugin, runnable, step, step) :
                Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, step, step);
        running = true;
    }

    public void stop() {
        if(!running)
            throw new IllegalStateException("Task must be running.");
        task.cancel();
        running = false;
        elapsed = 0;
    }

    abstract void schedule();

}
