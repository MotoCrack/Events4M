package me.devnatan.events4m.quiz.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

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
        int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(millis);
        int minutes = 0;

        do {
            minutes++;
            seconds -= 60;
        } while(seconds >= 60);

        if(minutes != 0) {
            String s = minutes + "min";
            if(seconds != 0)
                s += " " + seconds + "seg";
            return s;
        }

        return seconds + "seg";
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

}
