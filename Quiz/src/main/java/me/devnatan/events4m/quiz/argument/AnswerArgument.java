package me.devnatan.events4m.quiz.argument;

import me.devnatan.events4m.quiz.Quiz;
import me.devnatan.events4m.quiz.event.Event;
import me.devnatan.events4m.quiz.event.QA;
import me.devnatan.events4m.quiz.util.AnyUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class AnswerArgument extends Argument {

    public AnswerArgument() {
        super("resposta", 0);
        consoleExecutable = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        AnyUtil.ifPermissioned(sender, "events4m.quiz." + getName().toLowerCase(), () -> {
            if(args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Você quer alterar a resposta do evento?");
                sender.sendMessage(ChatColor.RED + "Se sim, use /quiz resposta [...]");
                return;
            }

            Quiz quiz = Quiz.getInstance();
            Event event = quiz.getEvent();
            String[] answers = AnyUtil.constructUncoloured(AnyUtil.fromArgs(args));
            if(answers == null || answers.length == 0) {
                sender.sendMessage(ChatColor.RED + "Há algo de errado nesta resposta...");
                sender.sendMessage(ChatColor.RED + "Nós fizemos algumas alterações nela e o resultado");
                sender.sendMessage(ChatColor.RED + "final ficou vazio, tente não usar código de cores.");
                return;
            }

            if(event.getQa() == null)
                event.setQa(new QA());
            event.getQa().setAnswers(answers);
            sender.sendMessage(ChatColor.GREEN + "Resposta do Quiz alterada, agora é:");
            sender.sendMessage(ChatColor.GREEN + Arrays.toString(answers).replace("[", "").replace("]", ""));
        });
    }
}
