package me.devnatan.events4m.quiz;

import lombok.Getter;
import me.devnatan.events4m.quiz.argument.Argument;
import me.devnatan.events4m.quiz.argument.StartArgument;
import me.devnatan.events4m.quiz.command.QuizCommand;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public final class Quiz extends JavaPlugin {

    @Getter private static Quiz instance;
    @Getter private Event event;
    @Getter private Map<String, String> messages;

    public void onLoad() {
        instance = this;
    }

    public void onEnable() {
        root();
        event();
        plugin();
    }

    private void root() {
        if(!getDataFolder().exists())
            getDataFolder().mkdir();

        if(!new File(getDataFolder(), "config.yml").exists())
            saveResource("config.yml", false);

        messages = getConfig().getConfigurationSection("messages").getValues(false)
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> ChatColor.translateAlternateColorCodes('&', (String) e.getValue())));
    }

    private void event() {
        event = new Event();
    }

    private void plugin() {
        new QuizCommand(new Argument[] {
                new StartArgument()
        }).register("quiz");
    }

    /**
     * Obtem uma pergunta e uma resposta randômicamente,
     * que já estão pre-definidas na configuração.
     * @return QA
     */
    public QA random() {
        ConfigurationSection section = getConfig().getConfigurationSection("questions-and-answers");
        ConfigurationSection randomic = section.getConfigurationSection(String.valueOf(new Random().nextInt(section.getKeys(false).size())));
        return new QA(
                randomic.getString("question"),
                randomic.getStringList("answer").toArray(new String[0])
        );
    }
}
