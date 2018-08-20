package me.devnatan.events4m.fight;

import lombok.Getter;
import lombok.Setter;
import me.devnatan.events4m.fight.event.Event;
import me.devnatan.events4m.fight.util.LocationUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Location;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;

public final class FightPlugin extends JavaPlugin {

    @Getter @Setter private static FightPlugin instance;
    @Getter private Economy economy;
    @Getter private Map<String, Location> locationMap;
    @Getter private Event event;

    public void onLoad() {
        setInstance(this);
    }

    public void onEnable() {
        root();
        economy();
        conf();
    }

    public void onDisable() {
        if(event != null && event.isStarted()) {
            event.forceStop();
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
        if(getConfig().contains("locations")) {
            locationMap = LocationUtil.load(getConfig().getConfigurationSection("locations"));
        }
    }

}
