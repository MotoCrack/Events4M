package me.devnatan.events4m.quiz.argument;

import me.devnatan.events4m.quiz.Event;
import me.devnatan.events4m.quiz.Quiz;
import me.devnatan.events4m.quiz.util.AnyUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AnswerArgument extends Argument {

    public AnswerArgument() {
        super("resposta", 0);
        consoleExecutable = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player && !AnyUtil.canContinue(sender, "events4m.quiz." + getName().toLowerCase())) {
            return;
        }

        if(args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Você quer alterar a resposta do evento?");
            sender.sendMessage(ChatColor.RED + "Se sim, use /quiz resposta [...]");
            return;
        }

        Quiz quiz = Quiz.getInstance();
        Event event = quiz.getEvent();
        event.getQa().setQuestion(ChatColor.stripColor(args[0]));
        sender.sendMessage(ChatColor.GREEN + "Pergunta do Quiz alterada, agora é:");
        sender.sendMessage(ChatColor.GREEN + event.getQa().getQuestion());
    }
}
