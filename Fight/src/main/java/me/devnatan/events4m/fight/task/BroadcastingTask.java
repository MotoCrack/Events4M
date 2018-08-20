package me.devnatan.events4m.fight.task;

import lombok.Getter;
import lombok.Setter;

public class BroadcastingTask extends AbstractTask {

    @Getter @Setter private int broadcasts = 5;

    public BroadcastingTask() {
        super(60L, true);
    }

    @Override
    void schedule() {
        broadcasts--;
        if(broadcasts == 0) {
            // TODO: verificar se há jogadores suficientes participando e iniciar
            return;
        }

        // TODO: Divulgar
    }

}
