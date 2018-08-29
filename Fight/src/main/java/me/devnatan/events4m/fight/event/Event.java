package me.devnatan.events4m.fight.event;

import lombok.Data;
import me.devnatan.events4m.fight.FightPlugin;
import me.devnatan.events4m.fight.util.CollectionUtil;
import org.bukkit.Bukkit;
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

    private int currentIndex = 0;
    private EventPlayer[] currentArray = new EventPlayer[2];
    private final List<EventPlayer> fighters = new LinkedList<>();
    private final List<EventPlayer[]> subfighters = new LinkedList<>();
    private EventPlayer winner;

    public void start() {
        if(started)
            throw new IllegalStateException("Event is already started");

        // TODO: Iniciar task de anúncios
    }

    public void stop(EventPlayer winner) {
        if(!started)
            throw new IllegalStateException("Event must be started");

        if(winner == null)
            throw new NullPointerException("Winner cannot be null");

        // TODO: Enviar premio para o vencedor e teleportar para saída do evento

        reset();
    }

    public void forceStop() {
        Map<String, Location> locationMap = FightPlugin.getInstance().getLocationMap();
        if(locationMap.containsKey("saida")) {
            fighters.stream().filter(it -> it.getPlayer().isOnline()).forEach(it -> it.getPlayer().teleport(locationMap.get("saida")));
        }
        reset();
    }

    private void reset() {
        started = false;
        fighters.clear();
        subfighters.clear();
        FightPlugin.getInstance().tasks();
    }

    public void subdivide(Consumer<EventPlayer> then) {
        EventPlayer remaining = CollectionUtil.remaining(fighters, 2);
        if(remaining != null) {
            fighters.remove(remaining);
            Bukkit.broadcastMessage(remaining.getPlayer().getName() + " removido por ser quem sobrou ksksks.");
            Bukkit.broadcastMessage("Agora temos um total de " + fighters.size() + " participando.");
        }
        then.accept(remaining);
    }

    public void next(BiConsumer<Integer, EventPlayer[]> next, Consumer<EventPlayer> finish) {
        if(currentIndex + 1 > subfighters.size()) {
            if(winner == null)
                throw new NullPointerException("Next player cannot be null.");
            finish.accept(winner);
        } else {
            EventPlayer[] array = subfighters.get(currentIndex += 1);
            if(array == null)
                throw new NullPointerException("Next duo cannot be null.");
            next.accept(currentIndex, array);
        }
    }

    public EventPlayer getPlayer(Player player) {
        return fighters.stream().filter(it -> it.getPlayer().equals(player)).findFirst().orElse(null);
    }

}
