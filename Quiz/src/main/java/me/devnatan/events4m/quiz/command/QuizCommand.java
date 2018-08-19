package me.devnatan.events4m.quiz.command;

import me.devnatan.events4m.quiz.Event;
import me.devnatan.events4m.quiz.Quiz;
import me.devnatan.events4m.quiz.argument.Argument;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class QuizCommand extends Command {

    public QuizCommand(Argument[] arguments) {
        super(arguments);
    }

    public void execute(CommandSender sender, String[] args) {
        Quiz quiz = Quiz.getInstance();
        Event event = quiz.getEvent();
        if(!event.isStarted()) {
            sender.sendMessage(ChatColor.RED + "O evento quiz ainda não começou.");
            return;
        }

        sender.sendMessage(ChatColor.GREEN + "  Evento " + ChatColor.BOLD + "QUIZ");
        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.YELLOW + "  Pergunta: " + ChatColor.RESET + event.getQa().getQuestion());
        sender.sendMessage(ChatColor.YELLOW + "  Resposta: " + ChatColor.RESET + "-/-");
    }

}
