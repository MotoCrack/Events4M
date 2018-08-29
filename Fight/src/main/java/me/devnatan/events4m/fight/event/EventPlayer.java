package me.devnatan.events4m.fight.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@EqualsAndHashCode
@Data
public class EventPlayer {

    private final Player player;
    private boolean playing;
    private int elapsed;
    private transient ItemStack[] armorContent, inventoryContent, extraContent;

    public EventPlayer(Player player) {
        this.player = player;
        playing = false;
        elapsed = 0;
    }
}
