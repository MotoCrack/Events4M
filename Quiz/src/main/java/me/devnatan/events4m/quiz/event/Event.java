package me.devnatan.events4m.quiz.event;

import lombok.Data;
import me.devnatan.events4m.quiz.Quiz;
import me.devnatan.events4m.quiz.util.AnyUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

import static me.devnatan.events4m.quiz.util.AnyUtil.announce;

@Data
public class Event {

    private QA qa;
    private long fired;
    private String input;

    private long complete = -1;
    private boolean started = false;

    private long getElapsed() {
        return complete - fired;
    }

    public boolean matchAnswer(String answer) {
        return AnyUtil.containsEqualsIgnoreCase(Arrays.asList(answer.split(" ")), Arrays.asList(qa.getAnswers()));
    }

    public void start() {
        if(started)
            throw new IllegalStateException("Event is already started");

        announce(" ");
        announce(" &eEvento &lQUIZ &r&einiciado!");
        announce(" &ePergunta: &a{0}?", qa.getQuestion());
        announce(" &eEscreva sua resposta usando &a/{0}&e.", "resposta");
        announce(" &eO primeiro que acertar ganha o evento.");
        announce(" ");
        complete = -1;
        fired = System.currentTimeMillis();
        started = true;
    }

    public void stop(Player winner) {
        if(!started)
            throw new IllegalStateException("Event must be started");

        if(winner == null)
            throw new NullPointerException("Winner cannot be null");

        if(!winner.isOnline())
            throw new IllegalStateException("Winer must be online");

        started = false;
        complete = System.currentTimeMillis();

        announce(" ");
        announce(" &eEvento &lQUIZ &r&eencerrado!");
        announce(" &eParabéns &a" + winner.getName() + "&e, você é um gênio!");
        announce(" &eDuração do evento: " + AnyUtil.millisToReadable(getElapsed()));
        announce(" &eResposta: &a" + input);
        announce(" ");
        announce(" &eObrigado a todos que participaram e boa sorte na próxima.");
        announce(" ");

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
    }

    public void interrupt() {
        started = false;
        complete = -1;
        fired = -1;
    }
}
