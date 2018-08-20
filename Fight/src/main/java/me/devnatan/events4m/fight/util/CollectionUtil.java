package me.devnatan.events4m.fight.util;

import java.util.List;

public class CollectionUtil {

    public static <T> T remaining(List<T> c, int i) {
        return (c.size() % i == 0) ? null : c.get(c.size() - 1);
    }

}
