package me.devnatan.events4m.quiz;

import lombok.Getter;
import lombok.Setter;

public class Event {

    @Getter @Setter private String question;
    @Getter @Setter private String answer;
    @Getter @Setter private long fired;

    /**
     * Variável que guarda o tempo que o jogador respondeu com a resposta correta
     * @see #getQuestion()
     * @see #getAnswer()
     */
    @Getter @Setter private long complete;

    /**
     * Quanto tempo passou desde o início do evento
     * um jogador respondeu a pergunta corretamente
     * @return long
     */
    public long getElapsed() {
        return fired - complete;
    }
}
