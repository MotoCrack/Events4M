package me.devnatan.events4m.fight.argument;

import lombok.Data;
import me.devnatan.events4m.fight.command.Executable;

@Data
public abstract class Argument implements Executable {

    private final String name;
    private final int target;
    private boolean consoleExecutable;

    Argument(String name, int target) {
        this.name = name;
        this.target = target;
        consoleExecutable = false;
    }

}
