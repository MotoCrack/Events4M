package me.devnatan.events4m.fight.task;

import me.devnatan.events4m.fight.FightPlugin;
import me.devnatan.events4m.fight.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class BroadcastingTask extends AbstractTask {

    private int broadcasts = 5;

    public BroadcastingTask() {
        super(20L * 30, true);
    }

    @Override
    void schedule() {
        broadcasts--;
        if (broadcasts == 0) {
            FightPlugin plugin = FightPlugin.getInstance();
            Event event = plugin.getEvent();
            if (event.getFighters().size() >= 10) {
                event.setStarting(false);
                event.setStarted(true);
                plugin.getTaskMap().get("starting").start(plugin);
                stop();
            } else event.forceStop(true);
        } else broadcast();
    }

    public static void broadcast() {
        Bukkit.broadcastMessage(" ");
        Bukkit.broadcastMessage(" " + ChatColor.AQUA + "Evento " + ChatColor.BOLD + "FIGHT " + ChatColor.AQUA + "prestes a iniciar!");
        Bukkit.broadcastMessage(" " + ChatColor.AQUA + "Participe usando " + ChatColor.RESET + "/fight entrar" + ChatColor.AQUA + ".");
        Bukkit.broadcastMessage(" " + ChatColor.AQUA + "Todos terão equipamentos iguais no PvP.");
        Bukkit.broadcastMessage(" " + ChatColor.AQUA + "Assista de camarote em " + ChatColor.RESET + "/fight camarote" + ChatColor.AQUA + ".");
        Bukkit.broadcastMessage(" ");
    }

}
