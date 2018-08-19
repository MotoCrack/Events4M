package me.devnatan.events4m.bolao.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.text.MessageFormat;

public class AnyUtil {

    public static void ifPermissioned(CommandSender sender, String permission, Runnable runnable) {
        if (!sender.hasPermission(permission)) {
            sender.sendMessage(ChatColor.RED + "Você não tem permissão para fazer isto.");
            return;
        } runnable.run();
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
}
