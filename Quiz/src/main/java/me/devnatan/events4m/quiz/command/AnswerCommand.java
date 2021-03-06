package me.devnatan.events4m.quiz.command;

import me.devnatan.events4m.quiz.Quiz;
import me.devnatan.events4m.quiz.event.Event;
import me.devnatan.events4m.quiz.util.AnyUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AnswerCommand extends Command {

    public AnswerCommand() {
        super();
    }

    public void execute(CommandSender sender, String[] args) {
        Quiz quiz = Quiz.getInstance();
        Event event = quiz.getEvent();
        if(!event.isStarted()) {
            sender.sendMessage(ChatColor.RED + "O evento quiz ainda não começou.");
            return;
        }

        if(args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Aparenta ter alguma coisa errada...");
            sender.sendMessage(ChatColor.RED + "Modo correto: /resposta (resposta)");
            return;
        }

        String constructed = AnyUtil.fromArgs(args);
        if(!event.matchAnswer(constructed)) {
            sender.sendMessage(ChatColor.RED + "Não, quem sabe outra hora..");
            sender.sendMessage(ChatColor.RED + "Esta resposta nao é a correta!");
            return;
        }

        sender.sendMessage(ChatColor.GREEN + "Muito bem, esta é a resposta correta.");
        sender.sendMessage(ChatColor.GREEN + "Você ganhou o evento Quiz!");
        event.setInput(constructed);
        event.stop((Player) sender);
    }

}
