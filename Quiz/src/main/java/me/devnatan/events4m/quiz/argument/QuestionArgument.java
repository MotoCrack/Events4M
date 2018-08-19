package me.devnatan.events4m.quiz.argument;

import me.devnatan.events4m.quiz.Event;
import me.devnatan.events4m.quiz.Quiz;
import me.devnatan.events4m.quiz.util.AnyUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class QuestionArgument extends Argument {

    public QuestionArgument() {
        super("pergunta", 0);
        consoleExecutable = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player && !AnyUtil.canContinue(sender, "events4m.quiz." + getName().toLowerCase())) {
            return;
        }

        if(args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Você quer alterar a pergunta do evento?");
            sender.sendMessage(ChatColor.RED + "Se sim, use /quiz pergunta [...]");
            return;
        }

        Quiz quiz = Quiz.getInstance();
        Event event = quiz.getEvent();
        String[] answers = AnyUtil.constructUncoloured(args[0]);
        if(answers == null || answers.length == 0) {
            sender.sendMessage(ChatColor.RED + "Há algo de errado nesta resposta...");
            sender.sendMessage(ChatColor.RED + "Nós fizemos algumas alterações nela e o resultado");
            sender.sendMessage(ChatColor.RED + "final ficou vazio, tente não usar código de cores.");
            return;
        }

        event.getQa().setAnswers(answers);
        sender.sendMessage(ChatColor.GREEN + "Resposta do Quiz alterada, agora é:");
        sender.sendMessage(ChatColor.GREEN + Arrays.toString(answers).replace("[", "").replace("]", ""));
    }
}
