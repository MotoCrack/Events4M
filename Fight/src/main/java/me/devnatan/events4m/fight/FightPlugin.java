package me.devnatan.events4m.fight;

import lombok.Getter;
import me.devnatan.events4m.fight.argument.*;
import me.devnatan.events4m.fight.command.FightCommand;
import me.devnatan.events4m.fight.event.Event;
import me.devnatan.events4m.fight.listener.PlayerListener;
import me.devnatan.events4m.fight.task.AbstractTask;
import me.devnatan.events4m.fight.task.BroadcastingTask;
import me.devnatan.events4m.fight.task.FightingTask;
import me.devnatan.events4m.fight.task.StartingTask;
import me.devnatan.events4m.fight.util.LocationUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class FightPlugin extends JavaPlugin {

    @Getter private static FightPlugin instance;
    @Getter private Economy economy;
    @Getter private Map<String, Location> locationMap = new HashMap<>();
    @Getter private Event event;
    @Getter private Map<String, AbstractTask> taskMap = new HashMap<>();

    public void onLoad() {
        instance = this;
    }

    public void onEnable() {
        root();
        economy();
        conf();
        event();
        plugin();
    }

    public void onDisable() {
        if(event != null && event.isStarted()) {
            event.forceStop(false);
        }
    }

    private void root() {
        if(!getDataFolder().exists())
            getDataFolder().mkdir();

        if(!new File(getDataFolder(), "config.yml").exists())
            saveResource("config.yml", false);
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

    private void conf() {
        locationMap.putAll(LocationUtil.load(getConfig().contains("locations") ? getConfig().getConfigurationSection("locations") : getConfig().createSection("locations")));
    }

    private void event() {
        event = new Event();
    }

    private void plugin() {
        tasks();
        new FightCommand(
                new JoinArgument(),
                new ForceStartArgument(),
                new StartArgument(),
                new StopArgument(),
                new CurrentArgument(),
                new SetEntradaArgument(),
                new SetLobbyArgument(),
                new SetSaidaArgument(),
                new SetCamaroteArgument()
        ).register(this, "fight");
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public void tasks() {
        taskMap.forEach((k, t) -> {
            if(t.isRunning()) t.stop();
            t = null;
        });

        taskMap.put("starting", new StartingTask());
        taskMap.put("broadcasting", new BroadcastingTask());
        taskMap.put("fighting", new FightingTask());
    }

    public void saveLocation(String key) {
        ConfigurationSection cs = getConfig().contains("locations") ? getConfig().getConfigurationSection("locations") : getConfig().createSection("locations");
        cs.set(key, locationMap.get(key).serialize());
        saveConfig();
    }

}
