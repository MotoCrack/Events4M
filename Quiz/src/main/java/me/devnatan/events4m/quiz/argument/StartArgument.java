package me.devnatan.events4m.quiz.argument;

import me.devnatan.events4m.quiz.Quiz;
import me.devnatan.events4m.quiz.event.Event;
import me.devnatan.events4m.quiz.event.QA;
import me.devnatan.events4m.quiz.util.AnyUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class StartArgument extends Argument {

    public StartArgument() {
        super("iniciar", 0);
        consoleExecutable = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        AnyUtil.ifPermissioned(sender, "events4m.quiz." + getName().toLowerCase(), () -> {
            Quiz quiz = Quiz.getInstance();
            Event event = quiz.getEvent();
            if(event.isStarted()) {
                sender.sendMessage(ChatColor.RED + "O evento quiz já começou.");
                return;
            }

            boolean ignoreQuestion = AnyUtil.containsIn(args, "-q");
            boolean ignoreAnswers = AnyUtil.containsIn(args, "-a");
            QA qa = event.getQa();

            if((qa == null || event.getQa().getQuestion() == null) && !ignoreQuestion) {
                sender.sendMessage(ChatColor.RED + "A pergunta do quiz ainda não foi definida.");
                sender.sendMessage(ChatColor.RED + "Use [/quiz pergunta <...>] para defini-la.");
                sender.sendMessage(ChatColor.RED + "Adicione [-q] aos argumentos para ignorar isto.");
                sender.sendMessage(ChatColor.RED + "Se você ignorar uma pergunta será gerada automáticamente.");
                return;
            }

            if((qa == null || event.getQa().getAnswers() == null) && !ignoreAnswers) {
                sender.sendMessage(ChatColor.RED + "A resposta do quiz ainda não foi definida.");
                sender.sendMessage(ChatColor.RED + "Use [/quiz resposta <...>] para defini-la.");
                sender.sendMessage(ChatColor.RED + "Adicione [-a] aos argumentos para ignorar isto.");
                sender.sendMessage(ChatColor.RED + "Se você ignorar uma resposta será gerada automáticamente.");
                return;
            }

            if(qa == null || ignoreQuestion || ignoreAnswers) {
                event.setQa(quiz.random());
                sender.sendMessage(ChatColor.YELLOW + "Usando pergunta e resposta aleatórias.");
            }

            sender.sendMessage(ChatColor.YELLOW + "Iniciando evento Quiz...");
            event.start();
        });
    }
}
