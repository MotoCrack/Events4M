package me.devnatan.events4m.bolao;

import lombok.Data;
import lombok.Getter;
import me.devnatan.events4m.bolao.argument.Argument;
import me.devnatan.events4m.bolao.argument.LeaveArgument;
import me.devnatan.events4m.bolao.argument.StartArgument;
import me.devnatan.events4m.bolao.command.BolaoCommand;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

@Data
public final class Bolao extends JavaPlugin {

    @Getter private static Bolao instance;
    private double amount;
    private Event event;
    private Economy economy;
    private Storage storage;

    public void onLoad() {
        instance = this;
    }

    public void onEnable() {
        try {
            root();
            economy();
            storage();
            event();
            plugin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDisable() {
        if(event != null && event.isStarted()) {
            event.interrupt();
        }

        if(storage != null) {
            try {
                storage.disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

    private void storage() {
        ConfigurationSection cs = getConfig().getConfigurationSection("mysql");
        storage = new Storage();
        storage.connect(this, cs.getString("host"), cs.getString("user"), cs.getString("password"), cs.getString("database"), !cs.getBoolean("use"));
    }

    private void event() {
        event = new Event();
        event.setElapsed(0);
        event.setPlayers(new ArrayList<>());
    }

    private void plugin() {
        new BolaoCommand(new Argument[] {
                new StartArgument(),
                new LeaveArgument()
        }).register("bolao");
        Bukkit.getPluginManager().registerEvents(new Events(), this);
    }

}
