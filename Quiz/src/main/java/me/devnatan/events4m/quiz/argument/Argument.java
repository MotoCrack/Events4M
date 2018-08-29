package me.devnatan.events4m.quiz.argument;

import lombok.Data;
import me.devnatan.events4m.quiz.util.Executable;

@Data
public abstract class Argument implements Executable {

    private final String name;
    private final int target;
    protected boolean consoleExecutable;

    Argument(String name, int target) {
        this.name = name;
        this.target = target;
        consoleExecutable = false;
    }

}
