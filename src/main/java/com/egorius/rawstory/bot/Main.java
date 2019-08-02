package com.egorius.rawstory.bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

    private static final String PROXY_PORT = "1080";
    private static final String PROXY_ADDRESS = "178.197.248.213";


    public static void main(String[] args) {
        System.getProperties().put( "proxySet", "true" );
        System.getProperties().put( "socksProxyHost", PROXY_ADDRESS );
        System.getProperties().put( "socksProxyPort", PROXY_PORT );

        System.out.println("Есть контакт");

        ApiContextInitializer.init();

        System.out.println("Есть контакт");

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        System.out.println("победа");
        try {
            telegramBotsApi.registerBot(new RawBot());
            System.out.println("ПОБЕДА");

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
