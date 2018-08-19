package me.devnatan.events4m.quiz;

import lombok.Getter;
import me.devnatan.events4m.quiz.argument.AnswerArgument;
import me.devnatan.events4m.quiz.argument.Argument;
import me.devnatan.events4m.quiz.argument.QuestionArgument;
import me.devnatan.events4m.quiz.argument.StartArgument;
import me.devnatan.events4m.quiz.command.AnswerCommand;
import me.devnatan.events4m.quiz.command.QuizCommand;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public final class Quiz extends JavaPlugin {

    @Getter private static Quiz instance;
    @Getter private Event event;
    @Getter private Map<String, String> messages;
    @Getter private Economy economy;
    @Getter private Reward reward;

    public void onLoad() {
        instance = this;
    }

    public void onEnable() {
        root();
        event();
        plugin();
        economy();
    }

    private void root() {
        if(!getDataFolder().exists())getDataFolder().mkdir();

        if(!new File(getDataFolder(), "config.yml").exists())
            saveResource("config.yml", false);

        ConfigurationSection conf = getConfig();
        reward = new Reward();
        reward.setMoney(conf.contains("reward.money") ? conf.getDouble("reward.money") : 0.0d);
        reward.setCommands(conf.contains("reward.commands") ? conf.getStringList("reward.commands") : new ArrayList<>());
    }

    private void event() {
        event = new Event();
        event.interrupt();
    }

    private void plugin() {
        new AnswerCommand().register("resposta");
        new QuizCommand(new Argument[] {
                new StartArgument(),
                new QuestionArgument(),
                new AnswerArgument()
        }).register("quiz");
    }

    private void economy() {
        if(!getServer().getPluginManager().isPluginEnabled("Vault")) {
            getLogger().severe("Dependência opcional Vault não encontrada.");
            return;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            getLogger().severe("Nenhum plugin de economia foi encontrado.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        economy = rsp.getProvider();
    }

    /**
     * Obtem uma pergunta e uma resposta randômicamente,
     * que já estão pre-definidas na configuração.
     * @return QA
     */
    public QA random() {
        ConfigurationSection section = getConfig().getConfigurationSection("questions-and-answers");
        ConfigurationSection randomly = section.getConfigurationSection(String.valueOf(new Random().nextInt(section.getKeys(false).size())));
        return new QA(
                randomly.getString("question"),
                randomly.getStringList("answers").toArray(new String[0])
        );
    }
}
