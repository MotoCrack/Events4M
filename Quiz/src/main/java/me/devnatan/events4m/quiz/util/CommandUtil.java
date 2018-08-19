package me.devnatan.events4m.quiz.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandUtil {

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

}
