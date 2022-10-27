package telegram;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegram.commands.BeginCommand;
import telegram.commands.HelpCommand;

import java.util.concurrent.TimeUnit;

public class MyTelegramBot extends TelegramLongPollingCommandBot {
    public static final String BOT_TOKEN="5725099911:AAErRLzhwt2iL4hxXSS-E1B3-7r48WZrp9M";
    public static final String BOT_USERNAME= "PomodoroNet_bot";
    public static String chatId; //public static final String CHAT_ID= "1749069726";


    public static final int WORK = 10;
    public static final int BREAK = 5;

    public MyTelegramBot() throws TelegramApiException{
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            botsApi.registerBot(this);
            register(new BeginCommand("begin", "Начнем"));
            register(new HelpCommand("help", "Помощь"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        chatId = update.getMessage().getChatId().toString();
        if (update.hasMessage() && isNumeric(update.getMessage().getText()) && BeginCommand.isStart) {
            BeginCommand.isStart = false;
            String message;
            int count = Integer.parseInt(update.getMessage().getText()); // Количество дел
            for (int i = 1; i <= count; i++) {
                message = "Задача №" + i + "началась";
                sendMessage(message);
                try {
                    TimeUnit.SECONDS.sleep(WORK);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                message = "Задача №" + i + "завершилась";
                sendMessage(message);
                if (count - i != 0) {
                    message = "Время отдыхать!";
                    sendMessage(message);
                    try {
                        TimeUnit.SECONDS.sleep(BREAK);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    message = "Время работать!";
                    sendMessage(message);

                }
            }

        } else {
            String message = "Я не понимаю";
            sendMessage(message);
        }
    }

    private void sendMessage(String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static boolean isNumeric(String strNum){
        if (strNum == null) {
            return false;
        }
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
