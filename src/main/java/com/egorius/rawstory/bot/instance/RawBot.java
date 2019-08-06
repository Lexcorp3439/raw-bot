package com.egorius.rawstory.bot.instance;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.egorius.rawstory.bot.connect.Connector;
import com.egorius.rawstory.bot.connect.RequestRunner;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

import com.egorius.rawstory.bot.handler.Action;
import com.egorius.rawstory.bot.handler.CommandsHandler;
import com.egorius.rawstory.bot.handler.MessageHandler;
import com.egorius.rawstory.bot.handler.Action.Act;

public class RawBot extends TelegramLongPollingBot {
    private static final String BOT_NAME = "Raw Story Bot";
    private static final String BOT_USERNAME = "raw_story_bot";
    private static final String BOT_TOKEN = "876212460:AAFXA1t7epiicYunoWbnZnbvxwMaNhX4PCg";
    private static final String TAG = "HandBot message";
    private static final String VERSION = "1.0.4";

    private Map<Long, MessageHandler> handlers = new HashMap<>();

    private final static String password = "admin";

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        long chatID = message.getChatId();
        Action action = commands(message);

        if (action.act == Act.Message
                || (action.act == Act.Photo && action.messages.length == 1)) {
            sendMsg(action.messages, chatID);
        }

        if (action.act == Act.Photo && action.messages.length != 1) {
            sendPhoto(action.messages, chatID);
        }
    }

    private void sendMsg(String[] text, Long id) {
        SendMessage sendMessage = new SendMessage();
        if (handlers.containsKey(id)) {
            sendMessage.setReplyMarkup(handlers.get(id).replyKeyboard);
        }
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

    private void sendPhoto(String[] ids, long chat_id) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chat_id);
        System.out.println(Arrays.toString(ids));
        for (int i = 0; i < ids.length - 1; i++) {
            sendPhoto.setPhoto(ids[i]);

            if (i == ids.length - 2) {
                sendPhoto.setCaption(ids[ids.length - 1]);
            }
            try {
                execute(sendPhoto); // Call method to send the photo with caption
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private Action commands(Message msg) {
        String txt = "";
        if (msg.hasText()) {
            txt = msg.getText();
        }
        boolean a = handlers.containsKey(msg.getChatId());
        switch (txt) {
            case Messages.Commands.start:
                return CommandsHandler.start(getBotUsername());
            case Messages.Commands.help:
                return CommandsHandler.help();
            case Messages.Commands.auth + password:
                if (!a) {
                    handlers.put(msg.getChatId(), new MessageHandler(msg.getChatId()));
                    Connector.putChatId(msg.getChatId());
                }
                return CommandsHandler.auth(a);
        }
        if (a) {
            MessageHandler handler = handlers.get(msg.getChatId());
            switch (txt) {
                case Messages.Commands.add:
                    return CommandsHandler.add(handler, msg);
                case Messages.Commands.drop:
                    return CommandsHandler.drop(handler);
                case Messages.Commands.bot_version:
                    return CommandsHandler.bot_version(VERSION);
                default:
                    return handler.processing(msg);
            }
        } else {
            return CommandsHandler.noAuth();
        }
    }

    public String getBotUsername() {
        return BOT_USERNAME;
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }

}
