package me.devnatan.events4m.fight.util;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LocationUtil {

    public static Map<String, Location> load(ConfigurationSection section) {
        Map<String, Location> map = new HashMap<>();

        if(section != null) {
            Set<String> keys = section.getKeys(false);
            if(keys != null) {
                for (String key : keys) {
                    map.put(key, Location.deserialize(section.getConfigurationSection(key).getValues(false)));
                }
            }
        }

        return map;
    }

}
