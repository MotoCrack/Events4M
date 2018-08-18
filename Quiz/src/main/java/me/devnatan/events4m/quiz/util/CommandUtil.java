package me.devnatan.events4m.quiz.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandUtil {

    public static boolean canContinue(Player player, String permission) {
        if(!player.hasPermission(permission)) {
            player.sendMessage(ChatColor.RED + "Você não tem permissão para fazer isto.");
            return true;
        }

        return false;
    }

}
