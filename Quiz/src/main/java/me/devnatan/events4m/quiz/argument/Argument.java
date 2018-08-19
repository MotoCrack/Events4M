package me.devnatan.events4m.quiz.argument;

import lombok.Getter;
import me.devnatan.events4m.quiz.util.Executable;

public abstract class Argument implements Executable {

    @Getter private final String name;
    @Getter private final int target;
    @Getter protected boolean consoleExecutable = false;

    Argument(String name, int target) {
        this.name = name;
        this.target = target;
    }

}
