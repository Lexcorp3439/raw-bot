package com.egorius.rawstory.bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.egorius.rawstory.bot.connect.Properties;
import com.egorius.rawstory.bot.instance.RawBot;

public class Main {

    public static void main(String[] args) {
        Properties.setProxy();

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new RawBot());
            System.out.println("Bot start!!!");

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
