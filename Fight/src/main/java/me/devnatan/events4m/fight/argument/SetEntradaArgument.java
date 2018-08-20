package me.devnatan.events4m.fight.argument;

import me.devnatan.events4m.fight.FightPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetEntradaArgument extends Argument {

    public SetEntradaArgument() {
        super("setentrada", 0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if(!player.hasPermission("events4m.fight." + getName().toLowerCase())) {
            player.sendMessage(ChatColor.RED + "Você não tem permissão para fazer isto.");
            return;
        }

        int i = 0;
        try {
            i = Integer.parseInt(args[0]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ignored) {}

        if(i != 1 && i != 2) {
            player.sendMessage(ChatColor.RED + "Modo correto: /fight setentrada (1 / 2)");
            return;
        }

        final String KEY = "entrada";
        FightPlugin plugin = FightPlugin.getInstance();
        plugin.getLocationMap().put(KEY + i, player.getLocation());
        plugin.saveLocation(KEY + i);
        player.sendMessage(ChatColor.YELLOW + "Local de " + KEY + " " + i + " definido na sua localização.");
    }
}
