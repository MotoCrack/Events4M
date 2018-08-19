package me.devnatan.events4m.quiz;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Reward {

    @Getter @Setter private double money;
    @Getter @Setter private List<String> commands;

}
