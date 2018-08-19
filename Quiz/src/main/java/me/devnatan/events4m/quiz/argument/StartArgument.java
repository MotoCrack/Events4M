package me.devnatan.events4m.quiz.argument;

import me.devnatan.events4m.quiz.Event;
import me.devnatan.events4m.quiz.QA;
import me.devnatan.events4m.quiz.Quiz;
import me.devnatan.events4m.quiz.util.CommandUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartArgument extends Argument {

    public StartArgument() {
        super("iniciar", 0);
        consoleExecutable = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player && !CommandUtil.canContinue(sender, "events4m.quiz." + getName().toLowerCase())) {
            return;
        }

        Quiz quiz = Quiz.getInstance();
        Event event = quiz.getEvent();
        if(event.isStarted()) {
            sender.sendMessage(ChatColor.RED + "O evento quiz já começou.");
            return;
        }

        boolean ignoreQuestion = CommandUtil.containsIn(args, "-q");
        boolean ignoreAnswers = CommandUtil.containsIn(args, "-a");

        if(event.getQa().getAnswers() == null && !ignoreQuestion) {
            sender.sendMessage(ChatColor.RED + "A pergunta do quiz ainda não foi definida.");
            sender.sendMessage(ChatColor.RED + "Use [/quiz pergunta <...>] para defini-la.");
            sender.sendMessage(ChatColor.RED + "Adicione [-g] aos argumentos para ignorar isto.");
            sender.sendMessage(ChatColor.RED + "Se você ignorar uma pergunta será gerada automáticamente.");
            return;
        }

        if(event.getQa().getAnswers() == null && !ignoreAnswers) {
            sender.sendMessage(ChatColor.RED + "A resposta do quiz ainda não foi definida.");
            sender.sendMessage(ChatColor.RED + "Use [/quiz resposta <...>] para defini-la.");
            sender.sendMessage(ChatColor.RED + "Adicione [-a] aos argumentos para ignorar isto.");
            sender.sendMessage(ChatColor.RED + "Se você ignorar uma resposta será gerada automáticamente.");
            return;
        }

        QA qa = event.getQa();
        if(qa == null || ignoreQuestion || ignoreAnswers) {
            event.setQa(quiz.random());
            sender.sendMessage(ChatColor.YELLOW + "Usando pergunta e resposta aleatórias.");
        }

        event.start();
    }
}
