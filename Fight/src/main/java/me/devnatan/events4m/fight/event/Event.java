package me.devnatan.events4m.fight.event;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Event {

    @Getter @Setter private boolean started = false;
    @Getter private final List<EventPlayer> fighters = new ArrayList<>();

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
        fighters.stream().filter(ep -> ep.getPlayer().isOnline()).forEach(ep -> {
            // TODO: Teleportar para a saída do evento
        });
    }

    private void reset() {
        started = false;
        fighters.clear();
    }

    private EventPlayer getWinner() {
        return fighters.size() == 1 ? fighters.get(0) : null;
    }

}
