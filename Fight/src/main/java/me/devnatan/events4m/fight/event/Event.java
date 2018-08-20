package me.devnatan.events4m.fight.event;

import lombok.Getter;
import lombok.Setter;
import me.devnatan.events4m.fight.FightPlugin;
import me.devnatan.events4m.fight.java.GenericTypeArray;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.function.Consumer;

public class Event {

    @Getter @Setter private boolean started = false;

    @Getter private int currentIndex = 0;
    @Getter private EventPlayer[] currentArray = new EventPlayer[2];
    @Getter private GenericTypeArray<EventPlayer> fighters = new GenericTypeArray<>(0);
    @Getter private GenericTypeArray<EventPlayer[]> subfighters = new GenericTypeArray<>(0);

    public void start() {
        if(started)
            throw new IllegalStateException("Event is already started");

        // TODO: Iniciar task de anúncios
    }

    public void stop() {
        if(!started)
            throw new IllegalStateException("Event must be started");

        EventPlayer winner = getWinner();
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
        fighters.reset(0);
        subfighters.reset(0);
        FightPlugin.getInstance().tasks();
    }

    public void subdivide(Consumer<EventPlayer> then) {
        EventPlayer remaining = fighters.remaining(2);
        if(remaining != null) {
            fighters.remove(remaining);
            Bukkit.broadcastMessage(remaining.getPlayer().getName() + " removido por ser quem sobrou ksksks.");
            Bukkit.broadcastMessage("Agora temos um total de " + fighters.len() + " participando.");
        }
        then.accept(remaining);
    }

    public EventPlayer getPlayer(Player player) {
        return fighters.stream().filter(it -> it.getPlayer().equals(player)).findFirst().orElse(null);
    }

    public EventPlayer getPlayerDuo(Player player) {
        for(EventPlayer[] players : subfighters.getElements()) {
            if(players[0] == player) {
                return players[1];
            } else if(players[1] == player) {
                return players[0];
            }
        } return null;
    }

    private EventPlayer getWinner() {
        // TODO: Refazer
        // return fighters.len() == 1 ? fighters.get(0) : null;
        return null;
    }

}
