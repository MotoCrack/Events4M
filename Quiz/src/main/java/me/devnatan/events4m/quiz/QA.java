package me.devnatan.events4m.quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class QA {

    @Getter @Setter private String question;
    @Getter @Setter private String[] answers;

}
