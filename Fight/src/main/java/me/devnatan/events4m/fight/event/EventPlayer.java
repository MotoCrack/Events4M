package me.devnatan.events4m.fight.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

public class EventPlayer {

    @Getter private final Player player;
    @Getter @Setter private boolean playing;
    @Getter @Setter private int elapsed;

    public EventPlayer(Player player) {
        this.player = player;
        playing = false;
        elapsed = 0;
    }
}
