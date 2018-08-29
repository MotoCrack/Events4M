package me.devnatan.events4m.quiz.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
@Data
public class QA {

    private String question;
    private String[] answers;

    public QA() {}

}
