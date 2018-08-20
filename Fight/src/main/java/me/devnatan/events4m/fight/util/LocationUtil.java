package me.devnatan.events4m.fight.util;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class LocationUtil {

    public static Map<String, Location> load(ConfigurationSection section) {
        Map<String, Location> map = new HashMap<>();

        for(String key : section.getKeys(false)) {
            map.put(key, Location.deserialize(section.getConfigurationSection(key).getValues(false)));
        }

        return map;
    }

}
