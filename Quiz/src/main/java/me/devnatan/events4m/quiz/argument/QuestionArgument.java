package me.devnatan.events4m.quiz.argument;

import me.devnatan.events4m.quiz.Quiz;
import me.devnatan.events4m.quiz.event.Event;
import me.devnatan.events4m.quiz.event.QA;
import me.devnatan.events4m.quiz.util.AnyUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class QuestionArgument extends Argument {

    public QuestionArgument() {
        super("pergunta", 0);
        consoleExecutable = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        AnyUtil.ifPermissioned(sender, "events4m.quiz." + getName().toLowerCase(), () -> {
            if(args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Você quer alterar a pergunta do evento?");
                sender.sendMessage(ChatColor.RED + "Se sim, use /quiz pergunta [...]");
                return;
            }

            Quiz quiz = Quiz.getInstance();
            Event event = quiz.getEvent();
            String question = ChatColor.stripColor(AnyUtil.fromArgs(args));
            if(question.trim().length() == 0) {
                sender.sendMessage(ChatColor.RED + "Há algo de errado nesta pergunta..");
                sender.sendMessage(ChatColor.RED + "Nós fizemos algumas alterações nela e o resultado");
                sender.sendMessage(ChatColor.RED + "final ficou vazio, tente não usar código de cores.");
                return;
            }

            if(event.getQa() == null)
                event.setQa(new QA());
            event.getQa().setQuestion(question);
            sender.sendMessage(ChatColor.GREEN + "Pergunta do Quiz alterada, agora é:");
            sender.sendMessage(ChatColor.GREEN + event.getQa().getQuestion());
        });
    }
}
