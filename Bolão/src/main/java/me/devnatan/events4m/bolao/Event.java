package me.devnatan.events4m.bolao;

import lombok.Getter;
import lombok.Setter;
import me.devnatan.events4m.bolao.util.AnyUtil;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static me.devnatan.events4m.bolao.util.AnyUtil.announce;

public class Event implements Runnable {

    @Getter @Setter private boolean started;
    @Getter @Setter private List<Player> players;
    @Getter @Setter private int elapsed;

    public double getTotalAmount() {
        return Bolao.getInstance().getAmount() * players.size();
    }

    @Override
    public void run() {
        elapsed++;
        if(elapsed == 30 || elapsed == 60 || elapsed == 90) {
            announce(" ");
            announce(" &aEvento &lBOLãO &r&aacontecendo!");
            announce(" &aAposta mínima: &f${0}", String.format("%.2f", Bolao.getInstance().getAmount()));
            announce(" &aDinheiro acumulado: &f${0}", String.format("%.2f", getTotalAmount()));
            announce(" &aTentando a sorte: &f{0} jogadores", players.size());
            announce(" ");
            announce(" &aAposte no bolão usando &f/{0} &ase tiver sorte!", "bolao");
            announce(" &aO jogador sorteado levará todo o dinheiro acumulado!");
            announce(" ");
        }
    }

    /**
     * Começa o evento.
     */
    public void start() {
        if(started)
            throw new IllegalStateException("Event is already started");

        announce(" ");
        announce(" &aEvento &lBOLãO &r&aacontecendo!");
        announce(" &aAposta mínima: &f{0}?", String.format("%.2f", Bolao.getInstance().getAmount()));
        announce(" ");
        announce(" &aAposte no bolão usando &f/{0} &ase tiver sorte.", "bolao");
        announce(" &aO jogador sorteado levará todo o dinheiro acumulado!");
        announce(" ");
        players.clear();
        elapsed = 0;
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
        announce(" ");
        announce(" &aEvento &lBOLãO &r&aencerrado!");
        announce(" &aO jogador sorteado foi: &f{0}", winner.getName());
        announce(" &aDuração do evento: {0}", AnyUtil.millisToReadable(System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(elapsed)));
        announce(" &aAcumulado: &f${0}", String.format("%.2f", getTotalAmount()));
        announce(" ");
        announce(" &aO vencedor levou todo o dinheiro acumulado.");
        announce(" &aPara quem não teve sorte desta vez, quem sabe na próxima!");
        announce(" ");
        players.clear();
        elapsed = 0;
    }

    /**
     * Interrompe o evento.
     */
    public void interrupt() {
        started = false;
        players.clear();
        elapsed = 0;
    }
}
