package me.devnatan.events4m.quiz.event;

import lombok.Data;

import java.util.List;

@Data
public class Reward {

    private double money;
    private List<String> commands;

}
