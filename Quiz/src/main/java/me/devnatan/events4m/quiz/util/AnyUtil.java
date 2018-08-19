package me.devnatan.events4m.quiz.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class AnyUtil {

    public static boolean canContinue(CommandSender sender, String permission) {
        if(!sender.hasPermission(permission)) {
            sender.sendMessage(ChatColor.RED + "Você não tem permissão para fazer isto.");
            return true;
        } return false;
    }

    public static boolean containsIn(String[] array, String needle) {
        boolean contains = false;
        for(String s : array) {
            if(s.equalsIgnoreCase(needle)) {
                contains = true;
                break;
            }
        } return contains;
    }

    public static String[] constructUncoloured(String str) {
        return Arrays.stream(str.split(" ")).peek(ChatColor::stripColor).toArray(String[]::new);
    }

    public static String millisToReadable(long millis) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        int minutes = 0;
        do {
            minutes++;
            seconds -= 60;
        } while(seconds >= 60);
        return minutes > 0 && seconds > 0 ? minutes + "min " + seconds + "seg " : seconds == 0 ? minutes + "min" : seconds + "seconds";
    }

    public static void announce(String message, Object... params) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(message, params)));
    }

}
