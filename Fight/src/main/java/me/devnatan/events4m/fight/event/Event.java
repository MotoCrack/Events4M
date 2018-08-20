package me.devnatan.events4m.fight.event;

import lombok.Getter;
import lombok.Setter;
import me.devnatan.events4m.fight.FightPlugin;
import me.devnatan.events4m.fight.java.GenericTypeArray;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Map;

public class Event {

    @Getter @Setter private boolean started = false;

    @Getter private int currentIndex = 0;
    @Getter private EventPlayer[] currentArray = new EventPlayer[2];
    @Getter private GenericTypeArray<EventPlayer[]> fighters = new GenericTypeArray<>(0);

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
            fighters.stream().forEach(a -> Arrays.stream(a).filter(it -> it.getPlayer().isOnline()).forEach(it -> {
                it.getPlayer().teleport(locationMap.get("saida"));
            }));
        }
        reset();
    }

    private void reset() {
        started = false;
        fighters.reset(0);
    }

    public void append(EventPlayer eventPlayer) {
        if(fighters.len() == 0) {
            fighters.append(new EventPlayer[] { eventPlayer });
        } else {
            for(EventPlayer[] players : fighters.getElements()) {
                if(players[0] == null) {
                    players[0] = eventPlayer;
                    break;
                }

                if(players[1] == null) {
                    players[1] = eventPlayer;
                    break;
                }
            }
        }
    }

    public EventPlayer getPlayer(Player player) {
        for(EventPlayer[] players : fighters.getElements()) {
            for(EventPlayer player1 : players) {
                if(player1.getPlayer().equals(player)) return player1;
            }
        } return null;
    }

    private EventPlayer getWinner() {
        // TODO: Refazer
        // return fighters.len() == 1 ? fighters.get(0) : null;
        return null;
    }

}
