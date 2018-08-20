package me.devnatan.events4m.fight.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EventPlayer {

    @Getter private final Player player;
    @Getter @Setter private boolean playing;
    @Getter @Setter private int elapsed;
    @Getter @Setter private ItemStack[] storedInventoryContent;

    public EventPlayer(Player player) {
        this.player = player;
        playing = false;
        elapsed = 0;
    }
}
