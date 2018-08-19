package me.devnatan.events4m.bolao;

import lombok.Getter;
import lombok.Setter;
import me.devnatan.events4m.bolao.util.AnyUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static me.devnatan.events4m.bolao.util.AnyUtil.announce;

public class Event implements Runnable {

    @Getter @Setter private boolean started;
    @Getter @Setter private List<Player> players;
    @Getter @Setter private int elapsed;
    @Getter BukkitTask task;

    @Override
    public void run() {
        elapsed++;
        if(elapsed == TimeUnit.MINUTES.toSeconds(3)) {
            stop(players.size() > 0 ? random() : null);
            return;
        }

        if(elapsed == 30 || elapsed == 60 || elapsed == 90 || elapsed == 120 || elapsed == 150) {
            announce(" ");
            announce(" &aEvento &lBOLãO &r&aacontecendo!");
            announce(" &aAposta mínima: &f${0}", String.format("%.2f", Bolao.getInstance().getAmount()));
            announce(" &aDinheiro acumulado: &f${0}", String.format("%.2f", getTotalAmount()));
            announce(" &aTentando a sorte: {0} jogadores", players.size());
            announce(" ");
            announce(" &aAposte no bolão usando &f/{0} &ase tiver sorte!", "bolao");
            announce(" &aO jogador sorteado levará todo o dinheiro acumulado!");
            announce(" ");
        }
    }

    public void start() {
        if(started)
            throw new IllegalStateException("Event is already started");

        announce(" ");
        announce(" &aEvento &lBOLãO &r&aacontecendo!");
        announce(" &aAposta mínima: &f${0}", String.format("%.2f", Bolao.getInstance().getAmount()));
        announce(" ");
        announce(" &aAposte no bolão usando &f/{0} &ase tiver sorte.", "bolao");
        announce(" &aO jogador sorteado levará todo o dinheiro acumulado!");
        announce(" ");
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(Bolao.getInstance(), this, 20L, 20L);
        players.clear();
        elapsed = 0;
        started = true;
    }

    private void stop(Player winner) {
        if(!started)
            throw new IllegalStateException("Event must be started");

        task.cancel();
        started = false;
        if(winner == null) {
            announce(" ");
            announce(" &aEvento &lBOLãO &r&aencerrado!");
            announce(" &aNenhum jogador participou =(");
            announce(" &aDuração do evento: {0}", getDuration());
            announce(" ");
            announce(" &aO vencedor levaria todo o dinheiro acumulado.");
            announce(" &aPara quem não participou desta vez, quem sabe na próxima!");
            announce(" ");
        } else {
            announce(" ");
            announce(" &aEvento &lBOLãO &r&aencerrado!");
            announce(" &aO jogador sorteado foi: &f{0}", winner.getName());
            announce(" &aDuração do evento: {0}", getDuration());
            announce(" &aAcumulado: &f${0}", String.format("%.2f", getTotalAmount()));
            announce(" ");
            announce(" &aO vencedor levou todo o dinheiro acumulado.");
            announce(" &aPara quem não teve sorte desta vez, quem sabe na próxima!");
            announce(" ");
            Bolao.getInstance().getEconomy().depositPlayer(winner, getTotalAmount());
        }

        players.clear();
        elapsed = 0;
    }

    public void interrupt() {
        started = false;
        if(players != null) players.clear();
        if(task != null && Bukkit.getScheduler().isCurrentlyRunning(task.getTaskId())) {
            task.cancel();
            task = null;
        }
        elapsed = 0;
    }

    private Player random() {
        return players.get(new Random().nextInt(players.size()));
    }

    private double getTotalAmount() {
        double amount = Bolao.getInstance().getAmount();
        return (amount * players.size()) + amount;
    }

    private String getDuration() {
        return AnyUtil.millisToReadable(elapsed * 1000);
    }
}
