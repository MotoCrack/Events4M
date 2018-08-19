package me.devnatan.events4m.bolao;

import lombok.Getter;
import me.devnatan.events4m.bolao.argument.Argument;
import me.devnatan.events4m.bolao.argument.StartArgument;
import me.devnatan.events4m.bolao.command.BolaoCommand;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public final class Bolao extends JavaPlugin {

    @Getter private static Bolao instance;
    @Getter private double amount;
    @Getter private Event event;
    @Getter private Economy economy;

    public void onLoad() {
        instance = this;
    }

    public void onEnable() {
        root();
        economy();
        event();
        plugin();
    }

    public void onDisable() {
        if(event != null && event.isStarted()) {
            event.interrupt();
        }
    }

    private void root() {
        if(!getDataFolder().exists())getDataFolder().mkdir();

        if(!new File(getDataFolder(), "config.yml").exists())
            saveResource("config.yml", false);

        ConfigurationSection conf = getConfig();
        amount = conf.contains("amount") ? conf.getDouble("amount") : 0.0d;
    }

    private void economy() {
        if(!getServer().getPluginManager().isPluginEnabled("Vault")) {
            getLogger().severe("Dependência obrigatória Vault não encontrada.");
            getServer().getPluginManager().disablePlugin(this);
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

    private void event() {
        event = new Event();
        event.setElapsed(0);
        event.setPlayers(new ArrayList<>());
    }

    private void plugin() {
        new BolaoCommand(new Argument[] {
                new StartArgument()
        }).register("bolao");
        Bukkit.getPluginManager().disablePlugin(this);
    }

}
