package me.devnatan.events4m.quiz;

import lombok.Getter;
import lombok.Setter;
import me.devnatan.events4m.quiz.util.AnyUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static me.devnatan.events4m.quiz.util.AnyUtil.announce;

public class Event {

    @Getter @Setter private QA qa;
    @Getter @Setter private long fired;
    @Getter @Setter private String input;

    /**
     * Variável que guarda o tempo que o jogador respondeu com a resposta correta
     * @see #getQa()
     */
    @Getter @Setter private long complete = -1;
    @Getter @Setter private boolean started = false;

    /**
     * Quanto tempo passou desde o início do evento
     * um jogador respondeu a pergunta corretamente
     * @return long
     */
    public long getElapsed() {
        return fired - complete;
    }

    /**
     * Verifica se palavras em uma string juntas
     * compõem uma resposta mesmo que particionada.
     * @param answer = a pergunta
     * @return "true" se conter uma resposta; "false" se não conter uma resposta
     */
    public boolean matchAnswer(String answer) {
        int i = 0;
        String[] a = answer.split(" ");
        for(String s : a) {
            if(AnyUtil.containsIn(qa.getAnswers(), s)) i++;
        }
        return i == answer.length();
    }

    /**
     * Começa o evento.
     */
    public void start() {
        if(started)
            throw new IllegalStateException("Event is already started");

        announce(" ");
        announce(" &eEvento &lQUIZ &r&einiciado!");
        announce(" &ePergunta: &a{0}?", qa.getQuestion());
        announce(" &eEscreva sua resposta usando &a/{0}&e.", "/resposta");
        announce(" &eO primeiro que acertar ganha o evento.");
        announce(" ");
        complete = -1;
        fired = System.currentTimeMillis();
        started = true;
    }

    /**
     * Termina o evento com um vencedor.
     * @param winner = o vencedor
     */
    public void stop(Player winner) {
        if(!started)
            throw new IllegalStateException("Event must be started");

        if(winner == null)
            throw new NullPointerException("Winner cannot be null");

        if(!winner.isOnline())
            throw new IllegalStateException("Winer must be online");

        started = false;
        complete = System.currentTimeMillis();

        Quiz quiz = Quiz.getInstance();
        if(quiz.getReward() != null) {
            Reward reward = quiz.getReward();
            if(reward.getMoney() > 0.0d && quiz.getEconomy() != null) {
                quiz.getEconomy().depositPlayer(winner, reward.getMoney());
            }

            for(String s : reward.getCommands()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s);
            }
        }
        announce(" ");
        announce(" &eEvento &lQUIZ &r&eencerrado!");
        announce(" &eParabéns &a" + winner.getName() + "&e, você é um gênio!");
        announce(" &eDuração do evento: " + AnyUtil.millisToReadable(getElapsed()));
        announce(" &eResposta: &a" + input);
        announce(" ");
        announce(" &eObrigado a todos que participaram e boa sorte na próxima.");
        announce(" ");
    }

    /**
     * Interrompe o evento quiz.
     */
    public void interrupt() {
        started = false;
        complete = -1;
        fired = -1;
    }
}
