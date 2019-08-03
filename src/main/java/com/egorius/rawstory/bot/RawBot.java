package com.egorius.rawstory.bot;

import java.util.HashMap;
import java.util.Map;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

import com.egorius.rawstory.bot.handler.MessageHandler;
import com.egorius.rawstory.bot.handler.Messages;

public class RawBot extends TelegramLongPollingBot {
    private static final String BOT_NAME = "Raw Story Bot";
    private static final String BOT_USERNAME = "raw_story_bot";
    private static final String BOT_TOKEN = "876212460:AAFXA1t7epiicYunoWbnZnbvxwMaNhX4PCg";
    private static final String TAG = "HandBot message";
    private static final String VERSION = "1.0.4";

    private static final String testURL = "http://192.168.1.251:8080/blog/add";

    private Map<Long, MessageHandler> handlers = new HashMap<>();

    private final String password = "admin";

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage();
        String[] text;

        text = commands(message);

        if (handlers.containsKey(message.getChatId())) {
            sendMessage.setReplyMarkup(handlers.get(message.getChatId()).replyKeyboard);
        }

        sendMsg(sendMessage, text, message.getChatId());
    }

    private void sendMsg(SendMessage sendMessage, String[] text, Long id) {
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(id);
        for (String t : text) {
            sendMessage.setText(t);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                BotLogger.error("Could not send message", TAG, e);
            }
        }
    }

    private String[] commands(Message msg) {
        String txt = "";
        if (msg.hasText()) {
            txt = msg.getText();
        }
        boolean a = handlers.containsKey(msg.getChatId());
        switch (txt) {
            case Messages.start:
                return new String[]{"Привет! Я - " + getBotUsername() + "! \n" +
                        "Чтобы узнать мои возможности, введи команду /help"};
            case Messages.help:
                return new String[]{
                        "" +
                                "/auth + <кодовое слово> (без пробела) - авторизация пользователя\n" +
                                "Методы, доступные только после авторизации: \n" +
                                "/add - создать новый объект\n" +
                                "/drop - сбросить конструктор\n" +
                                "/version - узнать версию бота"
                };
            case Messages.auth + password:
                if (!a) {
                    handlers.put(msg.getChatId(), new MessageHandler(msg.getChatId()));
                    return new String[]{"Авторизация прошла успешно!"};
                } else {
                    return new String[]{"Вы уже авторизованы"};
                }
        }
        if (a) {
            switch (txt) {
                case Messages.add:
                    if (handlers.get(msg.getChatId()).start) {
                        return new String[]{"Конструктор уже запущен. Для выхода завершите создание объекта или введите команду /drop"};
                    } else {
                        return handlers.get(msg.getChatId()).processing(msg);
                    }
                case Messages.drop:
                    handlers.get(msg.getChatId()).drop();
                    return new String[]{"Вы сбросили конструктор"};
                case Messages.bot_version:
                    return new String[]{"Версия бота: " + VERSION};
                default:
                    return handlers.get(msg.getChatId()).processing(msg);
            }
        } else {
            return new String[]{"Для использования всех функций необходимо авторизоваться. " +
                    "Введите /help для подробной информации"};
        }
    }

    public String getBotUsername() {
        return BOT_USERNAME;
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }

}
