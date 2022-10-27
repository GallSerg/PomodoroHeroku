package telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class BeginCommand extends ServiceCommand {
    public static boolean isStart = false;
    public BeginCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        sendAnswer(absSender, chat.getId(),
                "Пожалуйста, введите количество дел или '/help' для вызова помощи");
        isStart = true;
    }
}
