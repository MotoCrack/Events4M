package me.devnatan.events4m.quiz;

import lombok.Getter;
import lombok.Setter;

public class QA {

    @Getter @Setter private String question;
    @Getter @Setter private String[] answers;

    public QA() {}

    public QA(String question, String[] answers) {
        this.question = question;
        this.answers = answers;
    }

}
