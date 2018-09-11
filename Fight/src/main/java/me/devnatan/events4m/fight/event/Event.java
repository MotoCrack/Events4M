package me.devnatan.events4m.fight.event;

import lombok.Data;
import me.devnatan.events4m.fight.FightPlugin;
import me.devnatan.events4m.fight.task.BroadcastingTask;
import me.devnatan.events4m.fight.util.CollectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Data
public class Event {

    private boolean started = false;
    private boolean starting = false;

    private int currentIndex = 0;
    private EventPlayer[] currentArray = new EventPlayer[2];
    private final List<EventPlayer> fighters = new LinkedList<>();
    private final List<EventPlayer[]> subFighters = new LinkedList<>();
    private EventPlayer winner;

    public void start() {
        if(started)
            throw new IllegalStateException("Event is already started");

        if(starting)
            throw new IllegalStateException("Event is already starting");

        starting = true;
        FightPlugin plugin = FightPlugin.getInstance();
        plugin.getTaskMap().get("broadcasting").start(plugin);
        BroadcastingTask.broadcast();
    }

    public void stop(EventPlayer winner) {
        if(!started || !starting)
            throw new IllegalStateException("Event must be started");

        if(winner == null)
            throw new NullPointerException("Winner cannot be null");

        // TODO: Enviar premio para o vencedor
        winner.getPlayer().teleport(FightPlugin.getInstance().getLocationMap().get("saida"));
        forceStop(false);
    }

    public void forceStop(boolean broadcast) {
        Map<String, Location> locationMap = FightPlugin.getInstance().getLocationMap();
        if(locationMap.containsKey("saida")) {
            fighters.forEach(it -> {
                Player p = it.getPlayer();
                if(it.getInventoryContent() != null) p.getInventory().setContents(it.getInventoryContent());
                if(it.getArmorContent() != null) p.getInventory().setArmorContents(it.getArmorContent());
                it.getPlayer().teleport(locationMap.get("saida"));
            });
        }
        if(broadcast) {
            Bukkit.broadcastMessage(" ");
            Bukkit.broadcastMessage(" " + ChatColor.AQUA + "Evento " + ChatColor.BOLD + "FIGHT" + ChatColor.AQUA + " interrompido!");
            Bukkit.broadcastMessage(" " + ChatColor.AQUA + "Não foi possível prosseguir com o evento.");
            Bukkit.broadcastMessage(" ");
        }
        reset();
    }

    private void reset() {
        started = false;
        starting = false;
        currentIndex = 0;
        currentArray = new EventPlayer[2];
        fighters.clear();
        subFighters.clear();
        winner = null;
        FightPlugin.getInstance().tasks();
    }

    public void subdivide(Consumer<EventPlayer> then) {
        EventPlayer remaining = CollectionUtil.remaining(fighters, 2);
        if(remaining != null) {
            fighters.remove(remaining);
            Bukkit.broadcastMessage(remaining.getPlayer().getName() + " removido por ser quem sobrou ksksks.");
            Bukkit.broadcastMessage("Agora temos um total de " + fighters.size() + " participando.");
            then.accept(remaining);
        }
    }

    public void next(BiConsumer<Integer, EventPlayer[]> next, Consumer<EventPlayer> finish) {
        if(currentIndex + 1 > subFighters.size()) {
            if(winner == null)
                throw new NullPointerException("Next player cannot be null.");
            finish.accept(winner);
        } else {
            EventPlayer[] array = subFighters.get(currentIndex += 1);
            if(array == null)
                throw new NullPointerException("Next duo cannot be null.");
            next.accept(currentIndex, array);
        }
    }

    public void safeRemoveIfPresent(Player player) {
        EventPlayer ep = getPlayer(player);
        if(ep != null) {
            fighters.removeIf(it -> it.equals(ep));
            // TODO: Devolver itens
        }
    }

    public EventPlayer getPlayer(Player player) {
        return fighters.stream().filter(it -> it.getPlayer().equals(player)).findFirst().orElse(null);
    }

}
