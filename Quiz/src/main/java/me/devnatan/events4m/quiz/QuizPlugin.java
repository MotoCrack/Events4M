package me.devnatan.events4m.quiz;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;
import java.util.stream.Collectors;

public final class QuizPlugin extends JavaPlugin {

    @Getter private static QuizPlugin instance;
    @Getter private Event event;
    @Getter private Map<String, String> messages;

    public void onLoad() {
        instance = this;
    }

    public void onEnable() {
        root();
        event();
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

}
