package me.devnatan.events4m.fight.argument;

import me.devnatan.events4m.fight.FightPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSaidaArgument extends Argument {

    public SetSaidaArgument() {
        super("setsaida", 0);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if(!player.hasPermission("events4m.fight." + getName().toLowerCase())) {
            player.sendMessage(ChatColor.RED + "Você não tem permissão para fazer isto.");
            return;
        }

        final String KEY = "saida";
        FightPlugin plugin = FightPlugin.getInstance();
        plugin.getLocationMap().put(KEY, player.getLocation());
        plugin.saveLocation(KEY);
        player.sendMessage(ChatColor.YELLOW + "Local de " + KEY + " definido na sua localização.");
    }
}
