package me.devnatan.events4m.quiz.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class AnyUtil {

    public static void ifPermissioned(CommandSender sender, String permission, Runnable runnable) {
        if (!sender.hasPermission(permission)) {
            sender.sendMessage(ChatColor.RED + "Você não tem permissão para fazer isto.");
            return;
        } runnable.run();
    }

    public static boolean containsIn(String[] array, String needle) {
        return Arrays.stream(array).anyMatch(s -> s.equalsIgnoreCase(needle));
    }

    public static String[] constructUncoloured(String str) {
        return Arrays.stream(str.split(" ")).peek(ChatColor::stripColor).toArray(String[]::new);
    }

    public static String millisToReadable(long millis) {
        if(millis / 1000 < 1L) {
            return String.format("%.2f", (float) millis) + "ms";
        }
        int min = (int) (((millis / 1000) / 60) % 60);
        int seg = (int) ((millis / 1000) % 60);

        return (min > 0) ? min + "min " + seg + "seg" : seg + "seg";
    }

    public static void announce(String message, Object... params) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(message, params)));
    }

    public static String fromArgs(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg).append(" ");
        }

        try {
            return sb.toString().substring(0, sb.toString().length() - 1);
        } catch (StringIndexOutOfBoundsException e) {
            return sb.toString();
        }
    }

    public static boolean containsEqualsIgnoreCase(List<String> l1, List<String> l2) {
        if (l1.size() != l2.size()) {
            return false;
        }

        Iterator<String> i1= l1.iterator();
        Iterator<String> i2= l2.iterator();
        while(i1.hasNext()) {
            if (!i1.next().equalsIgnoreCase(i2.next())) {
                return false;
            }
        }

        return true;
    }

}
