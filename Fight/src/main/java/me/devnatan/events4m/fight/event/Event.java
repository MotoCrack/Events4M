package me.devnatan.events4m.fight.event;

import lombok.Getter;
import lombok.Setter;
import me.devnatan.events4m.fight.java.GenericTypeArray;

public class Event {

    @Getter @Setter private boolean started = false;
    @Getter private GenericTypeArray<EventPlayer> fighters = new GenericTypeArray<>(0);

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
        fighters.reset(0);
    }

    private EventPlayer getWinner() {
        return fighters.len() == 1 ? fighters.get(0) : null;
    }

}
